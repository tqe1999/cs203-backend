package csd.cs203project.service.footfalldata;

import csd.cs203project.model.FootfallData;

import java.util.List;

public interface FootfallDataService {
    /**
     * Returns list of FootfallData objects from db
     * @return List
     */
    List<FootfallData> listFootfallData();

    /** Reloads footfall data db by calling API */
    void reloadFootfallData();

    /**
     * Gets JSON response
     * @return String
     */
    String getJsonResponse();
}
