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
public class UpdateProdutoUsecase {

	private final ProdutoRepositoryPort produtoPort;
	
	public Produto update(Long id, Produto produto) {
		if (Objects.nonNull(id)) {
			produto.setId(id);
			Produto produto1 = produtoPort.get(id);

			if (Objects.isNull(produto1))
				return null;

			return produtoPort.save(produto);
		}
		return null;
	}

}
