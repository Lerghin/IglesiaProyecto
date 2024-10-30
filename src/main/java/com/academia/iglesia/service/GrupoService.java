package com.academia.iglesia.service;

import com.academia.iglesia.dto.GrupoDTO;
import com.academia.iglesia.dto.MiembroDTO;
import com.academia.iglesia.model.Grupo;
import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.repository.ICursoRepository;
import com.academia.iglesia.repository.IGrupoRepository;
import com.academia.iglesia.repository.MiembrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GrupoService  implements  IGrupoService{
    @Autowired
    private IGrupoRepository grupoRepository;
    @Autowired
    private MiembrosRepository miembrosRepository;
    @Override
    public List<Grupo> get() {
        List<Grupo> grupoList=grupoRepository.findAll();
        return grupoList;
    }

    @Override
    public Grupo save(GrupoDTO grupoDTO) {
        List<Miembro> miembroList= new ArrayList<>();
        Grupo grupo= new Grupo();
        grupo.setNumeroGrupo(grupoDTO.getNumeroGrupo());
        for(MiembroDTO miembroDTO: grupoDTO.getMiembroList()){
            Miembro miembro= miembrosRepository.findByCedula(miembroDTO.getCedula());
            miembroList.add(miembro);
        }
        grupo.setMiembroList(miembroList);
        grupoRepository.save(grupo);
        return grupo ;
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
        grupoFind.setMiembroList(grupo.getMiembroList());
        grupoRepository.save(grupoFind);
        return grupoFind;
    }
}
