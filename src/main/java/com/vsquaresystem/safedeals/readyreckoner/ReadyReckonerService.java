/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.readyreckoner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vsquaresystem.safedeals.util.AttachmentUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

/**
 *
 * @author ruchita
 */
@Service
@Transactional
public class ReadyReckonerService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReadyreckonerDAL readyReckonerDAL;

    @Autowired
    private AttachmentUtils attachmentUtils;

    @Transactional(readOnly = false)
    public Boolean insertAttachments(MultipartFile attachmentMultipartFile) throws JsonProcessingException, IOException {
        Readyreckoner rr = new Readyreckoner();
//        logger.info("attachmentMultipartFile in service Line31{}", attachmentMultipartFile);
//        System.out.println("attachmentMultipartFile" + attachmentMultipartFile);
        File outputFile = attachmentUtils.storeAttachmentByAttachmentType(
                attachmentMultipartFile.getOriginalFilename(),
                attachmentMultipartFile.getInputStream(),
                AttachmentUtils.AttachmentType.READY_RECKONER
        );
        return outputFile.exists();
    }

    public Vector read() throws IOException {
//        logger.info("are we in the vector read?");
        File excelFile = attachmentUtils.getDirectoryByAttachmentType(AttachmentUtils.AttachmentType.READY_RECKONER);
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

    private boolean saveToDatabase(Vector dataHolder) throws IOException {
//        logger.info("save to database");
//        System.out.println("save to database");
//        System.out.println("save to database" + dataHolder);
//        logger.info("save to database", dataHolder);
        readyReckonerDAL.truncateAll();
        dataHolder.remove(0);
//         logger.info("line1235SAVE::", dataHolder);
        Readyreckoner readyReckoner = new Readyreckoner();
        String id = "";
        String locationId = "";
        String rrYear = "";
        String rrRateLand = "";
        String rrRatePlot = "";
        String rrRateApartment = "";
        System.out.println(dataHolder);
        DataFormatter formatter = new DataFormatter();
        for (Iterator iterator = dataHolder.iterator(); iterator.hasNext();) {
            List list = (List) iterator.next();
            logger.info("list", list);
            locationId = list.get(1).toString();
            rrYear = list.get(2).toString();
            rrRateLand = list.get(3).toString();
            rrRatePlot = list.get(4).toString();
            rrRateApartment = list.get(5).toString();
            //List<String> strList = new ArrayList<String>(Arrays.asList(locationCategories.split(",")));
           // List<Integer> numberList = new ArrayList<Integer>();
//            for (String number : strList) {
//                numberList.add(Integer.parseInt(number));
//            }

            try {
                readyReckoner.setLocationId(Integer.parseInt(locationId));
                readyReckoner.setRrYear(Double.parseDouble(rrYear));
                readyReckoner.setRrRateLand(Double.parseDouble(rrRateLand));
                readyReckoner.setRrRatePlot(Double.parseDouble(rrRatePlot));
                readyReckoner.setRrRateApartment(Double.parseDouble(rrRateApartment));
//                System.out.println("location line167check" + location);
//                System.out.println(numberList);
                readyReckonerDAL.insert(readyReckoner);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File excelFile = attachmentUtils.getDirectoryByAttachmentType(AttachmentUtils.AttachmentType.READY_RECKONER);
        FileUtils.cleanDirectory(excelFile);
        return true;

    }

    public Boolean checkExistingDataa() throws IOException {
        logger.info("check ke andar hai bhai??");
        Vector checkCellVectorHolder = read();
        logger.info("checkCellVectorHolder line116 :{}", checkCellVectorHolder);
        logger.info("read in check line117 :{}", read());
        int excelSize = checkCellVectorHolder.size() - 1;
        System.out.println("excelSize" + excelSize);
        List<Readyreckoner> rs = readyReckonerDAL.getAll();
        JFrame parent = new JFrame();

        System.out.println("rs" + rs);
        int listSize = rs.size();
        logger.info("rsss:::::", listSize);
        System.out.println("rsss:::::" + listSize);
        if (excelSize == listSize || excelSize > listSize) {
            JDialog.setDefaultLookAndFeelDecorated(true);
            int response = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            switch (response) {
                case JOptionPane.NO_OPTION:
                    System.out.println("No button clicked");
                    JOptionPane.showMessageDialog(parent, "upload Cancelled");
                    break;
                case JOptionPane.YES_NO_OPTION:
                    saveToDatabase(checkCellVectorHolder);
                    System.out.println("Yes button clicked");
                    JOptionPane.showMessageDialog(parent, "Saved succesfully");
                    break;
                case JOptionPane.CLOSED_OPTION:
                    System.out.println("JOptionPane closed");
                    break;
            }
            //            if (response == JOptionPane.NO_OPTION) {
            //                System.out.println("No button clicked");
            //            } else if (response == JOptionPane.YES_OPTION) {
            //                saveToDatabase(checkCellVectorHolder);
            //                System.out.println("Yes button clicked");
            //                if (saveToDatabase(checkCellVectorHolder) == true) {
            //                    JOptionPane.showMessageDialog(parent, "Saved succesfully");
            //                    return true;
            //                } else {
            //                    JOptionPane.showMessageDialog(parent, "unsuccesfull");
            //                }
            //            } else if (response == JOptionPane.CLOSED_OPTION) {
            //                System.out.println("JOptionPane closed");
            //            }

        } else {
            System.out.println("No selected");
        }

        return true;
    }
}
