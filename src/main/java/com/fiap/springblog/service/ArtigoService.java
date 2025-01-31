package com.fiap.springblog.service;

import java.util.List;

import com.fiap.springblog.models.Artigo;

public interface ArtigoService {

    List<Artigo> listarArtigos();

    Artigo buscarArtigoPorCodigo(String codigo);

    Artigo criarArtigo(Artigo artigo);

}
