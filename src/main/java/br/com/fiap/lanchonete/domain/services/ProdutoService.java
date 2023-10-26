package br.com.fiap.lanchonete.domain.services;

import br.com.fiap.lanchonete.application.ports.input.usecase.*;
import br.com.fiap.lanchonete.application.ports.output.ProdutoOutputPort;
import br.com.fiap.lanchonete.domain.entities.Produto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@Transactional
@RequiredArgsConstructor
public class ProdutoService implements GetProdutoByIdUseCase, GetProdutoUseCase, CreateProdutoUseCase, UpdateProdutoUseCase, DeleteProdutoUseCase {

    private final ProdutoOutputPort produtoOutputPort;

    @Override
    public Produto get(Long id) {
        return Objects.nonNull(id)?produtoOutputPort.get(id):null;
    }

    @Override
    public List<Produto> findByCategoria(String categoria) {
        if(Objects.nonNull(categoria))
            return produtoOutputPort.findByCategoria(categoria);
        return produtoOutputPort.findAll();
    }

    @Override
    public Produto create(Produto produto) {
        return produtoOutputPort.save(produto);
    }

    @Override
    public Produto update(Long id, Produto produto) {
        if (Objects.nonNull(id)) {
            produto.setId(id);
            return produtoOutputPort.save(produto);
        }
        return null;
    }

    @Override
    public void delete(Produto produto) {
        if(Objects.nonNull(produto))
            produtoOutputPort.delete(produto);
    }
}
