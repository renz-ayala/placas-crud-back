package gg.renz.placainfo.domain.ports.in;

import gg.renz.placainfo.infraestructure.dtos.http.PlateResponse;

public interface GetPlateInformation {
    PlateResponse execute(String numero);
}
