package br.com.fiap.lanchonete.infrastructure.produto.controller;

import br.com.fiap.lanchonete.entity.produto.exception.ProdutoNotFoundException;
import br.com.fiap.lanchonete.infrastructure.cliente.dto.ClientePublicData;
import br.com.fiap.lanchonete.infrastructure.cliente.dto.ClienteRegistrationData;
import br.com.fiap.lanchonete.infrastructure.produto.dto.ProdutoPublicData;
import br.com.fiap.lanchonete.infrastructure.produto.dto.ProdutoRegistrationData;
import br.com.fiap.lanchonete.infrastructure.produto.dto.ProdutoUpdateData;
import br.com.fiap.lanchonete.usecase.cliente.CreateClienteUsecase;
import br.com.fiap.lanchonete.usecase.produto.UpdateProdutoUsecase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UpdateProdutoController {

	private final UpdateProdutoUsecase updateProdutoUsecase;

	public UpdateProdutoController(UpdateProdutoUsecase updateProdutoUsecase) {
		this.updateProdutoUsecase = updateProdutoUsecase;
	}

	@PutMapping("/produtos/{id}")
	public ResponseEntity<ProdutoPublicData> createProduto(@PathVariable Long id, @Valid @RequestBody ProdutoUpdateData produto) throws ProdutoNotFoundException {
		return ResponseEntity.ok(new ProdutoPublicData(updateProdutoUsecase.execute(id, produto)));
	}
}
