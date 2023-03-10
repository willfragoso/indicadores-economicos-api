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
public class PovertyHeadcountRatioCountry {

    @JsonProperty("id")
    private String id;

    @JsonProperty("value")
    private String value;

}
