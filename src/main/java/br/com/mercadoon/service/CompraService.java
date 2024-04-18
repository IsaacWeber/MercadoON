package br.com.mercadoon.service;

import br.com.mercadoon.dto.CompraDto;
import br.com.mercadoon.entity.Cartao;
import br.com.mercadoon.entity.Cliente;
import br.com.mercadoon.entity.Compra;
import br.com.mercadoon.entity.Produto;
import br.com.mercadoon.exception.CartaoNotFoundException;
import br.com.mercadoon.exception.ClienteNotFoundException;
import br.com.mercadoon.exception.CompraNotFoundException;
import br.com.mercadoon.exception.ProdutoNotFoundException;
import br.com.mercadoon.repository.CartaoRepository;
import br.com.mercadoon.repository.ClienteRepository;
import br.com.mercadoon.repository.CompraRepository;
import br.com.mercadoon.repository.ProdutoRepository;
import br.com.mercadoon.requestmodel.CompraRequestModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraService {

    private CompraRepository compraRepository;

    private ClienteRepository clienteRepository;

    private ProdutoRepository produtoRepository;

    private CartaoRepository cartaoRepository;

    private ModelMapper modelMapper;

    @Autowired
    public CompraService(CompraRepository compraRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository, CartaoRepository cartaoRepository, ModelMapper modelMapper) {
        this.compraRepository = compraRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.cartaoRepository = cartaoRepository;
        this.modelMapper = modelMapper;
    }

    public List<CompraDto> listar() {
        return compraRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, CompraDto.class))
                .collect(Collectors.toList());
    }

    public CompraDto buscar(Long id) {
        return modelMapper.map(compraRepository.findById(id)
                    .orElseThrow(() -> new CompraNotFoundException("Compra não encontrada para id = " + id)),
                CompraDto.class);
    }

    public CompraDto add(CompraRequestModel compraRequestModel) {
        Compra compra = new Compra();
        compra.setId(null);

        // Associa cliente
        Cliente cliente = clienteRepository.findById(compraRequestModel.getClienteId())
                        .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado para id = " + compraRequestModel.getClienteId()));
        compra.setCliente(cliente);
        compra.getCliente().getCompras().add(compra);
        //

        // Associa cartão
        Cartao cartao = cartaoEhDoCliente(cliente, compraRequestModel.getCartaoId());

        compra.setCartao(cartao);
        cartao.getCompras().add(compra);
        //

        // Associa produtos
        try {
            compra.setProdutos(compraRequestModel.getProdutosId()
                    .stream()
                    .map(id -> produtoRepository.findById((Long) id)
                            .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado para id = " + id)))
                    .collect(Collectors.toList()));
        } catch(ClassCastException e) {
            throw new ProdutoNotFoundException("O id não pode ser convertido para Long. " +
                    "Mude-o para um valor numérico inteiro.");
        }

        compra.setRealizacao(new Date(System.currentTimeMillis()));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(compra.getRealizacao());
        calendar.add(Calendar.DATE, 3); // Adiciona 3 dias de espera
        compra.setPrevisaoEntrega(new Date(calendar.getTime().getTime()));

        compra.setEntrega(null); // Quando for entregue, a entrega deve ser inicializada com a data efetiva

        return modelMapper.map(compraRepository.save(compra), CompraDto.class);
    }

    public void deletarPorId(Long id) {
        compraRepository.delete(compraRepository.findById(id)
                .orElseThrow(() -> new CompraNotFoundException("Compra não encontrada para id = " + id)));
    }

    public void deletarTodos() {
        compraRepository.deleteAll();
    }

    public CompraDto atualizar(Long id, CompraRequestModel compraRequestModel) {
        Compra compra = compraRepository.findById(id).orElseThrow(() -> new CompraNotFoundException("Compra não encontrada para id = " + id));
        boolean clienteAtualizado = false;

        if(compra.getCliente().getId() != compraRequestModel.getClienteId()) { // Reassocia cliente
            compra.setCliente(clienteRepository.findById(compraRequestModel.getClienteId())
                    .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado para id = " + compraRequestModel.getClienteId())));

            compra.getCliente().getCompras().add(compra);
            clienteAtualizado = true; // Sempre que atualizar cliente, deve-se verificar o cartão adicionado para garantir que não fique o cartão antigo que pode ser de outro cliente
        }

        if(clienteAtualizado || compraRequestModel.getCartaoId() != compra.getCartao().getId()) { // Reassocia cartão
            Cartao cartao = cartaoEhDoCliente(compra.getCliente(), compraRequestModel.getCartaoId());
            compra.setCartao(cartao);
            cartao.getCompras().add(compra);
        }

        if(!compraRequestModel.getProdutosId()
                .equals(
                    compra.getProdutos()
                            .stream()
                            .map(Produto::getId)
                            .collect(Collectors.toList()))
        ) { // Se os ids dos produtos para atualizar NÃO são iguais aos ids dos produtos das compras

            // Reassocia produtos
            try {
                compra.setProdutos(compraRequestModel.getProdutosId()
                        .stream()
                        .map(idp -> produtoRepository.findById((Long) idp)
                                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado para id = " + idp)))
                        .collect(Collectors.toList()));
            } catch(ClassCastException e) {
                throw new ProdutoNotFoundException("O id não pode ser convertido para Long. " +
                        "Mude-o para um valor numérico inteiro.");
            }

        }

        return modelMapper.map(compraRepository.save(compra), CompraDto.class);
    }

    // AUXILIARES
    private Cartao cartaoEhDoCliente(Cliente cliente, Long cartaoId) throws CartaoNotFoundException {
        return cliente.getCartoes()
                .stream()
                .filter(ct -> ct.getId() == cartaoId)
                .findFirst()
                .orElseThrow(() ->
                        new CartaoNotFoundException(String.format("Cartão de id = {%d} não encontrado para cliente de id = {%d}",
                                cartaoId, cliente.getId())));

    }
}
