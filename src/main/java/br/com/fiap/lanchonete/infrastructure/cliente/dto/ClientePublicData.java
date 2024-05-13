package br.com.fiap.lanchonete.infrastructure.cliente.dto;

import br.com.fiap.lanchonete.entity.cliente.model.Cliente;
import br.com.fiap.lanchonete.usecase.cliente.dto.IClientePublicData;

public record ClientePublicData(
	Long id,
	String nome,
	String cpf,
	String telefone,
	String endereco,
	String email
) implements IClientePublicData {
	public ClientePublicData(Cliente cliente) {
		this(
				cliente.getId(),
				cliente.getNome(),
				cliente.getCpf(),
				cliente.getTelefone(),
				cliente.getEndereco(),
				cliente.getEmail()
		);
	}
}
