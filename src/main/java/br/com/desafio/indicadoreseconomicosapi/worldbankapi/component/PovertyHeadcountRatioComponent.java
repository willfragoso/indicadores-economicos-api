package br.com.desafio.indicadoreseconomicosapi.worldbankapi.component;

import br.com.desafio.indicadoreseconomicosapi.worldbankapi.exception.WorldBankException;
import br.com.desafio.indicadoreseconomicosapi.worldbankapi.model.PovertyHeadcountRatioData;
import br.com.desafio.indicadoreseconomicosapi.worldbankapi.model.PovertyHeadcountRatioPageInfo;
import br.com.desafio.indicadoreseconomicosapi.worldbankapi.model.WorldBankMessage;
import br.com.desafio.indicadoreseconomicosapi.worldbankapi.model.WorldBankMessageResponse;
import br.com.desafio.indicadoreseconomicosapi.worldbankapi.model.WorldBankPovertyHeadcountRatioResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PovertyHeadcountRatioComponent {

    private static final String POVERTY_HEADCOUNT_RATIO_URL = "https://api.worldbank.org/v2/country/{countryCode}/indicator/SI.POV.DDAY?format=json";
    private static final String INVALID_CONTRY_ID = "120";

    private final ObjectMapper objectMapper;

    public PovertyHeadcountRatioComponent(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public WorldBankPovertyHeadcountRatioResponse getPovertyHeadcountRatioData(String countryCode, Integer perPage, Integer page) {
        WorldBankPovertyHeadcountRatioResponse worldBankPovertyHeadcountRatioResponse;

        try {
            // URI (URL) parameters
            Map<String, String> urlParams = new HashMap<>();
            urlParams.put("countryCode", countryCode);

            // Query parameters
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(POVERTY_HEADCOUNT_RATIO_URL)
                    .queryParam("page", page.toString())
                    .queryParam("per_page", perPage.toString());

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    uriComponentsBuilder.buildAndExpand(urlParams).toUri(),
                    String.class
            );
            if (responseEntity.getStatusCode() == HttpStatus.OK) {

                JsonNode rootJsonNode = objectMapper.readTree(responseEntity.getBody());

                WorldBankMessageResponse worldBankMessageResponse = extractWorldBankMessageResponse(rootJsonNode);

                if (worldBankMessageResponse != null
                        && worldBankMessageResponse.getWorldBankMessageList() != null
                        && !worldBankMessageResponse.getWorldBankMessageList().isEmpty()) {
                    WorldBankMessage worldBankMessage = worldBankMessageResponse.getWorldBankMessageList().get(0);
                    if (INVALID_CONTRY_ID.equals(worldBankMessage.getId())) {
                        throw new WorldBankException(
                                String.format(
                                        "O código informado para o país (%s) não é válido.",
                                        countryCode
                                )
                        );
                    } else {
                        throw new WorldBankException(
                                String.format(
                                        "Id - %s | Key - %s | Value - %s",
                                        worldBankMessage.getId(),
                                        worldBankMessage.getKey(),
                                        worldBankMessage.getValue()
                                )
                        );
                    }
                }

                PovertyHeadcountRatioPageInfo povertyHeadcountRatioPageInfo = extractPovertyHeadcountRatioPageInfo(rootJsonNode);

                List<PovertyHeadcountRatioData> povertyHeadcountRatioDataList = extractPovertyHeadcountRatioData(rootJsonNode);

                worldBankPovertyHeadcountRatioResponse = WorldBankPovertyHeadcountRatioResponse.builder()
                        .povertyHeadcountRatioPageInfo(povertyHeadcountRatioPageInfo)
                        .povertyHeadcountRatioDataList(povertyHeadcountRatioDataList)
                        .build();

            } else {
                throw new WorldBankException(
                        String.format(
                                "O código do status da resposta (%d) não é esperado.",
                                responseEntity.getStatusCode().value()

                        )
                );
            }
        } catch (JsonProcessingException jsonProcessingException) {
            throw new WorldBankException(
                    "Houve um erro na leitura da resposta referente ao serviço que busca os índices de incidência de probleza no Banco Mundial."
            );
        }

        return worldBankPovertyHeadcountRatioResponse;
    }

    private WorldBankMessageResponse extractWorldBankMessageResponse(JsonNode rootJsonNode) {
        JsonNode messageResponseJsonNode = rootJsonNode.get(0);
        return objectMapper.convertValue(messageResponseJsonNode, WorldBankMessageResponse.class);
    }

    private PovertyHeadcountRatioPageInfo extractPovertyHeadcountRatioPageInfo(JsonNode rootJsonNode) {
        JsonNode pageInfoJsonNode = rootJsonNode.get(0);
        return objectMapper.convertValue(pageInfoJsonNode, PovertyHeadcountRatioPageInfo.class);
    }

    private List<PovertyHeadcountRatioData> extractPovertyHeadcountRatioData(JsonNode rootJsonNode) {
        List<PovertyHeadcountRatioData> povertyHeadcountRatioDataList = new ArrayList<>();
        JsonNode dataArrayJsonNode = rootJsonNode.get(1);
        for (final JsonNode dataJsonNode : dataArrayJsonNode) {
            povertyHeadcountRatioDataList.add(
                    objectMapper.convertValue(dataJsonNode, PovertyHeadcountRatioData.class)
            );
        }
        return povertyHeadcountRatioDataList;
    }

}
