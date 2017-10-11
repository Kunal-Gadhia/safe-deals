/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquaresystem.safedeals.util.AttachmentUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.apache.commons.io.FileUtils;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class LocationService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LocationDAL locationDAL;

    @Autowired
    private AttachmentUtils attachmentUtils;

    @Transactional(readOnly = false)
    public Boolean insertAttachments(MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        logger.info("attachmentMultipartFile in service Line31{}", attachmentMultipartFile);
        System.out.println("attachmentMultipartFile" + attachmentMultipartFile);
        File outputFile = attachmentUtils.storeAttachmentByAttachmentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.LOCATION
        );
        return outputFile.exists();
    }

    ;

    private void writeBook(Location alocation, Row row) {
        Cell cell = row.createCell(1);
        cell.setCellValue(alocation.getId());

        cell = row.createCell(2);
        cell.setCellValue(alocation.getName());

        cell = row.createCell(3);
        cell.setCellValue(alocation.getCityId());
    }

    public Boolean exportExcel() throws IOException {
        logger.info("getExportExcel method is working");
        List<Location> rs = locationDAL.findAllLocations();
        System.out.println("result set excel sop" + rs);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Location Master");
        XSSFRow xssfrow = sheet.createRow((short) 0);
        xssfrow.createCell(1);
        xssfrow.createCell(2);
        xssfrow.createCell(3);
        xssfrow.getCell(1).setCellValue("Location Id");
        xssfrow.getCell(2).setCellValue("Location Name");
        xssfrow.getCell(3).setCellValue("City Id");
        String fileName = "/LocationMasterData.xls";
        String exportPath = attachmentUtils.getLocationExportAttachmentRootDirectory() + fileName;
        System.out.println("exportPath" + exportPath);
        int rowCount = 0;
        for (Location aLocation : rs) {
            Row row = sheet.createRow(++rowCount);
            writeBook(aLocation, row);
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

    public boolean saveExcelToDatabase() throws IOException {
        Vector dataHolder = read();
        dataHolder.remove(0);
        Location location = new Location();
        String id = "";
        String name = "";
        String description = "";
        String cityId = "";
        String locationTypeId = "";
        String locationCategories = "";
        String safedealZoneId = "";
        String majorApproachRoad = "";
        String advantage = "";
        String disadvantage = "";
        String population = "";
        String latitude = "";
        String longitude = "";
        String sourceOfWater = "";
        String publicTransport = "";
        String migrationRate = "";
        String distanceCenterCity = "";
        String isCommercialCenter = "";
        String distanceCommercialCenter = "";
        String imageUrl = "";

        System.out.println("line1785SAVE sop" + dataHolder);
        DataFormatter formatter = new DataFormatter();
        for (Iterator iterator = dataHolder.iterator(); iterator.hasNext();) {
            List list = (List) iterator.next();
            name = list.get(0).toString();
            description = list.get(1).toString();
            cityId = list.get(2).toString();
            locationTypeId = list.get(3).toString();
            locationCategories = list.get(4).toString();
            safedealZoneId = list.get(5).toString();
            majorApproachRoad = list.get(6).toString();
            advantage = list.get(7).toString();
            disadvantage = list.get(8).toString();
            population = list.get(9).toString();
            latitude = list.get(10).toString();
            longitude = list.get(11).toString();
            sourceOfWater = list.get(12).toString();
            publicTransport = list.get(13).toString();
            migrationRate = list.get(14).toString();
            distanceCenterCity = list.get(15).toString();
            isCommercialCenter = list.get(16).toString();
            distanceCommercialCenter = list.get(17).toString();
            imageUrl = list.get(18).toString();
            List<String> strList = new ArrayList<String>(Arrays.asList(locationCategories.split(",")));
            List<Integer> numberList = new ArrayList<Integer>();
            for (String number : strList) {
                numberList.add(Integer.parseInt(number));
            }
            try {
                location.setName(name);
                location.setDescription(description);
                location.setCityId(Integer.parseInt(cityId));
                location.setLocationTypeId(Integer.parseInt(locationTypeId));
                location.setLocationCategories(numberList);
                location.setSafedealZoneId(Integer.parseInt(safedealZoneId));
                location.setMajorApproachRoad(majorApproachRoad);
                location.setAdvantage(advantage);
                location.setDisadvantage(disadvantage);
                location.setPopulation(Integer.parseInt(population));
                location.setLatitude(Double.parseDouble(latitude));
                location.setLongitude(Double.parseDouble(longitude));
                location.setMigrationRatePerAnnum(MigrationRatePerAnnum.valueOf(migrationRate));
                location.setDistanceFromCentreOfCity(Double.parseDouble(distanceCenterCity));
                location.setDistanceFromCommercialCenter(Double.parseDouble(distanceCommercialCenter));
                location.setIsCommercialCenter(Boolean.valueOf(isCommercialCenter));
                location.setImageUrl(imageUrl);

                System.out.println("location line167 CHECKcheck" + location);
                System.out.println("numberList" + numberList);
                locationDAL.insert(location);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File excelFile = attachmentUtils.getDirectoryByAttachmentType(AttachmentUtils.AttachmentType.LOCATION);
        FileUtils.cleanDirectory(excelFile);
        return true;

    }
}
