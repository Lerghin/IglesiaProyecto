package com.academia.iglesia.service;

import com.academia.iglesia.dto.NotaMiembroDTO;
import com.academia.iglesia.model.AprobacionCurso;
import com.academia.iglesia.model.Miembro;
import com.academia.iglesia.model.Modulo;
import com.academia.iglesia.model.Nota;
import com.academia.iglesia.repository.IModuloRepository;
import com.academia.iglesia.repository.INotaRepository;
import com.academia.iglesia.repository.MiembrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotaService implements  INotaService {

    @Autowired
    private INotaRepository notaRepository;
    @Autowired
    private MiembrosRepository miembrosRepository;
    @Autowired
    private IModuloRepository moduloRepository;
    @Override
    public List<Nota> get() {

        List<Nota> notaList= notaRepository.findAll();
        return notaList;
    }

    @Override
    public List<NotaMiembroDTO> getDTO() {

        List<Nota> notaList= this.get();
        List<NotaMiembroDTO> notas= new ArrayList<>();
        for(Nota nota: notaList){
            NotaMiembroDTO notaMiembroDTO= new NotaMiembroDTO();
            notaMiembroDTO.setNota(nota.getNota());
            notaMiembroDTO.setIdNota(nota.getIdNota());
            notaMiembroDTO.setCedula(nota.getMiembro().getCedula());
            notaMiembroDTO.setIdModulo(nota.getModulo().getIdModulo());
            notaMiembroDTO.setStatusAprobacion(
             nota.getNota()>=60? AprobacionCurso.APROBADO: AprobacionCurso.REPROBADO);
            notas.add(notaMiembroDTO);

        }
        return notas;
    }

    @Override
    public Nota save(Nota not) {

        Nota nota = notaRepository.save(not);
        return nota;
    }
    @Override
    public Nota saveNotaDTO(NotaMiembroDTO not) {
        Modulo modulo=moduloRepository.findById(not.getIdModulo()).orElseThrow(null);
        Miembro miembro= miembrosRepository.findByCedula(not.getCedula());
        Nota nota = new Nota() ;
        nota.setNota(not.getNota());
        nota.setModulo(modulo);
        nota.setMiembro(miembro);
        notaRepository.save(nota);

        return nota;
    }



    @Override
    public void delete(String idNota) {
   notaRepository.deleteById(idNota);
    }

    @Override
    public Nota find(String idNota) {
        Nota notaFind= notaRepository.findById(idNota).orElseThrow();
        return notaFind;
    }
    @Override
    public NotaMiembroDTO findNota(String cedula, String idModulo) {
        List<NotaMiembroDTO> notaList = this.getDTO();

        for (NotaMiembroDTO nota : notaList) {
            boolean esIgual = nota.getCedula().equals(cedula) && nota.getIdModulo().equals(idModulo);
            if (esIgual) {
                NotaMiembroDTO notaMiembroDTO = new NotaMiembroDTO();
                notaMiembroDTO.setIdNota(nota.getIdNota());
                notaMiembroDTO.setCedula(nota.getCedula());
                notaMiembroDTO.setIdModulo(nota.getIdModulo());
                notaMiembroDTO.setNota(nota.getNota());
                notaMiembroDTO.setStatusAprobacion(
                        nota.getNota() >= 60 ? AprobacionCurso.APROBADO : AprobacionCurso.REPROBADO
                );
                return notaMiembroDTO; // Retorna inmediatamente si se encuentra la nota
            }
        }

        // Lanza excepción si no se encuentra ninguna nota que coincida
        throw new RuntimeException("Nota no encontrada para el miembro con cédula: " + cedula + " y módulo: " + idModulo);
    }




    @Override
    public Nota edit(String idNota, Nota nota) {
        Nota notaFind= this.find(idNota);
        notaFind.setModulo(nota.getModulo());
        notaFind.setMiembro(nota.getMiembro());
        notaFind.setNota(nota.getNota());
        notaRepository.save(notaFind);
        return  notaFind;
    }
}
