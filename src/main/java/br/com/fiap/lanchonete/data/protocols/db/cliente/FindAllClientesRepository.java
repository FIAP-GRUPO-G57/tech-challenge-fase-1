package br.com.fiap.lanchonete.data.protocols.db.cliente;

import java.util.List;

import br.com.fiap.lanchonete.domain.entities.Cliente;

public interface FindAllClientesRepository {
	List<Cliente> findAll();
}
