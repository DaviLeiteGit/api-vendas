package io.github.davileite.service;

import io.github.davileite.domain.entity.Pedido;
import io.github.davileite.rest.dto.PedidoDTO;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);
}
