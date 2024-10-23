package com.example.HighwayManager.dto;

import com.example.HighwayManager.model.Report;
import lombok.Data;

@Data
public class ReportDTO {
    private Long id;
    private Long authorId;
    private String authorName;
    private String authorEmail;
    private Long eventId;
    private String eventTypeName;

    public ReportDTO(Report report) {
        this.id = report.getId();
        if (report.getAuthor() != null) {
            this.authorId = report.getAuthor().getId();
            this.authorName = report.getAuthor().getFirstname() + " " + report.getAuthor().getLastname();
            this.authorEmail = report.getAuthor().getEmail();
        }
        if (report.getEvent() != null) {
            this.eventId = report.getEvent().getId();
            this.eventTypeName = report.getEvent().getType().getName();
        }
    }
}
