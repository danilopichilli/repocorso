package com.example.corso.service;

import com.example.corso.converter.CorsoConverter;
import com.example.corso.dto.CorsoDTO;
import com.example.corso.entity.Corso;
import com.example.corso.repository.CorsoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorsoService {

    @Autowired
    private CorsoRepository corsoRepository;

    @Autowired
    private CorsoConverter corsoConverter;

    public void create(Corso corso) {
        corsoRepository.save(corso);
    }

    public List<Corso> getAllCorsi() {
        return corsoRepository.findAll();
    }

    public Optional<Corso> update(Corso corso, long id) {
        Optional<Corso> foundCorso = corsoRepository.findById(id);
        if(foundCorso.isEmpty()) {
            return Optional.empty();
        }else {
            foundCorso.get().setNome(corso.getNome());
            foundCorso.get().setDurata(corso.getDurata());
            corsoRepository.save(foundCorso.get());
            return foundCorso;
        }
    }

    public Optional<Corso> delete(long id) {
        Optional<Corso> foundCorso = corsoRepository.findById(id);
        if(foundCorso.isEmpty()) {
            return Optional.empty();
        }else{
            corsoRepository.deleteById(id);
            return foundCorso;
        }
    }

    public List<Corso> findByDurata(int durata) {
        return corsoRepository.findByDurata(durata);
    }

    public List<CorsoDTO> convert() {
        return corsoConverter.convertEntityToDto(getAllCorsi());
    }
}
