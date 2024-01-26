package br.com.fiap.lanchonete.core.usecases.produto;

import br.com.fiap.lanchonete.core.domain.entities.Produto;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.ProdutoRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GetProdutoByIdUsecase {

	private final ProdutoRepositoryPort produtoPort;

	public Produto get(Long id) {
		return produtoPort.get(id);
	}

}
