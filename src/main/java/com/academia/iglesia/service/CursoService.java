package com.academia.iglesia.service;

import com.academia.iglesia.dto.CursoDTO;
import com.academia.iglesia.dto.MiembroDTO;
import com.academia.iglesia.dto.ModuloDTO;
import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.model.Modulo;
import com.academia.iglesia.model.Professor;
import com.academia.iglesia.repository.ICursoRepository;
import com.academia.iglesia.repository.IModuloRepository;
import com.academia.iglesia.repository.MiembrosRepository;
import com.academia.iglesia.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CursoService  implements  ICursoService{
    @Autowired
    private ICursoRepository cursoRepository;
    @Autowired
    private MiembrosRepository iMiembrosRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private IModuloRepository moduloRepository;
    @Override
    public List<Curso> get() {
        List<Curso> cursos= cursoRepository.findAll();
        return cursos;
    }
    public List<CursoDTO> getDTOCurso() {
        List<Curso> cursos=this.get();
        List<CursoDTO> cursoDTOS= new ArrayList<>();

        for(Curso curso: cursos){
           CursoDTO cursoDTO= new CursoDTO();
           cursoDTO.setNombreCurso(curso.getNombreCurso());
           cursoDTO.setFecha_inicio(curso.getFecha_inicio());
           cursoDTO.setFecha_fin(curso.getFecha_fin());
           cursoDTO.setDescripcion(curso.getDescripcion());
           cursoDTO.setIdCurso(curso.getIdCurso());
           List<ModuloDTO> moduloDTOS= new ArrayList<>();
           for(Modulo modulo: curso.getModuloList()){
               ModuloDTO moduloDTO= new ModuloDTO();
               moduloDTO.setIdModulo(modulo.getIdModulo());
               moduloDTO.setDescripcion(modulo.getDescripcion());
               moduloDTO.setNumModulo(modulo.getNumModulo());
               moduloDTOS.add(moduloDTO);
           }
           cursoDTO.setModuloList(moduloDTOS);
            List<MiembroDTO> miembroDTOS= new ArrayList<>();
           for(Miembro miembro: curso.getParticipantes()){
               MiembroDTO miembroDTO= new MiembroDTO();
               miembroDTO.setApellido(miembro.getApellido());
               miembroDTO.setNombre(miembro.getNombre());
               miembroDTO.setIdMiembro(miembro.getIdMiembro());
               miembroDTO.setCedula(miembro.getCedula());
               miembroDTOS.add(miembroDTO);
           }
           cursoDTO.setMiembroDTOList(miembroDTOS);
           cursoDTOS.add(cursoDTO);
        }
        return cursoDTOS;
    }

    @Override
    public Professor FindByCedula(String cedula) {
        Professor professor= professorRepository.findByCedula(cedula);
        return professor;
    }

    @Override
    public Curso save(CursoDTO curso) {
        List<Miembro> miembroList= new ArrayList<>();
        List<Modulo> moduloList= new ArrayList<>();
        Professor professor=this.FindByCedula(curso.getCedulaProfessor());
        for(MiembroDTO miembroDTO: curso.getMiembroDTOList()){
            Miembro miembro= iMiembrosRepository.findByCedula(miembroDTO.getCedula());
            miembroList.add(miembro);

        }
        for(ModuloDTO moduloDTO: curso.getModuloList()){
          Modulo moduloSaved= new Modulo();
          moduloSaved.setNumModulo(moduloDTO.getNumModulo());
          moduloSaved.setDescripcion(moduloDTO.getDescripcion());
          moduloSaved.setNumModulo(moduloDTO.getNumModulo());
          moduloRepository.save(moduloSaved);

          moduloList.add(moduloSaved);

        }

        Curso cursoSaved= new Curso();
        cursoSaved.setProfessor(professor);
        cursoSaved.setNombreCurso(curso.getNombreCurso());
        cursoSaved.setDescripcion(curso.getDescripcion());
        cursoSaved.setFecha_inicio(curso.getFecha_inicio());
        cursoSaved.setFecha_fin(curso.getFecha_fin());
        cursoSaved.setParticipantes(miembroList);
        cursoSaved.setModuloList(moduloList);
        cursoRepository.save(cursoSaved);

      for(Modulo modulo: moduloList){
          modulo.setCurso(cursoSaved);
          moduloRepository.save(modulo);
      }

        return cursoSaved;
    }



    @Override
    public void delete(String idCurso) {
      cursoRepository.deleteById(idCurso);
    }

    @Override
    public Curso find(String idCurso) {

        Curso curso= cursoRepository.findById(idCurso).orElseThrow(null);
        return curso;
    }

    @Override
    public Curso edit(String idCurso, Curso curso) {
        Curso cursoFind= this.find(idCurso);
        cursoFind.setNombreCurso(curso.getNombreCurso());
        cursoFind.setDescripcion(curso.getDescripcion());
        cursoFind.setFecha_fin(curso.getFecha_fin());
        cursoFind.setFecha_inicio(curso.getFecha_inicio());
        cursoFind.setParticipantes(curso.getParticipantes());
        cursoRepository.save(cursoFind);
        return  cursoFind;
    }




}
