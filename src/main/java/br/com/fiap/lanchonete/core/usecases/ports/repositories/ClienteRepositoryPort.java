package br.com.fiap.lanchonete.core.usecases.ports.repositories;

import java.util.List;

import br.com.fiap.lanchonete.core.domain.entities.Cliente;

public interface ClienteRepositoryPort {
	List<Cliente> findAll();

	Cliente findByCpf(String cpf);

	Cliente get(Long id);

	Cliente save(Cliente cliente);
}
