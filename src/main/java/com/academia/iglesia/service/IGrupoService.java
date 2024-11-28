package com.academia.iglesia.service;

import com.academia.iglesia.dto.GrupoDTO;
import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Grupo;

import java.util.List;

public interface IGrupoService {
    public List<Grupo> get();
    public Grupo save(GrupoDTO grupoDTO);
    public void delete(String idGrupo);
    public Grupo find(String  idGrupo);
    public Grupo edit(String idGrupo, Grupo grupo);
    public Grupo addMiembro(String idGrupo, String cedula);
    public Grupo deleteMiembro(String idGrupo, String idMiembro);
}
