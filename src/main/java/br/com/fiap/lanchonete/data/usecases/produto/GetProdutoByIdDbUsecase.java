package br.com.fiap.lanchonete.data.usecases.produto;

import br.com.fiap.lanchonete.data.protocols.db.produto.GetProdutoRepository;
import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.usecases.produto.GetProdutoByIdUsecase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GetProdutoByIdDbUsecase implements GetProdutoByIdUsecase {

	private final GetProdutoRepository getProdutoRepository;

	@Override
	public Produto get(Long id) {
		return getProdutoRepository.get(id);
	}

}
