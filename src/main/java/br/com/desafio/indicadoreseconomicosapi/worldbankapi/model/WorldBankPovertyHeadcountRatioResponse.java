package br.com.desafio.indicadoreseconomicosapi.worldbankapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorldBankPovertyHeadcountRatioResponse {

    private PovertyHeadcountRatioPageInfo povertyHeadcountRatioPageInfo;

    private List<PovertyHeadcountRatioData> povertyHeadcountRatioDataList;

}
