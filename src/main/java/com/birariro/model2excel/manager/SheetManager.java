package com.birariro.model2excel.manager;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.birariro.model2excel.data.Field;
import com.birariro.model2excel.support.FieldReflector;
import com.birariro.model2excel.support.RowReflector;


public class SheetManager {

  private final SXSSFWorkbook workbook;
  private final SXSSFSheet sheet;
  private final RowManager rowManager;

  public SheetManager(SXSSFWorkbook workbook, String sheetName) {
    this.workbook = workbook;
    this.sheet = workbook.createSheet(sheetName);
    this.rowManager = new RowManager(this.workbook, this.sheet);
  }

  public SXSSFWorkbook writeExcel(List<?> data) {


    Class<?> clazz = data.stream()
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException())
        .getClass();

    List<Field[]> field = FieldReflector.getFields(clazz);
    List<Object[]> rows = data.stream()
        .map(item -> RowReflector.getRows(item))
        .collect(Collectors.toList());

    rowManager.writeTitle(field);
    rowManager.writeBody(rows);
    rowManager.writeFooter(clazz);

    cellSize(sheet, field.get(0).length);
    return this.workbook;
  }

  private static void cellSize(SXSSFSheet sheet, int fieldSize) {

    sheet.trackAllColumnsForAutoSizing();

    for (int i = 0; i < fieldSize; i++) {

      sheet.autoSizeColumn(i);

      int defaultColumnWidth = 512 * sheet.getDefaultColumnWidth();
      if (sheet.getColumnWidth(i) < defaultColumnWidth) {

        sheet.setColumnWidth(i, defaultColumnWidth);
      }
    }
  }

}

