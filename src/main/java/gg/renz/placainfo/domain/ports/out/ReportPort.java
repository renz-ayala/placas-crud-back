package gg.renz.placainfo.domain.ports.out;

public interface ReportPort {
    byte[] getPlateReport(String numeroPlaca) throws Exception;
}
