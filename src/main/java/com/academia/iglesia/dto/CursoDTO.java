package com.academia.iglesia.dto;

import com.academia.iglesia.model.AprobacionCurso;
import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.model.Modulo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.time.LocalDate;
import java.util.List;

@Data
public class CursoDTO {

    private String idCurso;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private String nombreCurso;
    private String descripcion;
    private List<ModuloDTO> moduloList;
    private List<MiembroDTO> miembroDTOList;
    private List<String> cedulaProfessor;

}
