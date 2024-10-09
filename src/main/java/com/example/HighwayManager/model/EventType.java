package com.example.HighwayManager.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "eventtypes")
public class EventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
