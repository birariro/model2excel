package com.birariro.model2excel.manager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.birariro.model2excel.data.CellType;
import com.birariro.model2excel.data.Formula;
import com.birariro.model2excel.support.StringChecker;

public class CellManager {

  private final SXSSFWorkbook workbook;

  public CellManager(SXSSFWorkbook workbook) {
    this.workbook = workbook;
  }

  void writeFormula(Cell cell, Formula formula) {

    if (formula == null) {
      return;
    }
    if (!formula.isFormulated()) {
      writeCell(cell, formula.getText());
      return;
    }

    String text = formula.getText();
    if (!StringChecker.hasText(text)) {
      return;
    }
    cell.setCellFormula(text);
  }

  void writeCell(Cell cell, Object value) {

    if (value == null) {
      return;
    }
    if (value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double) {
      cell.setCellValue(Double.parseDouble(value.toString()));
    } else if (value instanceof Boolean) {
      cell.setCellValue((boolean) value);
    } else if (value instanceof String || value instanceof RichTextString) {
      cell.setCellValue(value.toString());
    } else if (value instanceof LocalDateTime) {
      String strLocalDateTime = ((LocalDateTime) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      cell.setCellValue(strLocalDateTime);
    } else if (value instanceof LocalDate) {
      cell.setCellValue(value.toString());
    } else if (value instanceof LocalTime) {
      String strLocalTime = ((LocalTime) value).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
      cell.setCellValue(strLocalTime);
    } else if (value instanceof Date) {
      cell.setCellValue((Date) value);
    } else if (value instanceof Calendar) {
      cell.setCellValue((Calendar) value);
    } else {
      cell.setCellValue(value.toString());
    }
  }


  Cell getCell(CellType type, Row row, int column) {
    HorizontalAlignment hAlign = HorizontalAlignment.CENTER;
    VerticalAlignment vAlign = VerticalAlignment.CENTER;

    Cell cell = row.createCell(column);

    CellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setAlignment(hAlign);
    cellStyle.setVerticalAlignment(vAlign);
    cellStyle.setWrapText(true);

    // header title color
    if (type == CellType.TITLE) {
      setTitleStyle(cellStyle);
    }
    else if (type == CellType.FOOTER) {
      setFooterStyle(cellStyle);
    }
    cell.setCellStyle(cellStyle);

    return cell;
  }

  private void setTitleStyle(CellStyle cellStyle) {
    IndexedColors headerColumnColor = IndexedColors.GREY_25_PERCENT;
    cellStyle.setFillForegroundColor(headerColumnColor.getIndex());
    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    cellStyle.setBorderBottom(BorderStyle.THIN);
    cellStyle.setBorderLeft(BorderStyle.THIN);
    cellStyle.setBorderRight(BorderStyle.THIN);
  }
  private void setFooterStyle(CellStyle cellStyle) {
    IndexedColors headerColumnColor = IndexedColors.GREY_25_PERCENT;
    cellStyle.setFillForegroundColor(headerColumnColor.getIndex());
    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
  }
}
