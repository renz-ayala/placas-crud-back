package gg.renz.placainfo.domain.ports.in;

public interface GetPlateReport {
    byte[] execute(String plateNumber) throws Exception;
}
