package gg.renz.placainfo.infrastructure.adapters.out.dataaccess;

import gg.renz.placainfo.domain.model.Plate;
import gg.renz.placainfo.domain.ports.out.PlateRepositoryPort;
import gg.renz.placainfo.infrastructure.adapters.out.dataaccess.jpa.PlateSpringJpa;
import gg.renz.placainfo.infrastructure.shared.dtos.repository.PlateEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlateRepositoryAdapter implements PlateRepositoryPort {
    private final PlateSpringJpa plateSpringJpa;

    @Override
    public Optional<Plate> findByNumber(String number) {
        String formattedNumber = number.toUpperCase().trim();
        return plateSpringJpa.findByPlateNumber(formattedNumber)
                .map(PlateRepositoryAdapter::mapper);
    }

    private static Plate mapper(PlateEntity p) {
        var np = new Plate();
        np.setId(p.getId());
        np.setPlateNumber(p.getPlateNumber());
        np.setMake(p.getMake());
        np.setModel(p.getModel());
        np.setColor(p.getColor());
        np.setManufactureYear(p.getManufactureYear());
        np.setOwnerId(p.getOwnerId());
        np.setState(p.getState());
        np.setRegistrationDate(p.getRegistrationDate());
        np.setObservations(p.getObservations());
        return np;
    }
}
