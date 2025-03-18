package com.jplumi.travel_management.model.routestop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jplumi.travel_management.model.Route;
import com.jplumi.travel_management.model.Stop;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "tb_route_stop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteStop {
    @EmbeddedId
    @JsonIgnore
    private RouteStopPK id;

    @ManyToOne
    @MapsId("routeId")
    @JoinColumn(name = "route_id", foreignKey = @ForeignKey(name = "fk_routestop_route"))
    @JsonIgnore
    private Route route;

    @ManyToOne
    @MapsId("stopId")
    @JoinColumn(name = "stop_id", foreignKey = @ForeignKey(name = "fk_routestop_stop"))
    private Stop stop;

    private int stopNumber;
    private LocalTime expectedTime;
}
