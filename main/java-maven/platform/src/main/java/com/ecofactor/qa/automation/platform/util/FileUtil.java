/*
 * FileUtil.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.platform.util;

/**
 * The Class FileUtil.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class FileUtil.
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public final class FileUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
    private static final String SLASH = "/";

    /**
     * Instantiates a new file util.
     */
    private FileUtil() {

    }

    /**
     * Add a folder to Zip folder.
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
        	LOGGER.error("Error in creating zip file", e);
        }
    }

    /**
     * Delete folder.
     * @param folder the folder
     */
    public static void deleteFolder(final File folder) {

        try {
            final File[] testCaseFiles = folder.listFiles();
            for (final File file : testCaseFiles) {
                file.delete();
            }
            folder.delete();
        } catch (Exception e) {
        	LOGGER.error("Error in delete folder", e);
        }
    }

    /**
     * Download Mobile Zip File from jenkinsosx.ecofactor.com
     * @param webURL the web url
     * @param destinationPath the destination path
     */
    public static void downloadFileFromURL(final String webURL, final String destinationPath) {

        LogUtil.setLogString(new StringBuilder("Download File from location :").append(webURL)
                .toString(), true);
        try {
            final URL url = new URL(webURL);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            final InputStream inStream = connection.getInputStream();
            final FileOutputStream outStream = new FileOutputStream(new File(destinationPath));
            final byte[] buf = new byte[1024];
            int inByte = inStream.read(buf);
            while (inByte >= 0) {
                outStream.write(buf, 0, inByte);
                inByte = inStream.read(buf);
            }
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
        	LOGGER.error("Error in download file", e);
        }
    }

    /**
     * Un zip a file to a destination folder.
     * @param zipFile the zip file
     * @param outputFolder the output folder
     */
    public static void unZipFile(final String zipFile, final String outputFolder) {

        LogUtil.setLogString("UnZip the File", true);
        final byte[] buffer = new byte[1024];
        int len;
        try {
            // create output directory is not exists
            final File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }
            // get the zip file content
            final ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            final StringBuilder outFolderPath = new StringBuilder();
            final StringBuilder fileLogPath = new StringBuilder();
            ZipEntry zipEntry;
            while((zipEntry = zis.getNextEntry()) != null) {
                final String fileName = zipEntry.getName();
                final File newFile = new File(outFolderPath.append(outputFolder)
                        .append(File.separator).append(fileName).toString());
                LogUtil.setLogString(
                        fileLogPath.append("file unzip : ").append(newFile.getAbsoluteFile())
                                .toString(), true);
                // create all non exists folders
                // else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();
                final FileOutputStream fos = new FileOutputStream(newFile);

                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                fileLogPath.setLength(0);
                outFolderPath.setLength(0);
            }

            zis.closeEntry();
            zis.close();

            LogUtil.setLogString("Done", true);

        } catch (IOException ex) {
        	LOGGER.error("Error in unzip file", ex);
        }
    }

    /**
     * Copy file using file streams.
     * @param source the source
     * @param dest the dest
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void copyFileUsingFileStreams(final File source, final File dest)
            throws IOException {

        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            final byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }

        } catch (IOException ex) {
        	LOGGER.error("Error in copy file", ex);
        } finally {

            if (input != null) {
                input.close();
                output.close();
            }
        }
    }

    /**
     * Delete all files and folder in Folder. The root directory will not be deleted.
     * @param path the path
     */
    public static void deleteAllFilesInFolder(final String path) {

        LogUtil.setLogString(
                new StringBuilder("Delete Files and folders in directory :").append(path)
                        .toString(), true);
        try {
            FileUtils.cleanDirectory(new File(path));
        } catch (IOException e) {
        	LOGGER.error("Error in delete all files in folder", e);
        }
    }

    /**
     * Create a new Folder if it is not exists.
     * @param dirPath the dir path
     */
    public static void createDir(final String dirPath) {

        final File file = new File(dirPath);
        if (!file.exists()) {
            if (file.mkdir()) {
                LogUtil.setLogString("Directory Created", true);
            } else {
                LogUtil.setLogString("Failed to create directory!", true);
            }
        }
    }

    /**
     * Adds the file to zip.
     * @param path the path
     * @param srcFile the src file
     * @param zip the zip
     */
    private static void addFileToZip(final String path, final String srcFile,
            final ZipOutputStream zip) {

        try {
            final File folder = new File(srcFile);
            if (folder.isDirectory()) {
                addFolderToZip(path, srcFile, zip);
            } else {
                final byte[] buf = new byte[1024];
                int len;
                final FileInputStream inputStream = new FileInputStream(srcFile);
                zip.putNextEntry(new ZipEntry(new StringBuilder(path).append(SLASH)
                        .append(folder.getName()).toString()));
                do {
                    len = inputStream.read(buf);
                    zip.write(buf, 0, len);
                } while (len > 0);
                inputStream.close();
            }
        } catch (IOException e) {
        	LOGGER.error("Error in add flies to zip", e);
        }
    }

    /**
     * Adds the folder to zip.
     * @param path the path
     * @param srcFolder the src folder
     * @param zip the zip
     */
    private static void addFolderToZip(final String path, final String srcFolder,
            final ZipOutputStream zip) {

        final File folder = new File(srcFolder);

        final StringBuilder srcFolderPath = new StringBuilder();
        final StringBuilder pathString = new StringBuilder();

        for (final String fileName : folder.list()) {
            if (path.trim().isEmpty()) {
                addFileToZip(folder.getName(),
                        srcFolderPath.append(srcFolder).append(SLASH).append(fileName).toString(),
                        zip);
            } else {
                addFileToZip(pathString.append(path).append(SLASH).append(folder.getName())
                        .toString(), srcFolderPath.append(srcFolder).append(SLASH).append(fileName)
                        .toString(), zip);
            }
            srcFolderPath.setLength(0);
            pathString.setLength(0);
        }
    }
}