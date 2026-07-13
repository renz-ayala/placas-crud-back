package gg.renz.placainfo.domain.ports.in;

import gg.renz.placainfo.domain.model.Plate;

public interface GetPlateInformation {
    Plate execute(String number);
}
