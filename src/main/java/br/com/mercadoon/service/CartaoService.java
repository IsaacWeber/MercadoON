package br.com.mercadoon.service;

import br.com.mercadoon.dto.CartaoDto;
import br.com.mercadoon.entity.Cartao;
import br.com.mercadoon.entity.Cliente;
import br.com.mercadoon.exception.CartaoNotFoundException;
import br.com.mercadoon.exception.ClienteNotFoundException;
import br.com.mercadoon.repository.CartaoRepository;
import br.com.mercadoon.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartaoService {
    private CartaoRepository cartaoRepository;
    private ClienteRepository clienteRepository;

    private ModelMapper modelMapper;

    @Autowired
    public CartaoService(CartaoRepository cartaoRepository, ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.cartaoRepository = cartaoRepository;
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    public List<CartaoDto> listar() {
        return cartaoRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, CartaoDto.class))
                .collect(Collectors.toList());
    }

    public CartaoDto add(Long clienteId, Cartao cartao) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado para id = " + clienteId));

        cartao.setId(null);
        cliente.getCartoes().add(cartao);
        cartao.setCliente(cliente);

        clienteRepository.save(cliente);

        return modelMapper.map(cartao, CartaoDto.class);
    }

    public CartaoDto buscar(Long id) {
        return modelMapper.map(
                cartaoRepository
                        .findById(id)
                        .orElseThrow(() -> new CartaoNotFoundException("Cartão não encontrado para id = " + id)),
                CartaoDto.class);
    }
    public void deletar(Long id) {
        cartaoRepository.delete(
                cartaoRepository.findById(id)
                        .orElseThrow(() ->
                                new CartaoNotFoundException("Cartão não encontrado para id = " + id)));
    }

    public CartaoDto atualizar(Long id, Cartao novoCartao) {
        Cartao bdCartao = cartaoRepository.findById(id).orElseThrow(() -> new CartaoNotFoundException("Cartão não encontrado para id = " + id));

        bdCartao.setNomeUsuario(novoCartao.getNomeUsuario());
        bdCartao.setNumero(novoCartao.getNumero());
        bdCartao.setCvv(novoCartao.getCvv());
        bdCartao.setValidade(novoCartao.getValidade());
        bdCartao.setBandeira(novoCartao.getBandeira());
        bdCartao.setFuncao(novoCartao.getFuncao());

        return modelMapper.map(cartaoRepository.save(bdCartao), CartaoDto.class);
    }

    public void deletarTodos() {
        cartaoRepository.deleteAll();
    }

    public List<CartaoDto> buscarCartoesPorClienteId(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado para id = " + clienteId))
                .getCartoes()
                .stream()
                .map(c -> modelMapper.map(c, CartaoDto.class))
                .collect(Collectors.toList());
    }
}
