package br.com.mercadoon.service;

import br.com.mercadoon.dto.ProdutoDto;
import br.com.mercadoon.entity.Produto;
import br.com.mercadoon.exception.ProdutoNotFoundException;
import br.com.mercadoon.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    private ProdutoRepository produtoRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, ModelMapper modelMapper) {
        this.produtoRepository = produtoRepository;
        this.modelMapper = modelMapper;
    }

    public List<ProdutoDto> listar() {
        return produtoRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, ProdutoDto.class))
                .collect(Collectors.toList());
    }

    public ProdutoDto add(Produto produto) {
        produto.setId(null);
        return modelMapper.map(produtoRepository.save(produto), ProdutoDto.class);
    }

    public ProdutoDto buscar(Long id) {
        return modelMapper.map(
                produtoRepository.findById(id)
                        .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado para id = " + id)),
                ProdutoDto.class);
    }

    public ProdutoDto atualizar(Long id, Produto produto) {
        produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado para id = " + id));
        produto.setId(id);
        produtoRepository.save(produto);
        return modelMapper.map(produto, ProdutoDto.class);
    }

    public void deletar(Long id) {
        produtoRepository.delete(
                produtoRepository.findById(id)
                        .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado para id = " + id)));
    }

    public void deletarTodos() {
        produtoRepository.deleteAll();
    }
}
