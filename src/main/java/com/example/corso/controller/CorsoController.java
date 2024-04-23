package com.example.corso.controller;

import com.example.corso.dto.CorsoDTO;
import com.example.corso.entity.Corso;
import com.example.corso.service.CorsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/corso")
public class CorsoController {

    @Autowired
    private CorsoService corsoService;

    @PostMapping("/salva")
    public String create(@RequestBody Corso corso){
        if(corso.getNome().isEmpty() || corso.getDurata()==0){
            return "Non si può salvare vuoto!!";
        }else{
            corsoService.create(corso);
            return "Salvato!";
        }

    }

    @GetMapping("/lista")
    public List<Corso> lista(){
        return corsoService.getAllCorsi();
    }

    @PutMapping("/modifica/{id}")
    public void update(@RequestBody Corso corso, @PathVariable int id){
        corsoService.update(corso,id);
    }

    @DeleteMapping("/cancella{id}")
    public void delete(@RequestParam long id){
        corsoService.delete(id);
    }

    @GetMapping("/findByDurata{durata}")
    public List<Corso> findByDurata(@RequestParam int durata){
        return corsoService.findByDurata(durata);
    }

    @GetMapping("/convert")
    public List<CorsoDTO> convert(){
        return corsoService.convert();
    }
}
