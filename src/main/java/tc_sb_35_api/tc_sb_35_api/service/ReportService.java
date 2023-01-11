package tc_sb_35_api.tc_sb_35_api.service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import lombok.extern.slf4j.Slf4j;
import tc_sb_35_api.tc_sb_35_api.dto.ReportConverter;
import tc_sb_35_api.tc_sb_35_api.dto.ReportInfoDTO;
import tc_sb_35_api.tc_sb_35_api.dto.ReportTableDTO;
import tc_sb_35_api.tc_sb_35_api.model.Report;
import tc_sb_35_api.tc_sb_35_api.repository.ReportRepository;

@Slf4j
@Service
@EnableScheduling
public class ReportService {
    
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    ReportConverter reportConverter;

    // Method set to be executed 1st day of every month at 01 hours to count monthly emergencies by species and main symptom
    // @Scheduled(fixedRate = 10000) for each 10 seconds (set in miliseconds) for test and demo purposes
    @Scheduled(cron = "0 0 1 1 * ?")
    public boolean countEmergencies() {
        log.info("INTERNAL PROCESS: counting all emergencies");
        if(reportRepository.countEmergencies()) {
            log.info("INTERNAL PROCESS: all emergencies counted");
            return true;
        }
        log.info("INTERNAL PROCESS: error counting all emergencies");
        return false;
    }

    public ReportInfoDTO getReports(int year, int month, Long idSpecies, Long idMainSymptom) {
        List<Report> reports = reportRepository.getReports(year, month, idSpecies, idMainSymptom);
        List<ReportTableDTO> reportsDto =  reports.stream()
            .map(report -> reportConverter.convertToReportTableDto(report)).collect(Collectors.toList());
        ReportInfoDTO reportInfoDto = new ReportInfoDTO();
        reportInfoDto.setReportsDto(reportsDto);
        int numberOfEmergencies = 0;
        for (ReportTableDTO report : reportsDto) {
            numberOfEmergencies += report.getNumberEmergencies();
        }
        reportInfoDto.setNumberOfEmergencies(numberOfEmergencies);
        return reportInfoDto;
    }

    public ICsvBeanWriter getCsv(Writer writer, int year, int month, Long idSpecies, Long idMainSymptom) throws IOException {
        ReportInfoDTO reportsDto = getReports(year, month, idSpecies, idMainSymptom);
        ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);
        String[] headers = {"Id reporte", "Año", "Mes", "Especie", "Síntoma", "Número de emergencias confirmadas", "Fecha de corte"};
        String[] properties = {"idReport", "reportYear", "reportMonth", "species", "mainSymptom", "numberEmergencies", "reportExecutionDate"};
        csvWriter.writeHeader(headers);
        for (ReportTableDTO report : reportsDto.getReportsDto()) {
            csvWriter.write(report, properties);
        }
        return csvWriter;
    }

}
