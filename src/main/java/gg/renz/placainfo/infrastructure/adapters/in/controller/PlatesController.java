package gg.renz.placainfo.infrastructure.adapters.in.controller;

import gg.renz.placainfo.domain.model.Plate;
import gg.renz.placainfo.domain.ports.in.GetPlateReport;
import gg.renz.placainfo.infrastructure.shared.dtos.http.PlateRequest;
import gg.renz.placainfo.infrastructure.shared.dtos.http.PlateResponse;
import gg.renz.placainfo.domain.ports.out.CaptchaPort;
import gg.renz.placainfo.domain.ports.in.GetPlateInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/placas")
@RequiredArgsConstructor
public class PlatesController {
    private final GetPlateInformation getPlateInformation;
    private final GetPlateReport getPlateReport;
    private final CaptchaPort captchaPort;

    @PostMapping(value = "/verificar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlateResponse> verify(@RequestBody PlateRequest data) {
        if(!captchaPort.verifyCaptcha(data.idTransaction())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    PlateResponse.error(data.numPlaca(), "El captcha es inválido/expirado", HttpStatus.BAD_REQUEST)
            );
        }

        try {
            Plate plate = getPlateInformation.execute(data.numPlaca());

            if ( plate == null) {
                var notFoundResponse = PlateResponse.error(
                        data.numPlaca(),
                        "La placa no existe en los registros",
                        HttpStatus.NOT_FOUND
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundResponse);
            }

            var successResponse = new PlateResponse(
                    plate.getPlateNumber(),
                    plate.getMake(),
                    plate.getModel(),
                    plate.getColor(),
                    plate.getManufactureYear(),
                    plate.getOwnerId(),
                    plate.getState(),
                    plate.getRegistrationDate(),
                    plate.getObservations(),
                    "Operación correcta",
                    HttpStatus.OK
            );

            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            log.error("Error crítico al consultar placa {}", data.numPlaca(), e);
            var errorServer = PlateResponse.error(
                    data.numPlaca(),
                    "Hubo un error en la operación",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
            return ResponseEntity.internalServerError().body(errorServer);
        }
    }

    @PostMapping(value = "/obtener-reporte", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getReport(@RequestBody PlateRequest data){
        try{
            byte[] response = getPlateReport.execute(data.numPlaca());
            return ResponseEntity.ok(response);
        }catch(Exception e){
            log.error("Error en el reporte", e);
            return ResponseEntity.internalServerError().build();
        }

    }
}
