package br.com.fiap.lanchonete.adapters.driver.rest;

import br.com.fiap.lanchonete.adapters.driver.dto.ClienteDTO;
import br.com.fiap.lanchonete.application.usecase.CreateClienteUseCase;
import br.com.fiap.lanchonete.application.usecase.GetClienteUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteRestAdapter {


    private final  CreateClienteUseCase createClienteUseCase;


    private final  GetClienteUseCase getClienteUseCase;

    public ClienteRestAdapter(CreateClienteUseCase createClienteUseCase, GetClienteUseCase getClienteUseCase) {
        this.createClienteUseCase = createClienteUseCase;
        this.getClienteUseCase = getClienteUseCase;
    }


    //Cria um cliente
    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(ClienteDTO cliente) {

        ClienteDTO clienteDTO =  createClienteUseCase.createCliente(cliente);

        return ResponseEntity.ok(clienteDTO);
    }
    @GetMapping("/{cpf}")
    //Identifica o cliente por CPF
    public ResponseEntity<ClienteDTO> retrieveCliente(@PathVariable  String cpf) {

        ClienteDTO cliente = getClienteUseCase.retrieveCliente(cpf);

        return ResponseEntity.ok(cliente);
    }


}
