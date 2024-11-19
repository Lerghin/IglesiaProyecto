package com.academia.iglesia.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ModuloCursoDTO {
    private String idModulo;
    private String idCurso;
    private  int numModulo;
    private String descripcion;
    private String nombreCurso;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;

}
