package com.fiap.springblog.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.springblog.models.Artigo;
import com.fiap.springblog.models.ArtigoStatusCount;
import com.fiap.springblog.models.AutorArtigosCount;
import com.fiap.springblog.service.ArtigoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/artigos")
public class ArtigoController {    

    @Autowired
    private ArtigoService artigoService;
    
    @GetMapping
    public List<Artigo> listarArtigos() {
        return this.artigoService.listarArtigos();
    }

    @GetMapping("/{codigo}")
    public Artigo buscarArtigoPorCodigo(@PathVariable String codigo) {
        return this.artigoService.buscarArtigoPorCodigo(codigo);
    }

    @PostMapping
    public Artigo criarArtigo(@RequestBody Artigo artigo) {
        return this.artigoService.criarArtigo(artigo);
    }

    @GetMapping("/maiordata")
    public List<Artigo> findDataGreaterThan(@RequestParam("data") LocalDateTime data) {
        return this.artigoService.findByDataGreaterThan(data);
    }

    @GetMapping("/datastatus")
    public List<Artigo> findDataAndStatus(@RequestParam("data") LocalDateTime data, 
        @RequestParam("status") Integer status) {
            return this.artigoService.findByDataAndStatus(data, status);
        }

    @PutMapping
    public void atualizarArtigo(@RequestBody Artigo updateArtigo) {
        this.artigoService.atualizarArtigo(updateArtigo);
    }

    @PutMapping("/{idArtigo}")
    public void atualizarUrlArtigoMongoTemplate(@PathVariable String idArtigo, 
        @RequestBody String novaUrl) {
            this.artigoService.atualizarUrlArtigoMongoTemplate(idArtigo, novaUrl);
        }

    @DeleteMapping("/{idArtigo}")
    public void deleteById(@PathVariable String idArtigo) {
        this.artigoService.deleteById(idArtigo);
    }

    @DeleteMapping("/delete")
    public void deleteArtigoByIdMongoTemplate(@RequestParam("idArtigo") String idArtigo) {
        this.artigoService.deleteArtigoByIdMongoTemplate(idArtigo);
    }

    @GetMapping("/status-maiordata")
    public List<Artigo> findByStatusAndDataPublicacaoGreaterThan(
            @RequestParam("status") Integer status, 
            @RequestParam("data") LocalDateTime data) {
                return this.artigoService.findByStatusAndDataPublicacaoGreaterThan(status, data);
            }
    
    @GetMapping("/periodo-data-hora")
    public List<Artigo> obterArtigoPorDataHora(
            @RequestParam("dataInicio") LocalDateTime dataInicio, 
            @RequestParam("dataFim") LocalDateTime dataFim) {
                return this.artigoService.obterArtigoPorDataHora(dataInicio, dataFim);
            }

    @GetMapping("/busca-complexa")
    public List<Artigo> encontrarArtigosComplexos(
            @RequestParam("status") Integer status,
            @RequestParam("data") LocalDateTime data,
            @RequestParam("titulo") String titulo) {
                return this.artigoService.encontrarArtigosComplexos(status, data, titulo);
            }

    @PostMapping("/lote")
    public List<Artigo> criarArtigosEmLote(@RequestBody List<Artigo> artigos) {
        return this.artigoService.criarArtigosEmLote(artigos);
    }

    @GetMapping("/busca-paginada")
    public ResponseEntity<Page<Artigo>> buscarArtigosPaginados(Pageable pageable) {
        Page<Artigo> artigos = this.artigoService.buscarArtigosPaginados(pageable);
        return ResponseEntity.ok(artigos);
    }

    @GetMapping("/ordenacao-status")
    public List<Artigo> findByStatusOrderByTituloAsc(@RequestParam("status") Integer status) {
        return this.artigoService.findByStatusOrderByTituloAsc(status);
    }

    @GetMapping("/obter-status-ordenado")
    public List<Artigo> obterArtigoPorStatusPorOrdenacao(@RequestParam("status") Integer status) {
        return this.artigoService.obterArtigoPorStatusComOrdenacao(status);
    }

    @GetMapping("/busca-por-texto")
    public List<Artigo> findByTexto(@RequestParam("texto") String textoDePesquisa) {
        return this.artigoService.findByTexto(textoDePesquisa);
    }

    @GetMapping("/contar-artigos-por-status")
    public List<ArtigoStatusCount> contarArtigosPorStatus() {
        return this.artigoService.obterQtdArtigosPorStatus();
    }

    @GetMapping("/contar-artigos-por-autor")
    public List<AutorArtigosCount> obterTotalArtigosPorAutorPorPeriodo(
            @RequestParam("dataInicio") LocalDate dataInicio, 
            @RequestParam("dataFim") LocalDate dataFim) {
                return this.artigoService.obterTotalArtigosPorAutorPorPeriodo(dataInicio, dataFim);
            }

}
