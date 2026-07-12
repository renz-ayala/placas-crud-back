package gg.renz.placainfo.application;

import gg.renz.placainfo.domain.ports.in.GetPlateReport;
import gg.renz.placainfo.domain.ports.out.ReportPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPlateReportUseCase implements GetPlateReport {
    private final ReportPort reportPort;

    @Override
    public byte[] execute(String numeroPlaca) throws Exception {
        return reportPort.getPlateReport(numeroPlaca);
    }
}
