package br.com.fiap.lanchonete.infra.controllers;

import br.com.fiap.lanchonete.domain.dto.ClienteDto;
import br.com.fiap.lanchonete.domain.usecases.cliente.CreateClienteUsecase;
import br.com.fiap.lanchonete.domain.usecases.cliente.GetClienteByCpfUsecase;
import br.com.fiap.lanchonete.domain.usecases.cliente.GetClienteByIdUsecase;
import br.com.fiap.lanchonete.domain.usecases.cliente.GetClienteUsecase;
import jakarta.validation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	private final CreateClienteUsecase createClienteUseCase;

	private final GetClienteUsecase getClienteUseCase;

	private final GetClienteByIdUsecase getClienteByIdUseCase;

	private final GetClienteByCpfUsecase getClienteByCpfUseCase;

	public ClienteController(CreateClienteUsecase createClienteUseCase, GetClienteUsecase getClienteUseCase,
			GetClienteByIdUsecase getClienteByIdUseCase, GetClienteByCpfUsecase getClienteByCpfUseCase) {
		this.createClienteUseCase = createClienteUseCase;
		this.getClienteUseCase = getClienteUseCase;
		this.getClienteByIdUseCase = getClienteByIdUseCase;
		this.getClienteByCpfUseCase = getClienteByCpfUseCase;
	}

	@PostMapping
	public ResponseEntity<ClienteDto> createCliente(@Valid @RequestBody ClienteDto cliente) {
		ClienteDto clienteDTO = createClienteUseCase.createCliente(cliente);
		return ResponseEntity.ok(clienteDTO);
	}

	@GetMapping
	public ResponseEntity<List<ClienteDto>> search() {
		List<ClienteDto> clientes = getClienteUseCase.getAll();
		return ResponseEntity.ok(clientes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteDto> get(@PathVariable Long id) {
		ClienteDto cliente = getClienteByIdUseCase.getById(id);
		return ResponseEntity.ok(cliente);
	}

	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<ClienteDto> getByCpf(@PathVariable String cpf) {
		ClienteDto cliente = getClienteByCpfUseCase.retrieveCliente(cpf);
		return ResponseEntity.ok(cliente);
	}

}
