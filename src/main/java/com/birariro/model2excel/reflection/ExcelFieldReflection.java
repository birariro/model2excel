package com.birariro.model2excel.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.birariro.model2excel.annotation.ExcelField;
import com.birariro.model2excel.annotation.ExcelFieldGroup;

public class ExcelFieldReflection {



  /**
   * ExcelFieldGroup 을 사용하지 않았다면 종료한다.
   *
   * @param clazz
   * @return
   */
  public static String[] getExcelFieldTitleGroup(Class<?> clazz) {

    Field[] fields = clazz.getDeclaredFields();

    //ExcelFieldGroup 를 사용하지 않았다면 종료
    long count = Arrays.stream(fields)
        .filter(field -> field.isAnnotationPresent(ExcelFieldGroup.class))
        .count();

    if (count == 0) {
      return new String[0];
    }

    String[] groups = Arrays.stream(fields)
        .filter(
            field -> field.isAnnotationPresent(ExcelFieldGroup.class) || field.isAnnotationPresent(ExcelField.class))
        .map(field -> getExcelFieldGroupValue(field))
        .toArray(String[]::new);

    return groups;
  }
  private static String getExcelFieldGroupValue(Field field) {
    if (field.isAnnotationPresent(ExcelFieldGroup.class)) {
      return field.getAnnotation(ExcelFieldGroup.class).value();
    }
    //ExcelFieldGroup 이 없는 ExcelField 는 "" 이다
    return field.getAnnotation(ExcelField.class).value();
  }

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
