package com.birariro.model2excel.mask.impl;


import com.birariro.model2excel.annotation.ExcelField.MaskingType;

public class AgeMask implements Mask {

  @Override
  public boolean supports(MaskingType type) {
    return type == MaskingType.AGE;
  }

  /**
   * 마지막 자리만 마스킹
   *
   * @param origin
   * @return
   */
  @Override
  public String run(String origin) {
    int length = origin.length();

    StringBuilder mask = new StringBuilder();

    for (int i = 0; i < length - 1; i++) {
      mask.append(origin.charAt(i));
    }
    mask.append("*");
    return mask.toString();
  }

}
