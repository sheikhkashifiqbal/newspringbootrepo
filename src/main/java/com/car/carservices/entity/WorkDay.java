// com/car/carservices/entity/WorkDay.java
package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Table(name = "work_days")
@Data
public class WorkDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(name = "working_day", nullable = false, length = 16)
    private String workingDay; // monday..sunday (lowercase)

    // FROM/TO are reserved keywords — keep them quoted at column level
    @Column(name = "\"from\"", nullable = false)
    private LocalTime from;

    @Column(name = "\"to\"", nullable = false)
    private LocalTime to;

    @Column(nullable = false, length = 16)
    private String status; // "active" | "inactive"
}
