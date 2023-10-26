package br.com.fiap.lanchonete.adapters.driven.repository;

import br.com.fiap.lanchonete.adapters.driven.data.PedidoData;
import br.com.fiap.lanchonete.adapters.driven.enums.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends CrudRepository<PedidoData, Long> {
    List<PedidoData> findByStatus(Status status);

    List<PedidoData> findAll();
}
