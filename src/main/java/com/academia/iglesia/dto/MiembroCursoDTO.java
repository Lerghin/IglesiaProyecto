package com.academia.iglesia.dto;

import com.academia.iglesia.model.AprobacionCurso;
import lombok.Data;

import java.util.List;

@Data
public class MiembroCursoDTO {
    private MiembroDTO miembroDTO;
    private String idCurso;
    private String nombreCurso;
    private List<NotaMiembroDTO> notaMiembroDTOList;
    private AprobacionCurso statusAprobacion;



}
