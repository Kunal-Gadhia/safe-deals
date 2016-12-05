///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.vsquaresystem.safedeals.readyreckoner;
//
//import java.io.FileInputStream;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Vector;
//import org.apache.poi.ss.usermodel.DataFormatter;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// *
// * @author hp-pc
// */
//public class ReadyreckonerReader {
//    
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    private RawReadyReckonerDAL rawReadyReckonerDAL;
//    
//     public static void main(String[] args) {
//        String fileName = "/home/hp-pc/Desktop/RR_Data.xlsx";
//        Vector dataHolder = read(fileName);
//        ReadyreckonerReader readyreckonerReader = new ReadyreckonerReader();
//        readyreckonerReader.saveToDatabase(dataHolder);
//    }
//     
//     public static Vector read(String fileName) {
//        DataFormatter formatter = new DataFormatter();
//        Vector cellVectorHolder = new Vector();
//        try {
//            FileInputStream myInput = new FileInputStream(fileName);
//            //POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
//            XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
//            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
//            Iterator rowIter = mySheet.rowIterator();
//            while (rowIter.hasNext()) {
//                XSSFRow myRow = (XSSFRow) rowIter.next();
//                Iterator cellIter = myRow.cellIterator();
//                //Vector cellStoreVector=new Vector();
//                List list = new ArrayList();
//                while (cellIter.hasNext()) {
//                    XSSFCell myCell = (XSSFCell) cellIter.next();
//                    
//                    list.add(myCell);
//                }
//                cellVectorHolder.addElement(list);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return cellVectorHolder;
//    }
//     
//     public void saveToDatabase(Vector dataHolder) {
//        System.out.println("dataHolder :- " + dataHolder);
//        DataFormatter formatter = new DataFormatter();
//        RawReadyReckoner rr = new RawReadyReckoner();
//
//        for (Iterator iterator = dataHolder.iterator(); iterator.hasNext();) {
//            iterator.next();
//            while (iterator.hasNext()) {
//
//                List list = (List) iterator.next();
//                System.out.println("List :- " + list);
////                System.out.println("List :- " + list.get(2));
//                rr.setId(1);
//                rr.setCity(list.get(0).toString());
//                rr.setLocation(list.get(1).toString());
//                rr.setSdZone(list.get(2).toString());
//                rr.setLocationType(list.get(3).toString());
////                logger.info("heeeeelooooo use of location:{}", list.get(4).toString().split(",", 1));
//                rr.setUseOfLocation(list.get(4).toString().split(",", 1));
//                rr.setRrYear(list.get(5).toString());
//                rr.setRrRateLand(list.get(6).toString());
//                rr.setRrRatePlot(list.get(7).toString());
//                rr.setRrRateApartment(list.get(8).toString());
////                System.out.println("list.get(5) " + list.get(5));
//                logger.info("rrObj UseOfLocation:{}", rr.getUseOfLocation());
//                logger.info("rrObj :{}", rr);
//                rawReadyReckonerDAL.insert(rr);
//            }
//        }
//    }
//
//    
////    public static Vector getRawReadyReckoner(String fileName) throws FileNotFoundException, IOException {
////        Vector cellVectorHolder = new Vector();
////        try {
//////            File excel = new File("/home/hp-pc/Desktop/RR_Data.xlsx");
////            FileInputStream fis = new FileInputStream(fileName);
////            XSSFWorkbook book = new XSSFWorkbook(fis);
////            XSSFSheet sheet = book.getSheetAt(0);
////            Iterator<Row> itr = sheet.iterator(); // Iterating over Excel file in Java 
////            while (itr.hasNext()) {
////                Row row = itr.next();
////                // Iterating over each column of Excel file
////                Iterator<Cell> cellIterator = row.cellIterator();
////                while (cellIterator.hasNext()) {
////                    Cell cell = cellIterator.next();
////                    switch (cell.getCellType()) {
////                        case Cell.CELL_TYPE_STRING:
////                            System.out.print(cell.getStringCellValue() + "\t");
////                            break;
////                        case Cell.CELL_TYPE_NUMERIC:
////                            System.out.print(cell.getNumericCellValue() + "\t");
////                            break;
////                        case Cell.CELL_TYPE_BOOLEAN:
////                            System.out.print(cell.getBooleanCellValue() + "\t");
////                            break;
////                        default:
////                    }
////                }
////                System.out.println("");
////            }
////
////        } catch (FileNotFoundException fe) {
////            fe.printStackTrace();
////        } catch (IOException ie) {
////            ie.printStackTrace();
////        }
////        
////      
////    }
//    
//}
