package gg.renz.placainfo.infrastructure.adapters.out.report;

import gg.renz.placainfo.domain.model.Plate;
import gg.renz.placainfo.domain.ports.out.PlateRepositoryPort;
import gg.renz.placainfo.domain.ports.out.ReportPort;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportAdapter implements ReportPort {
    private final PlateRepositoryPort plateRepositoryPort;

    @Override
    public byte[] getPlateReport(String plateNumber) throws Exception {
        Optional<Plate> response = plateRepositoryPort.findByNumber(plateNumber);
        if (response != null && response.isPresent() ) {
            Plate presentPlate = response.get();
            Map<String, Object> parameters = getStringObjectMap(presentPlate);
            InputStream reportStream = getClass().getClassLoader().getResourceAsStream("report/placa.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
        throw new Exception();
    }

    private static Map<String, Object> getStringObjectMap(Plate plate) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("numPlaca", plate.getPlateNumber());
        parameters.put("marca", plate.getMake());
        parameters.put("modelo", plate.getModel());
        parameters.put("color", plate.getColor());
        parameters.put("propietario", plate.getOwnerId());
        parameters.put("observaciones", plate.getObservations());
        parameters.put("estado", plate.getState());
        parameters.put("anioFabricacion", plate.getManufactureYear());
        parameters.put("fechaRegistro", plate.getRegistrationDate());
        return parameters;
    }
}
