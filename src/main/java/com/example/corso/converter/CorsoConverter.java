package com.example.corso.converter;

import com.example.corso.dto.CorsoDTO;
import com.example.corso.entity.Corso;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CorsoConverter {

    public CorsoDTO convertToDto(Corso corso) {
        CorsoDTO corsoDTO = new CorsoDTO();
        corsoDTO.setDurata(corso.getDurata());
        return corsoDTO;
    }

    public Corso convertToEntity(CorsoDTO corsoDTO) {
        Corso corso = new Corso();
        corso.setDurata(corsoDTO.getDurata());
        return corso;
    }

    public List<CorsoDTO> convertEntityToDto(List<Corso> corsoList) {
        List<CorsoDTO> corsoDTOList = new ArrayList<>();
        for(Corso corso : corsoList) {
            corsoDTOList.add(convertToDto(corso));
        }
        return corsoDTOList;
    }
}
