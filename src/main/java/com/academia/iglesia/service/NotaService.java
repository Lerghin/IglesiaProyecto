package com.academia.iglesia.service;

import com.academia.iglesia.dto.NotaMiembroDTO;
import com.academia.iglesia.model.*;
import com.academia.iglesia.repository.ICursoRepository;
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
    @Autowired
    private ICursoRepository cursoRepository;
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
        System.out.println(not);
        // Buscar el módulo asociado al ID del DTO
        Modulo modulo = moduloRepository.findById(not.getIdModulo())
                .orElseThrow(() -> new RuntimeException("El módulo especificado no existe"));

        // Obtener el curso asociado al módulo
        Curso curso = cursoRepository.findById(modulo.getCurso().getIdCurso())
                .orElseThrow(() -> new RuntimeException("El curso asociado al módulo no existe"));

        // Buscar el miembro por cédula
        Miembro miembroFind = miembrosRepository.findByCedula(not.getCedula());
        if (miembroFind == null) {
            throw new RuntimeException("El miembro con la cédula especificada no existe");
        }

        // Verificar si el miembro pertenece al curso
        boolean isMemberInCourse = curso.getParticipantes().stream()
                .anyMatch(miembro -> miembro.getCedula().equals(miembroFind.getCedula()));

        if (!isMemberInCourse) {
            throw new RuntimeException("Este miembro no está inscrito en el curso");
        }

        // Crear y asignar valores a la nueva entidad Nota
        Nota nota = new Nota();
        nota.setMiembro(miembroFind);
        nota.setModulo(modulo);
        nota.setNota(not.getNota());

        // Determinar la aprobación o reprobación según la nota
        nota.setAprobacionCurso(not.getNota() > 12 ? AprobacionCurso.APROBADO : AprobacionCurso.REPROBADO);

        // Guardar la nota en el repositorio
        return notaRepository.save(nota);
    }
    public List<NotaMiembroDTO> getNotasMiembro(String idModulo){
        Modulo modulo= moduloRepository.findById(idModulo).orElseThrow(null);
        List<NotaMiembroDTO> notaMiembroDTOS= new ArrayList<>();
        for(Nota nota: this.get()){
            boolean isEqual= nota.getModulo().getIdModulo().equals(modulo.getIdModulo());
            if(isEqual){
                NotaMiembroDTO notaMiembroDTO= new NotaMiembroDTO();
                notaMiembroDTO.setCedula(nota.getMiembro().getCedula());
                notaMiembroDTO.setIdModulo(nota.getModulo().getIdModulo());
                notaMiembroDTO.setNota(nota.getNota());
                notaMiembroDTO.setIdNota(nota.getIdNota());


               notaMiembroDTOS.add(notaMiembroDTO);
            }
        }
        return notaMiembroDTOS;
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
