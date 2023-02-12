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
public class PovertyHeadcountRatioPageInfo {

    @JsonProperty("page")
    private int page;

    @JsonProperty("pages")
    private int pages;

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("total")
    private int total;

    @JsonProperty("sourceid")
    private String sourceId;

    @JsonProperty("sourcename")
    private String sourceName;

    @JsonProperty("lastupdated")
    private String lastUpdated;

}
