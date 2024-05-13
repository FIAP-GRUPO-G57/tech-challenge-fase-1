package br.com.fiap.lanchonete.infrastructure.cliente.controller;

import br.com.fiap.lanchonete.infrastructure.cliente.dto.ClientePublicData;
import br.com.fiap.lanchonete.infrastructure.cliente.dto.ClienteRegistrationData;
import br.com.fiap.lanchonete.usecase.cliente.CreateClienteUsecase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreateClienteController {

	private final CreateClienteUsecase createClienteUsecase;

	public CreateClienteController(CreateClienteUsecase createClienteUsecase) {
		this.createClienteUsecase = createClienteUsecase;
	}

	@PostMapping("/cliente")
	public ResponseEntity<ClientePublicData> createCliente(@Valid @RequestBody ClienteRegistrationData cliente) {
		return ResponseEntity.ok(new ClientePublicData(createClienteUsecase.execute(cliente)));
	}
}
