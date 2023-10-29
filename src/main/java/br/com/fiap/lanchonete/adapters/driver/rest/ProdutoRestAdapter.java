package br.com.fiap.lanchonete.adapters.driver.rest;

import br.com.fiap.lanchonete.adapters.driver.dto.ProdutoDTO;
import br.com.fiap.lanchonete.adapters.driver.exception.EnumValidationException;
import br.com.fiap.lanchonete.application.ports.input.usecase.*;
import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.vo.Categoria;
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
public class ProdutoRestAdapter {

    private final GetProdutoByIdUseCase getProdutoByIdUseCase;

    private final GetProdutoUseCase getProdutoUseCase;

    private final CreateProdutoUseCase createProdutoUseCase;

    private final UpdateProdutoUseCase updateProdutoUseCase;

    private final DeleteProdutoUseCase deleteProdutoUseCase;

    @Autowired
    public ModelMapper modelMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> get(@PathVariable(value="id") Long id) {
        Produto produto = Optional.ofNullable(getProdutoByIdUseCase.get(id))
                .orElseThrow(() -> new EntityNotFoundException("Produto nao encontrado para o id :: " + id));
        return ResponseEntity.ok().body(modelMapper.map(produto, ProdutoDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> search(@RequestParam(name="categoria", required = false) String categoria) {

        Categoria categoriaEnum = null;
        try {
            if (Objects.nonNull(categoria)) {
                categoriaEnum = Categoria.valueOf(categoria);
            }
        } catch (IllegalArgumentException e) {

            throw new EnumValidationException("categoria",categoria);
        }

        List<ProdutoDTO> produtos = getProdutoUseCase.findByCategoria(categoriaEnum).stream()
                .map(produto-> modelMapper.map(produto, ProdutoDTO.class))
                .toList();
        return ResponseEntity.ok(produtos);
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> post(@Validated @RequestBody ProdutoDTO produtoDTO) {
        Produto produto =  createProdutoUseCase.create(modelMapper.map(produtoDTO, Produto.class));
        return new ResponseEntity(modelMapper.map(produto, ProdutoDTO.class), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> put(@PathVariable Long id, @Validated @RequestBody ProdutoDTO produtoDTO) {
        Produto produto = updateProdutoUseCase.update(id, modelMapper.map(produtoDTO, Produto.class));
        if(Objects.nonNull(produto)) {
            return ResponseEntity.ok(modelMapper.map(produto, ProdutoDTO.class));
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value="id") Long id){
        Produto produto = Optional.ofNullable(getProdutoByIdUseCase.get(id))
                .orElseThrow(() -> new EntityNotFoundException("Produto nao encontrado para o id :: " + id));
        deleteProdutoUseCase.delete(produto);
        return ResponseEntity.noContent().<Void>build();
    }
}
