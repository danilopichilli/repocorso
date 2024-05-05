package com.example.corso.service;

import com.example.corso.converter.CorsoConverter;
import com.example.corso.dto.CorsoDto;
import com.example.corso.entity.Corso;
import com.example.corso.excelgenerator.ExcelGenerator;
import com.example.corso.excelutility.ExcelUtility;
import com.example.corso.repository.CorsoRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CorsoService {

    @Autowired
    private CorsoRepository corsoRepository;

    @Autowired
    private CorsoConverter corsoConverter;

    public ResponseEntity<?> create(CorsoDto corsoDto) {
        if(!corsoDto.getNomeCorso().isEmpty() || corsoDto.getDurata() == 0 || corsoDto.getNomeDocente().isEmpty() || corsoDto.getCognomeDocente().isEmpty()) {
            try {
                corsoRepository.save(corsoConverter.convertDtoToEntity(corsoDto));
                String message = "The course has been created!";
                /*return ResponseEntity.status(HttpStatus.CREATED).body(message);*/
                return ResponseEntity.ok(Map.of(message, corsoDto));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
            }
        }
        String message = "The course has not been created!";
        return ResponseEntity.badRequest().body(Map.of(message, corsoDto));
    }

    public List<Corso> findCorsi() {
        return corsoRepository.findAll();
    }

    public Optional<Corso> update(Corso corso, Long id) {
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

    public Optional<Corso> delete(Long id) {
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

    public List<CorsoDto> findCorsiAndDocenti() {
        List<Corso> corsoList = corsoRepository.findAll();
       return corsoConverter.createCorsoAndDocenteList(corsoList);
    }

    public List<Corso> findByNome(String nome) {
        return corsoRepository.findByNome(nome);
    }

    public void exportCorsiListIntoExcelFile(HttpServletResponse response) throws IOException {
       response.setContentType("application/vnd.ms-excel");
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateTime = dateFormatter.format(new Date());

        String filename = UUID.randomUUID().toString();
        String headerKey = "Content-Disposition";
        String headerValue  = "attachment; filename="+ filename + "_" +currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<CorsoDto> listOfCorsiDto = findCorsiAndDocenti();
        ExcelGenerator generator = new ExcelGenerator(listOfCorsiDto);
        generator.generateExcelFile(response);
    }

    public ResponseEntity<?> importExcelFileCorsiListIntoDatabase(MultipartFile file, ExcelUtility excelUtility,String sheetName){
        String message;
        if(excelUtility.hasExcelFormat(file)){
            try{
                saveFile(file, excelUtility,sheetName);
                message = "The Excel file is uploaded: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    public void saveFile(MultipartFile file, ExcelUtility excelUtility, String sheetName) {
        try{
            List<CorsoDto> listOfCorsiDto = excelUtility.excelToCorsiDto(file.getInputStream(),sheetName);
            List<Corso> corsoList = corsoConverter.convertEntityToDto(listOfCorsiDto);
            corsoRepository.saveAll(corsoList);
        } catch (IOException e){
            throw new RuntimeException("Excel data is failed to store: "+ e.getMessage());
        }
    }

    public boolean findCorsoByNome(String nome) {
        return corsoRepository.existsByNome(nome);
    }
}
