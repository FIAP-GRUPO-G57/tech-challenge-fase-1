package br.com.fiap.lanchonete.data.usecases.produto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.com.fiap.lanchonete.data.protocols.db.produto.SaveProdutoRepository;
import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.usecases.produto.CreateProdutoUsecase;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateProdutoDbUsecase implements CreateProdutoUsecase {

    private final SaveProdutoRepository saveProdutoRepository;

    @Override
    public Produto create(Produto produto) {
        return saveProdutoRepository.save(produto);
    }

}