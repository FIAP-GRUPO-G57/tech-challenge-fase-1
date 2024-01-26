package br.com.fiap.lanchonete.infra.controllers;

import br.com.fiap.lanchonete.core.domain.dto.ClienteDto;
import br.com.fiap.lanchonete.core.usecases.cliente.CreateClienteDbUsecase;
import br.com.fiap.lanchonete.core.usecases.cliente.FindAllClientesDbUsecase;
import br.com.fiap.lanchonete.core.usecases.cliente.GetClienteByIdDbUsecase;
import br.com.fiap.lanchonete.core.usecases.cliente.RetrieveClienteDbUsecase;
import jakarta.validation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	private final CreateClienteDbUsecase createClienteDbUsecase;

	private final FindAllClientesDbUsecase findAllClientesDbUsecase;

	private final GetClienteByIdDbUsecase getClienteByIdDbUsecase;

	private final RetrieveClienteDbUsecase retrieveClienteDbUsecase;

	public ClienteController(CreateClienteDbUsecase createClienteDbUsecase, FindAllClientesDbUsecase findAllClientesDbUsecase,
			GetClienteByIdDbUsecase getClienteByIdDbUsecase, RetrieveClienteDbUsecase retrieveClienteDbUsecase) {
		this.createClienteDbUsecase = createClienteDbUsecase;
		this.findAllClientesDbUsecase = findAllClientesDbUsecase;
		this.getClienteByIdDbUsecase = getClienteByIdDbUsecase;
		this.retrieveClienteDbUsecase = retrieveClienteDbUsecase;
	}

	@PostMapping
	public ResponseEntity<ClienteDto> createCliente(@Valid @RequestBody ClienteDto cliente) {
		ClienteDto clienteDTO = createClienteDbUsecase.createCliente(cliente);
		return ResponseEntity.ok(clienteDTO);
	}

	@GetMapping
	public ResponseEntity<List<ClienteDto>> search() {
		List<ClienteDto> clientes = findAllClientesDbUsecase.getAll();
		return ResponseEntity.ok(clientes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteDto> get(@PathVariable Long id) {
		ClienteDto cliente = getClienteByIdDbUsecase.getById(id);
		return ResponseEntity.ok(cliente);
	}

	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<ClienteDto> getByCpf(@PathVariable String cpf) {
		ClienteDto cliente = retrieveClienteDbUsecase.retrieveCliente(cpf);
		return ResponseEntity.ok(cliente);
	}

}
