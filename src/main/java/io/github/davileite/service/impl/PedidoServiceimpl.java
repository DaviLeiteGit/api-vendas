package io.github.davileite.service.impl;

import io.github.davileite.domain.repository.Pedidos;
import io.github.davileite.service.PedidoService;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceimpl implements PedidoService {
    private Pedidos repository;

    public PedidoServiceimpl(Pedidos repository) {
        this.repository = repository;
    }
}
