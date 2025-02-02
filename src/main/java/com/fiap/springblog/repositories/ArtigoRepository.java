package com.fiap.springblog.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fiap.springblog.models.Artigo;

@Repository
public interface ArtigoRepository extends MongoRepository<Artigo, String> {

    public void deleteById(String idArtigo);

    public List<Artigo> findByStatusAndDataPublicacaoGreaterThan(Integer status, LocalDateTime data);
}
