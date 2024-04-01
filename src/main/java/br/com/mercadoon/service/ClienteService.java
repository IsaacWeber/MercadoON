package br.com.mercadoon.service;

import br.com.mercadoon.dto.ClienteDto;
import br.com.mercadoon.entity.Cliente;
import br.com.mercadoon.exception.ClienteNotFoundException;
import br.com.mercadoon.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private ClienteRepository clienteRepository;
    private ModelMapper modelMapper;
    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    public List<ClienteDto> listar() {
        return clienteRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, ClienteDto.class))
                .collect(Collectors.toList());
    }

    public Cliente buscar(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException("Usuário não encontrado para id = " + id));
    }

    public Cliente add(Cliente cliente) {
        cliente.setMembroDesde(new Date(System.currentTimeMillis()));
        cliente.getCartoes().forEach(c -> c.setCliente(cliente));
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente novoCliente) {
        clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException("Cliente não econtrado para id = " + id)); // Pesquisa somente para verificar a existência do cliente
        novoCliente.setId(id);
        return clienteRepository.save(novoCliente);
    }

    public void deletar(Long id) {
        clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException("Cliente não econtrado para id = " + id)); // Pesquisa somente para verificar a existência do cliente
        clienteRepository.deleteById(id);
    }
}

