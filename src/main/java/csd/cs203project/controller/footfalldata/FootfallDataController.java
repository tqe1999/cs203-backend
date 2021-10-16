package csd.cs203project.controller.footfalldata;

import csd.cs203project.model.FootfallData;
import csd.cs203project.service.footfalldata.FootfallDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class FootfallDataController {
    private FootfallDataService footfallDataService;

    @Autowired
    public FootfallDataController (FootfallDataService footfallDataService) {
        this.footfallDataService = footfallDataService;
    }

    @PostMapping("/footfallData")
    public void reloadFootfallData () {
        footfallDataService.reloadFootfallData();
    }

    @GetMapping("/footfallData")
    public String getJsonResponse() {
        System.out.println("yea executed");
        return footfallDataService.getJsonResponse();
    }
}
