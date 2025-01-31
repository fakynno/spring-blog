package com.fiap.springblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.springblog.models.Autor;
import com.fiap.springblog.repositories.AutorRepository;
import com.fiap.springblog.service.AutorService;

@Service
public class AutorServiceImpl implements AutorService {    

    @Autowired
    private AutorRepository autorRepository;
    
    @Override
    public Autor criarAutor(Autor autor) {
        return this.autorRepository.save(autor);
    }
    
    @Override
    public Autor buscarAutorPorCodigo(String codigo) {
        return this.autorRepository
            .findById(codigo)
            .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));
    }

    @Override
    public List<Autor> buscarAutores() {
        return this.autorRepository.findAll();
    }
}
