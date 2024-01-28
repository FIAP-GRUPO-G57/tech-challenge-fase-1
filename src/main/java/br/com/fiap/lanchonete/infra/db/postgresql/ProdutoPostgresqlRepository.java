package br.com.fiap.lanchonete.infra.db.postgresql;

import br.com.fiap.lanchonete.core.domain.entities.Produto;
import br.com.fiap.lanchonete.core.domain.enums.CategoriaEnum;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.ProdutoRepositoryPort;
import br.com.fiap.lanchonete.infra.db.entities.ProdutoEntity;
import br.com.fiap.lanchonete.infra.db.repositories.ProdutoRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoPostgresqlRepository implements ProdutoRepositoryPort {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	public ModelMapper modelMapper;

	@Override
	public Produto get(Long id) {
		return produtoRepository.findById(id).map(produtoData -> modelMapper.map(produtoData, Produto.class))
				.orElse(null);
	}

	@Override
	public List<Produto> findAll() {
		return produtoRepository.findAll().stream().map(produtoData -> modelMapper.map(produtoData, Produto.class))
				.toList();
	}

	@Override
	public List<Produto> findByCategoria(CategoriaEnum categoria) {
		return produtoRepository.findByCategoria(categoria).stream()
				.map(produtoData -> modelMapper.map(produtoData, Produto.class)).toList();
	}

	@Override
	public Produto save(Produto produto) {
		return modelMapper.map(produtoRepository.save(modelMapper.map(produto, ProdutoEntity.class)), Produto.class);
	}

	@Override
	public void delete(Produto produto) {
		produtoRepository.delete(modelMapper.map(produto, ProdutoEntity.class));
	}
}
