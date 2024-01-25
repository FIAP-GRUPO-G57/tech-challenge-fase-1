package br.com.fiap.lanchonete.infra.db.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.lanchonete.infra.db.schemas.ClienteSchema;

import java.util.List;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteSchema, Long> {
	ClienteSchema findByCpf(String cpf);
	List<ClienteSchema> findAll();
}
