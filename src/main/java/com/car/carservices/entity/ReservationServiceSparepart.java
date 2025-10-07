package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reservation_service_sparepart")
@Getter @Setter
public class ReservationServiceSparepart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_service_sparepart_id")
    private Long reservationServiceSparepartId;

    @Column(name = "part_name", nullable = false)
    private String partName;

    @Column(name = "qty", nullable = false)
    private Integer qty = 1;

    // NOTE: keep as plain FK columns to avoid touching parent entities
    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    @Column(name = "spareparts_id", nullable = false)
    private Long sparepartsId;
}
