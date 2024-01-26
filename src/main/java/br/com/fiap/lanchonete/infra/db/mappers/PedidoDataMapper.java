package br.com.fiap.lanchonete.infra.db.mappers;

import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.infra.db.schemas.PedidoSchema;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PedidoDataMapper {

	private final ModelMapper modelMapper;

	public PedidoDataMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public Pedido toDomain(PedidoSchema data) {
		return modelMapper.map(data, br.com.fiap.lanchonete.core.domain.entities.Pedido.class);
	}

	public PedidoSchema toData(Pedido pedido) {
		return modelMapper.map(pedido, PedidoSchema.class);
	}
	
}
