package com.gcs.ophwc.services.Controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gcs.ophwc.services.persistance.dao.entity.Divisions;
import com.gcs.ophwc.services.persistance.dao.entity.FloorWork;
import com.gcs.ophwc.services.persistance.dao.entity.Floors;
import com.gcs.ophwc.services.persistance.dao.entity.NatureOfWork;
import com.gcs.ophwc.services.persistance.dao.entity.ProjectFloorStatus;
import com.gcs.ophwc.services.persistance.dao.entity.Projects;
import com.gcs.ophwc.services.service.DivisionService;
import com.gcs.ophwc.services.service.FloorWiseStatusService;
import com.gcs.ophwc.services.service.NatureOfWorkService;
import com.gcs.ophwc.services.service.ProjectService;
import com.gcs.ophwc.services.util.ExcelFileUtil;
import com.gcs.ophwc.services.util.ProjectFloorsUtil;

@Controller
@RequestMapping("/projectFLoorStatus")
public class ProjectFloorStatusController {

	private static final Logger applicationLog = Logger.getLogger("applicationLogger");
	private static final Logger errorLog = Logger.getLogger("errorLoggger");

	@Autowired
	private SessionData sessionobj;

	@Autowired
	private FloorWiseStatusService floorService;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private DivisionService divisionService;
	
	@Autowired
	private NatureOfWorkService natureService;

	@RequestMapping(value = "/getAllFloors/{projectId}", method = RequestMethod.GET)
	public String getAllFloors(ModelMap model, @PathVariable Long projectId) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Projects proj = projectService.getProjectById(projectId);
			List<Floors> floorsList = floorService.getAllFloors(projectId);
			model.put("floorsList", floorsList);
			model.put("proj", proj);
			return "floorsList";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/floorStatusReport/{projectId}/{floorId}", method = RequestMethod.GET)
	public String floorStatusReport(ModelMap model, @PathVariable Long projectId, @PathVariable Long floorId) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			Projects proj = projectService.getProjectById(projectId);
			Floors floor = floorService.getFloorById(floorId);
			List<FloorWork> worksList = floorService.getAllFloorStatusByprojectAndFloor(projectId, floorId);
			model.put("worksList", worksList);
			model.put("proj", proj);
			model.put("floor", floor);
			return "floorStatusReport";
		} else {
			return "redirect:/";
		}
	}
	
	
	@RequestMapping(value = "/allProjectFloorStatusReport/{natureId}/{divId}", method = RequestMethod.GET)
	public String allProjectFloorStatusReport(ModelMap model, @PathVariable Long natureId,
			@PathVariable Long divId, HttpServletRequest req) {
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came Into allProjectFloorStatusReport");
			
			Divisions division=divisionService.getDivisionById(divId);
			NatureOfWork nature=natureService.getNatureOfWork(natureId);
			model.put("division", division);
			model.put("nature", nature);
			

			List<ProjectFloorsUtil> projectFloors = floorService.getAllProjectsWithFloorsUnderNatureAndDivision(divId, natureId);
			model.put("projectFloors", projectFloors);
			return "floorsListUnderNatureProjects";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/exportFloorStatusreport/{projId}/{floorId}", method = RequestMethod.GET)
	public String exportFloorStatusreport(HttpServletResponse response, @PathVariable Long projId,
			@PathVariable Long floorId) throws Exception {
		System.out.println("Out Side Session Check");
		if (sessionobj != null && sessionobj.getIsValidLogin()) {
			applicationLog.debug("Came into exportFloorStatusreport()");
			System.out.println("Came into exportFloorStatusreport()");
			HSSFWorkbook workBook = new HSSFWorkbook();
			HSSFSheet sheet = workBook.createSheet("Floor Status Report");

			Projects proj = projectService.getProjectById(projId);
			Floors floor = floorService.getFloorById(floorId);

			for (int columnIndex = 0; columnIndex <= 2; columnIndex++) {
				sheet.autoSizeColumn(columnIndex);
			}

			sheet.setColumnWidth(0, 10000);
			sheet.setColumnWidth(1, 10000);
			sheet.setColumnWidth(2, 10000);
			sheet.setHorizontallyCenter(true);
			sheet.setVerticallyCenter(true);
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			PrintSetup ps = sheet.getPrintSetup();

			sheet.setAutobreaks(true);

			ps.setFitHeight((short) 1);
			ps.setFitWidth((short) 1);

			Font font = workBook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("Calibri");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBold(true);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setWrapText(true);
			style.setFont(font);

			HSSFRow row0 = sheet.createRow(0);
			HSSFCell ch0 = row0.createCell(0);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());

			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
			ch0.setCellStyle(style);

			HSSFRow row1 = sheet.createRow(1);
			HSSFCell ch1 = row1.createCell(0);
			HSSFCellStyle style1 = workBook.createCellStyle();
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			font.setFontHeightInPoints((short) 11);
			style1.setFont(font);
			ch1.setCellValue(
					"OPHWC FLOOR STATUS REPORT OF " + floor.getFloorName() + " FOR " + proj.getProjectDefination());
			ch1.setCellStyle(style1);
			sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 2));

			style1.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			// style1.setFillForegroundColor(IndexedColors.valueOf("DarkOliveGreen").index);
			HSSFPalette palette = workBook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 170, (byte) 220, (byte) 170);
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 211, (byte) 207, (byte) 207);

			style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCellStyle style2 = workBook.createCellStyle();
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setWrapText(true);
			style2.setFont(font);
			HSSFRow headingRow = sheet.createRow(3);
			font.setFontHeightInPoints((short) 8);
			style2.setFont(font);
			style2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			HSSFCell ch3 = headingRow.createCell((short) 0);
			ch3.setCellValue("S.No");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 1);
			ch3.setCellValue("Floor Work");
			ch3.setCellStyle(style2);
			ch3 = headingRow.createCell((short) 2);
			ch3.setCellValue("Work Status");
			ch3.setCellStyle(style2);

			List<FloorWork> worksList = floorService.getAllFloorStatusByprojectAndFloor(projId, floorId);
			int rowNo = 4;
			for (FloorWork floorWork : worksList) {
				HSSFRow row = sheet.createRow(rowNo);
				row.createCell((short) 0).setCellValue(rowNo - 3);
				row.createCell((short) 1).setCellValue(floorWork.getWorkType());
				if (floorWork.getProjectStatus() == null)
					row.createCell((short) 2).setCellValue("Not Started");
				else
					row.createCell((short) 2).setCellValue(floorWork.getProjectStatus().getStatus());
				rowNo++;
			}

			String file = "ophwc_floor_status_report.xlsx";
			try {
				FileOutputStream fos = new FileOutputStream(file);
				workBook.write(fos);
				ExcelFileUtil.downloadFile(file, response);
				fos.flush();
				fos.close();
				System.out.println("Returning Jsp Page");
				applicationLog.debug("Returning Jsp Page");
				return "redirect:/projectFLoorStatus/floorStatusReport/" + projId + "/" + floorId;

			} catch (FileNotFoundException e) {
				errorLog.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				errorLog.error(e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				errorLog.error(e.getMessage());
				e.printStackTrace();
			}
		} else
			return "redirect:/";
		return "redirect:/projectFLoorStatus/floorStatusReport/" + projId + "/" + floorId;
	}

	public static String cellToString(XSSFCell cell) {

		int type;
		Object result = null;
		type = cell.getCellType();

		switch (type) {

		case XSSFCell.CELL_TYPE_NUMERIC:
			result = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();

			break;
		case XSSFCell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			result = cell.getBooleanCellValue();
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			result = "";
			break;
		case XSSFCell.CELL_TYPE_FORMULA:
			result = cell.getCellFormula();
		}

		return result.toString();
	}
}
