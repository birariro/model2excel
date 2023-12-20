package com.birariro.model2excel.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.birariro.model2excel.annotation.ExcelField;
import com.birariro.model2excel.data.Title;

public class FieldReflector {

  /**
   * row 들의 title 를 얻는다
   */
  public static List<Title[]> getFields(Class<?> clazz) {

    java.lang.reflect.Field[] fields = clazz.getDeclaredFields();

    int maxTitleSize = getMaxTitleSize(clazz);
    List<Title[]> titles = new ArrayList<>(maxTitleSize);
    for (int i = 0; i < maxTitleSize; i++) {

      int finalIndex = i;
      Title[] strings = Arrays.stream(fields)
          .filter(field -> field.isAnnotationPresent(ExcelField.class))
          .map(field -> field.getAnnotation(ExcelField.class))
          .map(ExcelField::value)
          .map(item -> {
            if (item.length > finalIndex) {
              return Title.of(item[finalIndex]);
            }
            return Title.ofEmpty();
          })
          .toArray(Title[]::new);

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
