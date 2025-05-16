package com.citizen.engagement_system_be.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "agencies")
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
    private String contactEmail;
    private String contactPhone;
}
