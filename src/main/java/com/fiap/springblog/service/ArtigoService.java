package com.fiap.springblog.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fiap.springblog.models.Artigo;
import com.fiap.springblog.models.ArtigoStatusCount;
import com.fiap.springblog.models.AutorArtigosCount;

public interface ArtigoService {

    List<Artigo> listarArtigos();

    Artigo buscarArtigoPorCodigo(String codigo);

    Artigo criarArtigo(Artigo artigo);

    List<Artigo> criarArtigosEmLote(List<Artigo> artigos);

    List<Artigo> findByDataGreaterThan(LocalDateTime data);

    List<Artigo> findByDataAndStatus(LocalDateTime data, Integer status);

    void atualizarArtigo(Artigo updateArtigo);

    void atualizarUrlArtigoMongoTemplate(String idArtigo, String novaUrl);

    void deleteById(String idArtigo);

    void deleteArtigoByIdMongoTemplate(String idArtigo);

    List<Artigo> findByStatusAndDataPublicacaoGreaterThan(Integer status, LocalDateTime data);

    List<Artigo> obterArtigoPorDataHora(LocalDateTime dataInicio, LocalDateTime dataFim);

    List<Artigo> encontrarArtigosComplexos(Integer status, LocalDateTime data, String titulo);

    Page<Artigo> buscarArtigosPaginados(Pageable pageable);

    List<Artigo> findByStatusOrderByTituloAsc(Integer status);

    List<Artigo> obterArtigoPorStatusComOrdenacao(Integer status);

    List<Artigo> findByText(String termoDePesquisa);

    List<Artigo> findByTexto(String textoDePesquisa);

    List<ArtigoStatusCount> obterQtdArtigosPorStatus();

    List<AutorArtigosCount> obterTotalArtigosPorAutorPorPeriodo(LocalDate dataInicio, LocalDate dataFim);

}
