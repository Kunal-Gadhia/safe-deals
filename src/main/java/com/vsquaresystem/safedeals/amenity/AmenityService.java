/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.amenity;

import com.vsquaresystem.safedeals.util.AttachmentUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
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
 * @author hp
 */
@Service
@Transactional

public class AmenityService {

    @Autowired
    private AmenityDAL amenityDAL;

    @Autowired
    private AttachmentUtils attachmentUtils;

    private void writeBook(Amenity amenity, Row row) {
        Cell cell = row.createCell(1);
        cell.setCellValue(amenity.getId());

        cell = row.createCell(2);
        cell.setCellValue(amenity.getName());

        cell = row.createCell(3);
        cell.setCellValue(amenity.getAmenityCodeId());

        cell = row.createCell(4);
        cell.setCellValue(amenity.getAmenityType().name());
    }

    public Boolean exportExcel() throws IOException {

        List<Amenity> rs = amenityDAL.findAllAmenities();
        System.out.println("result set excel sop" + rs);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Amenity Master");
        XSSFRow xssfrow = sheet.createRow((short) 0);
        xssfrow.createCell(1);
        xssfrow.createCell(2);
        xssfrow.createCell(3);
        xssfrow.getCell(1).setCellValue("Id");
        xssfrow.getCell(2).setCellValue("Amenity Name");
        xssfrow.getCell(3).setCellValue("Amenity Code Id");
        //        List<Book> listBook = excelWriter.getListBook();
        String fileName = "/AmenityMasterData.xls";
        String exportPath = attachmentUtils.getAmenityExportAttachmentRootDirectory() + fileName;

        System.out.println("exportPath" + exportPath);

        int rowCount = 0;

        for (Amenity aAmenity : rs) {
            Row row = sheet.createRow(++rowCount);
            writeBook(aAmenity, row);
        }

        try (FileOutputStream outputStream = new FileOutputStream(exportPath)) {
            workbook.write(outputStream);
        }

        return true;
    }

    public Vector read() throws IOException {
        File excelFile = attachmentUtils.getDirectoryByAttachmentType(AttachmentUtils.AttachmentType.LOCATION);
        File[] listofFiles = excelFile.listFiles();
        String fileName = excelFile + "/" + listofFiles[0].getName();

        Vector cellVectorHolder = new Vector();
        int type;
        try {
            FileInputStream myInput = new FileInputStream(fileName);

            XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator rowIter = mySheet.rowIterator();
            while (rowIter.hasNext()) {
                XSSFRow myRow = (XSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();

                List list = new ArrayList();
                while (cellIter.hasNext()) {
                    XSSFCell myCell = (XSSFCell) cellIter.next();
                    if (myCell != null) {
                        switch (myCell.getCellType()) {
                            case Cell.CELL_TYPE_BOOLEAN:

                                list.add(new DataFormatter().formatCellValue(myCell));
                                break;
                            case Cell.CELL_TYPE_NUMERIC:

                                list.add(new DataFormatter().formatCellValue(myCell));
                                break;
                            case Cell.CELL_TYPE_STRING:

                                list.add(new DataFormatter().formatCellValue(myCell));
                                break;
                            case Cell.CELL_TYPE_BLANK:
                                break;
                            case Cell.CELL_TYPE_ERROR:

                                list.add(new DataFormatter().formatCellValue(myCell));
                                break;

                            case Cell.CELL_TYPE_FORMULA:
                                break;
                        }
                    }
                }
                cellVectorHolder.addElement(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellVectorHolder;

    }
}
