package com.academia.iglesia.service;

import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Grupo;

import java.util.List;

public interface IGrupoService {
    public List<Grupo> get();
    public Grupo save(Grupo grupo);
    public void delete(String idGrupo);
    public Grupo find(String  idGrupo);
    public Grupo edit(String idGrupo, Grupo grupo);
}
