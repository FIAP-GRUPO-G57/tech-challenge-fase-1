package br.com.fiap.lanchonete.infra.db.postgresql;

import br.com.fiap.lanchonete.core.domain.entities.OrdemPedido;
import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.core.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.PedidoRepositoryPort;
import br.com.fiap.lanchonete.infra.db.mappers.PedidoDataMapper;
import br.com.fiap.lanchonete.infra.db.repositories.PedidoRepository;
import br.com.fiap.lanchonete.infra.db.schemas.PedidoSchema;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PedidoPostgresqlRepository implements PedidoRepositoryPort {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	public PedidoDataMapper pedidoDataMapper;

	@Override
	public Pedido get(Long id) {
		return pedidoRepository.findById(id).map(pedidoData -> modelMapper.map(pedidoData, Pedido.class)).orElse(null);
	}

	@Override
	public List<Pedido> findAll() {
		return pedidoRepository.findAll().stream().map(pedidoData -> modelMapper.map(pedidoData, Pedido.class))
				.toList();
	}

	@Override
	public List<Pedido> findByStatus(List<String> statuss) {
		List<StatusEnum> enums = statuss.stream().map(ss -> StatusEnum.valueOf(ss)).collect(Collectors.toList());
		List<PedidoSchema> list = pedidoRepository.findByStatusIn(enums);
		if (!list.isEmpty()) {
			Collections.sort(list,
					Comparator.comparing(PedidoSchema::getSteps).reversed().thenComparing(PedidoSchema::getCriacao));
			return list.stream().map(pedidoSchema -> modelMapper.map(pedidoSchema, Pedido.class)).toList();
		}
		return null;
	}

	@Override
	public Pedido save(Pedido pedido) {
		PedidoSchema pedidoSchema = pedidoDataMapper.toData(pedido);
		pedidoSchema.setSteps(pedidoSchema.getStatus());
		return modelMapper.map(pedidoRepository.save(pedidoSchema), Pedido.class);
	}

	@Override
	public Pedido checkout(Pedido pedido) {
		PedidoSchema pedidoSchema = modelMapper.map(pedido, PedidoSchema.class);
		pedidoSchema.setSteps(pedidoSchema.getStatus());
		pedidoRepository.save(pedidoSchema);
		return pedido;
	}

	@Override
	public Pedido confirm(Pedido pedido, OrdemPedido ordemPedido) {
		if (Objects.nonNull(ordemPedido)) {
			Pedido ped = pedidoRepository.findById(ordemPedido.getIdExterno())
					.map(pedidoData -> modelMapper.map(pedidoData, Pedido.class)).orElse(null);
			ped.setExternalReference(pedido.getOrderId());
			ped.setStatus(StatusEnum.CONFIRMADO);
			PedidoSchema pedidoSchema = modelMapper.map(ped, PedidoSchema.class);
			pedidoSchema.setSteps(pedidoSchema.getStatus());
			return modelMapper.map(pedidoRepository.save(pedidoSchema), Pedido.class);
		}
		return null;
	}

	@Override
	public Pedido pay(Pedido pedido, OrdemPedido ordemPedido) {
		if (Objects.nonNull(ordemPedido)) {
			Pedido ped = pedidoRepository.findById(ordemPedido.getIdExterno())
					.map(pedidoData -> modelMapper.map(pedidoData, Pedido.class)).orElse(null);
			ped.setPaymentId(pedido.getPaymentId());
			if ("approved".equals(ordemPedido.getStatus())) {
				ped.setStatus(StatusEnum.RECEBIDO);
				ped.setStatusPagamento(StatusEnum.PAGO);
			} else {
				ped.setStatusPagamento(StatusEnum.REJEITADO);
			}
			PedidoSchema pedidoSchema = modelMapper.map(ped, PedidoSchema.class);
			pedidoSchema.setSteps(pedidoSchema.getStatus());
			return modelMapper.map(pedidoRepository.save(pedidoSchema), Pedido.class);
		}
		return null;
	}
	
}
