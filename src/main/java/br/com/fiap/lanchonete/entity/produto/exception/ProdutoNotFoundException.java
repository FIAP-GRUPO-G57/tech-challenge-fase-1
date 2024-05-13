package br.com.fiap.lanchonete.entity.produto.exception;

public class ProdutoNotFoundException extends Exception {

    public ProdutoNotFoundException() {
        super("Cliente not found!!");
    }
}
