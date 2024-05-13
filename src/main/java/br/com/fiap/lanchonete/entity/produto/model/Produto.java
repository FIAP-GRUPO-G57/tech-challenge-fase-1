package br.com.fiap.lanchonete.entity.produto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    private	Long id;
    private String nome;
    private Categoria categoria;
    private BigDecimal preco;
    private String descricao;
    private List<String> imagens;
}
