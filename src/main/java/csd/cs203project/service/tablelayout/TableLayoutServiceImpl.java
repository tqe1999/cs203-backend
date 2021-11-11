package csd.cs203project.service.tablelayout;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import csd.cs203project.model.TableLayout;
import csd.cs203project.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TableLayoutServiceImpl implements TableLayoutService {
    private UserRepository userRepository;

    
    
    @Autowired
    public TableLayoutServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public ArrayList<ArrayList<HashMap<String, Double>>> generateTableLayout(TableLayout tableLayout) {
        int widthOfShop = tableLayout.getWidthOfShop();
        int heightOfShop = tableLayout.getHeightOfShop();
        int widthOfTable = tableLayout.getWidthOfTable();
        int heightOfTable = tableLayout.getHeightOfTable();


        int multiplierSpacing = 2;
        int numOfWidths = widthOfShop / widthOfTable;

        int numOfHeights = heightOfShop / heightOfTable;

        ArrayList<ArrayList<HashMap<String, Double>>> resultantList = new ArrayList<>();


        for (int i = 0; i < numOfHeights; i++) { 
            

            ArrayList<HashMap<String, Double>> innerList = new ArrayList<>();
            for (int j = 0; j < numOfWidths; j++) { 
                HashMap<String, Double> hashInnerMap = new HashMap<>();

                hashInnerMap.put("x", j + 1.00);

                if (i % multiplierSpacing != 0 || j % multiplierSpacing != 0) { 
                    hashInnerMap.put("y", 0.00);
                } else if ((i % multiplierSpacing == 0 && j % multiplierSpacing == 0)) { 
                    String number = widthOfTable + "." + heightOfTable;
                    hashInnerMap.put("y", Double.valueOf(number));
                    
                    
                }

                innerList.add(hashInnerMap);

            }
            resultantList.add(innerList);

            if (numOfWidths == 0 || numOfHeights == 0) {
                return null;
            }
        }


        return resultantList; 
    }
}