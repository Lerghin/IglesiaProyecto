package com.academia.iglesia.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "grupo")
@Data
public class Grupo {
   @Id
    private String idGrupo;
    private String numeroGrupo;
    @DBRef
    private List<Miembro> miembroList;



}
