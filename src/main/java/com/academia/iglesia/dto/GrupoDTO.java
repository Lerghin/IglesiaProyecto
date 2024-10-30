package com.academia.iglesia.dto;

import com.academia.iglesia.model.Miembro;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
public class GrupoDTO {

    private String idGrupo;
    private String numeroGrupo;
    private List<MiembroDTO> miembroList;


}
