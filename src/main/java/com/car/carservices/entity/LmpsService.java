package com.car.carservices.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "lmps_service")
public class LmpsService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lmps_service_id")
    private Long lmpsServiceId;

    // FK -> service_entity.service_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    // FK -> city.id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "price", precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "status")
    private String status;

    @Column(name = "currency")
    private String currency;

    // ---------------- Getters / Setters ----------------

    public Long getLmpsServiceId() {
        return lmpsServiceId;
    }

    public void setLmpsServiceId(Long lmpsServiceId) {
        this.lmpsServiceId = lmpsServiceId;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
