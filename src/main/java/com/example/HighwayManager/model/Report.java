package com.example.HighwayManager.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
}
