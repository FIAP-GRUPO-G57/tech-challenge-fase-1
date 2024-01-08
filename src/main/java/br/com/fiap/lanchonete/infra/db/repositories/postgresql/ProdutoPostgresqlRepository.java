package br.com.fiap.lanchonete.infra.db.repositories.postgresql;

import br.com.fiap.lanchonete.data.protocols.db.produto.DeleteProdutoRepository;
import br.com.fiap.lanchonete.data.protocols.db.produto.FindAllProdutosRepository;
import br.com.fiap.lanchonete.data.protocols.db.produto.FindProdutoByCategoriaRepository;
import br.com.fiap.lanchonete.data.protocols.db.produto.GetProdutoRepository;
import br.com.fiap.lanchonete.data.protocols.db.produto.SaveProdutoRepository;
import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.enums.CategoriaEnum;
import br.com.fiap.lanchonete.infra.db.repositories.ProdutoRepository;
import br.com.fiap.lanchonete.infra.db.schemas.ProdutoSchema;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoPostgresqlRepository implements GetProdutoRepository, FindAllProdutosRepository,
		FindProdutoByCategoriaRepository, SaveProdutoRepository, DeleteProdutoRepository {

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
		return modelMapper.map(produtoRepository.save(modelMapper.map(produto, ProdutoSchema.class)), Produto.class);
	}

	@Override
	public void delete(Produto produto) {
		produtoRepository.delete(modelMapper.map(produto, ProdutoSchema.class));
	}
}
