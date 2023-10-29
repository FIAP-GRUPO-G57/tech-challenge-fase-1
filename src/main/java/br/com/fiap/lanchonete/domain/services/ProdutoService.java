package br.com.fiap.lanchonete.domain.services;

import br.com.fiap.lanchonete.application.ports.input.usecase.*;
import br.com.fiap.lanchonete.application.ports.output.ProdutoOutputPort;
import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.vo.Categoria;
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
        return produtoOutputPort.get(id);
    }

    @Override
    public List<Produto> findByCategoria(Categoria categoria) {
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
            Produto produto1 = produtoOutputPort.get(id);

            if (Objects.isNull(produto1))
                return null;
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
