package br.com.fiap.lanchonete.infra.db.repositories.postgresql;

import br.com.fiap.lanchonete.data.mappers.PedidoDataMapper;
import br.com.fiap.lanchonete.data.protocols.db.pedido.FindAllPedidosRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.FindPedidoByStatusRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.GetPedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.SavePedidoRepository;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.infra.db.repositories.PedidoRepository;
import br.com.fiap.lanchonete.infra.db.schemas.PedidoSchema;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoPostgresqlRepository implements GetPedidoRepository, FindAllPedidosRepository, FindPedidoByStatusRepository, SavePedidoRepository  {

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
        return pedidoRepository.findAll().stream()
                .map(pedidoData-> modelMapper.map(pedidoData, Pedido.class))
                .toList();
    }

    @Override
    public List<Pedido> findByStatus(br.com.fiap.lanchonete.domain.enums.StatusEnum status) {
        StatusEnum st = StatusEnum.valueOf(status.name());
        return pedidoRepository.findByStatus(st).stream()
                .map(pedidoData-> modelMapper.map(pedidoData, Pedido.class))
                .toList();
    }

    @Override
    public Pedido save(Pedido pedido) {
        PedidoSchema pedidoData = pedidoDataMapper.toData(pedido);
        pedidoData =  pedidoRepository.save(pedidoData);
		return pedidoDataMapper.toDomain(pedidoData);
	}

}
