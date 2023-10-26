package com.birariro.model2excel.manager;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
