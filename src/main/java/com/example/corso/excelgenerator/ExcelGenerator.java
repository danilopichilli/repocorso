package com.example.corso.excelgenerator;

import com.example.corso.dto.CorsoDTO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class ExcelGenerator {

    private List<CorsoDTO> corsoDTOList;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(List<CorsoDTO> corsoList) {
        this.corsoDTOList = corsoList;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader(){
        sheet = workbook.createSheet("Corsi");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row,0,"Nome",style);
        createCell(row,1,"Durata",style);
        createCell(row,2,"Nome Docente",style);
        createCell(row,3,"Cognome Docente",style);
    }

    private void createCell(Row row,int columnCount,Object valueOfCell,CellStyle style){
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if(valueOfCell instanceof Integer){
            cell.setCellValue((Integer)valueOfCell);
        }else if(valueOfCell instanceof Long){
            cell.setCellValue((Long)valueOfCell);
        } else if(valueOfCell instanceof String){
            cell.setCellValue((String)valueOfCell);
        } else {
            cell.setCellValue((Boolean)valueOfCell);
        }
        cell.setCellStyle(style);
    }

    private void write(){
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for(CorsoDTO corsoDTO : corsoDTOList){

            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row,columnCount++,corsoDTO.getNomeCorso(),style);
            createCell(row,columnCount++,corsoDTO.getDurata(),style);
            createCell(row,columnCount++,corsoDTO.getNomeDocente(),style);
            createCell(row,columnCount++,corsoDTO.getCognomeDocente(),style);
        }
    }

    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
