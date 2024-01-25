package br.com.fiap.lanchonete.data.usecases.produto;

import br.com.fiap.lanchonete.data.protocols.db.produto.FindAllProdutosRepository;
import br.com.fiap.lanchonete.data.protocols.db.produto.FindProdutoByCategoriaRepository;
import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.enums.CategoriaEnum;
import br.com.fiap.lanchonete.domain.usecases.produto.FindProdutoByCategoriaUsecase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@Transactional
@RequiredArgsConstructor
public class FindProdutoByCategoriaDbUsecase implements FindProdutoByCategoriaUsecase {

    private final FindProdutoByCategoriaRepository findProdutoByCategoriaRepository;
    
    private final FindAllProdutosRepository findAllProdutosRepository;

    @Override
    public List<Produto> findByCategoria(CategoriaEnum categoria) {
        if(Objects.nonNull(categoria))
            return findProdutoByCategoriaRepository.findByCategoria(categoria);
        return findAllProdutosRepository.findAll();
    }

}
