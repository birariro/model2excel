package com.birariro.model2excel.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.birariro.model2excel.annotation.ExcelField;
import com.birariro.model2excel.annotation.ExcelSum;
import com.birariro.model2excel.data.Formula;


public class ExcelSumReflection {

  private static final String SUM_FORMULA_FORMAT = "sum(%s%d:%s%d)";
  private static final int START_ROW_NUM = 1;

  /**
   * included 에 포함된 field 를 sumLastRowNum 의 값까지 모두 더하는 formulas 를 반환한다.
   */
  public static List<Formula> fieldSumFormula(Class<?> clazz, int sumLastRowNum) {

    List<Formula> formulas = new ArrayList<>();
    if (!clazz.isAnnotationPresent(ExcelSum.class)) {
      return formulas;
    }
    /**
     * ExcelSum 에서 지정된 변수명을 가지고온다.
     */
    ExcelSum excelSumAnnotation = clazz.getAnnotation(ExcelSum.class);
    List<String> includedFields = Arrays.asList(excelSumAnnotation.fields());

    /**
     * ExcelField 의 변수들을 가지고온다
     */
    String[] fields = Arrays.stream(clazz.getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(ExcelField.class))
        .map(Field::getName).toArray(String[]::new);

    for (int i = 0; i < fields.length; i++) {

      /**
       * title 중 sum 으로 지정된 field 가 있다면 format 를 추가한다.
       */
      if (includedFields.contains(fields[i])) {
        char c = (char) (65 + i);
        String format = String.format(SUM_FORMULA_FORMAT, c, START_ROW_NUM, c, START_ROW_NUM + sumLastRowNum);
        formulas.add(Formula.of(format, true));
      } else {
        formulas.add(Formula.of("", false));
      }

    }

    return formulas;
  }

}
