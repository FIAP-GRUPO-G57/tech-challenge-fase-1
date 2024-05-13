package br.com.fiap.lanchonete.infrastructure.cliente.controller;

import br.com.fiap.lanchonete.entity.cliente.exception.ClienteNotFoundException;
import br.com.fiap.lanchonete.infrastructure.cliente.dto.ClientePublicData;
import br.com.fiap.lanchonete.usecase.cliente.GetClienteUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GetClienteController {

	private final GetClienteUsecase getClienteUsecase;

	public GetClienteController(GetClienteUsecase getClienteUsecase) {
		this.getClienteUsecase = getClienteUsecase;
	}

	@GetMapping("/cliente/{id}")
	public ResponseEntity<ClientePublicData> get(@PathVariable Long id)  throws ClienteNotFoundException {
		return ResponseEntity.ok(new ClientePublicData(getClienteUsecase.execute(id)));
	}
}
