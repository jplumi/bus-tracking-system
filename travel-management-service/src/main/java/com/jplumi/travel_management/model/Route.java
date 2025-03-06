package com.jplumi.travel_management.model;

import com.jplumi.travel_management.model.routestop.RouteStop;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "tb_route")
@Data
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "route")
    private Set<RouteStop> stops;
}
