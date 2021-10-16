package csd.cs203project.service.measures;

import csd.cs203project.model.Measures;

import java.util.List;

public interface MeasuresService {

    Measures addMeasures(Measures measures);

    Measures findByTypeOfShop(String typeOfShop);

    List<String> getChangeInMeasures(Measures oldMeasures, Measures newMeasures);

    List<Measures> findAllMeasures();
}
