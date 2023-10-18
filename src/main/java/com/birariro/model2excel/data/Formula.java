package com.birariro.model2excel.data;

/**
 * CellData 는 일단 text 데이터 거나 formulas 로 동작하는 text 일 수 있다.
 */
public class Formula {

  private final String text;
  private final boolean formulated;

  private Formula(String text, boolean formulated) {
    this.text = text;
    this.formulated = formulated;
  }

  public static Formula of(String text) {
    return new Formula(text, false);
  }

  public static Formula of(String text, boolean formulated) {
    return new Formula(text, formulated);
  }

  public String getText() {
    return text;
  }

  public boolean isFormulated() {
    return formulated;
  }
}
