/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.city;

import com.vsquaresystem.safedeals.util.AttachmentUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ruchita
 */
@Service
@Transactional
public class CityService {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CityDAL cityDAL;
    
    @Autowired
    private AttachmentUtils attachmentUtils;

    private void writeBook(City alocation, Row row) {
        Cell cell = row.createCell(1);
        cell.setCellValue(alocation.getId());

        cell = row.createCell(2);
        cell.setCellValue(alocation.getName());

        cell = row.createCell(3);
        cell.setCellValue(alocation.getCountryId());
        
        cell = row.createCell(4);
        cell.setCellValue(alocation.getStateId());
        
        cell = row.createCell(5);
        cell.setCellValue(alocation.getLatitude());
        
        cell = row.createCell(6);
        cell.setCellValue(alocation.getLongitude());
    }

    

    public Boolean exportExcel() throws IOException {
        logger.info("getExportExcel method is working");
        List<City> rs = cityDAL.findAllCities();
        System.out.println("result set excel sop" + rs);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("City Master");
        XSSFRow xssfrow = sheet.createRow((short) 0);
        xssfrow.createCell(1);
        xssfrow.createCell(2);
        xssfrow.createCell(3);
        xssfrow.createCell(4);
        xssfrow.createCell(5);
        xssfrow.createCell(6);
        xssfrow.getCell(1).setCellValue("City Id");
        xssfrow.getCell(2).setCellValue("Name");
        xssfrow.getCell(3).setCellValue("Country Id");
        xssfrow.getCell(4).setCellValue("State Id");
        xssfrow.getCell(5).setCellValue("Latitude Id");
        xssfrow.getCell(6).setCellValue("Longitude Id");
        String fileName = "/CityMasterData.xls";
        String exportPath = attachmentUtils.getCityExportAttachmentRootDirectory() + fileName;
        System.out.println("exportPath" + exportPath);
        int rowCount = 0;

        for (City aLocation : rs) {
            Row row = sheet.createRow(++rowCount);
            writeBook(aLocation, row);
        }

        try (FileOutputStream outputStream = new FileOutputStream(exportPath)) {
            workbook.write(outputStream);
        }

        return true;
    }
    
}
