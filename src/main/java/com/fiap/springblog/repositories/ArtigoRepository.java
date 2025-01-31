package com.fiap.springblog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fiap.springblog.models.Artigo;

@Repository
public interface ArtigoRepository extends MongoRepository<Artigo, String> {

}
