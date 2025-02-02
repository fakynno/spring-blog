package com.fiap.springblog.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.springblog.models.Artigo;
import com.fiap.springblog.service.ArtigoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

}
