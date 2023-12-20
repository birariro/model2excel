package com.birariro.model2excel.data;


import java.util.Objects;

public class Field {

  private final String text;

  private Field(String text) {
    this.text = text;
  }

  public static Field of(String text) {
    return new Field(text);
  }

  public static Field ofEmpty() {
    return new Field(null);
  }

  public String getText() {
    return text;
  }

  public boolean isEmpty() {
    return this.text == null;
  }

  public boolean hasField() {
    return !isEmpty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Field field = (Field) o;

    return Objects.equals(text, field.text);
  }

}
