package br.com.fiap.lanchonete.infrastructure.cliente.controller;

import br.com.fiap.lanchonete.entity.cliente.model.Cliente;
import br.com.fiap.lanchonete.infrastructure.cliente.dto.ClientePublicData;
import br.com.fiap.lanchonete.usecase.cliente.SearchClienteUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchClienteController {

	private final SearchClienteUsecase searchClienteUsecase;

	public SearchClienteController(SearchClienteUsecase searchClienteUsecase) {
		this.searchClienteUsecase = searchClienteUsecase;
	}

	@PostMapping("/cliente")
	public ResponseEntity<List<ClientePublicData>> search() {
		List<Cliente> clientes = searchClienteUsecase.execute();
		return ResponseEntity.ok(clientes.stream().map(ClientePublicData::new).toList());
	}
}
