package br.com.fiap.lanchonete.infra.controllers;

import br.com.fiap.lanchonete.core.domain.dto.ProdutoDto;
import br.com.fiap.lanchonete.core.domain.entities.Produto;
import br.com.fiap.lanchonete.core.domain.enums.CategoriaEnum;
import br.com.fiap.lanchonete.core.usecases.produto.CreateProdutoUsecase;
import br.com.fiap.lanchonete.core.usecases.produto.DeleteProdutoUsecase;
import br.com.fiap.lanchonete.core.usecases.produto.FindProdutoByCategoriaUsecase;
import br.com.fiap.lanchonete.core.usecases.produto.GetProdutoByIdUsecase;
import br.com.fiap.lanchonete.core.usecases.produto.UpdateProdutoUsecase;
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

	private final GetProdutoByIdUsecase getProdutoByIdUsecase;

	private final FindProdutoByCategoriaUsecase findProdutoByCategoriaUsecase;

	private final CreateProdutoUsecase createProdutoUsecase;

	private final UpdateProdutoUsecase updateProdutoUsecase;

	private final DeleteProdutoUsecase deleteProdutoUsecase;

	@Autowired
	public ModelMapper modelMapper;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProdutoDto> get(@PathVariable(value = "id") Long id) {
		Produto produto = Optional.ofNullable(getProdutoByIdUsecase.get(id))
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

		List<ProdutoDto> produtos = findProdutoByCategoriaUsecase.findByCategoria(categoriaEnum).stream()
				.map(produto -> modelMapper.map(produto, ProdutoDto.class)).toList();
		return ResponseEntity.ok(produtos);
	}

	@PostMapping
	public ResponseEntity<ProdutoDto> post(@Validated @RequestBody ProdutoDto produtoDTO) {
		Produto produto = createProdutoUsecase.create(modelMapper.map(produtoDTO, Produto.class));
		return new ResponseEntity<ProdutoDto>(modelMapper.map(produto, ProdutoDto.class), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ProdutoDto> put(@PathVariable Long id, @Validated @RequestBody ProdutoDto produtoDTO) {
		Produto produto = updateProdutoUsecase.update(id, modelMapper.map(produtoDTO, Produto.class));

		if (Objects.nonNull(produto)) {
			return ResponseEntity.ok(modelMapper.map(produto, ProdutoDto.class));
		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
		Produto produto = Optional.ofNullable(getProdutoByIdUsecase.get(id))
				.orElseThrow(() -> new EntityNotFoundException("Produto nao encontrado para o id :: " + id));
		deleteProdutoUsecase.delete(produto);
		return ResponseEntity.noContent().<Void>build();
	}
}
