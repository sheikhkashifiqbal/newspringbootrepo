package com.car.carservices.dto;

import java.math.BigDecimal;

public class LmpsServiceResponseDTO {

    private Long lmpsServiceId;
    private Long serviceId;
    private Long cityId;

    private BigDecimal price;
    private String status;
    private String currency;

    public LmpsServiceResponseDTO() {}

    public LmpsServiceResponseDTO(Long lmpsServiceId, Long serviceId, Long cityId,
                                  BigDecimal price, String status, String currency) {
        this.lmpsServiceId = lmpsServiceId;
        this.serviceId = serviceId;
        this.cityId = cityId;
        this.price = price;
        this.status = status;
        this.currency = currency;
    }

    public Long getLmpsServiceId() { return lmpsServiceId; }
    public void setLmpsServiceId(Long lmpsServiceId) { this.lmpsServiceId = lmpsServiceId; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public Long getCityId() { return cityId; }
    public void setCityId(Long cityId) { this.cityId = cityId; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
