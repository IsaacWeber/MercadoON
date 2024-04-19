package br.com.mercadoon.rest;

import br.com.mercadoon.dto.CompraDto;
import br.com.mercadoon.requestmodel.CompraRequestModel;
import br.com.mercadoon.service.CompraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compra")
public class CompraRestController {

    private CompraService compraService;

    @Autowired
    public CompraRestController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping
    public List<CompraDto> listar() {
        return compraService.listar();
    }

    @GetMapping("/{id}")
    public CompraDto buscar(@PathVariable Long id) {
        return compraService.buscar(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<CompraDto> buscarPorClienteId(@PathVariable Long clienteId) {
        return compraService.buscarPorClienteId(clienteId);
    }

    @PostMapping
    public ResponseEntity<CompraDto> add(@Valid @RequestBody CompraRequestModel compraRequestModel){
        return new ResponseEntity<>(compraService.add(compraRequestModel), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public CompraDto atualizar(@PathVariable Long id, @Valid @RequestBody CompraRequestModel compraRequestModel) {
        return compraService.atualizar(id, compraRequestModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        compraService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTodos() {
        compraService.deletarTodos();
        return ResponseEntity.noContent().build();
    }
}
