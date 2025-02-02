package com.fiap.springblog.service;

import java.time.LocalDateTime;
import java.util.List;

import com.fiap.springblog.models.Artigo;

public interface ArtigoService {

    List<Artigo> listarArtigos();

    Artigo buscarArtigoPorCodigo(String codigo);

    Artigo criarArtigo(Artigo artigo);

    List<Artigo> findByDataGreaterThan(LocalDateTime data);

    List<Artigo> findByDataAndStatus(LocalDateTime data, Integer status);

    void atualizarArtigo(Artigo updateArtigo);

}
