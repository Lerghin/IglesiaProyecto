package com.academia.iglesia.dto;

import lombok.Data;

import java.util.List;

@Data
public class ModuloNotaDTO {
    private String idModulo;
    private String idCurso;
    private String nombreCurso;
    private  int numModulo;
    private String descripcion;
    private List<NotaMiembroDTO> notaMiembroDTOList;

}
