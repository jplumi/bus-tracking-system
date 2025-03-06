package com.jplumi.travel_management.model.routestop;

import com.jplumi.travel_management.model.Route;
import com.jplumi.travel_management.model.Stop;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_route_stop")
@Data
public class RouteStop {
    @EmbeddedId
    private RouteStopPK id;

    @ManyToOne
    @MapsId("routeId")
    @JoinColumn(name = "route_id", foreignKey = @ForeignKey(name = "fk_routestop_route"))
    private Route route;

    @ManyToOne
    @MapsId("stopId")
    @JoinColumn(name = "stop_id", foreignKey = @ForeignKey(name = "fk_routestop_stop"))
    private Stop stop;

    private int stopNumber;
}
