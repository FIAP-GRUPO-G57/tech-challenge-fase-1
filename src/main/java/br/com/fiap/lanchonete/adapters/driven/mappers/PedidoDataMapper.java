package br.com.fiap.lanchonete.adapters.driven.mappers;

import br.com.fiap.lanchonete.adapters.driven.data.PedidoData;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PedidoDataMapper {

    private final ModelMapper modelMapper;

    public PedidoDataMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Pedido toDomain(PedidoData data) {
        return modelMapper.map(data, br.com.fiap.lanchonete.domain.entities.Pedido.class);
    }

    public PedidoData toData(Pedido pedido) {
        return modelMapper.map(pedido, PedidoData.class);
    }
}
