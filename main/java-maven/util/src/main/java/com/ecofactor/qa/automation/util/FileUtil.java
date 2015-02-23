/*
 * FileUtil.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * The Class FileUtil.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /** The Constant SLASH. */
    private static final String SLASH = "/";

    /**
     * Read file.
     * 
     * @param filePath the file path
     * @return the string
     */
    public static String readFile(String filePath) {

        StringBuffer stringBuffer = null;
        try {
            FileReader fileReader = new FileReader(new File(filePath));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");
            }
            fileReader.close();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    /**
     * Creates the nio file.
     * @param firstPath the first path
     * @param isNew the is new
     * @param nextPath the next path
     * @return the path
     */
    public static Path createNIOFile(final String firstPath, final boolean isNew, final String... nextPath) {

        try {
            final Path dir = Paths.get(firstPath);
            Files.createDirectories(dir);
            final Path filePath = Paths.get(firstPath, nextPath);
            if (isNew) {
                Files.deleteIfExists(filePath);
            }
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            return filePath;
        } catch (IOException e) {
            LOGGER.error("Error creating NIO file. Cause ::: " + e);
        }
        return null;
    }

    /**
     * Creates the file.
     * 
     * @param fileName the file name
     */
    public static void createFile(String fileName) {

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            LOGGER.error("Error creating file. Cause ::: " + e);
        }
    }

    /**
     * Delete file.
     * 
     * @param filePath the file path
     */
    public static void deleteFile(Path filePath) {

        try {
            Files.delete(filePath);
        } catch (Exception e) {
            LOGGER.error("Error deleting file. Cause ::: " + e);
        }
    }

    /**
     * Write properties to file.
     * 
     * @param properties the properties
     * @param fileName the file name
     * @param comment the comment
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writePropertiesToFile(final Properties properties, final String fileName, final String comment)
            throws IOException {

        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOut = new FileOutputStream(file);
        properties.store(fileOut, comment);
        fileOut.close();
    }

    /**
     * Creates the directory.
     * 
     * @param dirPath the dir path
     */
    public static void createDirectory(final String dirPath) {

        Path directoryPath = Paths.get(dirPath);
        try {
            if (!directoryPath.toFile().exists()) {
                Files.createDirectory(directoryPath);
                DriverConfig.setLogString("Directory created :" + directoryPath, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete folder.
     * 
     * @param folder the folder
     */
    public static void deleteFolder(final String folder) {

        File folderPath = new File(folder);

        try {
            final File[] filesList = folderPath.listFiles();
            for (final File file : filesList) {
                file.delete();
            }
            folderPath.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete all files in folder.
     * 
     * @param folder the folder
     */
    public static void deleteAllFilesInFolder(final String folder) {

        File folderPath = new File(folder);

        try {
            final File[] filesList = folderPath.listFiles();
            for (final File file : filesList) {
                if (file.isDirectory()) {
                    deleteAllFilesInFolder(file.toString());
                }
                file.delete();
            }
            folderPath.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Zip folder.
     * 
     * @param srcFolder the src folder
     * @param destZipFile the dest zip file
     */
    public static void zipFolder(final String srcFolder, final String destZipFile) {

        try {
            final FileOutputStream fileWriter = new FileOutputStream(destZipFile);
            final ZipOutputStream zip = new ZipOutputStream(fileWriter);
            addFolderToZip("", srcFolder, zip);
            zip.flush();
            zip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the folder to zip.
     * 
     * @param path the path
     * @param srcFolder the src folder
     * @param zip the zip
     */
    private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) {

        final File folder = new File(srcFolder);

        if (folder.list().length == 0) {
            addFileToZip(path, srcFolder, zip, true);
        } else {
            for (String fileName : folder.list()) {
                if (path.equals("")) {
                    addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip, false);
                } else {
                    addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip, false);
                }
            }
        }
    }

    /**
     * Adds the file to zip.
     * 
     * @param path the path
     * @param srcFile the src file
     * @param zip the zip
     * @param flag the flag
     */
    private static void addFileToZip(final String path, final String srcFile, final ZipOutputStream zip, boolean flag) {

        try {
            final File folder = new File(srcFile);

            if (folder.isDirectory()) {
                addFolderToZip(path, srcFile, zip);
            } else {
                final byte[] buf = new byte[1024];
                int len;
                final FileInputStream inputStream = new FileInputStream(srcFile);
                zip.putNextEntry(new ZipEntry(new StringBuilder(path).append(SLASH).append(folder.getName()).toString()));

                while (inputStream.read(buf) > 0) {
                    len = inputStream.read(buf);
                    zip.write(buf, 0, len);
                }
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
