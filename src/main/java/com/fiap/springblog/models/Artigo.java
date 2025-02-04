package com.fiap.springblog.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Artigo {

    @Id
    private String codigo;
    private String titulo;    
    private LocalDateTime dataPublicacao;
    @TextIndexed
    private String texto;
    private String url;
    private Integer status;

    @DBRef
    private Autor autor;

    @Version
    private Long version;

}
