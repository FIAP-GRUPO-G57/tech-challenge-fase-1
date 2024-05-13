package br.com.fiap.lanchonete.infrastructure.config.db.repository;

import br.com.fiap.lanchonete.infrastructure.config.db.schema.ClienteSchema;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteSchema, Long> {
	Optional<ClienteSchema> findByCpf(String cpf);
	List<ClienteSchema> findAll();
}
