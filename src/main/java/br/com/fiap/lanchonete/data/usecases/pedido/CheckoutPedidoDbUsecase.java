package br.com.fiap.lanchonete.data.usecases.pedido;

import br.com.fiap.lanchonete.data.protocols.db.pedido.CheckoutPedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.GetPedidoRepository;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.domain.usecases.pedido.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckoutPedidoDbUsecase implements CheckoutPedidoUsecase {

	private final GetPedidoRepository getPedidoRepository;
	
	private final CheckoutPedidoRepository checkoutPedidoRepository;

	@Override
    public Pedido checkoutPedido(Pedido pedido) {
        Pedido ped = getPedidoRepository.get(pedido.getId());
        
        if (Objects.isNull(ped))
            throw new EntityNotFoundException("Pedido nao encontrado para o id :: " + pedido.getId());

        if (!ped.getStatus().equals(StatusEnum.CRIADO))
            throw new EntityNotFoundException("Pedido já encaminhado nao pode ser mais alterado :: " + pedido.getId());
        
        ped.setCollector(pedido.getCollector());
        ped.setPos(pedido.getPos());
        ped.setStatus(StatusEnum.PENDING);
        
        return checkoutPedidoRepository.checkout(ped);
    }

}
