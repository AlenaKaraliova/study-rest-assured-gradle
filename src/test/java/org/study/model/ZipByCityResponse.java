package org.study.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class ZipByCityResponse {

    @JsonProperty("place name")
    private String placeName;

    @JsonProperty("country")
    private String country;

    @JsonProperty("country abbreviation")
    private String countryAbbreviation;

    @JsonProperty("state")
    private String state;

    @JsonProperty("state abbreviation")
    private String stateAbbreviation;

    @JsonProperty("places")
    private List<Places> places;

    @Data
    @Accessors(fluent = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Places {

        @JsonProperty("place name")
        private String placeName;

        @JsonProperty("longitude")
        private String longitude;

        @JsonProperty("latitude")
        private String latitude;

        @JsonProperty("state")
        private String state;

        @JsonProperty("state abbreviation")
        private String stateAbbreviation;

        @JsonProperty("post code")
        private String postCode;

    }

}

