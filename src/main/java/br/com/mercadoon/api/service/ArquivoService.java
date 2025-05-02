package br.com.mercadoon.api.service;

import br.com.mercadoon.api.dto.ArquivoDto;
import br.com.mercadoon.api.dto.ProdutoDto;
import br.com.mercadoon.api.entity.Arquivo;
import br.com.mercadoon.api.entity.Produto;
import br.com.mercadoon.api.exception.ArquivoNotFoundException;
import br.com.mercadoon.api.exception.ProdutoNotFoundException;
import br.com.mercadoon.api.repository.ArquivoRepository;
import br.com.mercadoon.api.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArquivoService {
    private ArquivoRepository arquivoRepository;
    private ProdutoRepository produtoRepository;
    private ModelMapper modelMapper;

    public ArquivoService(ArquivoRepository arquivoRepository, ProdutoRepository produtoRepository, ModelMapper modelMapper) {
        this.arquivoRepository = arquivoRepository;
        this.produtoRepository = produtoRepository;
        this.modelMapper = modelMapper;
    }

    public ArquivoDto add(MultipartFile arquivoMultipart) {
        if(arquivoMultipart == null) throw new RuntimeException("Arquivo não pode ser nulo.");

        try {
            byte[] data = arquivoMultipart.getBytes();
            if(data.length == 0) throw new RuntimeException("Arquivo não pode estar vazio.");

            Arquivo arquivo = new Arquivo(StringUtils.cleanPath(arquivoMultipart.getOriginalFilename()),
                    arquivoMultipart.getContentType(), arquivoMultipart.getBytes());

            return modelMapper.map(arquivoRepository.save(arquivo), ArquivoDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Erro adicionando arquivo! Verifique os dados.");
        }
    }

    public ArquivoDto buscar(Long id) {
        return modelMapper.map(
                arquivoRepository.findById(id)
                .orElseThrow(() -> new ArquivoNotFoundException("Arquivo não encontrado para id = " + id)),
                ArquivoDto.class);
    }

    public ProdutoDto add(Long produtoId, List<MultipartFile> arquivosMultipart) {
        if(arquivosMultipart.size() > 10) throw new RuntimeException("O Produto não pode ter mais de 10 imagens");
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado para id = " + produtoId));

        if(arquivosMultipart != null) {
            // Pega somente arquivos de imagens
            List<Arquivo> imagensNovas =
                    arquivosMultipart.stream()
                            .filter(a -> a != null)
                            .filter(a -> a.getContentType().startsWith("image/"))
                            .map(a -> {
                                try {
                                    return new Arquivo(StringUtils.cleanPath(
                                            a.getOriginalFilename()),
                                            a.getContentType(),
                                            a.getBytes());
                                } catch (IOException e) {
                                    throw new RuntimeException("Erro adicionando arquivo! Verifique os dados.");
                                }
                            })
                            .toList();

            if(produto.getImagens() != null) produto.getImagens().addAll(imagensNovas);
            else produto.setImagens(new ArrayList<>(imagensNovas));
        }

        return modelMapper.map(produtoRepository.save(produto), ProdutoDto.class);
    }
}


