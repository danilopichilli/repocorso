package com.example.corso.controller;

import com.example.corso.dto.CorsoDTO;
import com.example.corso.entity.Corso;
import com.example.corso.excelgenerator.ExcelGenerator;
import com.example.corso.service.CorsoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/corso")
public class CorsoController {

    @Autowired
    private CorsoService corsoService;

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
        return corsoService.findCorsi();
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

    @GetMapping("/findCorsiByDurata/{durata}")
    public List<Corso> findByDurata(@PathVariable String durata){
        return corsoService.findByDurata(durata);
    }

    @GetMapping("/findCorsiByNome/{nome}")
    public List<Corso> findByNome(@PathVariable String nome){
        return corsoService.findByNome(nome);
    }

    @GetMapping("/findCorsiAndDocenti")
    public List<CorsoDTO> findCorsiAndDocenti(){
        return corsoService.findCorsiAndDocenti();
    }

    @GetMapping("/export-to-excel")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue  = "attachment; filename=corso" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<CorsoDTO> listOfCorsiDto = corsoService.findCorsiAndDocenti();
        ExcelGenerator generator = new ExcelGenerator(listOfCorsiDto);
        generator.generateExcelFile(response);
    }

}
