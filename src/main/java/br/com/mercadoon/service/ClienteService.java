package br.com.mercadoon.service;

import br.com.mercadoon.entity.Cliente;
import br.com.mercadoon.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente buscar(Long id) {
        return clienteRepository.findById(id).orElseThrow();
    }

    public Cliente add(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente novoCliente) {
        clienteRepository.findById(id).orElseThrow(); // Pesquisa somente para verificar a existência do cliente
        novoCliente.setId(id);
        return clienteRepository.save(novoCliente);
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}

