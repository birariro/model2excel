package com.birariro.model2excel.reflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.birariro.model2excel.annotation.ExcelSum;
import com.birariro.model2excel.annotation.ExcelSum.RemainSpace;
import com.birariro.model2excel.data.Formula;


public class ExcelSumReflection {

  private static final String SUM_FORMULA_FORMAT = "sum(%s%d:%s%d)";

  /**
   * included 에 포함된 field 를 sumLastRowNum 의 값까지 모두 더하는 formulas 를 반환한다.
   */
  public static List<Formula> fieldSumFormula(Class<?> clazz, String[] titles, int sumLastRowNum) {

    List<Formula> formulas = new ArrayList<>();
    if (!clazz.isAnnotationPresent(ExcelSum.class)) {
      return formulas;
    }
    ExcelSum excelSumAnnotation = clazz.getAnnotation(ExcelSum.class);
    List<String> includedFields = Arrays.asList(excelSumAnnotation.fields());

    for (int i = 0; i < titles.length; i++) {

      if (includedFields.contains(titles[i])) {
        char c = (char) (65 + i);
        String format = String.format(SUM_FORMULA_FORMAT, c, 1, c, sumLastRowNum);
        formulas.add(Formula.of(format, true));
      } else {
        formulas.add(Formula.of(getAttach(excelSumAnnotation, titles[i])));
      }

    }

    return formulas;
  }

  private static String getAttach(ExcelSum excelSumAnnotation, String title) {

    return Arrays.stream(excelSumAnnotation.remainSpace())
        .filter(remainSpace -> Arrays.asList(remainSpace.fields()).contains(title))
        .map(RemainSpace::attach)
        .findFirst()
        .orElse("");
  }

}
