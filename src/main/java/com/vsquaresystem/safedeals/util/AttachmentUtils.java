/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vsquaresystem.safedeals.util;

import com.vsquaresystem.safedeals.setting.Setting;
import com.vsquaresystem.safedeals.setting.SettingDAL;
import com.vsquaresystem.safedeals.setting.SettingKey;
import com.vsquaresystem.safedeals.setting.SettingService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
public class AttachmentUtils {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SettingService settingService;

    @Autowired
    private SettingDAL settingDAL;

    private static final String RAW_READY_RECKONER_ATTACHMENT_DIR_NAME = "raw_ready_reckoner";
    private static final String RAW_MARKET_PRICE_ATTACHMENT_DIR_NAME = "raw_market_price";
    private static final String MARKET_PRICE_ATTACHMENT_DIR_NAME = "market_price";
    private static final String READY_RECKONER_ATTACHMENT_DIR_NAME = "ready_reckoner";
    //private static final String VIDEO_ATTACHMENT_DIR_NAME = "video";
    private static final String LOCATION_ATTACHMENT_DIR_NAME = "location";
    private static final String TESTIMONIAL_ATTACHMENT_DIR_NAME = "testimonial";
    private static final String TEAM_ATTACHMENT_DIR_NAME = "team";
    private static final String EVENT_ATTACHMENT_DIR_NAME = "event";
    private static final String AMENITY_NAME = "amenity_name";
    private static final String AMENITY_DETAIL_NAME = "amenity_detail";
    private static final String IMAGE_ATTACHMENT_DIR_NAME = "image";
    private static final String PROPERTY_ATTACHMENT_DIR_NAME = "property";
    private static final String PROJECT_ATTACHMENT_DIR_NAME = "project";
    private static final String PROJECT_ATTACHMENT_NAME = "project";
    private static final String MUTATION_ATTACHMENT_DIR_NAME = "mutation";
    private static final String SALEDEED_ATTACHMENT_DIR_NAME = "sale_deed";
    private static final String DEVELOPMENT_AGREEMENT_ATTACHMENT_DIR_NAME = "development_agreement";
    private static final String POWER_OF_AUTHORITY_ATTACHMENT_DIR_NAME = "power_of_authority";
    private static final String TAX_RECEIPT_ATTACHMENT_DIR_NAME = "tax_receipt";
    private static final String LAYOUT_SANCTION_ATTACHMENT_DIR_NAME = "layout_sanction";
    private static final String DEVELOPMENT_PLAN_ATTACHMENT_DIR_NAME = "development_plan";
    private static final String RELEASE_LETTER_ATTACHMENT_DIR_NAME = "release_letter";
    private static final String BUILDING_SANCTION_ATTACHMENT_DIR_NAME = "building_sanction";
    private static final String COMPLETION_CERTIFICATE_ATTACHMENT_DIR_NAME = "completion_certificate";
    private static final String OCCUPANCY_CERTIFICATE_ATTACHMENT_DIR_NAME = "occupancy_certificate";
    private static final String BIRD_EYE_VIEW_ATTACHMENT_DIR_NAME = "bird_eye_view";
    private static final String ELEVATION_ATTACHMENT_DIR_NAME = "elevation";
    private static final String FLOOR_PLANS_ATTACHMENT_DIR_NAME = "floor_plans";

    public static enum AttachmentType {

        RAW_READY_RECKONER,
        RAW_MARKET_PRICE,
        MARKET_PRICE,
        READY_RECKONER,
        //VIDEO,
        LOCATION,
        CITY,
        TESTIMONIAL,
        TEAM,
        AMENITY_NAME,
        AMENITY_DETAIL,
        EVENT,
        IMAGE,
        PROPERTY,
        PROJECT,

    }

    public static enum DocumentType {

        MUTATION_COPY,
        SALE_DEED,
        DEVELOPMENT_AGREEMENT,
        POWER_OF_AUTHORITY,
        TAX_RECEIPT,
        LAYOUT_SANCTION,
        DEVELOPMENT_PLAN,
        RELEASE_LETTER,
        BUILDING_SANCTION,
        COMPLETION_CERTIFICATE,
        OCCUPANCY_CERTIFICATE,
        BIRD_EYE_VIEW,
        ELEVATION,
        FLOOR_PLAN,
        OTHERS
    }

    public File getRootDirectory() {
        Setting setting = settingService.get(SettingKey.DOCUMENT_REPOSITORY_ROOT_PATH);
        if (setting.getSettingValue() == null) {
            throw new NullPointerException("Value for Setting: " + SettingKey.DOCUMENT_REPOSITORY_ROOT_PATH + " can not be null ");
        }
        File rootDir = new File(setting.getSettingValue());
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }
        return rootDir;
    }

    /**
     * This returns a directory as per the {SettingKey.ATTACHMENT_ROOT_PATH} If
     * directory does not exists then creation is attempted. If successful it
     * returns a {File} instance representing the directory.
     *
     * For Example: /foo/bar
     *
     * @return the {File} instance representing the attachment root directory
     */
    public File getAttachmentRootDirectory() {
//        System.out.println("are we inside getAttachmentRootDirectory");
        Setting setting = settingDAL.findByKey(SettingKey.ATTACHMENT_ROOT_PATH);
//        System.out.println("setting" + setting);
        File rootDir = new File(setting.getSettingValue());
//        System.out.println("rootDir" + rootDir);
        if (!rootDir.exists()) {
            logger.info("attachment root directory {} does not exist, creating...", rootDir);
            rootDir.mkdirs();
        }
        return rootDir;
    }

    public String getLocationExportAttachmentRootDirectory() {
        System.out.println("are we inside getAttachmentRootDirectory");
        Setting setting = settingDAL.findByKey(SettingKey.DOWNLOAD_ROOT_PATH);
        System.out.println("setting" + setting);
        String rootDir = setting.getSettingValue();
        System.out.println("rootDir" + rootDir);
//        if (!rootDir.exists()) {
//            logger.info("attachment root directory {} does not exist, creating...", rootDir);
//            rootDir.mkdirs();
//        }
        return rootDir;
    }

    public String getAmenityExportAttachmentRootDirectory() {
        System.out.println("are we inside getAttachmentRootDirectory");
        Setting setting = settingDAL.findByKey(SettingKey.DOWNLOAD_ROOT_PATH);
        System.out.println("setting" + setting);
        String rootDir = setting.getSettingValue();
        System.out.println("rootDir" + rootDir);
        return rootDir;
    }

    public String getAmenityDetailExportAttachmentRootDirectory() {
        System.out.println("are we inside getAttachmentRootDirectory");
        Setting setting = settingDAL.findByKey(SettingKey.DOWNLOAD_ROOT_PATH);
        System.out.println("setting" + setting);
        String rootDir = setting.getSettingValue();
        System.out.println("rootDir" + rootDir);
//        if (!rootDir.exists()) {
//            logger.info("attachment root directory {} does not exist, creating...", rootDir);
//            rootDir.mkdirs();
//        }
        return rootDir;
    }

    public String getRawMarketPriceExportAttachmentRootDirectory() {
        System.out.println("are we inside getAttachmentRootDirectory");
        Setting setting = settingDAL.findByKey(SettingKey.DOWNLOAD_ROOT_PATH);
        System.out.println("setting" + setting);
        String rootDir = setting.getSettingValue();
        System.out.println("rootDir" + rootDir);
        return rootDir;
    }

    public String getCityExportAttachmentRootDirectory() {
        System.out.println("are we inside getAttachmentRootDirectory");
        Setting setting = settingDAL.findByKey(SettingKey.DOWNLOAD_ROOT_PATH);
        System.out.println("setting" + setting);
        String rootDir = setting.getSettingValue();
        System.out.println("rootDir" + rootDir);
//        if (!rootDir.exists()) {
//            logger.info("attachment root directory {} does not exist, creating...", rootDir);
//            rootDir.mkdirs();
//        }
        return rootDir;
    }

    /**
     * li7mku6jn5t45ce32z`q1zwe3xcrt5vbu7m,9.[09-/9i896m7n45vc3e12zw`1lkjhgf
     * This returns the {AttachmentType] specific directories within the
     * attachment root directory.
     *
     * For Example: /foo/bar/visa for {AttachmentType.VISA}
     *
     * @param attachmentType the type of attachment
     * @return the directory representing the {AttachmentType} specific
     * directory
     * @throws IOException when the {AttachmentType} specified is not recognized
     */
    public File getDirectoryByAttachmentType(AttachmentType attachmentType) throws IOException {
//        logger.info("are we in getDirectoryByAttachmentType");
//        System.out.println("are we in getDirectoryByAttachmentType");
        File attachmentDir = null;
//        System.out.println("attachmentType" + attachmentType);
//        logger.info("attachmentType", attachmentType);

        switch (attachmentType) {
            case RAW_READY_RECKONER:
                attachmentDir = new File(getAttachmentRootDirectory(), RAW_READY_RECKONER_ATTACHMENT_DIR_NAME);

                break;
            case RAW_MARKET_PRICE:
                attachmentDir = new File(getAttachmentRootDirectory(), RAW_MARKET_PRICE_ATTACHMENT_DIR_NAME);

                break;
            case MARKET_PRICE:
                attachmentDir = new File(getAttachmentRootDirectory(), MARKET_PRICE_ATTACHMENT_DIR_NAME);

                break;
            case READY_RECKONER:
                attachmentDir = new File(getAttachmentRootDirectory(), READY_RECKONER_ATTACHMENT_DIR_NAME);
                break;
//            case VIDEO:
//                attachmentDir = new File(getAttachmentRootDirectory(), VIDEO_ATTACHMENT_DIR_NAME);
//                logger.info("attachmentDir Ruchita line n0 101");
//                break;
            case LOCATION:
                attachmentDir = new File(getAttachmentRootDirectory(), LOCATION_ATTACHMENT_DIR_NAME);
                logger.info("attachmentDir Ruchita line n0 101");
                break;

            case TESTIMONIAL:
                attachmentDir = new File(getAttachmentRootDirectory(), TESTIMONIAL_ATTACHMENT_DIR_NAME);
                logger.info("ATTACHMENT_BY_TYPE" + attachmentType);
                break;

            case TEAM:
                attachmentDir = new File(getAttachmentRootDirectory(), TEAM_ATTACHMENT_DIR_NAME);
                logger.info("ATTACHMENT_BY_TYPE" + attachmentType);
                break;

            case EVENT:
                attachmentDir = new File(getAttachmentRootDirectory(), EVENT_ATTACHMENT_DIR_NAME);
                logger.info("ATTACHMENT_BY_TYPE" + attachmentType);
                break;

            case IMAGE:
                attachmentDir = new File(getAttachmentRootDirectory(), IMAGE_ATTACHMENT_DIR_NAME);
                logger.info("ATTACHMENT_BY_TYPE" + attachmentType);
                break;

            case PROPERTY:
                attachmentDir = new File(getAttachmentRootDirectory(), PROPERTY_ATTACHMENT_DIR_NAME);
                logger.info("ATTACHMENT_BY_TYPE" + attachmentType);
                break;

            case AMENITY_NAME:
                attachmentDir = new File(getAttachmentRootDirectory(), AMENITY_NAME);
                logger.info("ATTACHMENT_BY_TYPE" + attachmentType);
                break;

            case AMENITY_DETAIL:
                attachmentDir = new File(getAttachmentRootDirectory(), AMENITY_DETAIL_NAME);
                logger.info("ATTACHMENT_BY_TYPE" + attachmentType);
                break;

            case PROJECT:
                attachmentDir = new File(getAttachmentRootDirectory(), PROJECT_ATTACHMENT_DIR_NAME);
                logger.info("ATTACHMENT_BY_TYPE" + attachmentType);
                break;

            default:
                throw new IOException("Could not determine location to store attachment of type: " + attachmentType);

        }

        if (!attachmentDir.exists()) {
            logger.info("attachment type {} : directory {} does not exist, creating...", attachmentType, attachmentDir);
            attachmentDir.mkdirs();
        }

        return attachmentDir;
    }

    /**
     * TODO WRITE DOCS HERE
     *
     * For Example: /foo/bar/visa/156 for {AttachmentType.VISA} and a {Visa}
     * with id 156
     *
     * @param attachmentType
     * @param videoId
     * @return
     * @throws IOException
     */
//    public File getDirectoryByAttachmentTypeAndVideoId(AttachmentType attachmentType, Integer videoId) throws IOException {
//        File videoDir = new File(getDirectoryByAttachmentType(attachmentType), videoId.toString());
//
//        if (!videoDir.exists()) {
//            videoDir.mkdirs();
//            
//        }
////        logger.info("getDirectoryByAttachmentTypeAndVideoId");
//        return videoDir;
//    }
    /**
     *
     * @param outputFilename
     * @param inputStream
     * @param attachmentType
     * @return
     * @throws FileExistsException
     * @throws IOException
     */
    public File storeAttachmentByAttachmentType(
            String outputFilename,
            InputStream inputStream,
            AttachmentType attachmentType
    )
            throws FileExistsException, IOException {
        logger.info("storeAttachmentByAttachmentType {} ..", attachmentType);
        File parentDir = getDirectoryByAttachmentType(attachmentType);
        //TODO sanitize the filename before using it like this.
        //TODO also make sure the file size is within limits here
        File outputFile = new File(parentDir, outputFilename);
        logger.info("outputFile" + outputFile);
        logger.info("parentDir" + parentDir);
        String filePath = outputFile.getPath();
        logger.info("filePath ::::::::::" + filePath);

//        
//         if (outputFile.exists()) {
//            throw new FileExistsException(outputFile);
//        }
        FileCopyUtils.copy(inputStream, new FileOutputStream(outputFile));
//        Vector dataHolder = read(filePath);
//        checkExistingData(dataHolder);
//        System.out.println("are we going in read method?");
//        logger.info("dataHolder ::::::::::" + dataHolder);
        return outputFile;
    }

    /**
     *
     * @param outputFilename
     * @param inputStream
     * @param attachmentType
     * @param videoId
     * @return
     * @throws FileExistsException
     * @throws IOException
     */
//    public File storeAttachmentByAttachmentTypeAndVideoId(
//            String outputFilename,
//            InputStream inputStream,
//            AttachmentType attachmentType,
//            Integer videoId)
//            throws FileExistsException, IOException {
//
//        File parentDir = getDirectoryByAttachmentTypeAndVideoId(attachmentType, videoId);
//        //TODO sanitize the filename before using it like this.
//        //TODO also make sure the file size is within limits here
//        File outputFile = new File(parentDir, outputFilename);
//
//        if (outputFile.exists()) {
//            throw new FileExistsException(outputFile);
//        }
//
//        FileCopyUtils.copy(inputStream, new FileOutputStream(outputFile));
//        System.out.println("storeAttachmentByAttachmentTypeAndVideoId");
//        return outputFile;
//    }
//    public boolean deleteAttachmentByAttachmentTypeAndVideoId(
//            String filename,
//            AttachmentType attachmentType,
//            Integer videoId)
//            throws FileNotFoundException, IOException {
//
//        File parentDir = getDirectoryByAttachmentTypeAndVideoId(attachmentType, videoId);
//        //TODO sanitize the filename before using it like this.
//        //TODO also make sure the file size is within limits here
//        File toBeDeletedFile = new File(parentDir, filename);
//
//        if (!toBeDeletedFile.exists()) {
//            throw new FileNotFoundException(toBeDeletedFile.getName());
//        }
//
//        return toBeDeletedFile.delete();
//    }
    public File getDirectoryByAttachmentTypeAndEntityId(AttachmentType attachmentType, Integer entityId, Boolean view) throws IOException {
        File entityDir = new File(getDirectoryByAttachmentType(attachmentType), entityId.toString());
        if (view == true) {
            return entityDir;
        } else {
            if (!entityDir.exists()) {
                entityDir.mkdirs();
            } else {
                String[] items = entityDir.list();
                for (String s : items) {
                    File currentFile = new File(entityDir.getPath(), s);
                    logger.info("Current File is **********"+currentFile);
//                    currentFile.delete();
                }
//                entityDir.mkdirs();
            }
            return entityDir;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    public File getDirectoryByAttachmentTypeAndEntityIdAndDocumentType(AttachmentType attachmentType, DocumentType documentType, Integer entityId, Boolean view) throws IOException {
        File documentDir = null;
//        System.out.println("attachmentType" + attachmentType);
//        logger.info("attachmentType", attachmentType);

        switch (documentType) {
            case MUTATION_COPY:
                documentDir = new File(MUTATION_ATTACHMENT_DIR_NAME);

                break;
            case SALE_DEED:
                documentDir = new File(SALEDEED_ATTACHMENT_DIR_NAME);

                break;

            case DEVELOPMENT_AGREEMENT:
                documentDir = new File(DEVELOPMENT_AGREEMENT_ATTACHMENT_DIR_NAME);

                break;

            case POWER_OF_AUTHORITY:
                documentDir = new File(POWER_OF_AUTHORITY_ATTACHMENT_DIR_NAME);

                break;

            case TAX_RECEIPT:
                documentDir = new File(TAX_RECEIPT_ATTACHMENT_DIR_NAME);

                break;

            case LAYOUT_SANCTION:
                documentDir = new File(LAYOUT_SANCTION_ATTACHMENT_DIR_NAME);

                break;

            case DEVELOPMENT_PLAN:
                documentDir = new File(DEVELOPMENT_PLAN_ATTACHMENT_DIR_NAME);

                break;

            case RELEASE_LETTER:
                documentDir = new File(RELEASE_LETTER_ATTACHMENT_DIR_NAME);

                break;

            case BUILDING_SANCTION:
                documentDir = new File(BUILDING_SANCTION_ATTACHMENT_DIR_NAME);

                break;

            case COMPLETION_CERTIFICATE:
                documentDir = new File(COMPLETION_CERTIFICATE_ATTACHMENT_DIR_NAME);

                break;

            case OCCUPANCY_CERTIFICATE:
                documentDir = new File(OCCUPANCY_CERTIFICATE_ATTACHMENT_DIR_NAME);

                break;

            case BIRD_EYE_VIEW:
                documentDir = new File(BIRD_EYE_VIEW_ATTACHMENT_DIR_NAME);

                break;

            case ELEVATION:
                documentDir = new File(ELEVATION_ATTACHMENT_DIR_NAME);

                break;

            case FLOOR_PLAN:
                documentDir = new File(FLOOR_PLANS_ATTACHMENT_DIR_NAME);

                break;

        }

        File entityDir = new File(getDirectoryByAttachmentTypeAndEntityId(attachmentType, entityId, view), documentDir.toString());
        if (view == true) {
            return entityDir;
        } else {
            if (!entityDir.exists()) {
                entityDir.mkdirs();
            } else {
                String[] items = entityDir.list();
                for (String s : items) {
                    File currentFile = new File(entityDir.getPath(), s);
                    logger.info("Current File is **********"+currentFile);
//                    currentFile.delete();
                }
//                entityDir.mkdirs();
            }
            return entityDir;
        }
    }
    ////////////////////////////////////////////////////////////////////////////

    /**
     *
     * @param outputFilename
     * @param inputStream
     * @param attachmentType
     * @param entityId
     * @return
     * @throws FileExistsException
     * @throws IOException
     */
    public File storeAttachmentByAttachmentTypeAndEntityId(
            String outputFilename,
            InputStream inputStream,
            AttachmentType attachmentType,
            Integer entityId,
            Boolean isView)
            throws FileExistsException, IOException {

        File parentDir = getDirectoryByAttachmentTypeAndEntityId(attachmentType, entityId, isView);
        //TODO sanitize the filename before using it like this.
        //TODO also make sure the file size is within limits here
        File outputFile = new File(parentDir, outputFilename);
        if (outputFile.exists()) {
            outputFile.delete();
            FileCopyUtils.copy(inputStream, new FileOutputStream(outputFile));
//            File outputFile = new File(parentDir, outputFilename);
        } else {
            FileCopyUtils.copy(inputStream, new FileOutputStream(outputFile));
//            File outputFile = new File(parentDir, outputFilename);
        }
        return outputFile;
    }

    /////////////////////////////////////////////////////////////////////////////
    public File storeAttachmentByAttachmentTypeAndEntityIdAndDocumentType(
            String outputFilename,
            InputStream inputStream,
            AttachmentType attachmentType,
            DocumentType documentType,
            Integer entityId,
            Boolean isView)
            throws FileExistsException, IOException {

        File parentDir = getDirectoryByAttachmentTypeAndEntityIdAndDocumentType(attachmentType, documentType, entityId, isView);
        //TODO sanitize the filename before using it like this.
        //TODO also make sure the file size is within limits here
        File outputFile = new File(parentDir, outputFilename);
        if (outputFile.exists()) {
            outputFile.delete();
            FileCopyUtils.copy(inputStream, new FileOutputStream(outputFile));
//            File outputFile = new File(parentDir, outputFilename);
        } else {
            FileCopyUtils.copy(inputStream, new FileOutputStream(outputFile));
//            File outputFile = new File(parentDir, outputFilename);
        }
        return outputFile;
    }
    /////////////////////////////////////////////////////////////////////////////

    public boolean deleteAttachmentByAttachmentTypeAndEntityId(
            String filename,
            AttachmentType attachmentType,
            Integer entityId,
            Boolean isView)
            throws FileNotFoundException, IOException {

        File parentDir = getDirectoryByAttachmentTypeAndEntityId(attachmentType, entityId, isView);
        //TODO sanitize the filename before using it like this.
        //TODO also make sure the file size is within limits here
        File toBeDeletedFile = new File(parentDir, filename);

        if (!toBeDeletedFile.exists()) {
            throw new FileNotFoundException(toBeDeletedFile.getName());
        }

        return toBeDeletedFile.delete();
    }
}
