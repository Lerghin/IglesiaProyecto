package com.academia.iglesia.service;

import com.academia.iglesia.dto.ModuloCursoDTO;
import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Modulo;

import java.util.List;

public interface IModuloService {
    public List<Modulo> get();
    public List<ModuloCursoDTO> getModuloDTO();

    public void delete(String idModulo);
    public Modulo find(String  idModulo);
    public Modulo edit(String idModulo, Modulo modulo);
    public void editCursoModulo(String idCurso, Modulo modulo);
    public Modulo save(Modulo modulo,String idCurso);
}
