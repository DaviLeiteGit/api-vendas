package io.github.davileite.rest.controller;


import io.github.davileite.domain.entity.Pedido;
import io.github.davileite.rest.dto.PedidoDTO;
import io.github.davileite.service.PedidoService;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save (@RequestBody PedidoDTO dto){
           Pedido pedido = service.salvar(dto);
           return pedido.getId();
    }
}
