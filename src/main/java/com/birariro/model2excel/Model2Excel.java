package com.birariro.model2excel;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.birariro.model2excel.reflection.AnnotationReflection;

public class Model2Excel {

	private String SHEET_NAME = "sheet";

	private final SXSSFWorkbook workbook;
	private final SXSSFSheet sheet;

	public Model2Excel() {

		workbook = new SXSSFWorkbook();
		sheet = workbook.createSheet(SHEET_NAME);
	}


	public SXSSFWorkbook execute(List<?> data) throws IOException {

		if (!data.isEmpty()) {

			AnnotationReflection annotationReflection = new AnnotationReflection();
			String[] title = annotationReflection.getExcelFieldValue(data.get(0));
			List<Object[]> rows = data
				.stream()
				.map(item -> annotationReflection.getExcelFieldData(item))
				.collect(Collectors.toList());

			writeExcel(title, rows);
		}
		return workbook;
	}

	private void writeExcel(String[] title, List<Object[]> rows) throws IOException {

		List<Object[]> dataList = new ArrayList<>();
		dataList.add(title);
		dataList.addAll(rows);

		writeExcel(dataList);
	}



	private void writeExcel(List<Object[]> dataList) throws IOException {

		for (int rowIndex = 0; rowIndex < dataList.size(); rowIndex++) {

			Row row = sheet.createRow(rowIndex);
			Object[] rowCell = dataList.get(rowIndex);

			for (int cellIndex = 0; cellIndex < rowCell.length; cellIndex++) {
				writeCell(workbook, row, cellIndex, rowCell[cellIndex]);
			}
		}

		cellSizeControl(sheet, dataList.get(0).length);
	}

	private void cellSizeControl(SXSSFSheet sheet, int cellSize) {

		sheet.trackAllColumnsForAutoSizing();

		for (int i = 0; i < cellSize; i++) {
			sheet.autoSizeColumn(i);
			int defaultColumnWidth = 256 * sheet.getDefaultColumnWidth();
			if (sheet.getColumnWidth(i) < defaultColumnWidth) {
				sheet.setColumnWidth(i, defaultColumnWidth);
			}
		}
	}

	private void writeCell(Workbook wb, Row row, int column, Object value) {

		HorizontalAlignment hAlign = HorizontalAlignment.CENTER;
		VerticalAlignment vAlign = VerticalAlignment.CENTER;
		IndexedColors headerColumnColor = IndexedColors.GREY_25_PERCENT;

		Cell cell = row.createCell(column);

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(hAlign);
		cellStyle.setVerticalAlignment(vAlign);

		if (row.getRowNum() == 0) {
			cellStyle.setFillForegroundColor(headerColumnColor.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}
		cell.setCellStyle(cellStyle);

		if (value == null) {
			return;
		}
		if (value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double) {
			cell.setCellValue(Double.parseDouble(value.toString()));
		} else if (value instanceof Boolean) {
			cell.setCellValue((boolean)value);
		} else if (value instanceof String || value instanceof RichTextString) {
			cell.setCellValue(value.toString());
		} else if (value instanceof LocalDateTime) {
			String strLocalDateTime = ((LocalDateTime)value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			cell.setCellValue(strLocalDateTime);
		} else if (value instanceof LocalDate) {
			cell.setCellValue(value.toString());
		} else if (value instanceof LocalTime) {
			String strLocalTime = ((LocalTime)value).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			cell.setCellValue(strLocalTime);
		} else if (value instanceof Date) {
			cell.setCellValue((Date)value);
		} else if (value instanceof Calendar) {
			cell.setCellValue((Calendar)value);
		} else {
			cell.setCellValue(value.toString());
		}
	}
}
