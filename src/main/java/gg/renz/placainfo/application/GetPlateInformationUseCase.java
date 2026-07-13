package gg.renz.placainfo.application;

import gg.renz.placainfo.domain.model.Plate;
import gg.renz.placainfo.domain.ports.in.GetPlateInformation;
import gg.renz.placainfo.domain.ports.out.FormatPort;
import gg.renz.placainfo.domain.ports.out.PlateRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetPlateInformationUseCase implements GetPlateInformation {
    private final PlateRepositoryPort plateRepositoryPort;
    private final FormatPort formatPort;

    @Override
    public Plate execute(String number) {
        formatPort.plateValidation(number);

        try{
            Optional<Plate> optionalPlate = plateRepositoryPort.findByNumber(number);
            return optionalPlate.orElse(null);
        } catch (Exception e) {
            log.error("Error crítico al consultar placa {}", number, e);
            return null;
        }
    }
}
