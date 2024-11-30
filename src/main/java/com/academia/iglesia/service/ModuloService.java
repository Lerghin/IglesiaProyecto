package com.academia.iglesia.service;

import com.academia.iglesia.dto.ModuloCursoDTO;
import com.academia.iglesia.dto.ModuloDTO;
import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Modulo;
import com.academia.iglesia.repository.ICursoRepository;
import com.academia.iglesia.repository.IModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class  ModuloService implements  IModuloService{
    @Autowired
    private IModuloRepository moduloRepository;
    @Autowired
    private ICursoRepository cursoRepository;
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
    public Modulo save(Modulo modulo, String idCurso) {
        // Buscar el curso por id
        Curso curso = cursoRepository.findById(idCurso).orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Establecer el curso al módulo
        modulo.setCurso(curso);

        // Guardar el módulo
        Modulo moduloSave = moduloRepository.save(modulo);

        // Obtener la lista de módulos del curso, y si no existe, inicializarla
        List<Modulo> moduloList = curso.getModuloList();
        if (moduloList == null) {
            moduloList = new ArrayList<>();
            curso.setModuloList(moduloList);
        }

        // Agregar el nuevo módulo a la lista
        moduloList.add(modulo);

        // Actualizar el curso con la lista de módulos modificada
        cursoRepository.save(curso);

        return moduloSave;
    }


    @Override
    public void delete(String idModulo) {
        // Buscar el curso correspondiente
        moduloRepository.deleteById(idModulo);


    }
    @Override
    public void editCursoModulo(String idCurso, Modulo modulo) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        List<Modulo> moduloList = curso.getModuloList();
        Iterator<Modulo> iterator = moduloList.iterator();

        while (iterator.hasNext()) {
            Modulo moduloFor = iterator.next();
            if (moduloFor.getIdModulo().equals(modulo.getIdModulo())) {
                // Elimina el módulo de la lista
                iterator.remove(); // Usar iterator.remove() para eliminar el módulo correctamente
                break;
            }
        }

        // Guarda los cambios en el curso después de modificar la lista
        cursoRepository.save(curso);
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
