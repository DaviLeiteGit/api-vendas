package io.github.davileite.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("Senha inválida.");
    }
}
