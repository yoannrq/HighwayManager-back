package com.example.HighwayManager.controller;

import com.example.HighwayManager.dto.ReportCreationDTO;
import com.example.HighwayManager.dto.ReportDTO;
import com.example.HighwayManager.model.Event;
import com.example.HighwayManager.model.Report;
import com.example.HighwayManager.model.User;
import com.example.HighwayManager.service.EventService;
import com.example.HighwayManager.service.ReportService;
import com.example.HighwayManager.service.UserService;
import com.example.HighwayManager.util.EntityValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ReportController {

    private final ReportService reportService;
    private final EntityValidator entityValidator;
    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public ReportController(ReportService reportService, EntityValidator entityValidator, UserService userService, EventService eventService) {
        this.reportService = reportService;
        this.entityValidator = entityValidator;
        this.userService = userService;
        this.eventService = eventService;
    }

    /**
     * Create - Add a new report
     * @param reportBody as an object report
     * @return The report object saved
     * @throws IllegalArgumentException if user or event is not found
     */
    @PostMapping("/report")
    public ReportDTO createReport(@Valid @RequestBody ReportCreationDTO reportBody) {
        //Verify if the event exist
        entityValidator.validateUser(reportBody.getAuthorId());

        //Verify if the event exist
        entityValidator.validateEvent(reportBody.getEventId());

        Report newReport = new Report();
        User author = userService.getUserById(reportBody.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
        newReport.setAuthor(author);

        Event event = eventService.getEventById(reportBody.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Événement non trouvé"));
        newReport.setEvent(event);

        Report savedReport = reportService.saveReport(newReport);
        return new ReportDTO(savedReport);
    }

    /**
     * Read - Get one report
     * @param id The id of the report
     * @return report
     * @throws IllegalArgumentException if report is not found
     */
    @GetMapping("/report/{id}")
    public ReportDTO getReport(@PathVariable long id) {
        Optional<Report> optionalReport = reportService.getReportById(id);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            return new ReportDTO(report);
        } else {
            throw new IllegalArgumentException("Rapport introuvable");
        }
    }

    /**
     * Read - Get all reports
     * @return - List of reports
     */
    @GetMapping("/report")
    public List<ReportDTO> getAllReports() {
        Iterable<Report> reports = reportService.getAllReports();
        List<ReportDTO> reportDTOs = new ArrayList<>();
        for (Report report : reports) {
            reportDTOs.add(new ReportDTO(report));
        }
        return reportDTOs;
    }

    /**
     * Patch - Update an existing report
     * @param id - The id of the report to update
     * @param reportBody - The Report object containing updated fields
     * @return ReportDTO - The updated report as a DTO
     * @throws IllegalArgumentException if report, user or event is not found
     */
    @PatchMapping("/report/{id}")
    public ReportDTO updateReport(@PathVariable final long id, @Valid @RequestBody ReportCreationDTO reportBody) {
        Optional<Report> optionalReport = reportService.getReportById(id);

        if (optionalReport.isEmpty()) {
            throw new IllegalArgumentException("Rapport introuvable");
        }

        Report reportToUpdate = optionalReport.get();

        if (reportBody.getAuthorId() != null) {
            entityValidator.validateUser(reportBody.getAuthorId());
            User author = userService.getUserById(reportBody.getAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
            reportToUpdate.setAuthor(author);
        }

        if (reportBody.getEventId() != null) {
            entityValidator.validateEvent(reportBody.getEventId());
            Event event = eventService.getEventById(reportBody.getEventId())
                    .orElseThrow(() -> new IllegalArgumentException("Événement introuvable"));
            reportToUpdate.setEvent(event);
        }

        Report savedReport = reportService.saveReport(reportToUpdate);
        return new ReportDTO(savedReport);
    }


    /**
     * Delete - Delete a report
     * @param id - The id of the report to delete
     */
    @DeleteMapping("/report/{id}")
    public void deleteReport(@PathVariable long id) {
        reportService.deleteReport(id);
    }
}
