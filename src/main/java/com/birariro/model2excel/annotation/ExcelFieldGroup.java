package com.birariro.model2excel.annotation;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelFieldGroup {

  String value() default "";

}
