package br.com.fiap.lanchonete.core.usecases.produto;

import br.com.fiap.lanchonete.core.domain.entities.Produto;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.ProdutoRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteProdutoUsecase {

	private final ProdutoRepositoryPort produtoPort;

    public void delete(Produto produto) {
        if(Objects.nonNull(produto))
        	produtoPort.delete(produto);
    }

}
