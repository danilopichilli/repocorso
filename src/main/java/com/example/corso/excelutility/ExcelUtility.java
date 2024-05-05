package com.example.corso.excelutility;

import com.example.corso.dto.CorsoDto;
import com.example.corso.service.CorsoService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelUtility {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Autowired
    private CorsoService corsoService;

    //This hasExcelFormat(MultipartFile file) method checks whether the file format is Excel or not.
    public boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public Sheet getSheetName(Workbook workbook,String sheetName) {
        List<String> sheetNames = new ArrayList<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            sheetNames.add(workbook.getSheetAt(i).getSheetName());
        }
        /*Sheet sheet = workbook.getSheet(sheetNames.get(sheetNames.size() - 1));*/
        for(String s : sheetNames){
            if(s.equalsIgnoreCase(sheetName)){
                return workbook.getSheet(s);
            }
        }
        return null;
    }

    //This excelToCorsiDto(InputStream inputStream) method reads the Excel Sheet data returns list of courses.
    public List<CorsoDto> excelToCorsiDto(InputStream inputStream,String sheetName) {
        try{
            //Creating Workbook from the InputStream
            Workbook workbook = new XSSFWorkbook(inputStream);
            //Create a Sheet from the workbook.getSheet() method
            Sheet sheet = getSheetName(workbook,sheetName);
            if (sheet == null) {
                workbook.close();
                throw new IllegalArgumentException("Sheet '" + sheet.getSheetName() + "' does not exist in the provided workbook");
            }
            //Get an Iterator with a Row from the sheet.iterator() method
            Iterator<Row> rows = sheet.iterator();
            List<CorsoDto> corsoDtoList = new ArrayList<>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                //skip header
                if(rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();

                // We assume that the course name is in the first cell of the row
                Cell firstCell = cellsInRow.next();
                String nomeCorso = firstCell.getStringCellValue();
                if(corsoService.findCorsoByNome(nomeCorso)) {
                    continue;  // If the course already exists, skip to the next cycle
                }

                CorsoDto corsoDto = new CorsoDto();
                corsoDto.setNomeCorso(nomeCorso);
                int cellIdx = 1; // Start at 1 because cell 0 is already read

                while (cellsInRow.hasNext()) {
                    Cell cell = cellsInRow.next();
                    switch (cellIdx) {
                        case 1: // Durata
                            corsoDto.setDurata((int) cell.getNumericCellValue());
                            break;
                        case 2: // Nome Docente
                            corsoDto.setNomeDocente(cell.getStringCellValue());
                            break;
                        case 3: // Cognome Docente
                            corsoDto.setCognomeDocente(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                corsoDtoList.add(corsoDto);
            }
            workbook.close();
            return corsoDtoList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse excel file" + e.getMessage());
        }
    }

}
