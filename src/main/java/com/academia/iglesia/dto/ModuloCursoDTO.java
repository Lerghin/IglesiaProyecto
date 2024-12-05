package com.academia.iglesia.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ModuloCursoDTO {
    private String idCurso;
    private String nombreCurso;
    private List<ModuloDTO> moduloDTOList;

}
