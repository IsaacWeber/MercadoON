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

    public ClienteDto buscar(Long id) {
        return modelMapper.map(clienteRepository
                        .findById(id)
                        .orElseThrow(() -> new ClienteNotFoundException("Usuário não encontrado para id = " + id)),
                ClienteDto.class);
    }

    public ClienteDto add(Cliente cliente) {
        cliente.setId(null);
        cliente.setMembroDesde(new Date(System.currentTimeMillis()));

        if(cliente.getCartoes() != null)
            cliente.getCartoes().forEach(c -> c.setCliente(cliente));

        return modelMapper.map(clienteRepository.save(cliente), ClienteDto.class);
    }

    public ClienteDto atualizar(Long id, Cliente novoCliente) {
        Cliente bdCliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException("Cliente não econtrado para id = " + id));

        novoCliente.setId(bdCliente.getId());
        novoCliente.setMembroDesde(bdCliente.getMembroDesde());
        novoCliente.setCartoes(null);

        return modelMapper.map(clienteRepository.save(novoCliente), ClienteDto.class);
    }

    public void deletar(Long id) {
        clienteRepository.delete(
                clienteRepository.findById(id)
                        .orElseThrow(() ->
                                new ClienteNotFoundException("Cliente não econtrado para id = " + id))); // Pesquisa somente para verificar a existência do cliente

    }

    public void deletarTodos() { // TODO Entender por que não dá para deletar o cliente quando associado a compras. Atualizar
        clienteRepository.deleteAll();
    }
}

