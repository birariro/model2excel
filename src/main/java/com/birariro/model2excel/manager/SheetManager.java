package com.birariro.model2excel.manager;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.birariro.model2excel.reflection.ExcelFieldReflection;

public class SheetManager {

  private final SXSSFWorkbook workbook;
  private final SXSSFSheet sheet;
  private final RowManager rowManager;

  public SheetManager(SXSSFWorkbook workbook, String sheetName) {
    this.workbook = workbook;
    this.sheet = workbook.createSheet(sheetName);
    this.rowManager = new RowManager(this.workbook, this.sheet);
  }


  public SXSSFWorkbook writeExcel(
      List<?> data) {

    Object firstData = data.stream()
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException());

    Class<?> clazz = firstData.getClass();
    String[] groups = ExcelFieldReflection.getExcelFieldTitleGroup(clazz);
    String[] title = ExcelFieldReflection.getExcelFieldTitle(clazz);

    List<Object[]> rows = data.stream()
        .map(item -> ExcelFieldReflection.getExcelFieldData(item))
        .collect(Collectors.toList());

    rowManager.writeTitleGroup(groups);
    rowManager.writeTitle(title);
    rowManager.mergeEmptyGroup(groups, title);
    rowManager.writeBody(rows);
    rowManager.writeFooter(clazz);

    cellSizeControl(sheet, title.length);
    return this.workbook;
  }


  private void cellSizeControl(SXSSFSheet sheet, int cellSize) {

    // auto size 적용을 위한 설정
    sheet.trackAllColumnsForAutoSizing();

    // 모든 열 크기 자동으로 맞춤.
    for (int i = 0; i < cellSize; i++) {
      sheet.autoSizeColumn(i);
      // 만약 자동으로 맞춘 열크기가 default column 크기보다 작을 때 default 로 변경처리
      int defaultColumnWidth = 256 * sheet.getDefaultColumnWidth();
      if (sheet.getColumnWidth(i) < defaultColumnWidth) {
        sheet.setColumnWidth(i, defaultColumnWidth);
      }
    }
  }

}
