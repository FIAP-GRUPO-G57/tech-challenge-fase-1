package br.com.fiap.lanchonete.data.usecases.pedido;

import br.com.fiap.lanchonete.data.protocols.db.pedido.FindAllPedidosRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.FindPedidoByStatusRepository;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.usecases.pedido.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class FindPedidoByStatusDbUsecase implements FindPedidoByStatusUsecase {

	private final FindPedidoByStatusRepository findPedidoByStatusRepository;
	
	private final FindAllPedidosRepository findAllPedidosRepository;

	@Override
	public List<Pedido> findByStatus(List<String> statuss) {
		if (Objects.nonNull(statuss)) {
			return findPedidoByStatusRepository.findByStatus(statuss);
		}

		return findAllPedidosRepository.findAll();
	}

}
