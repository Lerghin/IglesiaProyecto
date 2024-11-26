package com.academia.iglesia.service;

import com.academia.iglesia.dto.PercentMiembrosDTO;
import com.academia.iglesia.model.Miembro;

import java.util.List;
import java.util.Map;

public interface IMiembroService {

    public List<Miembro> getMiembros();
    public void save(Miembro miembro);
    public void delete(String idMiembro);
    public Miembro findMiembro(String  idMiembro);
    public Miembro editMiembro(String idMiembro, Miembro miembro);
    public Integer countMember();
    public PercentMiembrosDTO percent();
    public Map<String, Integer> obtenerDistribucionEdad();


}
