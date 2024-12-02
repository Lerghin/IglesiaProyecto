package com.academia.iglesia.service;

import com.academia.iglesia.dto.CursoDTO;
import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Professor;

import java.util.List;

public interface ICursoService {
    public List<Curso> get();
    public Curso save(CursoDTO curso);
    public void delete(String idCurso);
    public Curso find(String  idCurso);
    public Curso edit(String idCurso, Curso curso);
    public List<CursoDTO> getDTOCurso();
    public Professor FindByCedula(String cedula);
    public CursoDTO findDTOCurso(String idCurso);
    public void addMemberCurso(String idCurso, String cedula);
    public void removeMemberCurso(String idCurso, String cedula);
}