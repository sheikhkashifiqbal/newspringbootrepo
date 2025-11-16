package com.car.carservices.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SparePartsResponseDTO {

    @JsonProperty("spareparts")
    private List<SparePartItem> spareparts;

    public SparePartsResponseDTO(List<SparePartItem> spareparts) {
        this.spareparts = spareparts;
    }

    public List<SparePartItem> getSpareparts() {
        return spareparts;
    }

    public static class SparePartItem {
        @JsonProperty("spareparts_id")
        private Long sparepartsId;

        @JsonProperty("spareparts_type")
        private String sparepartsType;

        public SparePartItem(Long sparepartsId, String sparepartsType) {
            this.sparepartsId = sparepartsId;
            this.sparepartsType = sparepartsType;
        }

        public Long getSparepartsId() {
            return sparepartsId;
        }

        public String getSparepartsType() {
            return sparepartsType;
        }
    }
}
