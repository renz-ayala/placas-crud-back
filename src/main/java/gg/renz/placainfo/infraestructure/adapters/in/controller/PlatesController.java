package gg.renz.placainfo.infraestructure.adapters.in.controller;

import gg.renz.placainfo.domain.ports.in.GetPlateReport;
import gg.renz.placainfo.infraestructure.dtos.http.PlateRequest;
import gg.renz.placainfo.infraestructure.dtos.http.PlateResponse;
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
        PlateResponse response = getPlateInformation.execute(data.numPlaca());
        return switch (response.status()) {
            case OK -> ResponseEntity.ok(response);
            case NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            case BAD_REQUEST -> ResponseEntity.badRequest().body(response);
            case INTERNAL_SERVER_ERROR -> ResponseEntity.internalServerError().body(response);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        };
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
