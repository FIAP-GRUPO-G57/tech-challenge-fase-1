package br.com.fiap.lanchonete.domain.services;

import br.com.fiap.lanchonete.application.ports.input.usecase.*;
import br.com.fiap.lanchonete.application.ports.output.ClienteOutputPort;
import br.com.fiap.lanchonete.application.ports.output.ItemPedidoOutputPort;
import br.com.fiap.lanchonete.application.ports.output.PedidoOutputPort;
import br.com.fiap.lanchonete.application.ports.output.ProdutoOutputPort;
import br.com.fiap.lanchonete.domain.entities.Cliente;
import br.com.fiap.lanchonete.domain.entities.Item;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.vo.Status;
import jakarta.persistence.EntityNotFoundException;
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
public class PedidoService implements GetPedidoByIdUseCase, GetPedidoUseCase,
        CreatePedidoUseCase, AddItemPedidoUseCase,DeleteItemPedidoUseCase ,
       CheckoutPedidoUseCase {

    private final PedidoOutputPort pedidoOutputPort;
    private final ProdutoOutputPort produtoOutputPort;
    private final ClienteOutputPort clienteOutputPort;

    private final ItemPedidoOutputPort itemPedidoOutputPort;

    @Override
    public Pedido get(Long id) {
        return pedidoOutputPort.get(id);
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
        pedido.setStatus(Status.CRIADO);
        pedido.setPreco(BigDecimal.ZERO);

        Cliente cliente = clienteOutputPort.findByCpf(pedido.getCliente().getCpf());
        pedido.getCliente().setId(cliente.getId());
        pedido.setCliente(cliente);

        Produto produto = null;
        for (Item item:pedido.getItens()){
            produto = produtoOutputPort.get(item.getProduto().getId());
            if (Objects.isNull(produto))
                throw new EntityNotFoundException("Produto nao encontrado para o id :: " + item.getProduto().getId());
            item.setPreco(produto.getPreco());
            item.setPedido(pedido);
            item.setProduto(produto);
            pedido.setPreco(pedido.getPreco().add(item.getPreco()));
        }
        return pedidoOutputPort.save(pedido);
    }


    @Override
    public Pedido addItemPedido(Long id, List<Item> itens) {
        Pedido pedido = pedidoOutputPort.get(id);
        if (Objects.isNull(pedido))
            throw new EntityNotFoundException("Pedido nao encontrado para o id :: " + id);

        if (!pedido.getStatus().equals(Status.CRIADO))
            throw new EntityNotFoundException("Pedido já encaminhado nao pode ser mais alterado :: " + id);

        Produto produto = null;
        for (Item item:itens){
            produto = produtoOutputPort.get(item.getProduto().getId());
            if (Objects.isNull(produto))
                throw new EntityNotFoundException("Produto nao encontrado para o id :: " + item.getProduto().getId());
            item.setPreco(produto.getPreco());


            item.setPedido(pedido);
            item.setProduto(produto);

            item = itemPedidoOutputPort.addItemPedido(id, item);
            pedido.getItens().add(item);
            pedido.setPreco(pedido.getPreco().add(produto.getPreco()));
        }

        return pedidoOutputPort.save(pedido);


    }

        @Override
        public void deleteItemPedido(Long id, Long idItem) {
            Pedido pedido = pedidoOutputPort.get(id);

            if (Objects.isNull(pedido))
                throw new EntityNotFoundException("Pedido nao encontrado para o id :: " + id);

            if (!pedido.getStatus().equals(Status.CRIADO))
                throw new EntityNotFoundException("Pedido já encaminhado nao pode ser mais alterado :: " + id);

            Item item = pedido.getItens().stream().filter(i->i.getId().equals(idItem)).findFirst().orElse(null);
            if (Objects.isNull(item))
                throw new EntityNotFoundException("Item nao encontrado para o id :: " + idItem);

            pedido.getItens().remove(item);
            pedido.setPreco(pedido.getPreco().subtract(item.getPreco()));
            itemPedidoOutputPort.deleteItemPedido(item.getId());
            pedidoOutputPort.save(pedido);
        }


    @Override
    public void checkoutPedido(Long idPedido) {
        Pedido pedido = pedidoOutputPort.get(idPedido);
        if (Objects.isNull(pedido))
            throw new EntityNotFoundException("Pedido nao encontrado para o id :: " + idPedido);

        if (!pedido.getStatus().equals(Status.CRIADO))
            throw new EntityNotFoundException("Pedido já encaminhado nao pode ser mais alterado :: " + idPedido);

        pedido.setStatus(Status.RECEBIDO);
        pedidoOutputPort.save(pedido);
    }
}
