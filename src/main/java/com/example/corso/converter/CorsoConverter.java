package com.example.corso.converter;

import com.example.corso.dto.DocenteDTO;
import com.example.corso.service.DocenteService;
import com.example.corso.dto.CorsoDTO;
import com.example.corso.entity.Corso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CorsoConverter {

    @Autowired
    private DocenteService docenteService;

    public CorsoDTO convertToDto(Corso corso) {
        CorsoDTO corsoDTO = new CorsoDTO();
        corsoDTO.setNomeCorso(corso.getNome());
        corsoDTO.setDurata(corso.getDurata());
        DocenteDTO docenteDTO = docenteService.getDocenteById(corso.getIdDocente());
        corsoDTO.setNomeDocente(docenteDTO.getNome());
        corsoDTO.setCognomeDocente(docenteDTO.getCognome());
        return corsoDTO;
    }

    public List<CorsoDTO> createCorsoAndDocenteList(List<Corso> corsoList) {
        List<CorsoDTO> corsoDTOList = new ArrayList<>();
        for(Corso corso : corsoList) {
            corsoDTOList.add(convertToDto(corso));
        }
        return corsoDTOList;
    }

    public Corso convertDtoToEntity(CorsoDTO corsoDTO) {
        Corso corso = new Corso();
        corso.setNome(corsoDTO.getNomeCorso());
        corso.setDurata(corsoDTO.getDurata());
        corso.setIdDocente(docenteService.createOrFindDocente(corsoDTO).getId());
        return corso;
    }



}
