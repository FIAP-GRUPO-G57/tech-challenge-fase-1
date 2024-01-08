package br.com.fiap.lanchonete.infra.db.repositories;

import br.com.fiap.lanchonete.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.infra.db.schemas.PedidoSchema;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends CrudRepository<PedidoSchema, Long> {
    List<PedidoSchema> findByStatus(StatusEnum status);
    List<PedidoSchema> findAll();
}
