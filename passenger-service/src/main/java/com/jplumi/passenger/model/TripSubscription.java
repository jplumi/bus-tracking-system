package com.jplumi.passenger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_trip_subscription")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passenger_id", foreignKey = @ForeignKey(name = "fk_trip_subscription_passenger"))
    @JsonIgnore
    private Passenger passenger;

    private Long tripId;
    private Integer stopNumber;
}
