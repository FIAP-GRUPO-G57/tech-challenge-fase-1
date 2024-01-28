package br.com.fiap.lanchonete.infra.controllers;

import br.com.fiap.lanchonete.core.domain.dto.ClienteDto;
import br.com.fiap.lanchonete.core.usecases.cliente.CreateClienteUsecase;
import br.com.fiap.lanchonete.core.usecases.cliente.FindAllClientesUsecase;
import br.com.fiap.lanchonete.core.usecases.cliente.GetClienteByIdUsecase;
import br.com.fiap.lanchonete.core.usecases.cliente.RetrieveClienteUsecase;
import jakarta.validation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	private final CreateClienteUsecase createClienteUsecase;

	private final FindAllClientesUsecase findAllClientesUsecase;

	private final GetClienteByIdUsecase getClienteByIdUsecase;

	private final RetrieveClienteUsecase retrieveClienteUsecase;

	public ClienteController(CreateClienteUsecase createClienteDbUsecase, FindAllClientesUsecase findAllClientesDbUsecase,
			GetClienteByIdUsecase getClienteByIdDbUsecase, RetrieveClienteUsecase retrieveClienteDbUsecase) {
		this.createClienteUsecase = createClienteDbUsecase;
		this.findAllClientesUsecase = findAllClientesDbUsecase;
		this.getClienteByIdUsecase = getClienteByIdDbUsecase;
		this.retrieveClienteUsecase = retrieveClienteDbUsecase;
	}

	@PostMapping
	public ResponseEntity<ClienteDto> createCliente(@Valid @RequestBody ClienteDto cliente) {
		ClienteDto clienteDTO = createClienteUsecase.createCliente(cliente);
		return ResponseEntity.ok(clienteDTO);
	}

	@GetMapping
	public ResponseEntity<List<ClienteDto>> search() {
		List<ClienteDto> clientes = findAllClientesUsecase.getAll();
		return ResponseEntity.ok(clientes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteDto> get(@PathVariable Long id) {
		ClienteDto cliente = getClienteByIdUsecase.getById(id);
		return ResponseEntity.ok(cliente);
	}

	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<ClienteDto> getByCpf(@PathVariable String cpf) {
		ClienteDto cliente = retrieveClienteUsecase.retrieveCliente(cpf);
		return ResponseEntity.ok(cliente);
	}

}
