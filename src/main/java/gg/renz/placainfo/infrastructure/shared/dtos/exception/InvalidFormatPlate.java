package gg.renz.placainfo.infrastructure.shared.dtos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidFormatPlate extends IllegalArgumentException {
    public InvalidFormatPlate(String plateNumber) {
        super(plateNumber);
    }
}
