package br.com.mercadoon.rest;

import br.com.mercadoon.dto.CartaoDto;
import br.com.mercadoon.entity.Cartao;
import br.com.mercadoon.service.CartaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartao")
public class CartaoRestController {

    private CartaoService cartaoService;

    @Autowired
    public CartaoRestController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @GetMapping
    public List<CartaoDto> listar() {
        return cartaoService.listar();
    }

    @GetMapping("/{id}")
    public CartaoDto buscar(@PathVariable Long id) {
        return cartaoService.buscar(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<CartaoDto> buscarCartoesPorClienteId(@PathVariable Long clienteId) {
        return cartaoService.buscarCartoesPorClienteId(clienteId);
    }

    @PostMapping("/{clienteId}")
    public ResponseEntity<CartaoDto> add(@PathVariable Long clienteId, @Valid @RequestBody Cartao cartao) {
        return new ResponseEntity<>(cartaoService.add(clienteId, cartao), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public CartaoDto atualiar(@PathVariable Long id, @Valid @RequestBody Cartao cartao) {
        return cartaoService.atualizar(id, cartao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cartaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTodos() {
        cartaoService.deletarTodos();
        return ResponseEntity.noContent().build();
    }

}
