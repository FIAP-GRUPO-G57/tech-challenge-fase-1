package br.com.fiap.lanchonete.application.ports.output;

import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.vo.Status;

import java.util.List;

public interface PedidoOutputPort {

        Pedido get(Long id);

        List<Pedido> findAll();

        List<Pedido> findByStatus(Status status);

        Pedido save(Pedido pedido);
}
