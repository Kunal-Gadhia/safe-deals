package com.vsquaresystem.safedeals.amenitydetail;

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

@Service
@Transactional
public class AmenityDetailService {

    @Autowired
    private AmenityDetailDAL amenityDetailDAL;

    @Autowired
    private AttachmentUtils attachmentUtils;
    
     private void writeBook(AmenityDetail aAmenityDetail, Row row) {
        Cell cell = row.createCell(1);
        cell.setCellValue(aAmenityDetail.getId());

        cell = row.createCell(2);
        cell.setCellValue(aAmenityDetail.getName());

        cell = row.createCell(3);
        cell.setCellValue(aAmenityDetail.getAddress());
        
        cell = row.createCell(4);
        cell.setCellValue(aAmenityDetail.getLatitude());
        
        cell = row.createCell(5);
        cell.setCellValue(aAmenityDetail.getLongitude());
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Boolean exportExcel() throws IOException {

        List<AmenityDetail> rs = amenityDetailDAL.findAllAmenityDetails();
        System.out.println("result amenity details" + rs);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Amenity Detail Master");
        XSSFRow xssfrow = sheet.createRow((short) 0);
        xssfrow.createCell(1);
        xssfrow.createCell(2);
        xssfrow.createCell(3);
        xssfrow.createCell(4);
        xssfrow.createCell(5);
        xssfrow.getCell(1).setCellValue("Amenity Detail Id");
        xssfrow.getCell(2).setCellValue("Amenity Detail Name");
        xssfrow.getCell(3).setCellValue("Amenity Detail Address");
        xssfrow.getCell(4).setCellValue("Amenity Detail Latitude");
        xssfrow.getCell(5).setCellValue("Amenity Detail Longitude");
        String fileName = "/AmenityDetailMasterData.xls";
        String exportPath = attachmentUtils.getAmenityDetailExportAttachmentRootDirectory()+ fileName;
//         System.out.println("exportPath" + exportPath);
//        String excelFilePath = "/home/hp-pc/Downloads/LocationMasterData.xls";

        //        excelWriter.writeExcel(listBook, excelFilePath);
        //        Workbook workbook = new HSSFWorkbook();
        //    Sheet sheet = workbook.createSheet();
        int rowCount = 0;

        for (AmenityDetail aAmenityDetail : rs) {
            Row row = sheet.createRow(++rowCount);
            writeBook(aAmenityDetail, row);
        }

        try (FileOutputStream outputStream = new FileOutputStream(exportPath)) {
            workbook.write(outputStream);
        }

        return true;
    }
}
