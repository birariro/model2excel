package com.birariro.model2excel.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.birariro.model2excel.annotation.ExcelField;
import com.birariro.model2excel.annotation.ExcelField.MaskingType;
import com.birariro.model2excel.annotation.ExcelFieldGroup;
import com.birariro.model2excel.utils.ExcelUtils;
import com.birariro.model2excel.utils.MaskUtils;

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

    String[] titles = Arrays.stream(fields)
        .filter(field -> field.isAnnotationPresent(ExcelField.class))
        .map(field -> field.getAnnotation(ExcelField.class))
        .map(field -> field.value())
        .toArray(String[]::new);

    return titles;
  }

  /**
   * row data 를 얻는다
   */
  public static Object[] getExcelFieldData(Object model) {

    return Arrays.stream(model.getClass().getDeclaredFields())
        .filter(field -> field.isAnnotationPresent(ExcelField.class))
        .map(field -> {
              MaskingType type = field.getAnnotation(ExcelField.class).mask();
              return findFieldData(model, field.getName(), type);
            }
        )
        .toArray(Object[]::new);
  }

  /**
   * 마스킹 설정이 되어있는 field는 마스킹하여 반환한다.
   *
   * @param model
   * @param field
   * @param masked
   * @return
   */
  private static Object findFieldData(Object model, String field, MaskingType masked) {
    if (!ExcelUtils.hasText(field)) {
      return "";
    }
    String getterMethodName = "get" + Character.toUpperCase(field.charAt(0)) + field.substring(1);
    Object o = getterMethodInvoke(model, getterMethodName);

    if (masked != MaskingType.NONE) {
      return MaskUtils.convertMaskString(masked, (String) o);
    }
    return o;
  }

  private static Object getterMethodInvoke(Object model, String methodName) {
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
