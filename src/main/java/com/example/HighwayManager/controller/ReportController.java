package com.example.HighwayManager.controller;

import com.example.HighwayManager.model.Event;
import com.example.HighwayManager.model.Report;
import com.example.HighwayManager.model.User;
import com.example.HighwayManager.service.ReportService;
import com.example.HighwayManager.util.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ReportController {

    private final ReportService reportService;
    private final EntityValidator entityValidator;

    @Autowired
    public ReportController(ReportService reportService, EntityValidator entityValidator) {
        this.reportService = reportService;
        this.entityValidator = entityValidator;
    }

    /**
     * Create - Add a new report
     * @param reportBody as an object report
     * @return The report object saved
     */
    @PostMapping("/report")
    public Report createReport(@RequestBody Report reportBody) {
        //Verify if the event exist
        User bodyUser = reportBody.getAuthor();
        entityValidator.validateUser(bodyUser.getId());

        //Verify if the event exist
        Event bodyEvent = reportBody.getEvent();
        entityValidator.validateEvent(bodyEvent.getId());

        return reportService.saveReport(reportBody);
    }

    /**
     * Read - Get one report
     * @param id The id of the report
     * @return report || null
     */
    @GetMapping("/report/{id}")
    public Report getReport(@PathVariable long id) {
        Optional<Report> report = reportService.getReportById(id);
        return report.orElse(null);
    }

    /**
     * Read - Get all reports
     * @return - An iterable object of reports
     */
    @GetMapping("/report")
    public Iterable<Report> getAllReports() {
        return reportService.getAllReports();
    }

    /**
     * Patch - Update an existing report
     * @param id - The id of the report to update
     * @param reportBody - The report object to update
     * @return report || null - The report object updated
     */
    @PatchMapping("/report/{id}")
    public Report updateReport(@PathVariable long id, @RequestBody Report reportBody) {
        Optional<Report> reportInDatabase = reportService.getReportById(id);
        if (reportInDatabase.isPresent()) {
            Report reportToUpdate = reportInDatabase.get();

            User authorBody = reportBody.getAuthor();
            if (authorBody != null && authorBody.getId() != null) {
                //Verify if the user is existing
                entityValidator.validateUser(authorBody.getId());
                reportToUpdate.setAuthor(authorBody);
            }

            Event eventBody = reportBody.getEvent();
            if (eventBody != null && eventBody.getId() != null) {
                //Verify if the event is existing
                entityValidator.validateEvent(eventBody.getId());
                reportToUpdate.setEvent(eventBody);
            }

            return reportService.saveReport(reportToUpdate);
        } else {
            return null;
        }
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
