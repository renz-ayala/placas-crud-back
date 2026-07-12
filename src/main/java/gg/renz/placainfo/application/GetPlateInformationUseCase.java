package gg.renz.placainfo.application;

import gg.renz.placainfo.domain.model.Plate;
import gg.renz.placainfo.domain.ports.in.GetPlateInformation;
import gg.renz.placainfo.domain.ports.out.PlateRepositoryPort;
import gg.renz.placainfo.infraestructure.dtos.http.PlateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetPlateInformationUseCase implements GetPlateInformation {
    private final PlateRepositoryPort plateRepositoryPort;

    @Override
    public PlateResponse execute(String numero) {
        String validators = "^[A-Z0-9Ñ]{6,7}$";

        HttpStatus statusOk = HttpStatus.OK;
        HttpStatus statusNotFound = HttpStatus.NOT_FOUND;

        String errorResponse = "La placa no existe en los registros";
        String successResponse = "Operación correcta";

        if (numero == null || !numero.matches(validators)) {
            return PlateResponse.error(
                    numero,
                    "El formato de la placa es inválido",
                    HttpStatus.BAD_REQUEST
            );
        }

        try{
            Optional<Plate> optionalPlate = plateRepositoryPort.findByNumber(numero);

            if (optionalPlate.isEmpty()) {
                return PlateResponse.error(
                        numero,
                        errorResponse,
                        statusNotFound);
            }

            Plate obtainedPlate = optionalPlate.get();

            return new PlateResponse(
                    obtainedPlate.getPlateNumber(),
                    obtainedPlate.getMake(),
                    obtainedPlate.getModel(),
                    obtainedPlate.getColor(),
                    obtainedPlate.getManufactureYear(),
                    obtainedPlate.getOwnerId(),
                    obtainedPlate.getState(),
                    obtainedPlate.getRegistrationDate(),
                    obtainedPlate.getObservations(),
                    successResponse,
                    statusOk
            );

        } catch (Exception e) {
            log.error("Error crítico al consultar placa {}", numero, e);
            return PlateResponse.error(
                    numero,
                    "Hubo un error en la operación",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
