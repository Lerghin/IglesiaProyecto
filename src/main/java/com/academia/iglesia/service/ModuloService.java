package com.academia.iglesia.service;

import com.academia.iglesia.dto.ModuloCursoDTO;
import com.academia.iglesia.dto.ModuloDTO;
import com.academia.iglesia.model.Modulo;
import com.academia.iglesia.repository.IModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class  ModuloService implements  IModuloService{
    @Autowired
    private IModuloRepository moduloRepository;
    @Override
    public List<Modulo> get() {
        List<Modulo> moduloList= moduloRepository.findAll();
        return moduloList;
    }

    public List<ModuloCursoDTO> getModuloDTO() {
        List<Modulo> moduloList=this.get();
        List<ModuloCursoDTO> moduloCursoDTOS=new ArrayList<>();

        for(Modulo modulo: moduloList){
            ModuloCursoDTO moduloDTO= new ModuloCursoDTO();
            moduloDTO.setIdModulo(modulo.getIdModulo());
            moduloDTO.setIdCurso(modulo.getCurso().getIdCurso());
            moduloDTO.setNumModulo(modulo.getNumModulo());
            moduloDTO.setDescripcion(modulo.getDescripcion());
            moduloDTO.setNombreCurso(modulo.getCurso().getNombreCurso());
            moduloDTO.setFecha_inicio(modulo.getCurso().getFecha_inicio());
            moduloDTO.setFecha_fin(modulo.getCurso().getFecha_fin());
            moduloCursoDTOS.add(moduloDTO);
        }
        return  moduloCursoDTOS;
    }



    @Override
    public Modulo save(Modulo modulo) {
        Modulo moduloSave= moduloRepository.save(modulo);
        return moduloSave;
    }

    @Override
    public void delete(String idModulo) {
   moduloRepository.deleteById(idModulo);
    }

    @Override
    public Modulo find(String idModulo) {

        Modulo modulo = moduloRepository.findById(idModulo).orElseThrow(null);
        return modulo;
    }

    @Override
    public Modulo edit(String idModulo, Modulo modulo) {
        Modulo moduloFind= this.find(idModulo);
        moduloFind.setNumModulo(modulo.getNumModulo());
        moduloFind.setCurso(modulo.getCurso());
        moduloFind.setDescripcion(modulo.getDescripcion());
        moduloRepository.save(moduloFind);
        return  moduloFind;


    }
}
