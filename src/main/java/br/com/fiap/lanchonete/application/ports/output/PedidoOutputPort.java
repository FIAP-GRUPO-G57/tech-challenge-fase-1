package br.com.fiap.lanchonete.application.ports.output;

import br.com.fiap.lanchonete.domain.entities.Pedido;

import java.util.List;

public interface PedidoOutputPort {

        Pedido get(Long id);

        List<Pedido> findAll();

        List<Pedido> findByStatus(List<String> statuss);

        Pedido save(Pedido pedido);

        Pedido checkout(Pedido pedido);

        Pedido confirm(Pedido pedido);

        Pedido pay(Pedido pedido);
}
