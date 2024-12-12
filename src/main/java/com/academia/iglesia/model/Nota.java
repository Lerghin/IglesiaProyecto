package com.academia.iglesia.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "nota")
@Data
public class Nota {
    @Id
    private String idNota;
    @DBRef
    @ToString.Exclude
    private Modulo modulo;
    private double nota;
    @DBRef
    @ToString.Exclude
    private Miembro miembro;
    private AprobacionCurso aprobacionCurso;


    // Método para evaluar la aprobación
    public void evaluarAprobacion(double nota) {
        if (nota >= 12) {
           this.aprobacionCurso = AprobacionCurso.APROBADO;
        } else {
            this.aprobacionCurso = AprobacionCurso.REPROBADO;
        }

    }


}



