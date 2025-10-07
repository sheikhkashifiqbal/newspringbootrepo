// src/main/java/com/car/carservices/entity/SparePartsRequestDetail.java
package com.car.carservices.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "spare_parts_request_details")
public class SparePartsRequestDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK to spare_parts_request(sparepartsrequest_id) – scalar for simplicity
    @Column(name = "sparepartsrequest_id", nullable = false)
    private Long sparepartsrequestId;

    @Column(name = "spare_part", nullable = false)
    private String sparePart;

    @Column(name = "class_type")
    private String classType;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSparepartsrequestId() { return sparepartsrequestId; }
    public void setSparepartsrequestId(Long sparepartsrequestId) { this.sparepartsrequestId = sparepartsrequestId; }
    public String getSparePart() { return sparePart; }
    public void setSparePart(String sparePart) { this.sparePart = sparePart; }
    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }
    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
