/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.rawmarketprice;

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
import javax.swing.JFrame;
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
public class RawMarketPriceService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RawMarketPriceDAL rawMarketPriceDAL;

    @Autowired
    private AttachmentUtils attachmentUtils;

    @Transactional(readOnly = false)
    public Boolean insertAttachments(MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        logger.info("attachmentMultipartFile in service Line31{}", attachmentMultipartFile);
        System.out.println("attachmentMultipartFile" + attachmentMultipartFile);
        File outputFile = attachmentUtils.storeAttachmentByAttachmentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.RAW_MARKET_PRICE
        );
        return outputFile.exists();
    }

    ;
    
     public class Result {

        String response;
    }

    public Vector read() throws IOException {
//        logger.info("are we in the vector read?");
        File excelFile = attachmentUtils.getDirectoryByAttachmentType(AttachmentUtils.AttachmentType.RAW_MARKET_PRICE);
        File[] listofFiles = excelFile.listFiles();
        String fileName = excelFile + "/" + listofFiles[0].getName();
//        logger.info("fileeeeeeeeeee" + fileName);
        Vector cellVectorHolder = new Vector();
        int type;
        try {
            FileInputStream myInput = new FileInputStream(fileName);
            //POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator rowIter = mySheet.rowIterator();
            while (rowIter.hasNext()) {
                XSSFRow myRow = (XSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                //Vector cellStoreVector=new Vector();              
                List list = new ArrayList();
                while (cellIter.hasNext()) {
                    XSSFCell myCell = (XSSFCell) cellIter.next();
                    if (myCell != null) {
                        switch (myCell.getCellType()) {
                            case Cell.CELL_TYPE_BOOLEAN:
                                System.out.println(new DataFormatter().formatCellValue(myCell));
                                list.add(new DataFormatter().formatCellValue(myCell));
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                System.out.println(new DataFormatter().formatCellValue(myCell));
                                list.add(new DataFormatter().formatCellValue(myCell));
                                break;
                            case Cell.CELL_TYPE_STRING:
                                System.out.println(new DataFormatter().formatCellValue(myCell));
                                list.add(new DataFormatter().formatCellValue(myCell));
                                break;
                            case Cell.CELL_TYPE_BLANK:
                                break;
                            case Cell.CELL_TYPE_ERROR:
                                System.out.println(new DataFormatter().formatCellValue(myCell));
                                list.add(new DataFormatter().formatCellValue(myCell));
                                break;

                            // CELL_TYPE_FORMULA will never occur
                            case Cell.CELL_TYPE_FORMULA:
                                break;
                        }
                    }

//                    type = myCell.getCellType();
//                    list.add(myCell);
                }
//                logger.info("Line Line108 {}" + list);
                cellVectorHolder.addElement(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        logger.info("cellVectorHolder Line108 {}" + cellVectorHolder);
        return cellVectorHolder;

    }

    public Boolean checkExistingData() throws IOException {
//        logger.info("check ke andar hai bhai??");
        Boolean a = true;
        Result result = new Result();
        result.response = "true";
        Vector checkCellVectorHolder = read();
//        logger.info("checkCellVectorHolder line116 :{}", checkCellVectorHolder);
//        logger.info("read in check line117 :{}", read());
        int excelSize = checkCellVectorHolder.size() - 1;
//        System.out.println("excelSize" + excelSize);
        List<RawMarketPrice> rs = rawMarketPriceDAL.getAll();
        JFrame parent = new JFrame();

//        System.out.println("rs" + rs);
        int listSize = rs.size();
//        logger.info("rsss:::::", listSize);
//        System.out.println("rsss:::::" + listSize);
        if (excelSize == listSize || excelSize > listSize) {

        } else {
            System.out.println("No selected");
//                return false;
        }
        return true;
    }

    public boolean saveExcelToDatabase() throws IOException {

        Vector dataHolder = read();
        rawMarketPriceDAL.truncateAll();
        dataHolder.remove(0);
//         logger.info("line1235SAVE::", dataHolder);
        RawMarketPrice rawMarketPrice = new RawMarketPrice();
        String id = "";
        String cityId = "";
        String locationName = "";
        String year = "";
        String month = "";
        String mpAgriLandLowest = "";
        String mpAgriLandHighest = "";
        String mpPlotLowest = "";
        String mpPlotHighest = "";
        String mpResidentialLowest = "";
        String mpResidentialHighest = "";
        String mpCommercialLowest = "";
        String mpCommercialHighest = "";
        String sdZoneId = "";
        String locationTypeId = "";
        String locationCategories = "";
        String description = "";
        String majorApproachRoad = "";
        String sourceOfWater = "";
        String publicTransport = "";
        String advantage = "";
        String disadvantage = "";
        String population = "";
        String migrationRate = "";
        String isCommercialCenter = "";
        System.out.println(dataHolder);
        DataFormatter formatter = new DataFormatter();
        for (Iterator iterator = dataHolder.iterator(); iterator.hasNext();) {
            List list = (List) iterator.next();
            logger.info("list", list);
            cityId = list.get(1).toString();
            locationName = list.get(2).toString();
            year = list.get(3).toString();
            month = list.get(4).toString();
            mpAgriLandLowest = list.get(5).toString();
            mpAgriLandHighest = list.get(6).toString();
            mpPlotLowest = list.get(7).toString();
            mpPlotHighest = list.get(8).toString();
            mpResidentialLowest = list.get(9).toString();
            mpResidentialHighest = list.get(10).toString();
            mpCommercialLowest = list.get(11).toString();
            mpCommercialHighest = list.get(12).toString();
            sdZoneId = list.get(13).toString();
            locationTypeId = list.get(14).toString();
            locationCategories = list.get(15).toString();
            description = list.get(16).toString();
            majorApproachRoad = list.get(17).toString();
            sourceOfWater = list.get(18).toString();
            publicTransport = list.get(19).toString();
            advantage = list.get(20).toString();
            disadvantage = list.get(21).toString();
            population = list.get(22).toString();
            migrationRate = list.get(23).toString();
            isCommercialCenter = list.get(24).toString();
            List<String> strList = new ArrayList<String>(Arrays.asList(locationCategories.split(",")));
            List<Integer> numberList = new ArrayList<Integer>();
            for (String number : strList) {
                numberList.add(Integer.parseInt(number));
            }

            try {
                rawMarketPrice.setCityId(Integer.parseInt(cityId));
                rawMarketPrice.setLocationName(locationName);
                rawMarketPrice.setYear(Integer.parseInt(year));
                rawMarketPrice.setMonth(Integer.parseInt(month));
                rawMarketPrice.setMpAgriLandLowest(Double.parseDouble(mpAgriLandLowest));
                rawMarketPrice.setMpAgriLandHighest(Double.parseDouble(mpAgriLandHighest));
                rawMarketPrice.setMpPlotLowest(Double.parseDouble(mpPlotLowest));
                rawMarketPrice.setMpPlotHighest(Double.parseDouble(mpPlotHighest));
                rawMarketPrice.setMpResidentialLowest(Double.parseDouble(mpResidentialLowest));
                rawMarketPrice.setMpResidentialHighest(Double.parseDouble(mpResidentialHighest));
                rawMarketPrice.setMpCommercialLowest(Double.parseDouble(mpCommercialLowest));
                rawMarketPrice.setMpCommercialHighest(Double.parseDouble(mpCommercialHighest));
                rawMarketPrice.setSafedealZoneId(Integer.parseInt(sdZoneId));
                rawMarketPrice.setLocationTypeId(Integer.parseInt(locationTypeId));
                rawMarketPrice.setLocationCategories(numberList);
                rawMarketPrice.setDescription(description);
                rawMarketPrice.setMajorApproachRoad(majorApproachRoad);
                rawMarketPrice.setSourceOfWater(sourceOfWater);
                rawMarketPrice.setPublicTransport(publicTransport);
                rawMarketPrice.setAdvantage(advantage);
                rawMarketPrice.setDisadvantage(disadvantage);
                rawMarketPrice.setPopulation(Integer.parseInt(population));
                rawMarketPrice.setMigrationRate(Integer.parseInt(migrationRate));
                rawMarketPrice.setIsCommercialCenter((Boolean.parseBoolean(isCommercialCenter)));
//                System.out.println("location line167check" + location);
//                System.out.println(numberList);
                rawMarketPriceDAL.insert(rawMarketPrice);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File excelFile = attachmentUtils.getDirectoryByAttachmentType(AttachmentUtils.AttachmentType.RAW_MARKET_PRICE);
        FileUtils.cleanDirectory(excelFile);
        return true;

    }

    private void writeBook(RawMarketPrice rawMarketPrice, Row row) {
        Cell cell = row.createCell(1);
        cell.setCellValue(rawMarketPrice.getId());

        cell = row.createCell(2);
        cell.setCellValue(rawMarketPrice.getCityId());

        cell = row.createCell(3);
        cell.setCellValue(rawMarketPrice.getLocationName());

        cell = row.createCell(4);
        cell.setCellValue(rawMarketPrice.getYear());

        cell = row.createCell(5);
        cell.setCellValue(rawMarketPrice.getMonth());

        cell = row.createCell(6);
        cell.setCellValue(rawMarketPrice.getMpAgriLandLowest());

        cell = row.createCell(7);
        cell.setCellValue(rawMarketPrice.getMpAgriLandHighest());

        cell = row.createCell(8);
        cell.setCellValue(rawMarketPrice.getMpPlotLowest());

        cell = row.createCell(9);
        cell.setCellValue(rawMarketPrice.getMpPlotHighest());

        cell = row.createCell(10);
        cell.setCellValue(rawMarketPrice.getMpResidentialLowest());

        cell = row.createCell(11);
        cell.setCellValue(rawMarketPrice.getMpResidentialHighest());

        cell = row.createCell(12);
        cell.setCellValue(rawMarketPrice.getMpCommercialLowest());

        cell = row.createCell(13);
        cell.setCellValue(rawMarketPrice.getMpCommercialHighest());

        cell = row.createCell(14);
        cell.setCellValue(rawMarketPrice.getSafedealZoneId());

        cell = row.createCell(15);
        cell.setCellValue(rawMarketPrice.getLocationTypeId());

        cell = row.createCell(16);
        cell.setCellValue("1,2,3");

        cell = row.createCell(17);
        cell.setCellValue(rawMarketPrice.getDescription());

        cell = row.createCell(18);
        cell.setCellValue(rawMarketPrice.getMajorApproachRoad());

        cell = row.createCell(19);
        cell.setCellValue(rawMarketPrice.getSourceOfWater());

        cell = row.createCell(20);
        cell.setCellValue(rawMarketPrice.getPublicTransport());

        cell = row.createCell(21);
        cell.setCellValue(rawMarketPrice.getAdvantage());

        cell = row.createCell(22);
        cell.setCellValue(rawMarketPrice.getDisadvantage());

        cell = row.createCell(23);
        cell.setCellValue(rawMarketPrice.getPopulation());

        cell = row.createCell(24);
        cell.setCellValue(rawMarketPrice.getMigrationRate());

        cell = row.createCell(25);
        cell.setCellValue(rawMarketPrice.getIsCommercialCenter());

    }

    public Boolean exportExcel() throws IOException {
        logger.info("getExportExcel method is working for raw market price");
        List<RawMarketPrice> rmp = rawMarketPriceDAL.findAllRawMarketPrice();
//        logger.info("result set excel sop" + rmp);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Raw Market Price Master");
        XSSFRow xssfrow = sheet.createRow((short) 0);
        xssfrow.createCell(1);
        xssfrow.createCell(2);
        xssfrow.createCell(3);
        xssfrow.createCell(4);
        xssfrow.createCell(5);
        xssfrow.createCell(6);
        xssfrow.createCell(7);
        xssfrow.createCell(8);
        xssfrow.createCell(9);
        xssfrow.createCell(10);
        xssfrow.createCell(11);
        xssfrow.createCell(12);
        xssfrow.createCell(13);
        xssfrow.createCell(14);
        xssfrow.createCell(15);
        xssfrow.createCell(16);
        xssfrow.createCell(17);
        xssfrow.createCell(18);
        xssfrow.createCell(19);
        xssfrow.createCell(20);
        xssfrow.createCell(21);
        xssfrow.createCell(22);
        xssfrow.createCell(23);
        xssfrow.createCell(24);
        xssfrow.createCell(25);
        xssfrow.getCell(1).setCellValue("Id");
        xssfrow.getCell(2).setCellValue("City Name");
        xssfrow.getCell(3).setCellValue("Location Name");
        xssfrow.getCell(4).setCellValue("Year");
        xssfrow.getCell(5).setCellValue("Month");
        xssfrow.getCell(6).setCellValue("MP Agri Land Lowest");
        xssfrow.getCell(7).setCellValue("MP Agri Land Highest");
        xssfrow.getCell(8).setCellValue("MP Plot Lowest");
        xssfrow.getCell(9).setCellValue("MP Plot Highest");
        xssfrow.getCell(10).setCellValue("MP Residential Lowest");
        xssfrow.getCell(11).setCellValue("MP Residential Highest");
        xssfrow.getCell(12).setCellValue("MP Commercial Lowest");
        xssfrow.getCell(13).setCellValue("MP Commercial Highest");
        xssfrow.getCell(14).setCellValue("Safedeal Zone Id");
        xssfrow.getCell(15).setCellValue("Location Type Id");
        xssfrow.getCell(16).setCellValue("Location Categories");
        xssfrow.getCell(17).setCellValue("Description");
        xssfrow.getCell(18).setCellValue("Major Approach Road");
        xssfrow.getCell(19).setCellValue("Source of Water");
        xssfrow.getCell(20).setCellValue("Public Transport");
        xssfrow.getCell(21).setCellValue("Advantage");
        xssfrow.getCell(22).setCellValue("Disadvantage");
        xssfrow.getCell(23).setCellValue("Population");
        xssfrow.getCell(24).setCellValue("Migration Rate");
        xssfrow.getCell(25).setCellValue("Commercial Center");

        String fileName = "/RawMarketPriceMasterData.xls";
        String exportPath = attachmentUtils.getRawMarketPriceExportAttachmentRootDirectory() + fileName;

        logger.info("exportPath" + exportPath);

        int rowCount = 0;

        for (RawMarketPrice aRawMarketPrice : rmp) {
            Row row = sheet.createRow(++rowCount);
            writeBook(aRawMarketPrice, row);
        }

        try (FileOutputStream outputStream = new FileOutputStream(exportPath)) {
            workbook.write(outputStream);
        }

        return true;
    }
}
