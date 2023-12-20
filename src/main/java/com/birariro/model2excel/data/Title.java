package com.birariro.model2excel.data;


import java.util.Objects;

public class Title {

  private final String text;

  private Title(String text) {
    this.text = text;
  }

  public static Title of(String text) {
    return new Title(text);
  }

  public static Title ofEmpty() {
    return new Title(null);
  }

  public String getText() {
    return text;
  }

  public boolean isEmpty() {
    return this.text == null;
  }

  public boolean hasText() {
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

    Title field = (Title) o;

    return Objects.equals(text, field.text);
  }

}
