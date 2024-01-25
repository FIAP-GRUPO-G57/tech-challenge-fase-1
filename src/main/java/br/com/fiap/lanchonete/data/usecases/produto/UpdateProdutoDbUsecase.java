package br.com.fiap.lanchonete.data.usecases.produto;

import br.com.fiap.lanchonete.data.protocols.db.produto.GetProdutoRepository;
import br.com.fiap.lanchonete.data.protocols.db.produto.SaveProdutoRepository;
import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.usecases.produto.UpdateProdutoUsecase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateProdutoDbUsecase implements UpdateProdutoUsecase {

	private final GetProdutoRepository getProdutoRepository;
	
	private final SaveProdutoRepository saveProdutoRepository;

	@Override
	public Produto update(Long id, Produto produto) {
		if (Objects.nonNull(id)) {
			produto.setId(id);
			Produto produto1 = getProdutoRepository.get(id);

			if (Objects.isNull(produto1))
				return null;

			return saveProdutoRepository.save(produto);
		}
		return null;
	}

}
