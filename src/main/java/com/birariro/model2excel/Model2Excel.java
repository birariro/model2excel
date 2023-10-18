package com.birariro.model2excel;

import java.util.List;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.birariro.model2excel.manager.SheetManager;

public class Model2Excel {

  private final SXSSFWorkbook workbook;

  public Model2Excel() {
    this.workbook = new SXSSFWorkbook();
  }

  /**
   * 매번 새로운 Sheet 를 생성하여 data 를 주입한다.
   *
   * @param sheetName
   * @param data
   * @return
   */
  public SXSSFWorkbook writeSheet(String sheetName, List<?> data) {
    SheetManager sheetManager = new SheetManager(workbook, sheetName);
    sheetManager.writeExcel(data);
    return workbook;
  }

  public SXSSFWorkbook writeEmptyExcel() {
    return workbook;
  }
}
