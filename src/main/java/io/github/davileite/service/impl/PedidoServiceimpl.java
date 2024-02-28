package io.github.davileite.service.impl;

import io.github.davileite.domain.entity.Cliente;
import io.github.davileite.domain.entity.ItemPedido;
import io.github.davileite.domain.entity.Pedido;
import io.github.davileite.domain.entity.Produto;
import io.github.davileite.domain.enums.StatusPedido;
import io.github.davileite.domain.repository.Clientes;
import io.github.davileite.domain.repository.ItemsPedido;
import io.github.davileite.domain.repository.Pedidos;
import io.github.davileite.domain.repository.Produtos;
import io.github.davileite.exception.PedidoNaoEncontratoException;
import io.github.davileite.exception.RegraNegocioException;
import io.github.davileite.rest.dto.ItemPedidoDTO;
import io.github.davileite.rest.dto.PedidoDTO;
import io.github.davileite.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceimpl implements PedidoService {
    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedidoRepository;


    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(()-> new RegraNegocioException("Código de cliente inválido"));
        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);
        List<ItemPedido> itemPedidos = converterItens(pedido,dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemPedidos);
        pedido.setItens(itemPedidos);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow( () -> new PedidoNaoEncontratoException());
    }

    private List<ItemPedido> converterItens(Pedido pedido,List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possivel realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(()-> new RegraNegocioException(
                                    "Código de produto inválido:" + idProduto
                            ));
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

    }
}
