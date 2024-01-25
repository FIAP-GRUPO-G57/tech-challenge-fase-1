package br.com.fiap.lanchonete.adapters.driver.rest;

import br.com.fiap.lanchonete.adapters.driver.dto.ClienteDTO;
import br.com.fiap.lanchonete.application.ports.input.usecase.CreateClienteUseCase;
import br.com.fiap.lanchonete.application.ports.input.usecase.GetClienteByCpfUseCase;
import br.com.fiap.lanchonete.application.ports.input.usecase.GetClienteByIdUseCase;
import br.com.fiap.lanchonete.application.ports.input.usecase.GetClienteUseCase;
import jakarta.validation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteRestAdapter {


    private final  CreateClienteUseCase createClienteUseCase;

    private final  GetClienteUseCase getClienteUseCase;

    private final GetClienteByIdUseCase getClienteByIdUseCase;

    private final GetClienteByCpfUseCase getClienteByCpfUseCase;

    public ClienteRestAdapter(CreateClienteUseCase createClienteUseCase, GetClienteUseCase getClienteUseCase, GetClienteByIdUseCase getClienteByIdUseCase, GetClienteByCpfUseCase getClienteByCpfUseCase) {
        this.createClienteUseCase = createClienteUseCase;
        this.getClienteUseCase = getClienteUseCase;
        this.getClienteByIdUseCase = getClienteByIdUseCase;
        this.getClienteByCpfUseCase = getClienteByCpfUseCase;
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody ClienteDTO cliente) {
            ClienteDTO clienteDTO = createClienteUseCase.createCliente(cliente);
            return ResponseEntity.ok(clienteDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> search() {
        List<ClienteDTO> clientes = getClienteUseCase.getAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> get(@PathVariable Long id) {
        ClienteDTO cliente = getClienteByIdUseCase.getById(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteDTO> getByCpf(@PathVariable  String cpf) {
        ClienteDTO cliente = getClienteByCpfUseCase.retrieveCliente(cpf);
        return ResponseEntity.ok(cliente);
    }


}
