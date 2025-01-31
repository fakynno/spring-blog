package com.fiap.springblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.springblog.models.Artigo;
import com.fiap.springblog.models.Autor;
import com.fiap.springblog.repositories.ArtigoRepository;
import com.fiap.springblog.repositories.AutorRepository;
import com.fiap.springblog.service.ArtigoService;

@Service
public class ArtigoServiceImpl implements ArtigoService{

    @Autowired
    private ArtigoRepository artigoRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<Artigo> listarArtigos() {
        return this.artigoRepository.findAll();        
    }

    @Override
    public Artigo buscarArtigoPorCodigo(String codigo) {
        return this.artigoRepository
            .findById(codigo)
            .orElseThrow(() -> new IllegalArgumentException("Artigo não encontrado"));
    }

    @Override
    public Artigo criarArtigo(Artigo artigo) {

        // verifica se o autor existe
        if (artigo.getAutor().getCodigo() != null) {

            // recupera o autor
            Autor autor = this.autorRepository
                .findById(artigo.getAutor().getCodigo())
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado"));

            // seta (define) o autor no artigo
            artigo.setAutor(autor);
        }

        // salva o artigo com os dados do autor (caso existam)
        return this.artigoRepository.save(artigo);
    }

}
