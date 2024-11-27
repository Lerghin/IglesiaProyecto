package com.academia.iglesia.service;

import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.model.Professor;
import com.academia.iglesia.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProfessorService implements  IProfessorService {
    @Autowired
    private ProfessorRepository profeRepo;
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


}
