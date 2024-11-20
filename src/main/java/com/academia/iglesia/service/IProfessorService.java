package com.academia.iglesia.service;

import com.academia.iglesia.model.Pago;
import com.academia.iglesia.model.Professor;

import java.util.List;

public interface IProfessorService {
    public List<Professor> get();
    public Professor save(Professor pro);
    public void delete(String idProfessor);
    public Professor find(String  idProfessor);
    public Professor edit(String idProfessor, Professor pro);
    public Professor findByCedula(String cedula);
    public Integer countProfessor();
}
