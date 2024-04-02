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

    @PostMapping("/{clienteId}")
    public ResponseEntity<CartaoDto> add(@PathVariable Long clienteId, @Valid @RequestBody Cartao cartao) {
        return new ResponseEntity<>(cartaoService.add(clienteId, cartao), HttpStatus.CREATED);
    }
}
