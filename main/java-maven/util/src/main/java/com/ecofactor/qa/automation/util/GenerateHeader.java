package com.ecofactor.qa.automation.util;

import java.io.*;

import org.testng.annotations.*;

/**
 * The Class GenerateHeader.
 * 
 * @author $Author:$
 * @version $Rev:$ $Date:$
 */
public class GenerateHeader {

    @Test
    public void generateSummaryHeaderTest() {
        writeHeader();
    }

    /**
     * Write header.
     */
    public static void writeHeader() {

        DriverConfig.setLogString("Create header File", true);
        String artifactId = SystemUtil.getProperty("artifactId");
        File headerDir = new File("../" + artifactId );
        DriverConfig.setLogString("Header File path :" + headerDir.getAbsolutePath(), true);
        FileUtil.createDirectory(headerDir.getAbsolutePath());
        String path = headerDir.getAbsolutePath();
        PrintWriter headerWriter = createWriter(path);
        String content = writeDocumentHead();
        content += writeBody(null);
        content += closeBody();
        headerWriter.println(content.toString());
        headerWriter.close();
    }

    /**
     * Creates the writer.
     * 
     * @param outdir the outdir
     * @return the prints the writer
     */
    private static PrintWriter createWriter(String outdir) {
        try {
            new File(outdir).mkdirs();
            return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir, "summaryHeader.html"))));
        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    /**
     * Write document head.
     * 
     * @return the string
     */
    private static String writeDocumentHead() {
        StringBuilder content = new StringBuilder();
        content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        content.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        content.append("<head>");
        content.append("<title>Header</title>");
        content.append("</head><body>");
        return content.toString();
    }

    /**
     * Write body.
     * 
     * @return the string
     */
    public static String writeBody(String title) {

        StringBuilder content = new StringBuilder();
       
        String moduleName = title != null && !title.isEmpty() && !title.equalsIgnoreCase("-") ? title : getModuleName();
        DriverConfig.setLogString("Generate Header for moduleName : " + moduleName, true);
        if (moduleName != null && !moduleName.isEmpty()) {
            content.append("<table width=\"100%\" style=\"border:1px solid #009;border-bottom-style:none;border-bottom:none;border-collapse:collapse;\" border=\"1px\">");
            content.append("<tr>");
            content.append("<th style=\"height:40px;background-color:#a8a89d\">" + moduleName.toUpperCase() + "</th>");
            content.append("</tr>");
            content.append("</table>");
        }
        return content.toString();
    }

    private static String getModuleName() {
        // Convert artifact names to current portal version
        String moduleName = System.getProperty("artifactId");
        if (moduleName != null) {
            switch (moduleName) {
            case "insite":
                moduleName = "INSITE";
                break;

            case "consumer":
                moduleName = "CONSUMER";
                break;

            case "newapp":
                moduleName = "NEWAPP";
                break;

            default:
                break;
            }
        }
        return moduleName == null || moduleName.contains("${") ? "-" : moduleName;
    }

    /**
     * Close body.
     * 
     * @return the string
     */
    private static String closeBody() {
        StringBuilder content = new StringBuilder();
        content.append("</body>");
        content.append("</html>");
        return content.toString();
    }

    public static void main(String[] args) {
        writeHeader();
    }
}
