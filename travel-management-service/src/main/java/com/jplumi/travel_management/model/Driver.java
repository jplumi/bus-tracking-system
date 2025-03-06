package com.jplumi.travel_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_driver")
@Data
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Long companyId;
}
