package com.birariro.model2excel.mask.impl;


import com.birariro.model2excel.annotation.ExcelField.MaskingType;

public class PhoneMask implements Mask {

  @Override
  public boolean supports(MaskingType type) {
    return type == MaskingType.PHONE;
  }

  /**
   * 국번 자리 4자리만 마스킹 010-****-1234
   *
   * @param origin
   * @return
   */
  @Override
  public String run(String origin) {

    int length = origin.length();

    StringBuilder mask = new StringBuilder();

    for (int i = (length - 1); i >= 0; i--) {
      if (mask.length() < 4 || mask.length() > 7) {
        mask.append(origin.charAt(i));
      } else {
        mask.append("*");
      }
    }

    return mask.reverse().toString();
  }
}
