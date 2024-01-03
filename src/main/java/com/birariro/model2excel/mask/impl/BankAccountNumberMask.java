package com.birariro.model2excel.mask.impl;


import com.birariro.model2excel.annotation.ExcelField.MaskingType;

public class BankAccountNumberMask implements Mask {

  @Override
  public boolean supports(MaskingType type) {
    return type == MaskingType.BANK_ACCOUNT_NUMBER;
  }

  /**
   * 앞 3자리와 뒤 3자리를 제외한 나머지 마스킹
   *
   * @param origin
   * @return
   */
  @Override
  public String run(String origin) {

    int padding = 3;
    int length = origin.length();

    StringBuilder mask = new StringBuilder();

    for (int i = 0; i < length; i++) {
      if (i < padding || i > length - padding) {
        mask.append(origin.charAt(i));
      } else {
        mask.append("*");
      }
    }

    return mask.toString();
  }
}
