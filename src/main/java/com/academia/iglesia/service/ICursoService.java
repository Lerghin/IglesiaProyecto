package com.academia.iglesia.service;

import com.academia.iglesia.dto.CursoDTO;
import com.academia.iglesia.model.Curso;

import java.util.List;

public interface ICursoService {
    public List<Curso> get();
    public Curso save(CursoDTO curso);
    public void delete(String idCurso);
    public Curso find(String  idCurso);
    public Curso edit(String idCurso, Curso curso);
    public List<CursoDTO> getDTOCurso();

}