package com.ecofactor.qa.automation.util;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ecofactor.qa.automation.util.mail.ThermostatData;
import com.ecofactor.qa.automation.util.mail.UserData;

/**
 * The Class WriteTestDataExcel.
 */
public class WriteTestDataExcel {

	public Row row;
	public Row rowHeader;

	public void writeExcel(List<UserData> userDataList) {

		String environment = System.getProperty("env");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Good Users");
		XSSFSheet sheet1 = workbook.createSheet("Bad Users");

		// Green Color foreground
		XSSFCellStyle cellStyleGreen = workbook.createCellStyle();
		cellStyleGreen = addBorderToCells(cellStyleGreen);
		cellStyleGreen.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		XSSFColor greenFG = new XSSFColor((new Color(35, 154, 6)));
		cellStyleGreen.setFillForegroundColor(greenFG);

		// Red Color foreground
		XSSFCellStyle cellStyleRed = workbook.createCellStyle();
		cellStyleRed = addBorderToCells(cellStyleRed);
		cellStyleRed.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		XSSFColor redFG = new XSSFColor(Color.RED);
		cellStyleRed.setFillForegroundColor(redFG);

		// Header Bold
		XSSFCellStyle styleBold = workbook.createCellStyle();
		styleBold = addBorderToCells(styleBold);
		XSSFFont boldText = workbook.createFont();
		boldText.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		styleBold.setFont(boldText);

		// border
		XSSFCellStyle borderStyle = workbook.createCellStyle();
		borderStyle = addBorderToCells(borderStyle);

		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			rowHeader = workbook.getSheetAt(i).createRow((short) 0);
			Cell headercell0 = rowHeader.createCell(0);
			headercell0.setCellValue("S.NO");
			headercell0.setCellStyle(styleBold);

			Cell headercell1 = rowHeader.createCell(1);
			rowHeader.createCell(1).setCellValue("UserId");
			headercell1.setCellStyle(styleBold);

			Cell headercell2 = rowHeader.createCell(2);
			rowHeader.createCell(2).setCellValue("Login");
			headercell2.setCellStyle(styleBold);

			Cell headercell3 = rowHeader.createCell(3);
			rowHeader.createCell(3).setCellValue("Thermostat");
			headercell3.setCellStyle(styleBold);

			Cell headercell4 = rowHeader.createCell(4);
			rowHeader.createCell(4).setCellValue("Heat Mode");
			headercell4.setCellStyle(styleBold);

			Cell headercell5 = rowHeader.createCell(5);
			rowHeader.createCell(5).setCellValue("Cool Mode");
			headercell5.setCellStyle(styleBold);

			Cell headercell6 = rowHeader.createCell(6);
			rowHeader.createCell(6).setCellValue("Programs");
			headercell6.setCellStyle(styleBold);

			Cell headercell7 = rowHeader.createCell(7);
			rowHeader.createCell(7).setCellValue("Schedule");
			headercell7.setCellStyle(styleBold);

			Cell headercell8 = rowHeader.createCell(8);
			rowHeader.createCell(8).setCellValue("New Schedule UI");
			headercell8.setCellStyle(styleBold);

			Cell headercell9 = rowHeader.createCell(9);
			rowHeader.createCell(9).setCellValue("Algorithm");
			headercell9.setCellStyle(styleBold);

			Cell headercell10 = rowHeader.createCell(10);
			rowHeader.createCell(10).setCellValue("Active algorithm");
			headercell10.setCellStyle(styleBold);

			Cell headercell11 = rowHeader.createCell(11);
			headercell11.setCellValue("Active Algorithm's Next Phase Time");
			headercell11.setCellStyle(styleBold);
		}

		int rownum = 1;
		int index = 1;
		// Bad User index
		int badUserRownum = 1;
		int badUserindex = 1;
		for (UserData userdata : userDataList) {
			Cell cell;
			if (!userdata.isHasFailures()) {
				row = sheet.createRow(rownum++);
				cell = row.createCell(0);
				cell.setCellValue(index);
			} else {
				row = sheet1.createRow(badUserRownum++);
				cell = row.createCell(0);
				cell.setCellValue(badUserindex);
			}
			cell.setCellStyle(borderStyle);

			// user id :
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(userdata.getUserId());
			cell1.setCellStyle(borderStyle);

			// login success
			Cell cell2 = row.createCell(2);
			if (userdata.isLogin()) {
				cell2.setCellValue("SUCCESS");
				cell2.setCellStyle(cellStyleGreen);
			} else {
				cell2.setCellValue("FAILURE");
				cell2.setCellStyle(cellStyleRed);
			}

			// thermostat
			List<ThermostatData> thDetails = userdata.getThermostatDatas();
			ThermostatData thermostatData = null;

			if (thDetails != null && !thDetails.isEmpty()) {
				int noOfThermostats = 0;
				while (noOfThermostats < thDetails.size()) {
					thermostatData = thDetails.get(noOfThermostats);
					noOfThermostats++;

					Cell cell3 = row.createCell(3);
					if (thermostatData.isConnected()) {
						cell3.setCellValue(thermostatData.getThermostatId());
						cell3.setCellStyle(cellStyleGreen);
					} else {
						cell3.setCellStyle(cellStyleRed);
						cell3.setCellValue(thermostatData.getThermostatId());
					}

					Cell cell4 = row.createCell(4);
					if (thermostatData.getModes()[0] == "Heat") {
						cell4.setCellValue("Heat");
						cell4.setCellStyle(cellStyleGreen);
					} else {
						cell4.setCellStyle(cellStyleRed);
						cell4.setCellValue("-");
					}

					Cell cell5 = row.createCell(5);
					if (thermostatData.getModes()[1] == "Cool") {
						cell5.setCellValue("Cool");
						cell5.setCellStyle(cellStyleGreen);
					} else {
						cell5.setCellStyle(cellStyleRed);
						cell5.setCellValue("-");
					}

					Cell cell6 = row.createCell(6);
					if (userdata.isProgram()) {
						cell6.setCellValue("True");
						cell6.setCellStyle(cellStyleGreen);
					} else {
						cell6.setCellStyle(cellStyleRed);
						cell6.setCellValue("False");
					}

					Cell cell7 = row.createCell(7);
					if (userdata.isSchedule()) {
						cell7.setCellStyle(cellStyleGreen);
						cell7.setCellValue("True");
					} else {
						cell7.setCellStyle(cellStyleRed);
						cell7.setCellValue("False");
					}

					Cell cell8 = row.createCell(8);
					if (userdata.isNewScheduleUI()) {
						cell8.setCellStyle(cellStyleGreen);
						cell8.setCellValue("True");
					} else {
						cell8.setCellStyle(cellStyleRed);
						cell8.setCellValue("False");
					}

					String algoSub = "";
					for (String algoname : thermostatData
							.getSubsribedAlgorithms()) {
						algoSub += algoname + ",";
					}

					Cell cell9 = row.createCell(9);
					cell9.setCellValue(algoSub.trim());
					cell9.setCellStyle(cellStyleGreen);

					Cell cell10 = row.createCell(10);
					cell10.setCellValue(thermostatData.getActiveAlgorithm());
					cell10.setCellStyle(cellStyleGreen);

					Cell cell11 = row.createCell(11);
					cell11.setCellValue(thermostatData.getNextPhaseTime());
					cell11.setCellStyle(cellStyleGreen);
					if (thDetails.size() >= 1) {
						if (noOfThermostats == thDetails.size()) {
						} else {
							if (!userdata.isHasFailures()) {
								row = sheet.createRow(rownum++);
							} else {
								row = sheet1.createRow(badUserRownum++);
							}
						}
					}
				}
			} else {
				Cell cell3 = row.createCell(3);
				cell3.setCellValue("No thermostat");
				cell3.setCellStyle(cellStyleRed);

				Cell cell4 = row.createCell(4);
				cell4.setCellValue("-");
				cell4.setCellStyle(cellStyleRed);

				Cell cell5 = row.createCell(5);
				cell5.setCellValue("-");
				cell5.setCellStyle(cellStyleRed);

				Cell cell6 = row.createCell(6);
				cell6.setCellValue("-");
				cell6.setCellStyle(cellStyleRed);

				Cell cell7 = row.createCell(7);
				cell7.setCellValue("-");
				cell7.setCellStyle(cellStyleRed);

				Cell cell8 = row.createCell(8);
				cell8.setCellValue("-");
				cell8.setCellStyle(cellStyleRed);

				Cell cell9 = row.createCell(9);
				cell9.setCellValue("-");
				cell9.setCellStyle(cellStyleRed);

				Cell cell10 = row.createCell(10);
				cell10.setCellValue("-");
				cell10.setCellStyle(cellStyleRed);
				
				Cell cell11 = row.createCell(11);
				cell11.setCellValue("-");
				cell11.setCellStyle(cellStyleRed);
			}
			if (!userdata.isHasFailures()) {
				index++;
			} else {
				badUserindex++;
			}
		}
		try {
			// Write the Excel in file system
			FileOutputStream out = new FileOutputStream(new File("../consumer/target/" + environment + "_TestData.xlsx"));
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				workbook.getSheetAt(i).autoSizeColumn(1);
				workbook.getSheetAt(i).autoSizeColumn(2);
				workbook.getSheetAt(i).autoSizeColumn(3);
				workbook.getSheetAt(i).autoSizeColumn(4);
				workbook.getSheetAt(i).autoSizeColumn(5);
				workbook.getSheetAt(i).autoSizeColumn(6);
				workbook.getSheetAt(i).autoSizeColumn(7);
				workbook.getSheetAt(i).autoSizeColumn(8);
				workbook.getSheetAt(i).autoSizeColumn(9);
				workbook.getSheetAt(i).autoSizeColumn(10);
				workbook.getSheetAt(i).autoSizeColumn(11);
				workbook.getSheetAt(i).autoSizeColumn(12);
			}
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Adds the border to cells.
	 * @param borderStyle the border style
	 * @return the xSSF cell style
	 */
	private XSSFCellStyle addBorderToCells(XSSFCellStyle borderStyle) {
		borderStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		borderStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		borderStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		borderStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		return borderStyle;
	}

}
