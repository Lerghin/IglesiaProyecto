package com.academia.iglesia.service;

import com.academia.iglesia.dto.ModuloCursoDTO;
import com.academia.iglesia.dto.ModuloDTO;
import com.academia.iglesia.dto.ModuloNotaDTO;
import com.academia.iglesia.dto.NotaMiembroDTO;
import com.academia.iglesia.model.AprobacionCurso;
import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Modulo;
import com.academia.iglesia.model.Nota;
import com.academia.iglesia.repository.ICursoRepository;
import com.academia.iglesia.repository.IModuloRepository;
import com.academia.iglesia.repository.INotaRepository;
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


    @Autowired
    private INotaRepository notaRepository;
    @Override
    public List<Modulo> get() {
        List<Modulo> moduloList= moduloRepository.findAll();
        return moduloList;
    }


    @Override
    public ModuloCursoDTO ModuloGetDTO(String idCurso) {
        Curso curso= cursoRepository.findById(idCurso).orElseThrow(null);
        System.out.println(curso);
        ModuloCursoDTO moduloCursoDTO= new ModuloCursoDTO();
        moduloCursoDTO.setIdCurso(curso.getIdCurso());
        moduloCursoDTO.setNombreCurso(curso.getNombreCurso());
        List<ModuloDTO> moduloDTOS= new ArrayList<>();
        for(Modulo modulo: curso.getModuloList() ){
            ModuloDTO moduloDTO= new ModuloDTO();
            moduloDTO.setNumModulo(modulo.getNumModulo());
            moduloDTO.setDescripcion(modulo.getDescripcion());
            moduloDTO.setIdModulo(modulo.getIdModulo());
            moduloDTOS.add(moduloDTO);
        }
        moduloCursoDTO.setModuloDTOList(moduloDTOS);

        return  moduloCursoDTO;
    }

    @Override
    public List<ModuloNotaDTO> getModuloDTO() {
        List<Modulo> moduloList=this.get();
        List<ModuloNotaDTO> moduloNotaDTOList = new ArrayList<>();
        List <Nota> notaList= notaRepository.findAll();

        for(Modulo modulo: moduloList){
            ModuloNotaDTO moduloNotaDTO= new ModuloNotaDTO();
            moduloNotaDTO.setIdModulo(modulo.getIdModulo());
            moduloNotaDTO.setIdCurso(modulo.getIdModulo());
            moduloNotaDTO.setNumModulo(modulo.getNumModulo());
            moduloNotaDTO.setDescripcion(modulo.getDescripcion());
            moduloNotaDTO.setNombreCurso(modulo.getCurso().getNombreCurso());
            moduloNotaDTO.setFecha_fin(modulo.getCurso().getFecha_fin());
            moduloNotaDTO.setFecha_inicio(modulo.getCurso().getFecha_inicio());
            List<NotaMiembroDTO> notaMiembroDTOs= new ArrayList<>();

            for(Nota nota: notaList ){
                boolean igualModulo= modulo.getIdModulo().equals(nota.getModulo().getIdModulo());
                if( igualModulo){

                    NotaMiembroDTO notaMiembroDTO= new NotaMiembroDTO();
                    notaMiembroDTO.setIdModulo(modulo.getIdModulo());
                    notaMiembroDTO.setNota(nota.getNota());
                    notaMiembroDTO.setIdNota(nota.getIdNota());
                    if(nota.getNota()>12){
                        notaMiembroDTO.setStatusAprobacion(AprobacionCurso.APROBADO);
                    }
                    notaMiembroDTOs.add(notaMiembroDTO);
                }

            }
            moduloNotaDTO.setNotaMiembroDTOList(notaMiembroDTOs);
            moduloNotaDTOList.add(moduloNotaDTO);
        }
        return moduloNotaDTOList ;
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
