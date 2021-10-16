package csd.cs203project.controller.measures;

import csd.cs203project.model.Measures;
import csd.cs203project.service.measures.MeasuresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MeasuresController {
    private MeasuresService measuresService;

    @Autowired
    public MeasuresController(MeasuresService measuresService) {
        this.measuresService = measuresService;
    }

    @GetMapping("/measures")
    public List<Measures> findAllMeasures() {
        System.out.println("test");
        return measuresService.findAllMeasures();
    }

    @PostMapping("/measures")
    public Measures addMeasures(@RequestBody Measures measures) {
        System.out.println(measures);
        return measuresService.addMeasures(measures);
    }

    @GetMapping("/measures/{shopType}")
    public Measures getMeasures(@PathVariable("shopType") String shopType){
        return measuresService.findByTypeOfShop(shopType);
    }

}
