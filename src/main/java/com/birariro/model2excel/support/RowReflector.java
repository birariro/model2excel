package com.birariro.model2excel.support;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.birariro.model2excel.annotation.ExcelField;
import com.birariro.model2excel.annotation.ExcelField.MaskingType;
import com.birariro.model2excel.utils.ExcelUtils;
import com.birariro.model2excel.utils.MaskUtils;

public class RowReflector {

  /**
   * row data 를 얻는다
   */
  public static Object[] getRows(Object model) {

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
      e.printStackTrace();
    }
    throw new IllegalArgumentException();
  }
}
