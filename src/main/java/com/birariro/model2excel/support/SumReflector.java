package com.birariro.model2excel.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.birariro.model2excel.annotation.ExcelField;
import com.birariro.model2excel.data.Formula;

/**
 * ExcelSum 어노테이션 의 우선순위가 높으며<br> ExcelSum 어노테이션을 사용하지 않는경우 ExcelField 의 sum 을 확인하여 동작한다.<br>
 */
public class SumReflector {

  private static final String SUM_FORMULA_FORMAT = "sum(%s%d:%s%d)";
  private static final int START_ROW_NUM = 1;

  /**
   * ExcelSum 사용시 동작 included 에 포함된 field 를 sumLastRowNum 의 값까지 모두 더하는 formulas 를 반환한다.
   */
  public static List<Formula> getFormula(Class<?> clazz, int sumLastRowNum) {

    return fieldFormula(clazz, sumLastRowNum);
  }

  /**
   * ExcelSum 어노테이션을 사용하지 않았을때 ExcelField 의 sum 을 사용한다.
   *
   * @param clazz
   * @param sumLastRowNum
   * @return
   */
  private static List<Formula> fieldFormula(Class<?> clazz, int sumLastRowNum) {

    List<Formula> formulas = new ArrayList<>();
    List<Field> collect = Arrays.stream(clazz.getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(ExcelField.class))
        .collect(Collectors.toList());

    for (int i = 0; i < collect.size(); i++) {

      if (collect.get(i).getAnnotation(ExcelField.class).sum()) {
        char c = (char) (65 + i);
        String format = String.format(SUM_FORMULA_FORMAT, c, START_ROW_NUM, c, START_ROW_NUM + sumLastRowNum);
        formulas.add(Formula.of(format, true));
      } else {
        formulas.add(Formula.of("", false));
      }

    }

    //하나라도 Formula 가 있다면 반환
    for (Formula formula : formulas) {
      if (formula.isFormulated()) {
        return formulas;
      }
    }

    return new ArrayList<>();
  }
}
