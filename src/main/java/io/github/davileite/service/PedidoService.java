package io.github.davileite.service;

import io.github.davileite.domain.entity.Pedido;
import io.github.davileite.rest.dto.PedidoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);


    Optional<Pedido> obterPedidoCompleto(Integer id);
}
