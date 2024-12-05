package com.academia.iglesia.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ModuloNotaDTO {
    private String idModulo;
    private String idCurso;
    private String nombreCurso;
    private  int numModulo;
    private String descripcion;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private List<NotaMiembroDTO> notaMiembroDTOList;


}
