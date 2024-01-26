package br.com.fiap.lanchonete.infra.controllers;

import br.com.fiap.lanchonete.core.domain.dto.ProdutoDto;
import br.com.fiap.lanchonete.core.domain.entities.Produto;
import br.com.fiap.lanchonete.core.domain.enums.CategoriaEnum;
import br.com.fiap.lanchonete.core.usecases.produto.CreateProdutoDbUsecase;
import br.com.fiap.lanchonete.core.usecases.produto.DeleteProdutoDbUsecase;
import br.com.fiap.lanchonete.core.usecases.produto.FindProdutoByCategoriaDbUsecase;
import br.com.fiap.lanchonete.core.usecases.produto.GetProdutoByIdDbUsecase;
import br.com.fiap.lanchonete.core.usecases.produto.UpdateProdutoDbUsecase;
import br.com.fiap.lanchonete.main.exception.EnumValidationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

	private final GetProdutoByIdDbUsecase getProdutoByIdDbUsecase;

	private final FindProdutoByCategoriaDbUsecase findProdutoByCategoriaDbUsecase;

	private final CreateProdutoDbUsecase createProdutoDbUsecase;

	private final UpdateProdutoDbUsecase updateProdutoDbUsecase;

	private final DeleteProdutoDbUsecase deleteProdutoDbUsecase;

	@Autowired
	public ModelMapper modelMapper;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProdutoDto> get(@PathVariable(value = "id") Long id) {
		Produto produto = Optional.ofNullable(getProdutoByIdDbUsecase.get(id))
				.orElseThrow(() -> new EntityNotFoundException("Produto nao encontrado para o id :: " + id));
		return ResponseEntity.ok().body(modelMapper.map(produto, ProdutoDto.class));
	}

	@GetMapping
	public ResponseEntity<List<ProdutoDto>> search(
			@RequestParam(name = "categoria", required = false) String categoria) {
		CategoriaEnum categoriaEnum = null;

		try {
			if (Objects.nonNull(categoria)) {
				categoriaEnum = CategoriaEnum.valueOf(categoria);
			}
		} catch (IllegalArgumentException e) {
			throw new EnumValidationException("categoria", categoria);
		}

		List<ProdutoDto> produtos = findProdutoByCategoriaDbUsecase.findByCategoria(categoriaEnum).stream()
				.map(produto -> modelMapper.map(produto, ProdutoDto.class)).toList();
		return ResponseEntity.ok(produtos);
	}

	@PostMapping
	public ResponseEntity<ProdutoDto> post(@Validated @RequestBody ProdutoDto produtoDTO) {
		Produto produto = createProdutoDbUsecase.create(modelMapper.map(produtoDTO, Produto.class));
		return new ResponseEntity<ProdutoDto>(modelMapper.map(produto, ProdutoDto.class), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ProdutoDto> put(@PathVariable Long id, @Validated @RequestBody ProdutoDto produtoDTO) {
		Produto produto = updateProdutoDbUsecase.update(id, modelMapper.map(produtoDTO, Produto.class));

		if (Objects.nonNull(produto)) {
			return ResponseEntity.ok(modelMapper.map(produto, ProdutoDto.class));
		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
		Produto produto = Optional.ofNullable(getProdutoByIdDbUsecase.get(id))
				.orElseThrow(() -> new EntityNotFoundException("Produto nao encontrado para o id :: " + id));
		deleteProdutoDbUsecase.delete(produto);
		return ResponseEntity.noContent().<Void>build();
	}
}
