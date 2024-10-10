package com.academia.iglesia.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "modulo")
@Data
public class Modulo {

    @Id
    private String idModulo;
    @DBRef
    private Curso curso;
    private  int numModulo;
    private String descripcion;


}
