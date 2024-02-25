package io.github.davileite.rest.controller;

import io.github.davileite.exception.RegraNegocioException;
import io.github.davileite.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraDeNegocioException(RegraNegocioException ex){
            String mensagemErro = ex.getMessage();
            return new ApiErrors(mensagemErro);
    }

}
