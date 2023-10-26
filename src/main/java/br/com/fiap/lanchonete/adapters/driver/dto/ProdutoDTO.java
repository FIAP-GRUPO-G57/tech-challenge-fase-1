package br.com.fiap.lanchonete.adapters.driver.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProdutoDTO{
    private Long id;
    @NotNull private String nome;
    @NotNull private String categoria;
    @NotNull private BigDecimal preco;
    private String descricao;
    private List<String> imagens;
}
