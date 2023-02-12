package br.com.desafio.indicadoreseconomicosapi.api.controller;

import br.com.desafio.indicadoreseconomicosapi.api.dto.grid.IndiceIncidenciaPobrezaGridDto;
import br.com.desafio.indicadoreseconomicosapi.api.dto.page.PesquisaPageDto;
import br.com.desafio.indicadoreseconomicosapi.core.service.IndiceIncidenciaPobrezaService;
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

    @GetMapping(value = "/pais", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public PesquisaPageDto<IndiceIncidenciaPobrezaGridDto> recuperarIndicadoresPobrezaDoPais(
            @RequestParam(value = "codigoPais") String codigoPais,
            @RequestParam(value = "size", defaultValue = "50") Integer tamanhoPagina,
            @RequestParam(value = "page", defaultValue = "1") Integer numeroPagina) {
        return indiceIncidenciaPobrezaService.recuperarIndicadoresPobrezaDoPais(codigoPais, tamanhoPagina, numeroPagina);
    }

}
