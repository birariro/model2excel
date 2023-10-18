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

  /**
   * includes 로 지정 받지 못한 field 의 row 는 빈공간으로 남게된다. 이 공간에 text 를 채워 넣을 수 있다 <br/> 연속된 field 는 병합 된다.
   */
  RemainSpace[] remainSpace() default {};

  @interface RemainSpace {

    String[] fields() default {};

    String attach() default "";
  }
}
