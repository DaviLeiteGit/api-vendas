package io.github.davileite.exception;

public class PedidoNaoEncontratoException extends RuntimeException {

    public PedidoNaoEncontratoException() {
        super("Pedido não encontrado.");
    }
}
