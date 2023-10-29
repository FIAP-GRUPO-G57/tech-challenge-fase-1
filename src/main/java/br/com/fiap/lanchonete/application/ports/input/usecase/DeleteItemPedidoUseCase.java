package br.com.fiap.lanchonete.application.ports.input.usecase;

public interface DeleteItemPedidoUseCase {
    void deleteItemPedido(Long id, Long idItem);
}
