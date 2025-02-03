package com.fiap.springblog.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fiap.springblog.models.Artigo;

@Repository
public interface ArtigoRepository extends MongoRepository<Artigo, String> {

    public void deleteById(@SuppressWarnings("null") String idArtigo);

    public List<Artigo> findByStatusAndDataPublicacaoGreaterThan(Integer status, LocalDateTime data);

    @Query("{ $and: [ { 'dataPublicacao': { $gte: ?0 } }, { 'dataPublicacao': { $lte: ?1 } } ] }")
    public List<Artigo> obterArtigoPorDataHora(LocalDateTime dataInicio, LocalDateTime dataFim);

    @SuppressWarnings("null")
    Page<Artigo> findAll(Pageable pageable);

    public List<Artigo> findByStatusOrderByTituloAsc(Integer status);
}
