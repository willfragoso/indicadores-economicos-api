package br.com.desafio.indicadoreseconomicosapi.worldbankapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@JsonIgnoreProperties(ignoreUnknown = true)
public class PovertyHeadcountRatioData {

    @JsonProperty("indicator")
    private PovertyHeadcountRatioIndicator povertyHeadcountRatioIndicator;

    @JsonProperty("country")
    private PovertyHeadcountRatioCountry povertyHeadcountRatioCountry;

    @JsonProperty("countryiso3code")
    private String countryIso3Code;

    @JsonProperty("date")
    private String date;

    @JsonProperty("value")
    private Double value;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("obs_status")
    private String obsStatus;

    @JsonProperty("decimal")
    private Integer decimal;

}
