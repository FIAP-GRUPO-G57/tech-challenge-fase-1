package br.com.fiap.lanchonete.entity.cliente.exception;

public class ClienteNotFoundException extends Exception {

    public ClienteNotFoundException() {
        super("Cliente not found!!");
    }
}
