package br.com.fiap.lanchonete.core.usecases.pedido;

import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.PedidoRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GetPedidoUsecase {

	private final PedidoRepositoryPort pedidoPort;

    public List<Pedido> findByStatus(List<String> statuss) {
        if(Objects.nonNull(statuss))
            return pedidoPort.findByStatus(statuss);
        return pedidoPort.findAll();
    }

}
