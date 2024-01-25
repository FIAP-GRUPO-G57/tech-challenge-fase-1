package br.com.fiap.lanchonete.domain.services;

import br.com.fiap.lanchonete.application.ports.input.usecase.*;
import br.com.fiap.lanchonete.application.ports.output.ClienteOutputPort;
import br.com.fiap.lanchonete.application.ports.output.ItemPedidoOutputPort;
import br.com.fiap.lanchonete.application.ports.output.PedidoOutputPort;
import br.com.fiap.lanchonete.application.ports.output.ProdutoOutputPort;
import br.com.fiap.lanchonete.domain.entities.*;
import br.com.fiap.lanchonete.domain.vo.Status;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class PedidoService implements GetPedidoByIdUseCase, GetPedidoUseCase,
        CreatePedidoUseCase, AddItemPedidoUseCase,DeleteItemPedidoUseCase ,
       CheckoutPedidoUseCase, ConfirmPedidoUseCase, PayPedidoUseCase, UpdatePedidoUseCase {

    private final PedidoOutputPort pedidoOutputPort;
    private final ProdutoOutputPort produtoOutputPort;
    private final ClienteOutputPort clienteOutputPort;

    private final ItemPedidoOutputPort itemPedidoOutputPort;

    @Override
    public Pedido get(Long id) {
        return pedidoOutputPort.get(id);
    }

    @Override
    public List<Pedido> findByStatus(List<String> statuss) {
        if(Objects.nonNull(statuss))
            return pedidoOutputPort.findByStatus(statuss);
        return pedidoOutputPort.findAll();
    }

    @Override
    public Pedido update(Long id, Pedido pedido) {
        if (Objects.nonNull(id)) {
            pedido.setId(id);
            Pedido pedido1 = pedidoOutputPort.get(id);
            if (Objects.isNull(pedido1))
                return null;
            pedido1.setStatus(pedido.getStatus());
            return pedidoOutputPort.save(pedido1);
        }
        return null;
    }

    @Override
    public Pedido create(Pedido pedido) {
        pedido.setCriacao(LocalDateTime.now());
        pedido.setStatus(Status.CRIADO);
        pedido.setStatusPagamento(Status.PENDING);
        pedido.setPreco(BigDecimal.ZERO);
        Cliente cliente = Optional.ofNullable(pedido)
                .map(Pedido::getCliente)
                .map(Cliente::getCpf)
                .map(cpf->clienteOutputPort.findByCpf(cpf))
                .orElse(null);
        if(cliente!=null) {
            pedido.getCliente().setId(cliente.getId());
            pedido.setCliente(cliente);
        }
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
    public Pedido checkoutPedido(Pedido pedido) {
        Pedido ped = pedidoOutputPort.get(pedido.getId());
        if (Objects.isNull(ped))
            throw new EntityNotFoundException("Pedido nao encontrado para o id :: " + pedido.getId());

        if (!ped.getStatus().equals(Status.CRIADO))
            throw new EntityNotFoundException("Pedido já encaminhado nao pode ser mais alterado :: " + pedido.getId());
        ped.setCollector(pedido.getCollector());
        ped.setPos(pedido.getPos());
        ped.setStatus(Status.PENDING);
        return pedidoOutputPort.checkout(ped);
    }

    @Override
    public Pedido confirmPedido(Pedido pedido) {
        if (Objects.isNull(pedido) || Objects.isNull(pedido.getOrderId()))
            throw new EntityNotFoundException("Order nao encontrado para o id :: " + pedido.getOrderId());
        return pedidoOutputPort.confirm(pedido);
    }

    @Override
    public Pedido payPedido(Pedido pedido) {
        if (Objects.isNull(pedido) || Objects.isNull(pedido.getPaymentId()))
            throw new EntityNotFoundException("Payment nao encontrado para o id :: " + pedido.getPaymentId());

        return pedidoOutputPort.pay(pedido);
    }

}
