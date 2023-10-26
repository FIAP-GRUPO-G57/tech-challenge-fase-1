package br.com.fiap.lanchonete.domain.services;

import br.com.fiap.lanchonete.application.ports.input.usecase.*;
import br.com.fiap.lanchonete.application.ports.output.ClienteOutputPort;
import br.com.fiap.lanchonete.application.ports.output.PedidoOutputPort;
import br.com.fiap.lanchonete.application.ports.output.ProdutoOutputPort;
import br.com.fiap.lanchonete.domain.entities.Cliente;
import br.com.fiap.lanchonete.domain.entities.Item;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.vo.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
@Transactional
@RequiredArgsConstructor
public class PedidoService implements GetPedidoByIdUseCase, GetPedidoUseCase, CreatePedidoUseCase, UpdatePedidoUseCase {

    private final PedidoOutputPort pedidoOutputPort;
    private final ProdutoOutputPort produtoOutputPort;
    private final ClienteOutputPort clienteOutputPort;

    @Override
    public Pedido get(Long id) {
        return Objects.nonNull(id)?pedidoOutputPort.get(id):null;
    }

    @Override
    public List<Pedido> findByStatus(Status status) {
        if(Objects.nonNull(status))
            return pedidoOutputPort.findByStatus(status);
        return pedidoOutputPort.findAll();
    }

    //cadastrar cliente
    //validacoes
    @Override
    public Pedido create(Pedido pedido) {
        pedido.setCriacao(LocalDateTime.now());
        pedido.setStatus(Status.RECEBIDO);
        pedido.setPreco(BigDecimal.ZERO);

        Cliente cliente = clienteOutputPort.findByCpf(pedido.getCliente().getCpf());
        pedido.getCliente().setId(cliente.getId());

        Produto produto = null;
        for (Item item:pedido.getItens()){
            produto = produtoOutputPort.get(item.getProduto().getId());
            item.setPreco(produto.getPreco());
            item.setPedido(pedido);
            pedido.setPreco(pedido.getPreco().add(item.getPreco()));
        }
        return pedidoOutputPort.save(pedido);
    }

    @Override
    public Pedido update(Long id, Pedido pedido) {
        if (Objects.nonNull(id)) {
            pedido.setId(id);
            return pedidoOutputPort.save(pedido);
        }
        return null;
    }
}
