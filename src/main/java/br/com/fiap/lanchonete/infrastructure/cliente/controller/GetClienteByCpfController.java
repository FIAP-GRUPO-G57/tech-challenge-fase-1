package br.com.fiap.lanchonete.infrastructure.cliente.controller;

import br.com.fiap.lanchonete.entity.cliente.exception.ClienteNotFoundException;
import br.com.fiap.lanchonete.infrastructure.cliente.dto.ClientePublicData;
import br.com.fiap.lanchonete.usecase.cliente.GetClienteByCpfUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetClienteByCpfController {

	private final GetClienteByCpfUsecase getClienteByCpfUsecase;

	public GetClienteByCpfController(GetClienteByCpfUsecase getClienteByCpfUsecase) {
		this.getClienteByCpfUsecase = getClienteByCpfUsecase;
	}

	@GetMapping("/cliente/cpf/{cpf}")
	public ResponseEntity<ClientePublicData> get(@PathVariable String cpf) throws ClienteNotFoundException {
		return ResponseEntity.ok(new ClientePublicData(getClienteByCpfUsecase.execute(cpf)));
	}
}
