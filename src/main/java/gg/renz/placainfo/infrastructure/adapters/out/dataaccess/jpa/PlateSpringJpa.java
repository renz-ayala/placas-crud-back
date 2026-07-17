package gg.renz.placainfo.infrastructure.adapters.out.dataaccess.jpa;

import gg.renz.placainfo.infrastructure.shared.dtos.repository.PlateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlateSpringJpa extends JpaRepository<PlateEntity, String>{
    Optional<PlateEntity> findByPlateNumber(String number);
}
