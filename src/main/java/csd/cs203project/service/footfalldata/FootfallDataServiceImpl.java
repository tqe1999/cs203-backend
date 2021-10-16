package csd.cs203project.service.footfalldata;

import csd.cs203project.model.FootfallData;
import csd.cs203project.model.LastUpdateDate;
import csd.cs203project.repository.footfalldata.FootfallDataRepository;
import csd.cs203project.repository.footfalldata.LastUpdateDateRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class FootfallDataServiceImpl implements FootfallDataService {
    private FootfallDataRepository footfallDataRepository;
    private LastUpdateDateRepository lastUpdateDateRepository;

    @Autowired
    public FootfallDataServiceImpl(FootfallDataRepository footfallDataRepository, LastUpdateDateRepository lastUpdateDateRepository) {
        this.footfallDataRepository = footfallDataRepository;
        this.lastUpdateDateRepository = lastUpdateDateRepository;
    }

    @Override
    public List<FootfallData> listFootfallData() {
        return footfallDataRepository.findAll();
    }

    /**
     * Returns a JSON formatted String that contains:
     * 1. boolean indicating if information is updated
     * 2. the list of footfall data for a year
     * @return
     */
    @Override
    public String getJsonResponse () {
        JSONObject jsonObject = new JSONObject();

        LastUpdateDate lastUpdateDate = lastUpdateDateRepository.findById(1L).orElse(null);
        List<FootfallData> footfallDataList = listFootfallData();
        
        jsonObject.put("isChanged", lastUpdateDate.isChanged());
        jsonObject.put("lastUpdated", lastUpdateDate.getDataLastUpdated());
        jsonObject.put("list", footfallDataList);
        jsonObject.put("averages", calculateAverage(footfallDataList.subList(55, 60)));

        return jsonObject.toString();
    }

    /**
     * If the last updated date from the database matches the one from API, do not call process other data again
     *
     * Else clear database and reload the database with updated data
     */
    @Override
    public void reloadFootfallData () {
        System.out.println("I AM EXECUTED???");
        String dateInDb = lastUpdateDateRepository.findById(1L).map(date -> date.getDataLastUpdated()).orElse(null);

        try {
            String jsonString = parseAPI();
            JSONObject jsonObject = new JSONObject(jsonString);
            String dataLastUpdated = jsonObject.getString("DataLastUpdated");

            if (dataLastUpdated.equals(dateInDb)) {
                System.out.println("ok same");
                LastUpdateDate lastUpdateDate = new LastUpdateDate(1L, dataLastUpdated, false);
                lastUpdateDateRepository.deleteAll();
                lastUpdateDateRepository.save(lastUpdateDate);
                return;
            }

            System.out.println("not same");
            LastUpdateDate lastUpdateDate = new LastUpdateDate(1L, dataLastUpdated, true);
            lastUpdateDateRepository.deleteAll();
            lastUpdateDateRepository.save(lastUpdateDate);

            footfallDataRepository.deleteAll();
            parseFootfallData(jsonObject);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses footfall data from singstat such that only 5 years worth of relevant data is kept
     */
    public void parseFootfallData (JSONObject jsonObject) throws IOException, JSONException {
        System.out.println("i am now parsing footfall data");
        JSONArray level1Array = jsonObject.getJSONArray("Level1");
        JSONArray level2Array = jsonObject.getJSONArray("Level2");
        int length1 = level1Array.length();
        int length2 = level2Array.length();

        //60 iterations of level1Array, then 60, 60, 60, 60 for level2Array
        for (int i = 60; i > 0; i--) {

            //construct FootfallData with month & total
            JSONObject tempObj = level1Array.getJSONObject(length1 - i);
            String month = tempObj.getString("month");
//                String category = tempObj.getString("level_1"); -> should just be total yea
            Double value = Double.parseDouble(tempObj.getString("value"));
            FootfallData footfallData = new FootfallData(month, value);

            //set restaurant value
            tempObj = level2Array.getJSONObject(length2 - 4 * i);
            value = Double.parseDouble(tempObj.getString("value"));
            footfallData.setRestaurants(value);

            //set fast food outlets value
            tempObj = level2Array.getJSONObject(length2 - 4 * i + 1);
            value = Double.parseDouble(tempObj.getString("value"));
            footfallData.setFastFoodOutlets(value);

            //set caterers value
            tempObj = level2Array.getJSONObject(length2 - 4 * i + 2);
            value = Double.parseDouble(tempObj.getString("value"));
            footfallData.setCaterers(value);

            //set other places value
            tempObj = level2Array.getJSONObject(length2 - 4 * i + 3);
            value = Double.parseDouble(tempObj.getString("value"));
            footfallData.setOtherPlaces(value);

            //still need to set restriction type...
            //nope no need...

            footfallDataRepository.save(footfallData);
        }
    }

    /**
     * Reads data from singstat and returns string
     *
     * @return String
     * @throws IOException
     */
    public String parseAPI () throws IOException {
        URL urlForGetRequest = new URL("https://www.tablebuilder.singstat.gov.sg/publicfacing/api/json/title/17038.json");
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            } in .close();
            // print result
//            System.out.println("JSON String Result " + response.toString());
            return response.toString();
        }
        return null;
    }

    public List<Double> calculateAverage(List<FootfallData> data) {
        Double restaurant = 0.0, fastFoodOutlet = 0.0, caterer = 0.0, other = 0.0;

        for (int i = 0; i < 5; i++) {
            restaurant += data.get(i).getRestaurants() / 5;
            fastFoodOutlet += data.get(i).getFastFoodOutlets() / 5;
            caterer += data.get(i).getCaterers() / 5;
            other += data.get(i).getOtherPlaces() / 5;
        }

        List<Double> averages = new ArrayList<>();
        averages.add(restaurant);
        averages.add(fastFoodOutlet);
        averages.add(caterer);
        averages.add(other);

        return averages;
    }
}
