package com.academia.iglesia.service;

import com.academia.iglesia.dto.CursoDTO;
import com.academia.iglesia.dto.MiembroDTO;
import com.academia.iglesia.dto.ModuloDTO;
import com.academia.iglesia.dto.ProfessorDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @Override
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
           List<ProfessorDTO> professorDTOList=new ArrayList<>();
         for(Professor professor: curso.getProfessor()){

             ProfessorDTO professorDTO= new ProfessorDTO();
             professorDTO.setCedula(professor.getCedula());
             professorDTO.setName(professor.getName());
             professorDTO.setLastName(professor.getLastName());
             professorDTO.setIdProfessor(professor.getIdProfessor());
             professorDTOList.add(professorDTO);
         }
         cursoDTO.setProfessorDTOS(professorDTOList);
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
    public CursoDTO findDTOCurso(String idCurso){
        Curso curso= this.find(idCurso);
        CursoDTO cursoDTO= new CursoDTO();
        cursoDTO.setIdCurso(curso.getIdCurso());
        cursoDTO.setNombreCurso(curso.getNombreCurso());
        cursoDTO.setDescripcion(curso.getDescripcion());
        cursoDTO.setFecha_inicio(curso.getFecha_inicio());
        cursoDTO.setFecha_fin(curso.getFecha_fin());

        List<ProfessorDTO> professorDTOS= new ArrayList<>();
        for(Professor professor: curso.getProfessor()){
            ProfessorDTO professorDTO= new ProfessorDTO();
            professorDTO.setCedula(professor.getCedula());
            professorDTO.setName(professor.getName());
            professorDTO.setLastName(professor.getLastName());
            professorDTO.setIdProfessor(professor.getIdProfessor());
            professorDTOS.add(professorDTO);
        }
        cursoDTO.setProfessorDTOS(professorDTOS);
        List<MiembroDTO> miembroDTOS= new ArrayList<>();
       for(Miembro miembro :curso.getParticipantes()){
           MiembroDTO miembroDTO =new MiembroDTO();
           miembroDTO.setNombre(miembro.getNombre());
           miembroDTO.setApellido(miembro.getApellido());
           miembroDTO.setCedula(miembro.getCedula());
           miembroDTO.setIdMiembro(miembro.getIdMiembro());
           miembroDTOS.add(miembroDTO);
       }
       cursoDTO.setMiembroDTOList(miembroDTOS);

       List<ModuloDTO> moduloDTOS= new ArrayList<>();
       for(Modulo  modulo: curso.getModuloList()){
           ModuloDTO moduloDTO= new ModuloDTO();
           moduloDTO.setIdModulo(modulo.getIdModulo());
           moduloDTO.setNumModulo(modulo.getNumModulo());
           moduloDTO.setDescripcion(modulo.getDescripcion());
           moduloDTOS.add(moduloDTO);
       }
       cursoDTO.setModuloList(moduloDTOS);
       return cursoDTO;
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
        List<Professor> professorList=new ArrayList<>();
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
        for(ProfessorDTO professorDTO: curso.getProfessorDTOS()){
            Professor professor= this.FindByCedula(professorDTO.getCedula());
             if(professor==null){
                 throw  new RuntimeException("No existe profesor con esa cedula");
             }
            professorList.add(professor);
        }

        Curso cursoSaved= new Curso();
        cursoSaved.setProfessor(professorList);
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

        Curso curso = this.find(idCurso);
        for(Modulo modulo: curso.getModuloList()){
            moduloRepository.deleteById(modulo.getIdModulo());
        }

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
        cursoFind.setParticipantes(cursoFind.getParticipantes());
        cursoFind.setModuloList(cursoFind.getModuloList());
        cursoFind.setProfessor(cursoFind.getProfessor());
        cursoRepository.save(cursoFind);
        return  cursoFind;
    }

  public void addMemberCurso(String idCurso, String cedula) {
      Curso curso = this.find(idCurso);
      Miembro miembro = iMiembrosRepository.findByCedula(cedula);
  
      List<Miembro> miembroList = curso.getParticipantes();
  
      // Verificar si ya existe el miembro en la lista
      boolean exists = miembroList.stream()
              .anyMatch(existingMember -> existingMember.getCedula().equals(miembro.getCedula()));
  
      if (exists) {
          throw new RuntimeException("Ya existe miembro en el curso");
      }
  
      // Agregar el miembro y guardar el curso
      miembroList.add(miembro);
      curso.setParticipantes(miembroList);
      cursoRepository.save(curso);
  }
  
  
  
  
   public void removeMemberCurso(String idCurso, String cedula) {
       Curso curso = this.find(idCurso);
       Miembro miembro = iMiembrosRepository.findByCedula(cedula);
   
       List<Miembro> miembroList = curso.getParticipantes();
   
       // Remover el miembro si existe en la lista
       boolean removed = miembroList.removeIf(existingMember -> existingMember.getCedula().equals(miembro.getCedula()));
   
       if (removed) {
        curso.setParticipantes(miembroList);
           cursoRepository.save(curso);
       } else {
           throw new RuntimeException("El miembro no pertenece al curso");
    }
   }

   public Map<String, String> cedulasCursos(String idCurso){
        Curso curso = this.find(idCurso);

        Map<String, String> cedulasyNombres= new HashMap<>();
        for(Miembro miembro: curso.getParticipantes()){
            String cedula= new String();
            cedula= miembro.getCedula();
            String nombre= miembro.getNombre();
            String apellido = miembro.getApellido();
            String nombreApellido= nombre+ " " + apellido;
            cedulasyNombres.put(cedula, nombreApellido);
        }
        return  cedulasyNombres;
   }


}
