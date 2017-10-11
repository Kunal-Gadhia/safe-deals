/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.marketprice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquaresystem.safedeals.util.AttachmentUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
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
public class MarketPriceService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MarketPriceDAL marketPriceDAL;

    @Autowired
    private AttachmentUtils attachmentUtils;

    @Transactional(readOnly = false)
    public Boolean insertAttachments(MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        File outputFile = attachmentUtils.storeAttachmentByAttachmentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.MARKET_PRICE
        );
        return outputFile.exists();
    }

    ;
    
    public Vector read() throws IOException {

        File excelFile = attachmentUtils.getDirectoryByAttachmentType(AttachmentUtils.AttachmentType.MARKET_PRICE);
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

                            case Cell.CELL_TYPE_FORMULA:
                                break;
                        }
                    }

                }
                logger.info("Line Line108 {}" + list);
                System.out.println("MAINlist" + list);
                cellVectorHolder.addElement(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cellVectorHolder;

    }

    public Boolean checkExistingData() throws IOException {
        Vector checkCellVectorHolder = read();
        int excelSize = checkCellVectorHolder.size() - 1;
        List<MarketPrice> rs = marketPriceDAL.getAll();
        JFrame parent = new JFrame();
        Boolean val;
        int listSize = rs.size();
        if (excelSize == listSize || excelSize > listSize) {
            val = true;
        } else {
            System.out.println("No selected");
            val = false;
        }

        return val;
    }

    public boolean saveExcelToDatabase() throws IOException {

        Vector dataHolder = read();
        marketPriceDAL.truncateAll();
        dataHolder.remove(0);
        System.out.println("data line 147" + dataHolder);
        MarketPrice marketPrice = new MarketPrice();
        String id = "";
        String cityId = "";
        String locationId = "";
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

        DataFormatter formatter = new DataFormatter();
        for (Iterator iterator = dataHolder.iterator(); iterator.hasNext();) {

            List list = (List) iterator.next();

            System.out.println("list for save" + list);
            cityId = list.get(1).toString();
            locationId = list.get(2).toString();
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

            List<Integer> numberList = new ArrayList<Integer>();

            try {

                marketPrice.setCityId(Integer.parseInt(cityId));
                marketPrice.setLocationId(Integer.parseInt(locationId));
                marketPrice.setYear(Integer.parseInt(year));
                marketPrice.setMonth(Integer.parseInt(month));
                marketPrice.setMpAgriLandLowest(Double.parseDouble(mpAgriLandLowest));
                marketPrice.setMpAgriLandHighest(Double.parseDouble(mpAgriLandHighest));

                marketPrice.setMpPlotLowest(Double.parseDouble(mpPlotLowest));
                marketPrice.setMpPlotHighest(Double.parseDouble(mpPlotHighest));

                marketPrice.setMpResidentialLowest(Double.parseDouble(mpResidentialLowest));
                marketPrice.setMpResidentialHighest(Double.parseDouble(mpResidentialHighest));

                marketPrice.setMpCommercialLowest(Double.parseDouble(mpCommercialLowest));
                marketPrice.setMpCommercialHighest(Double.parseDouble(mpCommercialHighest));

                marketPriceDAL.insert(marketPrice);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File excelFile = attachmentUtils.getDirectoryByAttachmentType(AttachmentUtils.AttachmentType.MARKET_PRICE);
        FileUtils.cleanDirectory(excelFile);
        return true;

    }

}
