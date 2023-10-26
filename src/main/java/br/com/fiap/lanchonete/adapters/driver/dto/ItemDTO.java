package br.com.fiap.lanchonete.adapters.driver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDTO {
    private Long id;
    private ProdutoDTO produto;
    private BigDecimal preco;
}
