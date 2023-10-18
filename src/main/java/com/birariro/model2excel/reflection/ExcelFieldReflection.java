package com.birariro.model2excel.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.birariro.model2excel.annotation.ExcelField;

public class ExcelFieldReflection {


  /**
   * row 들의 title 를 얻는다
   */
  public static String[] getExcelFieldTitle(Class<?> clazz) {

    Field[] fields = clazz.getDeclaredFields();

    String[] result = Arrays.stream(fields)
        .filter(field -> field.isAnnotationPresent(ExcelField.class))
        .map(field -> field.getAnnotation(ExcelField.class))
        .map(field -> field.value())
        .toArray(String[]::new);

    return result;
  }

  /**
   * row data 를 얻는다
   */
  public static Object[] getExcelFieldData(Object model) {

    return Arrays.stream(model.getClass().getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(ExcelField.class))
        .map(Field::getName)
        .filter(field -> field != null)
        .filter(field -> !field.isEmpty())
        .map(field -> Character.toUpperCase(field.charAt(0)) + field.substring(1))
        .map(field -> "get" + field)
        .map(methodName -> methodInvoke(model, methodName))
        .toArray(Object[]::new);
  }

  private static Object methodInvoke(Object model, String methodName) {
    try {
      Method method = model.getClass().getMethod(methodName);
      return method.invoke(model);
    } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
      // getter 메서드가 존재하지 않거나 호출할 수 없는 경우
      e.printStackTrace();
    }
    throw new IllegalArgumentException();
  }
}
