package gg.renz.placainfo.infraestructure;

import gg.renz.placainfo.infraestructure.shared.dtos.exception.InvalidFormatPlate;
import gg.renz.placainfo.infraestructure.shared.dtos.http.PlateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFormatPlate.class)
    public ResponseEntity<PlateResponse> handlePlateWithInvalidFormat(InvalidFormatPlate e) {
        var errorResponse = PlateResponse.error(
                e.getMessage(),
                "El formato de la placa es inválido",
                HttpStatus.BAD_REQUEST
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
