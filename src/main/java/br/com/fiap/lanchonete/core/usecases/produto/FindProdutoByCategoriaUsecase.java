package br.com.fiap.lanchonete.core.usecases.produto;

import br.com.fiap.lanchonete.core.domain.entities.Produto;
import br.com.fiap.lanchonete.core.domain.enums.CategoriaEnum;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.ProdutoRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@Transactional
@RequiredArgsConstructor
public class FindProdutoByCategoriaUsecase {

    private final ProdutoRepositoryPort produtoPort;
    
    public List<Produto> findByCategoria(CategoriaEnum categoria) {
        if(Objects.nonNull(categoria))
            return produtoPort.findByCategoria(categoria);
        return produtoPort.findAll();
    }

}
