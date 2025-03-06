package com.jplumi.travel_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_stop")
@Data
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Long companyId;
    private String address;
    private Double latitude;
    private Double longitude;
}
