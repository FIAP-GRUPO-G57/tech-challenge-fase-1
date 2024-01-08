package br.com.fiap.lanchonete.infra.controllers;

import br.com.fiap.lanchonete.domain.dto.ClienteDto;
import br.com.fiap.lanchonete.domain.usecases.cliente.CreateClienteUsecase;
import br.com.fiap.lanchonete.domain.usecases.cliente.GetClienteUsecase;
import jakarta.validation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	private final CreateClienteUsecase createClienteUsecase;

	private final GetClienteUsecase getClienteUsecase;

	public ClienteController(CreateClienteUsecase createClienteUseCase, GetClienteUsecase getClienteUseCase) {
		this.createClienteUsecase = createClienteUseCase;
		this.getClienteUsecase = getClienteUseCase;
	}

	@PostMapping
	public ResponseEntity<ClienteDto> createCliente(@Valid @RequestBody ClienteDto cliente) {
		ClienteDto clienteDTO = createClienteUsecase.createCliente(cliente);
		return ResponseEntity.ok(clienteDTO);
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<ClienteDto> retrieveCliente(@PathVariable String cpf) {
		ClienteDto cliente = getClienteUsecase.retrieveCliente(cpf);
		return ResponseEntity.ok(cliente);
	}

}
