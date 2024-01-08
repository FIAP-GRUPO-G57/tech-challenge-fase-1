package br.com.fiap.lanchonete.data.usecases.produto;

import br.com.fiap.lanchonete.data.protocols.db.produto.DeleteProdutoRepository;
import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.usecases.produto.DeleteProdutoUsecase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteProdutoDbUsecase implements DeleteProdutoUsecase {

	private final DeleteProdutoRepository deleteProdutoRepository;

	@Override
    public void delete(Produto produto) {
        if(Objects.nonNull(produto))
        	deleteProdutoRepository.delete(produto);
    }

}
