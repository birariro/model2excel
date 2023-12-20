package com.birariro.model2excel.support;

import java.util.List;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import com.birariro.model2excel.data.Title;


public class FieldMerge {

  public static void execute(SXSSFSheet sheet, List<Title[]> fields) {

    mergeFieldRow(sheet, fields);
    mergeFieldColumn(sheet, fields);
  }


  /**
   * 다른 필드의 타이틀 보다 적은 수의 타이틀을 가진 필드는 최대 타이틀의 크기 만큼 병합 한다.
   *
   * @param fields
   */
  private static void mergeFieldRow(SXSSFSheet sheet, List<Title[]> fields) {

    if (fields.size() <= 1) {
      return;
    }
    int length = fields.get(0).length;

    for (int column = 0; column < length; column++) {

      int startPos = -1;
      int endPos = -1;
      boolean isMerging = false;

      for (int row = 1; row < fields.size(); row++) {

        if (fields.get(row)[column].isEmpty()) {
          if (!isMerging) {
            startPos = (row - 1);
          }
          endPos = row;
          isMerging = true;

        } else if (isMerging) {
          sheet.addMergedRegion(new CellRangeAddress(startPos, endPos, column, column));
          isMerging = false;
        }

      }

      if (isMerging) {
        sheet.addMergedRegion(new CellRangeAddress(startPos, endPos, column, column));
      }
    }
  }

  /**
   * 같은 이름의 타이틀을 가진 필드들을 병합힌다.
   */
  private static void mergeFieldColumn(SXSSFSheet sheet, List<Title[]> fields) {

    for (int row = 0; row < fields.size(); row++) {
      Title[] field = fields.get(row);

      int startPos = -1;
      int endPos = -1;
      boolean isMerging = false;

      for (int column = 1; column < field.length; column++) {
        Title left = field[column - 1];
        Title right = field[column];
        if (left.hasText() && left.equals(right)) {

          if (!isMerging) {
            startPos = (column - 1);
          }
          endPos = column;
          isMerging = true;
        } else if (isMerging && !isCellRangeMerged(sheet, row, row, startPos, endPos)) {
          sheet.addMergedRegion(new CellRangeAddress(row, row, startPos, endPos));
          isMerging = false;
        }
      }
      if (isMerging) {
        sheet.addMergedRegion(new CellRangeAddress(row, row, startPos, endPos));
      }
    }
  }

  private static boolean isCellRangeMerged(SXSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
    for (CellRangeAddress mergedRegion : sheet.getMergedRegions()) {
      if (mergedRegion.isInRange(firstRow, firstCol) || mergedRegion.isInRange(lastRow, lastCol)) {
        return true;
      }
    }
    return false;
  }
}


