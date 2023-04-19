package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "region")
@Entity
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name_uz")
    private String name_uz;
    @Column(name = "name_ru")
    private String name_ru;
    @Column(name = "name_en")
    private String name_en;
    @Column(name = "visible")
    private Boolean visible = Boolean.FALSE;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
