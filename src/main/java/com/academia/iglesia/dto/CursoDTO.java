package com.academia.iglesia.dto;

import lombok.Data;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CursoDTO {

    private String idCurso;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private String nombreCurso;
    private String descripcion;
    private List<ModuloDTO> moduloList ;
    private List<MiembroDTO> miembroDTOList;
    private List<ProfessorDTO> professorDTOS ;

}
