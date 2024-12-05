package com.academia.iglesia.dto;

import com.academia.iglesia.model.AprobacionCurso;
import com.academia.iglesia.model.Modulo;
import lombok.Data;

@Data
public class NotaMiembroDTO {
    private String idNota;
    private String idModulo;
    private double nota;
    private String cedula;
    private AprobacionCurso aprobacionCurso;

}
