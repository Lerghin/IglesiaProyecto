package com.academia.iglesia.service;

import com.academia.iglesia.model.Curso;
import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.model.Professor;
import com.academia.iglesia.repository.ICursoRepository;
import com.academia.iglesia.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProfessorService implements  IProfessorService {
    @Autowired
    private ProfessorRepository profeRepo;
    @Autowired
    private ICursoRepository cursoRepository;
    @Override
    public List<Professor> get() {
        return  profeRepo.findAll();
    }

    @Override
    public Professor save(Professor pro) {
        Professor professor= profeRepo.save(pro);
        return professor;
    }

    @Override
    public void delete(String idProfessor) {
      profeRepo.deleteById(idProfessor);
    }

    @Override
    public Professor find(String idProfessor) {
        Professor professor= profeRepo.findById(idProfessor).orElseThrow(null);
        return professor;
    }

    @Override
    public Professor edit(String idProfessor, Professor pro) {
      Professor professor = this.find(idProfessor);

      professor.setCedula(pro.getCedula());
      professor.setEmail(pro.getEmail());
      professor.setAddress(pro.getAddress());
      professor.setPhone(pro.getPhone());
      professor.setName(pro.getName());
      professor.setCursos(pro.getCursos());
      professor.setFecha_nacimiento(pro.getFecha_nacimiento());

      professor.setLastName(pro.getLastName());
      profeRepo.save(professor);
      return professor;
    }
    @Override
    public Professor findByCedula(String cedula) {
        Professor professor= profeRepo.findByCedula(cedula);
        return professor;
    }

    @Override
    public Integer countProfessor() {
        int count=0;
        List<Professor> professors= this.get();
        for(Professor professor: professors){
            count= count+1;
        }
        return count;
    }


    public void addProfesorCurso(String cedula, String idCurso){
        Professor professor=this.findByCedula(cedula);
        Curso curso= cursoRepository.findById(idCurso).orElseThrow(null);
       List<Professor> professors= curso.getProfessor();
        List<String> cursosProfe= professor.getCursos();
        cursosProfe.add(curso.getNombreCurso());
        professors.add(professor);
        cursoRepository.save(curso);
        profeRepo.save(professor);


    }

    public void removeProfessor(String cedula, String idCurso) {
        // Encuentra el profesor por su cédula
        Professor professor = this.findByCedula(cedula);
        if (professor == null) {
            throw new IllegalArgumentException("El profesor no existe");
        }

        // Obtiene el curso por su ID
        Curso curso = cursoRepository.findById(idCurso).orElseThrow(() ->
                new IllegalArgumentException("El curso no existe"));

        // Filtra la lista de profesores del curso para eliminar el profesor deseado
        List<Professor> professorList = curso.getProfessor();
        boolean removed = professorList.removeIf(prof ->
                professor.getIdProfessor().equals(prof.getIdProfessor()));

        if (removed) {
            // Guarda el curso actualizado
            cursoRepository.save(curso);

            // Filtra los cursos del profesor para eliminar este curso
            List<String> cursosProfe = professor.getCursos();
            cursosProfe.removeIf(cur -> cur.equalsIgnoreCase(curso.getNombreCurso()));

            // Guarda el profesor actualizado
            profeRepo.save(professor);
        } else {
            throw new IllegalStateException("El profesor no estaba asociado al curso");
        }
    }




    }







