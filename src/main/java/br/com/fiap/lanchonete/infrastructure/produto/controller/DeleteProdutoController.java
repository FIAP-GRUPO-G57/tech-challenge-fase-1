package br.com.fiap.lanchonete.infrastructure.produto.controller;

import br.com.fiap.lanchonete.entity.produto.exception.ProdutoNotFoundException;
import br.com.fiap.lanchonete.infrastructure.cliente.dto.ClientePublicData;
import br.com.fiap.lanchonete.infrastructure.produto.dto.ProdutoPublicData;
import br.com.fiap.lanchonete.usecase.produto.DeleteProdutoUsecase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteProdutoController {

	private final DeleteProdutoUsecase deleteProdutoUsecase;

	public DeleteProdutoController(DeleteProdutoUsecase deleteProdutoUsecase) {
		this.deleteProdutoUsecase = deleteProdutoUsecase;
	}

	@DeleteMapping("/produtos/{id}")
	public ResponseEntity<ProdutoPublicData> deleteProduto(@PathVariable Long id) throws ProdutoNotFoundException {
		return ResponseEntity.ok(new ProdutoPublicData(deleteProdutoUsecase.execute(id)));
	}
}
