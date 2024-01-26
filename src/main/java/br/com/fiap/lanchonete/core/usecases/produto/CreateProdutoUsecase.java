package br.com.fiap.lanchonete.core.usecases.produto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.com.fiap.lanchonete.core.domain.entities.Produto;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.ProdutoRepositoryPort;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateProdutoUsecase {

    private final ProdutoRepositoryPort produtoPort;

    public Produto create(Produto produto) {
        return produtoPort.save(produto);
    }

}