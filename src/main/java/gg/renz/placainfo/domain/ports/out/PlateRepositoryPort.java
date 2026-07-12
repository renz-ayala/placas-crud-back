package gg.renz.placainfo.domain.ports.out;

import gg.renz.placainfo.domain.model.Plate;

import java.util.Optional;

public interface PlateRepositoryPort {
    Optional<Plate> findByNumber(String number);
}
