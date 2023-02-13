package br.com.desafio.indicadoreseconomicosapi.api.controller;

import br.com.desafio.indicadoreseconomicosapi.api.dto.error.ErrorDto;
import br.com.desafio.indicadoreseconomicosapi.api.dto.grid.IndiceIncidenciaPobrezaGridDto;
import br.com.desafio.indicadoreseconomicosapi.api.dto.page.PesquisaPageDto;
import br.com.desafio.indicadoreseconomicosapi.core.service.IndiceIncidenciaPobrezaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.xml.bind.annotation.XmlSchemaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/indice-incidencia-pobreza")
public class IndiceIncidenciaPobrezaController {

    @Autowired
    private IndiceIncidenciaPobrezaService indiceIncidenciaPobrezaService;

    @Operation(summary = "Serviço que recupera os índices de incidência de pobreza de um determinado País.")
    @GetMapping(value = "/pais", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public PesquisaPageDto<IndiceIncidenciaPobrezaGridDto> recuperarIndiceIncidenciaPobrezaDoPais(
            @Parameter(
                    description = "O código do País, que pode ser seu identificador (ex.: BR) ou seu código ISO 3 (ex.: BRA).",
                    required = true,
                    example = "BR")
            @RequestParam(value = "codigoPais") String codigoPais,
            @Parameter(
                    description = "O tamanho da página de pesquisa (de 1 a n).",
                    example = "10")
            @RequestParam(value = "tamanhoPagina", defaultValue = "50") Integer tamanhoPagina,
            @Parameter(
                    description = "O número da página de pesquisa (de 1 a n).",
                    example = "1")
            @RequestParam(value = "numeroPagina", defaultValue = "1") Integer numeroPagina) {
        return indiceIncidenciaPobrezaService.recuperarIndicadoresPobrezaDoPais(codigoPais, tamanhoPagina, numeroPagina);
    }

}
