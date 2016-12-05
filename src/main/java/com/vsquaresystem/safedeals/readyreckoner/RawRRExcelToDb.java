package com.vsquaresystem.safedeals.readyreckoner;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RawRRExcelToDb {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) throws Exception {

        String fileName = "/home/hp-pc/Desktop/RR_Test_Data.xlsx";
        Vector dataHolder = read(fileName);
        checkExistingData(dataHolder);
        System.out.println("dataHolder" + dataHolder);
//        saveToDatabase(dataHolder);

    }

    public static Vector read(String fileName) {
        Vector cellVectorHolder = new Vector();
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
                    list.add(myCell);
                }
                cellVectorHolder.addElement(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellVectorHolder;
    }

    private static void checkExistingData(Vector dataHolder) {
        System.out.println("dataHolder" + dataHolder.size());
        int i = dataHolder.size();
        Scanner sc = new Scanner(System.in);

        if (i != 0) {
            System.out.println("within if");
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/safedeals", "root", "admin");
                Statement stmtO = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmtO.executeQuery("SELECT * from raw_ready_reckoner");
                int size = 0;
                rs.beforeFirst();
                rs.last();
                size = rs.getRow();
                int totalSize = size + 1;
                System.out.println(totalSize);

                if (i == totalSize || i > totalSize) {
                    JOptionPane.showMessageDialog(null, "Are you sure you want to overwrite?");
                    System.out.println("Enter your rollno");
                    int selectOverwrite = sc.nextInt();
                    System.out.println("rollno" + selectOverwrite);                
                        if (selectOverwrite == 1) {
                            Statement statement = con.createStatement();
                        // Use TRUNCATE
                        String sql = "TRUNCATE raw_ready_reckoner";
                        // Execute deletion
                        statement.executeUpdate(sql);
                        saveToDatabase(dataHolder);
                            } else 
                        {
                            System.out.println("Dont overwrite");
                        }
                } else {
                    System.out.println("else");
                }
                con.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    private static void saveToDatabase(Vector dataHolder) {
        String id = "";
        String city = "";
        String location = "";
        String sdZone = "";
        String locationType = "";
        String useOfLocation = "";
        String rrYear = "";
        String rrRateLand = "";
        String rrRatePlot = "";
        String rrRateApartment = "";
        System.out.println(dataHolder);
        DataFormatter formatter = new DataFormatter();
        for (Iterator iterator = dataHolder.iterator(); iterator.hasNext();) {
            List list = (List) iterator.next();
            id = list.get(0).toString();
            city = list.get(1).toString();
            location = list.get(2).toString();
            sdZone = list.get(3).toString();
            locationType = list.get(4).toString();
            useOfLocation = list.get(5).toString();
            rrYear = list.get(6).toString();
            rrRateLand = list.get(7).toString();
            rrRatePlot = list.get(8).toString();
            rrRateApartment = list.get(9).toString();

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
//                Connection conOne;
                 
//                DatabaseMetaData dmd = con.getMetaData();
//                String url = dmd.getURL();
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/safedeals", "root", "admin");
                Statement stmtO = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

//                ResultSet rs = stmtO.executeQuery("select * from raw_ready_reckoner");
                //getting the record of 3rd row  
//                rs.absolute(3);
//                System.out.println(rs.getString(1) + " " + rs.getString(1) + " " + rs.getString(1));
//                con.close();
                PreparedStatement stmt = con.prepareStatement("INSERT INTO raw_ready_reckoner(id, city,location,sd_zone,location_type,use_of_location, rr_year, rr_rate_land, rr_rate_plot,rr_rate_apartment ) VALUES(?,?,?,?,?,?,?,?,?,?)");
                System.out.println("connection made...");
                stmt.setString(1, id);
                stmt.setString(2, city);
                stmt.setString(3, location);
                stmt.setString(4, sdZone);
                stmt.setString(5, locationType);
                stmt.setString(6, useOfLocation);
                stmt.setString(7, rrYear);
                stmt.setString(8, rrRateLand);
                stmt.setString(9, rrRatePlot);
                stmt.setString(10, rrRateApartment);
                stmt.executeUpdate();

                System.out.println("Data is inserted");
                stmt.close();
                con.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

}
