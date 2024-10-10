package com.academia.iglesia.service;

import com.academia.iglesia.model.Miembro;

import java.util.List;

public interface IMiembroService {

    public List<Miembro> getMiembros();
    public void save(Miembro miembro);
    public void delete(String idMiembro);
    public Miembro findMiembro(String  idMiembro);
    public Miembro editMiembro(String idMiembro, Miembro miembro);



}
