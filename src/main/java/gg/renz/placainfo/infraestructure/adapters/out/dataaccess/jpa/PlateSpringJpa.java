package gg.renz.placainfo.infraestructure.adapters.out.dataaccess.jpa;

import gg.renz.placainfo.infraestructure.shared.dtos.repository.PlateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlateSpringJpa extends JpaRepository<PlateEntity, String>{
    Optional<PlateEntity> findByPlateNumber(String number);
}
