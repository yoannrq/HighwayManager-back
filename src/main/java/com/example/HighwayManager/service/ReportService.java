package com.example.HighwayManager.service;

import com.example.HighwayManager.model.Report;
import com.example.HighwayManager.repository.ReportRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class ReportService {

    private ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Optional<Report> getReportById(final long id) {
        return reportRepository.findById(id);
    }

    public Iterable<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public void deleteReport(final long id) {
        reportRepository.deleteById(id);
    }

    public Report saveReport(final Report report) {
        return reportRepository.save(report);
    }
}
