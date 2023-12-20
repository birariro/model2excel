package com.birariro.model2excel.manager;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.birariro.model2excel.data.CellType;
import com.birariro.model2excel.data.Title;
import com.birariro.model2excel.data.Formula;
import com.birariro.model2excel.support.FieldMerge;
import com.birariro.model2excel.support.SumReflector;

public class RowManager {

  private final SXSSFSheet sheet;
  private final CellManager cellManager;

  public RowManager(SXSSFWorkbook workbook, SXSSFSheet sheet) {
    this.sheet = sheet;
    this.cellManager = new CellManager(workbook);
  }

  public void writeTitle(List<Title[]> fields) {

    for (Title[] title : fields) {
      Row row = sheet.createRow(sheet.getLastRowNum() + 1);
      for (int column = 0; column < title.length; column++) {
        Cell cell = cellManager.getCell(CellType.TITLE, row, column);
        cellManager.writeCell(cell, title[column].getText());
      }
    }
    FieldMerge.execute(sheet, fields);
  }

  public void writeBody(List<Object[]> rows) {

    for (int index = 0; index < rows.size(); index++) {
      Row row = sheet.createRow(sheet.getLastRowNum() + 1);
      Object[] rowData = rows.get(index);

      for (int column = 0; column < rowData.length; column++) {
        Cell cell = cellManager.getCell(CellType.COMMON, row, column);
        cellManager.writeCell(cell, rowData[column]);
      }
    }
  }

  public void writeFooter(Class<?> clazz) {

    List<Formula> formulas = SumReflector.getFormula(clazz, sheet.getLastRowNum());
    if (formulas.isEmpty()) {
      return;
    }

    Row row = sheet.createRow(sheet.getLastRowNum() + 2);
    for (int index = 0; index < formulas.size(); index++) {
      Cell cell = cellManager.getCell(CellType.FOOTER, row, index);
      Formula formula = formulas.get(index);

      cellManager.writeFormula(cell, formula);
    }
  }

}