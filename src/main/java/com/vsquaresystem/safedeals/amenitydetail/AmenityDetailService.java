package com.vsquaresystem.safedeals.amenitydetail;

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
public class AmenityDetailService {

    @Autowired
    private AmenityDetailDAL amenityDetailDAL;

    @Autowired
    private AttachmentUtils attachmentUtils;

    @Transactional(readOnly = false)
    public Boolean insertAttachments(MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        logger.info("attachmentMultipartFile in service Line31{}", attachmentMultipartFile);
        System.out.println("attachmentMultipartFile" + attachmentMultipartFile);
        File outputFile = attachmentUtils.storeAttachmentByAttachmentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.AMENITY_DETAIL
        );
        return outputFile.exists();
    }

    ;
    
    private void writeBook(AmenityDetail aAmenityDetail, Row row) {
        Cell cell = row.createCell(0);
        cell.setCellValue(aAmenityDetail.getId());

        cell = row.createCell(1);
        cell.setCellValue(aAmenityDetail.getName());

        cell = row.createCell(2);
        cell.setCellValue(aAmenityDetail.getAddress());

        cell = row.createCell(3);
        cell.setCellValue(aAmenityDetail.getPhoneNumber());

        cell = row.createCell(4);
        cell.setCellValue(aAmenityDetail.getMobileNumber());

        cell = row.createCell(5);
        cell.setCellValue(aAmenityDetail.getLocationId());

        cell = row.createCell(6);
        cell.setCellValue(aAmenityDetail.getAmenityId());

        cell = row.createCell(7);
        cell.setCellValue(aAmenityDetail.getLatitude());

        cell = row.createCell(8);
        cell.setCellValue(aAmenityDetail.getLongitude());

        cell = row.createCell(9);
        cell.setCellValue(aAmenityDetail.getCityId());
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Boolean exportExcel() throws IOException {

        List<AmenityDetail> rs = amenityDetailDAL.findAllAmenityDetails();
        System.out.println("result amenity details" + rs);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Amenity Detail Master");
        XSSFRow xssfrow = sheet.createRow((short) 0);
        xssfrow.createCell(0);
        xssfrow.createCell(1);
        xssfrow.createCell(2);
        xssfrow.createCell(3);
        xssfrow.createCell(4);
        xssfrow.createCell(5);
        xssfrow.createCell(6);
        xssfrow.createCell(7);
        xssfrow.createCell(8);
        xssfrow.createCell(9);

        xssfrow.getCell(0).setCellValue("Amenity Detail Id");
        xssfrow.getCell(1).setCellValue("Amenity Detail Name");
        xssfrow.getCell(2).setCellValue("Amenity Detail Address");
        xssfrow.getCell(3).setCellValue("Amenity Detail Phone Number");
        xssfrow.getCell(4).setCellValue("Amenity Detail Mobile Number");
        xssfrow.getCell(5).setCellValue("Amenity Detail Location Id");
        xssfrow.getCell(6).setCellValue("Amenity Detail Amenity Id");
        xssfrow.getCell(7).setCellValue("Amenity Detail Latitude");
        xssfrow.getCell(8).setCellValue("Amenity Detail Longitude");
        xssfrow.getCell(9).setCellValue("Amenity Detail City Id");
        String fileName = "/AmenityDetailMasterData.xls";
        String exportPath = attachmentUtils.getAmenityDetailExportAttachmentRootDirectory() + fileName;
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

    public Vector read() throws IOException {
//        logger.info("are we in the vector read?");
        File excelFile = attachmentUtils.getDirectoryByAttachmentType(AttachmentUtils.AttachmentType.AMENITY_DETAIL);
        logger.info("Excel FIle Kunal :" + excelFile);
        logger.info("Excel File List Kunal :" + excelFile.listFiles());
        File[] listofFiles = excelFile.listFiles();
        String fileName = excelFile + "/" + listofFiles[0].getName();
        logger.info("fileeeeeeeeeee" + fileName);
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
//                                System.out.println(new DataFormatter().formatCellValue(myCell));
                                list.add(new DataFormatter().formatCellValue(myCell));
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
//                                System.out.println(new DataFormatter().formatCellValue(myCell));
                                list.add(new DataFormatter().formatCellValue(myCell));
                                break;
                            case Cell.CELL_TYPE_STRING:
//                                System.out.println(new DataFormatter().formatCellValue(myCell));
                                list.add(new DataFormatter().formatCellValue(myCell));
                                break;
                            case Cell.CELL_TYPE_BLANK:
                                break;
                            case Cell.CELL_TYPE_ERROR:
//                                System.out.println(new DataFormatter().formatCellValue(myCell));
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

    public boolean saveExcelToDatabase() throws IOException {
        Vector dataHolder = read();
        dataHolder.remove(0);
        AmenityDetail amenityDetail = new AmenityDetail();
        String id = "";
        String name = "";
        String address = "";
        String phoneNumber = "";
        String mobileNumber = "";
        String locationId = "";
        String amenityId = "";
        String latitude = "";
        String longitude = "";
        String cityId = "";
        logger.info("Line SOP " + dataHolder);
        System.out.println("line1785SAVE sop" + dataHolder);
        DataFormatter formatter = new DataFormatter();
        for (Iterator iterator = dataHolder.iterator(); iterator.hasNext();) {
            List list = (List) iterator.next();
//            logger.info("list log 182", list.size());
//             System.out.println("list sop 182 GET" + list);
            name = list.get(1).toString();
            address = list.get(2).toString();
            phoneNumber = list.get(3).toString();
            mobileNumber = list.get(4).toString();
            locationId = list.get(5).toString();
            amenityId = list.get(6).toString();
            latitude = list.get(7).toString();
            longitude = list.get(8).toString();
            cityId = list.get(9).toString();
//            List<String> strList = new ArrayList<String>(Arrays.asList(locationCategories.split(",")));
//            List<Integer> numberList = new ArrayList<Integer>();
//            for (String number : strList) {
//                numberList.add(Integer.parseInt(number));
//            }
            try {
                amenityDetail.setName(name);
                amenityDetail.setAddress(address);
                amenityDetail.setPhoneNumber(phoneNumber);
                amenityDetail.setMobileNumber(mobileNumber);
                amenityDetail.setLocationId(Integer.parseInt(locationId));
                amenityDetail.setAmenityId(Integer.parseInt(amenityId));
                amenityDetail.setLatitude(Double.parseDouble(latitude));
                amenityDetail.setLongitude(Double.parseDouble(longitude));

                System.out.println("Amenity Detail line167 CHECKcheck" + amenityDetail);
                amenityDetailDAL.insert(amenityDetail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File excelFile = attachmentUtils.getDirectoryByAttachmentType(AttachmentUtils.AttachmentType.AMENITY_DETAIL);
        FileUtils.cleanDirectory(excelFile);
        return true;

    }
}
