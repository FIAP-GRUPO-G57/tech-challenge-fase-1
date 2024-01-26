package br.com.fiap.lanchonete.infra.db.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.lanchonete.infra.db.entities.ItemEntity;

@Repository
public interface ItemPedidoRepository extends CrudRepository<ItemEntity, Long> {
}
