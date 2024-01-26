package br.com.fiap.lanchonete.infra.db.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.lanchonete.infra.db.entities.ClienteEntity;

import java.util.List;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteEntity, Long> {
	ClienteEntity findByCpf(String cpf);
	List<ClienteEntity> findAll();
}
