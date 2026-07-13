package gg.renz.placainfo.infraestructure.adapters.out.web;

import gg.renz.placainfo.domain.ports.out.FormatPort;
import gg.renz.placainfo.infraestructure.shared.dtos.exception.InvalidFormatPlate;
import org.springframework.stereotype.Service;

@Service
public class FormatAdapter implements FormatPort {

    @Override
    public void plateValidation(String number) {
        String validators = "^[A-Z0-9Ñ]{6,7}$";
        if (number == null || number.isBlank()|| !number.matches(validators)) {
            throw new InvalidFormatPlate(number);
        }
    }
}
