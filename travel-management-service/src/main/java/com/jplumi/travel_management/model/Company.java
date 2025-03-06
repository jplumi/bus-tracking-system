package com.jplumi.travel_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_company")
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
}
