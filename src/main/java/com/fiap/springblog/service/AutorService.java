package com.fiap.springblog.service;

import java.util.List;

import com.fiap.springblog.models.Autor;

public interface AutorService {

    Autor criarAutor(Autor autor);
    Autor buscarAutorPorCodigo(String codigo);
    List<Autor> buscarAutores();

}
