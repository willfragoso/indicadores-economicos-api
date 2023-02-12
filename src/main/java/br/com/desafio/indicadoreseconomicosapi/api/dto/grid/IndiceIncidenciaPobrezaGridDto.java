package br.com.desafio.indicadoreseconomicosapi.api.dto.grid;

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
public class IndiceIncidenciaPobrezaGridDto {

    private String idIndicador;

    private String nomeIndicador;

    private String idPais;

    private String nomePais;

    private String ano;

    private Double valor;

}
