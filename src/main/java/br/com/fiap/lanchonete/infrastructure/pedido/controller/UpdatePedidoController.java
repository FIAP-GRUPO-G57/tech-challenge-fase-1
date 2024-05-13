package br.com.fiap.lanchonete.infrastructure.pedido.controller;

import br.com.fiap.lanchonete.infrastructure.cliente.dto.ClientePublicData;
import br.com.fiap.lanchonete.infrastructure.cliente.dto.ClienteRegistrationData;
import br.com.fiap.lanchonete.usecase.cliente.CreateClienteUsecase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdatePedidoController {

	private final CreateClienteUsecase createClienteUsecase;

	public UpdatePedidoController(CreateClienteUsecase createClienteUsecase) {
		this.createClienteUsecase = createClienteUsecase;
	}

	@PostMapping("/cliente")
	public ResponseEntity<ClientePublicData> createCliente(@Valid @RequestBody ClienteRegistrationData cliente) {
		return ResponseEntity.ok(new ClientePublicData(createClienteUsecase.execute(cliente)));
	}
}
