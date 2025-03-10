package com.jplumi.travel_management.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_trip")
@Data
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private LocalDateTime startTime;

    @ManyToOne
    @JoinColumn( name = "driver_id", foreignKey = @ForeignKey(name = "fk_trip_driver") )
    private Driver driver;

    @ManyToOne
    @JoinColumn( name = "route_id", foreignKey = @ForeignKey(name = "fk_trip_route") )
    private Route route;
}
