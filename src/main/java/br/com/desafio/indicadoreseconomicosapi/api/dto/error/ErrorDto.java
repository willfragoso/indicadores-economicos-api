package br.com.desafio.indicadoreseconomicosapi.api.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {

    private String path;

    private LocalDateTime timestamp;

    private String error;

    private Integer status;

    private String exception;

    private String message;

    private List<String> detailMessages;

}
