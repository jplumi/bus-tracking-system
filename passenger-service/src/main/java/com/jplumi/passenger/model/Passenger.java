package com.jplumi.passenger.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_passenger")
@Data
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
}
