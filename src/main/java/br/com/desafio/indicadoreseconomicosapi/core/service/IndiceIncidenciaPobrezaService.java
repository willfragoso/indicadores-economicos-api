package br.com.desafio.indicadoreseconomicosapi.core.service;

import br.com.desafio.indicadoreseconomicosapi.api.dto.grid.IndiceIncidenciaPobrezaGridDto;
import br.com.desafio.indicadoreseconomicosapi.api.dto.page.PesquisaPageDto;
import br.com.desafio.indicadoreseconomicosapi.core.exception.BusinessException;
import br.com.desafio.indicadoreseconomicosapi.worldbankapi.component.PovertyHeadcountRatioComponent;
import br.com.desafio.indicadoreseconomicosapi.worldbankapi.model.WorldBankPovertyHeadcountRatioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndiceIncidenciaPobrezaService {

    private final PovertyHeadcountRatioComponent povertyHeadcountRatioComponent;

    @Autowired
    public IndiceIncidenciaPobrezaService(PovertyHeadcountRatioComponent povertyHeadcountRatioComponent) {
        this.povertyHeadcountRatioComponent = povertyHeadcountRatioComponent;
    }

    public PesquisaPageDto<IndiceIncidenciaPobrezaGridDto> recuperarIndicadoresPobrezaDoPais(String codigoPais) {
        WorldBankPovertyHeadcountRatioResponse worldBankPovertyHeadcountRatioResponse;

        try {
            worldBankPovertyHeadcountRatioResponse = povertyHeadcountRatioComponent.getPovertyHeadcountRatioData(codigoPais);
        } catch (Exception exception) {
            throw new BusinessException(
                    String.format(
                            "Ocorreu um erro ao consultar o índice de incidência de probreza na API do Banco Mundial. Detalhe(s) do erro: %s",
                            exception.getMessage()
                    )
            );
        }

        List<IndiceIncidenciaPobrezaGridDto> elementos = worldBankPovertyHeadcountRatioResponse.getPovertyHeadcountRatioDataList().stream()
                .map(
                        povertyHeadcountRatioData -> IndiceIncidenciaPobrezaGridDto.builder()
                                .idIndicador(povertyHeadcountRatioData.getPovertyHeadcountRatioIndicator().getId())
                                .nomeIndicador(povertyHeadcountRatioData.getPovertyHeadcountRatioIndicator().getValue())
                                .idPais(povertyHeadcountRatioData.getPovertyHeadcountRatioCountry().getId())
                                .nomePais(povertyHeadcountRatioData.getPovertyHeadcountRatioCountry().getValue())
                                .ano(povertyHeadcountRatioData.getDate())
                                .valor(povertyHeadcountRatioData.getValue())
                                .build()
                )
                .collect(Collectors.toList());

        PesquisaPageDto<IndiceIncidenciaPobrezaGridDto> pesquisaPageDto = new PesquisaPageDto<>();
        pesquisaPageDto.setElementos(elementos);
        pesquisaPageDto.setTotalElementos(worldBankPovertyHeadcountRatioResponse.getPovertyHeadcountRatioPageInfo().getTotal());
        pesquisaPageDto.setTotalPaginas(worldBankPovertyHeadcountRatioResponse.getPovertyHeadcountRatioPageInfo().getPages());
        pesquisaPageDto.setTamanhoPagina(worldBankPovertyHeadcountRatioResponse.getPovertyHeadcountRatioPageInfo().getPerPage());
        pesquisaPageDto.setNumeroPagina(worldBankPovertyHeadcountRatioResponse.getPovertyHeadcountRatioPageInfo().getPage());

        return pesquisaPageDto;
    }

}
