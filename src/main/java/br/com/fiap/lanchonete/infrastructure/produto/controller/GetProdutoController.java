package br.com.fiap.lanchonete.infrastructure.produto.controller;

import br.com.fiap.lanchonete.entity.produto.exception.ProdutoNotFoundException;
import br.com.fiap.lanchonete.infrastructure.produto.dto.ProdutoPublicData;
import br.com.fiap.lanchonete.usecase.produto.GetProdutoUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProdutoController {

	private final GetProdutoUsecase getProdutoUsecase;

	public GetProdutoController(GetProdutoUsecase getProdutoUsecase) {
		this.getProdutoUsecase = getProdutoUsecase;
	}

	@GetMapping("/produtos/{id}")
	public ResponseEntity<ProdutoPublicData> get(@PathVariable Long id)  throws ProdutoNotFoundException {
		return ResponseEntity.ok(new ProdutoPublicData(getProdutoUsecase.execute(id)));
	}
}
