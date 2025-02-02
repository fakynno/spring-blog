package com.fiap.springblog.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    private final MongoTemplate mongoTemplate;

    public ArtigoServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

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
        } else {
            // caso o autor não exista, seta o autor como null
            artigo.setAutor(null);
        }

        // salva o artigo com os dados do autor (caso existam)
        return this.artigoRepository.save(artigo);
    }

    @Override
    public List<Artigo> findByDataGreaterThan(LocalDateTime data) {
        Query query = new Query(Criteria.where("dataPublicacao").gt(data));
        return mongoTemplate.find(query, Artigo.class);
    }

    @Override
    public List<Artigo> findByDataAndStatus(LocalDateTime data, Integer status) {
        Query query = new Query(Criteria
            .where("dataPublicacao")
            .is(data)
            .and("status")
            .is(status));
        return mongoTemplate.find(query, Artigo.class);
    }

}
