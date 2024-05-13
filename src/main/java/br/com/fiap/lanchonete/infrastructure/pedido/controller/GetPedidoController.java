package br.com.fiap.lanchonete.infrastructure.pedido.controller;

import br.com.fiap.lanchonete.entity.cliente.exception.ClienteNotFoundException;
import br.com.fiap.lanchonete.infrastructure.cliente.dto.ClientePublicData;
import br.com.fiap.lanchonete.usecase.cliente.GetClienteUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetPedidoController {

	private final GetClienteUsecase getClienteUsecase;

	public GetPedidoController(GetClienteUsecase getClienteUsecase) {
		this.getClienteUsecase = getClienteUsecase;
	}

	@GetMapping("/cliente/{id}")
	public ResponseEntity<ClientePublicData> get(@PathVariable Long id)  throws ClienteNotFoundException {
		return ResponseEntity.ok(new ClientePublicData(getClienteUsecase.execute(id)));
	}
}
