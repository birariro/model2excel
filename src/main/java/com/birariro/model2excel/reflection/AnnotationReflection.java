package com.birariro.model2excel.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.birariro.model2excel.annotation.ExcelField;
import com.birariro.model2excel.annotation.ExcelFieldModel;

public class AnnotationReflection {
	public String[] getExcelFieldValue(Object object) {
		List<String> result = getExcelFieldValue(object, new ArrayList<>());
		return result.toArray(new String[result.size()]);
	}

	private List<String> getExcelFieldValue(Object object, List<String> result) {

		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(ExcelField.class)) {
				ExcelField excelField = field.getAnnotation(ExcelField.class);
				String value = excelField.value();
				result.add(value);
			}

			if (field.isAnnotationPresent(ExcelFieldModel.class)) {

				String name = field.getName();
				String method = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
				if (field.getType().equals(List.class)) {
					List filedModel = (List)methodInvoke(object, method);
					if (filedModel.isEmpty())
						continue;
					getExcelFieldValue(filedModel.get(0), result);
				} else {
					Object o = methodInvoke(object, method);
					getExcelFieldValue(o, result);
				}
			}
		}

		return result;
	}

	public Object[] getExcelFieldData(Object model) {
		List<Object> result = getExcelFieldData(model, new ArrayList<>());
		return result.toArray(new Object[result.size()]);
	}

	private List<Object> getExcelFieldData(Object model, List result) {

		Field[] fields = model.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(ExcelField.class)) {
				String name = field.getName();
				String method = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
				result.add(methodInvoke(model, method));
			}

			if (field.isAnnotationPresent(ExcelFieldModel.class)) {

				String name = field.getName();
				String method = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
				if (field.getType().equals(List.class)) {
					List filedModel = (List)methodInvoke(model, method);
					if (filedModel.isEmpty())
						continue;
					getExcelFieldData(filedModel.get(0), result);
				} else {
					Object o = methodInvoke(model, method);
					getExcelFieldData(o, result);
				}
			}
		}

		return result;

	}

	private Object methodInvoke(Object model, String methodName) {
		try {
			Method method = model.getClass().getMethod(methodName);
			return method.invoke(model);
		} catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException();
	}

}
