package br.com.desafio.indicadoreseconomicosapi.api.dto.page;

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
public class PesquisaPageDto<T> {

    private List<T> elementos;

    private long totalElementos;

    private long totalPaginas;

    private long tamanhoPagina;

    private long numeroPagina;

}
