package br.com.mercadoon.api.rest;

import br.com.mercadoon.api.dto.ProdutoDto;
import br.com.mercadoon.api.entity.Produto;
import br.com.mercadoon.api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/produto", produces = { "application/json" })
@Tag(name = "Produto")
public class ProdutoRestController {
    private ProdutoService produtoService;

    @Autowired
    public ProdutoRestController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Lista todos os produtos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @GetMapping
    public List<ProdutoDto> listar() {
        return produtoService.listar();
    }

    @Operation(summary = "Lista produtos recentes", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos listados"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @GetMapping("recentes")
    public List<ProdutoDto> listarRecentes() {
        return produtoService.listarRecentes();
    }

    @Operation(summary = "Busca produto por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ProdutoDto buscar(@PathVariable Long id) {
        return produtoService.buscar(id);
    }

    @Operation(summary = "Cadastra produto", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PostMapping(value = "/{clienteId}", consumes = { "application/json" })
    public ResponseEntity<ProdutoDto> add(@PathVariable Long clienteId, @Valid @RequestBody Produto produto) {
        return new ResponseEntity<>(produtoService.add(clienteId, produto), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualiza produto", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping(value = "/{id}", consumes = { "application/json" })
    public ProdutoDto atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        return produtoService.atualizar(id, produto);
    }

    @Operation(summary = "Deleta produto", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deleta todos os produtos", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produtos deletados"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @DeleteMapping
    public ResponseEntity<Void> deletarTodos() {
        produtoService.deletarTodos();
        return ResponseEntity.noContent().build();
    }

}
