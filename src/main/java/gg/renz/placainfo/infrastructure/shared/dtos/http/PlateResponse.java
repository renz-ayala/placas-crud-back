package gg.renz.placainfo.infrastructure.shared.dtos.http;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record PlateResponse(
        String numPlaca,
        String marca,
        String modelo,
        String color,
        Integer anioFabricacion,
        String propietario,
        String estado,
        LocalDateTime fechaRegistro,
        String observaciones,
        String response,
        HttpStatus status
){
    public static PlateResponse error(String numero, String mensaje, HttpStatus status) {
        return new PlateResponse(numero, null, null, null, null, null, null, null, null, mensaje, status);
    }
}
