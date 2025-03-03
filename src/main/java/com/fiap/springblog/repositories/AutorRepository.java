package com.fiap.springblog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fiap.springblog.models.Autor;

@Repository
public interface AutorRepository extends MongoRepository<Autor, String> {    

}
