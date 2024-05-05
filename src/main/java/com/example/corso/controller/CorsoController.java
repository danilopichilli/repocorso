package com.example.corso.controller;

import com.example.corso.dto.CorsoDto;
import com.example.corso.entity.Corso;
import com.example.corso.excelutility.ExcelUtility;
import com.example.corso.service.CorsoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/corso")
public class CorsoController {

    @Autowired
    private CorsoService corsoService;

    @Autowired
    private ExcelUtility excelUtility;

    @PostMapping("/salva")
    public String create(@RequestBody CorsoDto corsoDto){
        if(corsoDto.getNomeCorso().isEmpty() || corsoDto.getDurata() == 0 || corsoDto.getNomeDocente().isEmpty() || corsoDto.getCognomeDocente().isEmpty()){
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
    public List<Corso> findByDurata(@PathVariable int durata){
        return corsoService.findByDurata(durata);
    }

    @GetMapping("/findCorsiByNome/{nome}")
    public List<Corso> findByNome(@PathVariable String nome){
        return corsoService.findByNome(nome);
    }

    @GetMapping("/findCorsiAndDocenti")
    public List<CorsoDto> findCorsiAndDocenti(){
        return corsoService.findCorsiAndDocenti();
    }

    @GetMapping("/export-to-excel")
    public void exportCorsiListIntoExcelFile(HttpServletResponse response) throws IOException {
        corsoService.exportCorsiListIntoExcelFile(response);
    }

    @PostMapping("/upload-excelfile/{sheetName}")
    public String importExcelFileCorsiListIntoDatabase(@RequestParam ("file") MultipartFile file, @PathVariable String sheetName) {
        if(!file.isEmpty()){
            corsoService.importExcelFileCorsiListIntoDatabase(file, excelUtility,sheetName);
            return "Uploaded Successfully!";
        }
        return "Missing file!";

    }

}
