package com.fiap.springblog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.springblog.models.Autor;
import com.fiap.springblog.service.AutorService;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping("/{codigo}")
    public Autor buscarPorCodigo(@PathVariable String codigo) {
        return this.autorService.buscarAutorPorCodigo(codigo);
    }

    @GetMapping
    public List<Autor> buscarAutores() {
        return this.autorService.buscarAutores();
    }

    @PostMapping
    public Autor criar(@RequestBody Autor autor) {
        return this.autorService.criarAutor(autor);
    }

}