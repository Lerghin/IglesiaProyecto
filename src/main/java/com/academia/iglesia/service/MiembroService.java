package com.academia.iglesia.service;

import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.repository.MiembrosRepository;
import com.mongodb.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MiembroService implements  IMiembroService {
    @Autowired
    private MiembrosRepository miembrosRepository;


    @Override
    public List<Miembro> getMiembros() {
       List<Miembro> miembroList= miembrosRepository.findAll();
       return  miembroList;

    }

    @Override
    public void save(Miembro miembro) {
        List<Miembro> miembroList= this.getMiembros();
            for(Miembro miembro1: miembroList){
                if(miembro1.getCedula().equals(miembro.getCedula())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "La cédula ya está registrada");
                }else {
                    Miembro miembroSaved=  miembrosRepository.save(miembro);
                }
            }
    }

    @Override
    public void delete(String idMiembro) {
      miembrosRepository.deleteById(idMiembro);
    }

    @Override
    public Miembro findMiembro(String idMiembro) {
        Miembro miembro= miembrosRepository.findById(idMiembro).orElseThrow(null);

        return miembro;
    }

    @Override
    public Miembro editMiembro(String idMiembro, Miembro miembro) {
      Miembro miembroFind= this.findMiembro(idMiembro);
      miembroFind.setNombre(miembro.getNombre());
      miembroFind.setApellido(miembro.getApellido());
      miembroFind.setCedula(miembro.getCedula());
      miembroFind.setDireccion(miembro.getDireccion());
      miembroFind.setTelefono(miembro.getTelefono());
      miembroFind.setFecha_nacimiento(miembro.getFecha_nacimiento());
      miembroFind.setOcupacion(miembro.getOcupacion());
      miembrosRepository.save(miembroFind);
      return miembroFind;
    }




}
