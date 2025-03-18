package com.jplumi.travel_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tb_trip")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private LocalDate date;

    @ManyToOne
    @JoinColumn( name = "driver_id", foreignKey = @ForeignKey(name = "fk_trip_driver") )
    private Driver driver;

    @ManyToOne
    @JoinColumn( name = "route_id", foreignKey = @ForeignKey(name = "fk_trip_route") )
    private Route route;
}
