package com.birariro.model2excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelSum {


  /**
   * sum 을 할 ExcelField 의 value 를 지정한다 지정된 field 는 마지막 row 에 sum 된 결과를 얻는다
   */
  String[] fields() default {};
}
