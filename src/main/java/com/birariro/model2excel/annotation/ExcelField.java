package com.birariro.model2excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

  String value() default "";
  MaskingType mask() default MaskingType.NONE;
  /**
   * ExcelSum 어노테이션을 사용한다면 ExcelField::sum 은 동작하지 않는다.
   */
  boolean sum() default false;
  enum MaskingType {
    NONE,
    /* 이름 */
    NAME,
    /* 핸드폰 번호 */
    PHONE,
    /* 나이 */
    AGE,
    /* 계좌 번호 */
    BANK_ACCOUNT_NUMBER,
  }
}
