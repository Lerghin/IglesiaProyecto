package com.academia.iglesia.service;

import com.academia.iglesia.model.Grupo;
import com.academia.iglesia.repository.ICursoRepository;
import com.academia.iglesia.repository.IGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoService  implements  IGrupoService{
    @Autowired
    private IGrupoRepository grupoRepository;
    @Override
    public List<Grupo> get() {
        List<Grupo> grupoList=grupoRepository.findAll();
        return grupoList;
    }

    @Override
    public Grupo save(Grupo grupo) {
        Grupo grupo1= grupoRepository.save(grupo);
        return grupo1;
    }

    @Override
    public void delete(String idGrupo) {
   grupoRepository.deleteById(idGrupo);
    }

    @Override
    public Grupo find(String idGrupo) {
        Grupo grupo= grupoRepository.findById(idGrupo).orElseThrow(null);

        return grupo;
    }

    @Override
    public Grupo edit(String idGrupo, Grupo grupo) {
        Grupo grupoFind= this.find(idGrupo);
        grupoFind.setNumeroGrupo(grupo.getNumeroGrupo());
        grupoFind.setDescripcion(grupo.getDescripcion());
        grupoFind.setMiembroList(grupo.getMiembroList());
        grupoRepository.save(grupoFind);
        return grupoFind;
    }
}
