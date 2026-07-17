package gg.renz.placainfo.infrastructure;

import gg.renz.placainfo.infrastructure.shared.dtos.exception.InvalidFormatPlate;
import gg.renz.placainfo.infrastructure.shared.dtos.http.PlateResponse;
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
