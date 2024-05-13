package br.com.fiap.lanchonete.infrastructure.produto.controller;

import br.com.fiap.lanchonete.infrastructure.produto.dto.ProdutoPublicData;
import br.com.fiap.lanchonete.infrastructure.produto.dto.ProdutoRegistrationData;
import br.com.fiap.lanchonete.usecase.produto.CreateProdutoUsecase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateProdutoController {

	private final CreateProdutoUsecase createProdutoUsecase;

	public CreateProdutoController(CreateProdutoUsecase createProdutoUsecase) {
		this.createProdutoUsecase = createProdutoUsecase;
	}

	@PostMapping("/produtos")
	public ResponseEntity<ProdutoPublicData> createProduto(@Valid @RequestBody ProdutoRegistrationData produto) {
		return ResponseEntity.ok(new ProdutoPublicData(createProdutoUsecase.execute(produto)));
	}
}
