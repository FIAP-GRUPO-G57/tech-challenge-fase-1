package br.com.fiap.lanchonete.usecase.cliente;

import br.com.fiap.lanchonete.entity.cliente.gateway.ClienteGateway;
import br.com.fiap.lanchonete.entity.cliente.model.Cliente;
import br.com.fiap.lanchonete.usecase.cliente.dto.IClienteRegistrationData;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateClienteUsecase {

	private final ClienteGateway clienteGateway;

	public CreateClienteUsecase(ClienteGateway clienteGateway) {
		this.clienteGateway = clienteGateway;
	}

	public Cliente execute(IClienteRegistrationData data) {
		Optional.ofNullable(data)
				.map(IClienteRegistrationData::cpf)
				.map(clienteGateway::findByCpf)
				.filter(Optional::isPresent)
				.ifPresent(user->{
					throw new EntityExistsException("Cliente ja cadastrado para o cpf :: "+user.get().getCpf() );
				});

		Cliente cliente = new Cliente(data.id(), data.nome(), data.cpf(), data.telefone(), data.endereco(), data.email());
		return clienteGateway.save(cliente);
	}
}
