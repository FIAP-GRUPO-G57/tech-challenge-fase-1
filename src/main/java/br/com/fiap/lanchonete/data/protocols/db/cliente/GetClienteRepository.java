package br.com.fiap.lanchonete.data.protocols.db.cliente;

import br.com.fiap.lanchonete.domain.entities.Cliente;

public interface GetClienteRepository {
	Cliente get(Long id);
}
