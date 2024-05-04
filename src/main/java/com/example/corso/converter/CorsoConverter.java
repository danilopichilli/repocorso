package com.example.corso.converter;

import com.example.corso.dto.DocenteDto;
import com.example.corso.service.DocenteService;
import com.example.corso.dto.CorsoDto;
import com.example.corso.entity.Corso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CorsoConverter {

    @Autowired
    private DocenteService docenteService;

    public CorsoDto convertToDto(Corso corso) {
        CorsoDto corsoDTO = new CorsoDto();
        corsoDTO.setNomeCorso(corso.getNome());
        corsoDTO.setDurata(corso.getDurata());
        DocenteDto docenteDTO = docenteService.getDocenteById(corso.getIdDocente());
        corsoDTO.setNomeDocente(docenteDTO.getNome());
        corsoDTO.setCognomeDocente(docenteDTO.getCognome());
        return corsoDTO;
    }

    public List<CorsoDto> createCorsoAndDocenteList(List<Corso> corsoList) {
        List<CorsoDto> corsoDtoList = new ArrayList<>();
        for(Corso corso : corsoList) {
            corsoDtoList.add(convertToDto(corso));
        }
        return corsoDtoList;
    }

    public Corso convertDtoToEntity(CorsoDto corsoDTO) {
        Corso corso = new Corso();
        corso.setNome(corsoDTO.getNomeCorso());
        corso.setDurata(corsoDTO.getDurata());
        corso.setIdDocente(docenteService.createOrFindDocente(corsoDTO).getId());
        return corso;
    }

    public List<Corso> convertEntityToDto(List<CorsoDto> corsoDtoList) {
        List<Corso> corsoList = new ArrayList<>();
        for(CorsoDto corsoDto : corsoDtoList) {
            corsoList.add(convertDtoToEntity(corsoDto));
        }
        return corsoList;
    }


}
