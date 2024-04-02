package br.com.mercadoon.service;

import br.com.mercadoon.dto.CartaoDto;
import br.com.mercadoon.entity.Cartao;
import br.com.mercadoon.entity.Cliente;
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

        cliente.getCartoes().add(cartao);
        cartao.setCliente(cliente);

        clienteRepository.save(cliente);

        return modelMapper.map(cartao, CartaoDto.class);
    }
}
