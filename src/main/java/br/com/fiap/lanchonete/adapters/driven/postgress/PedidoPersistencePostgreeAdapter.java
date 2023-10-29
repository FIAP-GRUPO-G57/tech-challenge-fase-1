package br.com.fiap.lanchonete.adapters.driven.postgress;

import br.com.fiap.lanchonete.adapters.driven.data.PedidoData;;
import br.com.fiap.lanchonete.adapters.driven.mappers.PedidoDataMapper;
import br.com.fiap.lanchonete.adapters.driven.repository.PedidoRepository;;
import br.com.fiap.lanchonete.application.ports.output.ItemPedidoOutputPort;
import br.com.fiap.lanchonete.application.ports.output.PedidoOutputPort;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.adapters.driven.enums.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoPersistencePostgreeAdapter implements PedidoOutputPort {

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
    public List<Pedido> findByStatus(br.com.fiap.lanchonete.domain.vo.Status status) {
        Status st = Status.valueOf(status.name());
        return pedidoRepository.findByStatus(st).stream()
                .map(pedidoData-> modelMapper.map(pedidoData, Pedido.class))
                .toList();
    }

    @Override
    public Pedido save(Pedido pedido) {

        PedidoData pedidoData = pedidoDataMapper.toData(pedido);
        pedidoData =  pedidoRepository.save(pedidoData);

        return pedidoDataMapper.toDomain(pedidoData);
       // return modelMapper.map(pedidoRepository.save(modelMapper.map(pedido, PedidoData.class)), Pedido.class);
    }


}
