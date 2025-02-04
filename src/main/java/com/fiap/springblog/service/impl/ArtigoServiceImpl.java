package com.fiap.springblog.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.fiap.springblog.models.Artigo;
import com.fiap.springblog.models.ArtigoStatusCount;
import com.fiap.springblog.models.Autor;
import com.fiap.springblog.models.AutorArtigosCount;
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

    @Override
    public void atualizarArtigo(Artigo updateArtigo) {
        this.artigoRepository.save(updateArtigo);
    }

    @Override
    public void atualizarUrlArtigoMongoTemplate(String idArtigo, String novaUrl) {
        // Define um critério de busca pelo id do artigo
        Query query = new Query(Criteria
            .where("_id")
            .is(idArtigo));
        
        // Define o novo valor para o campo url através do método set
        Update update = new Update().set("url", novaUrl);

        // Faz a chamada do mongoTemplate para atualizar o artigo, passando como parâmetos a query e o update
        this.mongoTemplate.updateFirst(query, update, Artigo.class);
    }

    @Override
    public void deleteById(String idArtigo) {
        this.artigoRepository.deleteById(idArtigo);
    }

    @Override
    public void deleteArtigoByIdMongoTemplate(String idArtigo) {
        Query query = new Query(Criteria
            .where("_id")
            .is(idArtigo));
        this.mongoTemplate.remove(query, Artigo.class);
    }

    @Override
    public List<Artigo> findByStatusAndDataPublicacaoGreaterThan(Integer status, LocalDateTime data) {
        return this.artigoRepository.findByStatusAndDataPublicacaoGreaterThan(status, data);
    }

    @Override
    public List<Artigo> obterArtigoPorDataHora(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return this.artigoRepository.obterArtigoPorDataHora(dataInicio, dataFim);
    }

    @Override
    public List<Artigo> encontrarArtigosComplexos(Integer status, LocalDateTime data, String titulo) {
        
        Criteria criteria = new Criteria();

        // 1º Critério de filtros de artigos: data menor ou igual ao parâmetro informado (data)
        criteria.and("dataPublicacao").lte(data);
        
        // 2º Critério de filtros de artigos: apenas com o status especificado
        if (status != null) {
            criteria.and("status").is(status);
        }

        // 3º Critério de filtros de artigos: onde exista o título e que não esteja vazio
        if (titulo != null && !titulo.isEmpty()) {
            // o regex é utilizado para buscar o título ignorando maiúsculas e minúsculas
            criteria.and("titulo").regex(titulo, "i");
        }

        Query query = new Query(criteria);
        return mongoTemplate.find(query, Artigo.class);
    }

    @Override
    public List<Artigo> criarArtigosEmLote(List<Artigo> artigos) {
        
        // para cada artigo, verifica se o autor existe
        artigos.forEach(artigo -> {
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
        });

        // salva o artigo com os dados do autor (caso existam)
        return this.artigoRepository.saveAll(artigos);
    }

    @Override
    public Page<Artigo> buscarArtigosPaginados(Pageable pageable) {
        Sort sort = Sort.by("titulo").ascending();
        Pageable paginacao = PageRequest.of(
            pageable.getPageNumber()
            ,pageable.getPageSize()
            , sort);

        return this.artigoRepository.findAll(paginacao);
    }

    @Override
    public List<Artigo> findByStatusOrderByTituloAsc(Integer status) {
        return this.artigoRepository.findByStatusOrderByTituloAsc(status);
    }

    @Override
    public List<Artigo> obterArtigoPorStatusComOrdenacao(Integer status) {
        return this.artigoRepository.obterArtigoPorStatusComOrdenacao(status);
    }

    @Override
    public List<Artigo> findByText(String termoDePesquisa) {
        return null;
    }

    @Override
    public List<Artigo> findByTexto(String textoDePesquisa) {
        TextCriteria textCriteria = 
                TextCriteria.forDefaultLanguage().matchingPhrase(textoDePesquisa);
        Query query = TextQuery.queryText(textCriteria).sortByScore();
        return mongoTemplate.find(query, Artigo.class);
    }

    @Override
    public List<ArtigoStatusCount> obterQtdArtigosPorStatus() {

        TypedAggregation<Artigo> typedAggregation = 
            Aggregation.newAggregation(
                Artigo.class,
                Aggregation.group("status").count().as("quantidade"),
                Aggregation.project("quantidade").and("status").previousOperation()
                );
        
        AggregationResults<ArtigoStatusCount> result = 
                    mongoTemplate.aggregate(typedAggregation, ArtigoStatusCount.class);

        return result.getMappedResults();
    }

    @Override
    public List<AutorArtigosCount> obterTotalArtigosPorAutorPorPeriodo(LocalDate dataInicio, LocalDate dataFim){
        
        TypedAggregation<Artigo> typedAggregation = 
            Aggregation.newAggregation(
                Artigo.class,
                // usamos o método match para filtrar os artigos por data de publicação
                Aggregation.match(
                    Criteria.where("dataPublicacao")
                        .gte(dataInicio.atStartOfDay())
                        .lt(dataFim.plusDays(1).atStartOfDay())
                ),
                // em seguida usamos o método group para definir qual campo vamos usar para agrupar os dados
                Aggregation.group("autor").count().as("quantidade"),
                // por fim, usamos o método project para projetar(definir) quais campos serão retornados
                Aggregation.project("quantidade").and("autor").previousOperation()                
                );

        AggregationResults<AutorArtigosCount> result = 
                    mongoTemplate.aggregate(typedAggregation, AutorArtigosCount.class);

        return result.getMappedResults();
    }

}
