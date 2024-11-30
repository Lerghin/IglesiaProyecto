package com.academia.iglesia.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Objects;

@Document(value = "modulo")
@Data
public class Modulo {

    @Id
    private String idModulo;
    @DBRef
    private Curso curso;
    private  int numModulo;
    private String descripcion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modulo modulo = (Modulo) o;
        return Objects.equals(idModulo, modulo.idModulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idModulo);
    }

}
