package com.birariro.model2excel.manager;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.birariro.model2excel.data.CellType;
import com.birariro.model2excel.data.Formula;
import com.birariro.model2excel.reflection.ExcelSumReflection;

public class RowManager {

  private final static int ZERO = 0;

  private final SXSSFWorkbook workbook;
  private final SXSSFSheet sheet;
  private final CellManager cellManager;

  public RowManager(SXSSFWorkbook workbook, SXSSFSheet sheet) {
    this.workbook = workbook;
    this.sheet = sheet;
    this.cellManager = new CellManager(workbook);
  }
  public void writeTitleGroup(String[] group) {

    Row row = sheet.createRow(sheet.getLastRowNum() + 1);
    for (int column = 0; column < group.length; column++) {
      Cell cell = cellManager.getCell(CellType.TITLE, row, column);
      cellManager.writeCell(cell, group[column]);
    }
  }

  /**
   * group 을 사용했다면 title 와 중복값을 merge 한다
   *
   * @param group
   * @param title
   */
  public void mergeEmptyGroup(String[] group, String[] title) {

    if (group.length == 0) {
      return;
    }
    int groupRowNum = sheet.getLastRowNum() - 1;
    int titleRowNum = sheet.getLastRowNum();

    for (int column = 0; column < group.length; column++) {
      if (group[column].equals(title[column])) {
        sheet.addMergedRegion(new CellRangeAddress(groupRowNum, titleRowNum, column, column));
      }
    }
  }

  public void writeTitle(String[] title) {

    Row row = sheet.createRow(ZERO);
    for (int column = 0; column < title.length; column++) {
      Cell cell = cellManager.getCell(CellType.TITLE, row, column);
      cellManager.writeCell(cell, title[column]);
    }
  }

  public void writeBody(List<Object[]> rows) {
    //body
    for (int index = 0; index < rows.size(); index++) {

      Row row = sheet.createRow(sheet.getLastRowNum() + 1);
      Object[] rowData = rows.get(index);

      for (int column = 0; column < rowData.length; column++) {
        Cell cell = cellManager.getCell(CellType.COMMON, row, column);
        cellManager.writeCell(cell, rowData[column]);
      }
    }
  }

  public void writeFooter(Class<?> clazz, String[] title) {

    List<Formula> formulas = ExcelSumReflection.fieldSumFormula(clazz, title, sheet.getLastRowNum());

    if (formulas.isEmpty()) {
      return;
    }

    Row row = sheet.createRow(sheet.getLastRowNum() + 1);
    for (int index = 0; index < formulas.size(); index++) {
      Cell cell = cellManager.getCell(CellType.TITLE, row, index);
      Formula formula = formulas.get(index);

      cellManager.writeFormula(cell, formula);
    }
  }

}
