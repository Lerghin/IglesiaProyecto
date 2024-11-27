package com.academia.iglesia.service;

import com.academia.iglesia.dto.PercentMiembrosDTO;
import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.model.Sexo;
import com.academia.iglesia.repository.MiembrosRepository;
import com.mongodb.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.academia.iglesia.model.Sexo.*;

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
      miembroFind.setSexo(miembro.getSexo());
      miembroFind.setDireccion(miembro.getDireccion());
      miembroFind.setTelefono(miembro.getTelefono());
      miembroFind.setFecha_nacimiento(miembro.getFecha_nacimiento());
      miembroFind.setOcupacion(miembro.getOcupacion());
      miembroFind.setCursosRealizados(miembro.getCursosRealizados());
      miembroFind.setFecha_ingreso(miembro.getFecha_ingreso());
      miembrosRepository.save(miembroFind);
      return miembroFind;
    }

    public Integer countMember(){
      int count=0;
      List<Miembro> miembroList= this.getMiembros();
      for(Miembro miembro: miembroList){
          count= count+1;
      }
        return count;
    }


   public PercentMiembrosDTO percent(){
        PercentMiembrosDTO percentMiembrosDTO= new PercentMiembrosDTO();
        percentMiembrosDTO.setFillMen("#C3EBFA");
        percentMiembrosDTO.setFillWomen("#FAE27C");
        percentMiembrosDTO.setFillTot("white");
        int total= this.countMember();
        List<Miembro> miembroList= this.getMiembros();
        int countMen= 0;
       int countWomen= 0;
       double porcentWomen=0.00;
       double porcentMen=0.00;
       for(Miembro miembro: miembroList){
            if(miembro.getSexo()== FEMENINO){
            countWomen=countWomen+1;
            porcentWomen= (countWomen*100)/total;
            }else{
                countMen=countMen+1;
                porcentMen= (countMen*100)/total;
            }
        }
        percentMiembrosDTO.setTotal(total);
        percentMiembrosDTO.setCountMen(countMen);
        percentMiembrosDTO.setCountWomen(countWomen);
        percentMiembrosDTO.setPorcentWomen(porcentWomen);
        percentMiembrosDTO.setPorcenMen(porcentMen);


       return percentMiembrosDTO;
   }


    public Map<String, Integer> obtenerDistribucionEdad() {
        List<Miembro> miembros = miembrosRepository.findAll();
        Map<String, Integer> distribucion = new HashMap<>();

        for (Miembro miembro : miembros) {
            int edad = miembro.getEdad();
            String rangoEdad = obtenerRangoEdad(edad);
            distribucion.put(rangoEdad, distribucion.getOrDefault(rangoEdad, 0) + 1);
        }

        return distribucion;
    }

    private String obtenerRangoEdad(int edad) {
        if (edad >= 0 && edad <= 10) {
            return "0-10";
        } else if (edad >= 11 && edad <= 20) {
            return "11-20";
        } else if (edad >= 21 && edad <= 30) {
            return "21-30";
        } else if (edad >= 31 && edad <= 40) {
            return "31-40";
        } else if (edad >= 41 && edad <= 50) {
            return "41-50";
        } else if (edad >= 51 && edad <= 60) {
            return "51-60";
        } else {
            return "61+";
        }
    }


}
