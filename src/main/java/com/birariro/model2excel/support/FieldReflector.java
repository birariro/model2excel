package com.birariro.model2excel.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.birariro.model2excel.annotation.ExcelField;
import com.birariro.model2excel.data.Field;

public class FieldReflector {

  /**
   * row 들의 title 를 얻는다
   */
  public static List<Field[]> getFields(Class<?> clazz) {

    java.lang.reflect.Field[] fields = clazz.getDeclaredFields();

    int maxTitleSize = getMaxTitleSize(clazz);
    List<Field[]> titles = new ArrayList<>(maxTitleSize);
    for (int i = 0; i < maxTitleSize; i++) {

      int finalIndex = i;
      Field[] strings = Arrays.stream(fields)
          .filter(field -> field.isAnnotationPresent(ExcelField.class))
          .map(field -> field.getAnnotation(ExcelField.class))
          .map(ExcelField::value)
          .map(item -> {
            if (item.length > finalIndex) {
              return Field.of(item[finalIndex]);
            }
            return Field.ofEmpty();
          })
          .toArray(Field[]::new);

      titles.add(strings);
    }

    return titles;
  }

  private static int getMaxTitleSize(Class<?> clazz) {
    java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
    Integer integer = Arrays.stream(fields)
        .filter(field -> field.isAnnotationPresent(ExcelField.class))
        .map(field -> field.getAnnotation(ExcelField.class))
        .map(ExcelField::value)
        .map(value -> value.length)
        .max(Comparator.comparing(x -> x))
        .orElseGet(() -> 0);

    return integer;
  }

}
