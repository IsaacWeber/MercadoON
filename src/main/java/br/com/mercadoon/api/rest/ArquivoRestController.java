package br.com.mercadoon.api.rest;

import br.com.mercadoon.api.dto.ArquivoDto;
import br.com.mercadoon.api.dto.ProdutoDto;
import br.com.mercadoon.api.entity.Arquivo;
import br.com.mercadoon.api.entity.Produto;
import br.com.mercadoon.api.service.ArquivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping(value = "/api/arquivo", produces = { "application/json" })
@Tag(name = "Arquivo")
public class ArquivoRestController {
    private ArquivoService arquivoService;

    public ArquivoRestController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @Operation(summary = "Download de arquivo por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Download realizado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Arquivo não encontrado")
    })
    @GetMapping("/download/{id}")
    public ArquivoDto download(@PathVariable Long id) {
        return arquivoService.buscar(id);
    }

    @Operation(summary = "Upload de arquivo", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Upload realizado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArquivoDto> upload(@RequestParam(value = "arquivo", required = false)MultipartFile arquivoMultipart) {
        return new ResponseEntity<>(arquivoService.add(arquivoMultipart), HttpStatus.CREATED);
    }

    @Operation(summary = "Upload de arquivo associado a produto", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Upload realizado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
    })
    @PostMapping(value = "/upload_imagens_produto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProdutoDto> upload(
            @RequestParam Long produtoId,
            @RequestParam(value = "arquivos", required = false)List<MultipartFile> arquivosMultipart) {
        return new ResponseEntity<>(arquivoService.add(produtoId, arquivosMultipart), HttpStatus.CREATED);
    }

}
