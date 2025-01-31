package com.fiap.springblog.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Autor {

    @Id
    private String codigo;
    private String nome;
    private String biografia;
    private String imagem;

}
