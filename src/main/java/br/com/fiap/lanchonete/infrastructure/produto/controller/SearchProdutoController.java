package br.com.fiap.lanchonete.infrastructure.produto.controller;

import br.com.fiap.lanchonete.entity.produto.model.Produto;
import br.com.fiap.lanchonete.infrastructure.produto.dto.ProdutoPublicData;
import br.com.fiap.lanchonete.usecase.produto.SearchProdutoUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchProdutoController {

	private final SearchProdutoUsecase searchProdutoUsecase;

	public SearchProdutoController(SearchProdutoUsecase searchProdutoUsecase) {
		this.searchProdutoUsecase = searchProdutoUsecase;
	}

	@GetMapping("/produtos")
	public ResponseEntity<List<ProdutoPublicData>> search(@RequestParam(name = "categoria", required = false) String categoria) {
		List<Produto> produtos = searchProdutoUsecase.execute(categoria);
		return ResponseEntity.ok(produtos.stream().map(ProdutoPublicData::new).toList());
	}
}
