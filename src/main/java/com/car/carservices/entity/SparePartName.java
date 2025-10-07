// SparePartName.java  (child table)
package com.car.carservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "spare_part_names")
public class SparePartName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // PK

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "spareparts_id")
    private SpareParts sparePart;       // FK → spare_parts.spareparts_id

    private String sparepartsName;      // e.g. “Air Filter”
}
