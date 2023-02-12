package br.com.desafio.indicadoreseconomicosapi.api.advice;

import br.com.desafio.indicadoreseconomicosapi.api.dto.error.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ApplicationRestControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ErrorDto handleException(
            Exception exception,
            WebRequest webRequest) {
        log.error("Ocorreu um erro ao processar a requisição.", exception);
        List<String> detailMessages = new ArrayList<>();
        if (StringUtils.isNotBlank(exception.getMessage())) {
            detailMessages.add(exception.getMessage());
        }
        return ErrorDto.builder()
                .path(webRequest.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .exception(exception.getClass().getName())
                .message("Ocorreu um erro inesperado ao processar a requisição. Se o problema persistir, entre em contato com o administrador.")
                .detailMessages(detailMessages)
                .build();
    }

    @ExceptionHandler(br.com.desafio.indicadoreseconomicosapi.core.exception.BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorDto handleBusinessException(
            br.com.desafio.indicadoreseconomicosapi.core.exception.BusinessException exception,
            WebRequest webRequest) {
        return ErrorDto.builder()
                .path(webRequest.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(HttpStatus.BAD_REQUEST.value())
                .exception(exception.getClass().getName())
                .message(exception.getMessage())
                .detailMessages(new ArrayList<>())
                .build();
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorDto handleHttpMessageNotReadableException(
            org.springframework.http.converter.HttpMessageNotReadableException exception,
            WebRequest webRequest) {
        List<String> detailMessages = new ArrayList<>();
        if (StringUtils.isNotBlank(exception.getMessage())) {
            detailMessages.add(exception.getMessage());
        }
        return ErrorDto.builder()
                .path(webRequest.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(HttpStatus.BAD_REQUEST.value())
                .exception(exception.getClass().getName())
                .message("O corpo (obrigatório) da requisição não está preenchido.")
                .detailMessages(detailMessages)
                .build();
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorDto handleMethodArgumentNotValidException(
            org.springframework.web.bind.MethodArgumentNotValidException exception,
            WebRequest webRequest) {
        List<String> detailMessages = new ArrayList<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            detailMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        for (ObjectError error : exception.getBindingResult().getGlobalErrors()) {
            detailMessages.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return ErrorDto.builder()
                .path(webRequest.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(HttpStatus.BAD_REQUEST.value())
                .exception(exception.getClass().getName())
                .message("O argumento do método é inválido.")
                .detailMessages(detailMessages)
                .build();
    }

    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorDto handleMissingServletRequestParameterException(
            org.springframework.web.bind.MissingServletRequestParameterException exception,
            WebRequest webRequest) {
        List<String> detailMessages = new ArrayList<>();
        detailMessages.add(
                String.format(
                        "É necessário informar um valor para o parâmetro '%s'.",
                        exception.getParameterName()
                )
        );
        return ErrorDto.builder()
                .path(webRequest.getDescription(false).replace("uri=", ""))
                .timestamp(LocalDateTime.now())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(HttpStatus.BAD_REQUEST.value())
                .exception(exception.getClass().getName())
                .message("O parâmetro da requisição é inválido.")
                .detailMessages(detailMessages)
                .build();
    }

}
