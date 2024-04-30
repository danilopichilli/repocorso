package com.example.corso.controller;

import com.example.corso.dto.CorsoDTO;
import com.example.corso.entity.Corso;
import com.example.corso.dto.DocenteDTO;
import com.example.corso.service.CorsoService;
import com.example.corso.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/corso")
public class CorsoController {

    @Autowired
    private CorsoService corsoService;

    @Autowired
    private DocenteService docenteService;

    @PostMapping("/salva")
    public String create(@RequestBody CorsoDTO corsoDto){
        if(corsoDto.getNomeCorso().isEmpty() || corsoDto.getDurata().isEmpty() || corsoDto.getNomeDocente().isEmpty() || corsoDto.getCognomeDocente().isEmpty()){
            return "Non si può salvare vuoto!!";
        }
        corsoService.create(corsoDto);
        return "Salvato!";
    }

    @GetMapping("/lista")
    public List<Corso> lista(){
        return corsoService.getAllCorsi();
    }

    @PutMapping("/modifica/{id}")
    public String update(@RequestBody Corso corso, @PathVariable Long id){
        if(corsoService.update(corso,id).isPresent()){
            return "Aggiornato!";
        }
        return "Id non trovato!";
    }

    @DeleteMapping("/cancella")//cancella?id=1
    public String delete(@RequestParam Long id){
        if(corsoService.delete(id).isPresent()){
            return "Eliminato!";
        }
        return "Id non trovato!";
    }

    @GetMapping("/findByDurata/{durata}")
    public List<Corso> findByDurata(@PathVariable String durata){
        return corsoService.findByDurata(durata);
    }

    @GetMapping("/convert")
    public List<CorsoDTO> convert(){
        return corsoService.convert();
    }

    @GetMapping(value = "/findDocente/{id}",produces = "application/json")
    public DocenteDTO findDocente(@PathVariable Long id){
        return docenteService.getDocenteById(id);
    }


}
