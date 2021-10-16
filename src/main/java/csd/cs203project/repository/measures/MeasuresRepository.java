package csd.cs203project.repository.measures;

import csd.cs203project.model.Measures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeasuresRepository extends JpaRepository<Measures, Long> {
    List<Measures> findByTypeOfShop(String typeOfShop);
    @Transactional
    void deleteByTypeOfShop(String typeOfShop);

}
