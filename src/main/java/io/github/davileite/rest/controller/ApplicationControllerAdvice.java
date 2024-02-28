package io.github.davileite.rest.controller;

import io.github.davileite.exception.PedidoNaoEncontratoException;
import io.github.davileite.exception.RegraNegocioException;
import io.github.davileite.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraDeNegocioException(RegraNegocioException ex){
            String mensagemErro = ex.getMessage();
            return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(PedidoNaoEncontratoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handePedidoNotFoundException(PedidoNaoEncontratoException ex){
        return new ApiErrors(ex.getMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex){
       List<String>errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(e -> e.getDefaultMessage()).collect(Collectors.toList());
       return  new ApiErrors(errors);
    }

}
