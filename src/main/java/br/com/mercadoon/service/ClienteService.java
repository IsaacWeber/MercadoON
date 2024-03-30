package br.com.mercadoon.service;

import br.com.mercadoon.entity.Cliente;
import br.com.mercadoon.exception.ClienteNotFoundException;
import br.com.mercadoon.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.sql.Date;

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
        return clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException("Usuário não encontrado para id = " + id));
    }

    public Cliente add(Cliente cliente) {
        cliente.setMembroDesde(new Date(System.currentTimeMillis()));
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

