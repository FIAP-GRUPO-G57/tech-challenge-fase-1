package br.com.fiap.lanchonete.infra.db.repositories;

import br.com.fiap.lanchonete.core.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.infra.db.entities.PedidoEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends CrudRepository<PedidoEntity, Long> {
	List<PedidoEntity> findByStatusIn(List<StatusEnum> status);
    List<PedidoEntity> findAll();
}
