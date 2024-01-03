package com.birariro.model2excel.mask.impl;


import com.birariro.model2excel.annotation.ExcelField.MaskingType;

public class NameMask implements Mask {

  @Override
  public boolean supports(MaskingType type) {
    return type == MaskingType.NAME;
  }

  /**
   * 가운데 자리 마스킹 <br> 2. 2글자 일경우 마지막자리 마스킹
   *
   * @param origin
   * @return
   */

  @Override
  public String run(String origin) {

    int length = origin.length();

    if (length <= 1) {
      return origin;
    }
    if (length == 2) {
      return origin.charAt(0) + "*";
    }

    StringBuilder mask = new StringBuilder();
    mask.append(origin.charAt(0));
    for (int i = 2; i < length; i++) {
      mask.append("*");
    }
    mask.append(origin.charAt(length - 1));
    return mask.toString();
  }

}
