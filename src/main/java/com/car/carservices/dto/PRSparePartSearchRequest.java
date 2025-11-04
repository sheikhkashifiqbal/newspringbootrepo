package com.car.carservices.dto;

import java.util.List;

public class PRSparePartSearchRequest {
    private String vin;
    private Long spareparts_id;
    private List<String> state; // ["used","new"]
    private String city;

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public Long getSpareparts_id() { return spareparts_id; }
    public void setSpareparts_id(Long spareparts_id) { this.spareparts_id = spareparts_id; }

    public List<String> getState() { return state; }
    public void setState(List<String> state) { this.state = state; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}
