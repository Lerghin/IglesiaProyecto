package com.academia.iglesia.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "nota")
@Data
public class Nota {
    @Id
    private String idNota;
    @DBRef
    private Modulo modulo;
    private double nota;
    @DBRef
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



