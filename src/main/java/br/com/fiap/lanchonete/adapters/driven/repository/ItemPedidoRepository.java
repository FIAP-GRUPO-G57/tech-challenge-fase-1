package br.com.fiap.lanchonete.adapters.driven.repository;

import br.com.fiap.lanchonete.adapters.driven.data.ItemData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends CrudRepository<ItemData, Long> {
}
