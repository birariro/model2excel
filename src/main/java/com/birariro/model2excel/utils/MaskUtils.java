package com.birariro.model2excel.utils;

import com.birariro.model2excel.annotation.ExcelField.MaskingType;

public class MaskUtils {


  public static String convertMaskString(MaskingType type, String str) {

    if (type == MaskingType.NAME) {
      return getNameMask(str);
    }
    if (type == MaskingType.BANK_ACCOUNT_NUMBER) {
      return getBankAccountNumberMask(str);
    }
    if (type == MaskingType.PHONE) {
      return getPhoneMask(str);
    }
    if (type == MaskingType.AGE) {
      return getAgeMask(str);
    }

    return str;
  }

  /**
   * 국번 자리 4자리만 마스킹 010-****-1234
   *
   * @param phone
   * @return
   */
  private static String getPhoneMask(String phone) {

    int length = phone.length();

    StringBuilder mask = new StringBuilder();

    for (int i = (length - 1); i >= 0; i--) {
      if (mask.length() < 4 || mask.length() > 7) {
        mask.append(phone.charAt(i));
      } else {
        mask.append("*");
      }
    }

    return mask.reverse().toString();
  }

  /**
   * 앞 3자리와 뒤 3자리를 제외한 나머지 마스킹
   *
   * @param account
   * @return
   */
  private static String getBankAccountNumberMask(String account) {

    int length = account.length();
    StringBuilder mask = new StringBuilder();

    for (int i = 0; i < length; i++) {
      if (i < 3 || i > length - 3) {
        mask.append(account.charAt(i));
      } else {
        mask.append("*");
      }
    }

    return mask.toString();
  }

  /**
   * 마지막 자리만 마스킹
   *
   * @param age
   * @return
   */
  private static String getAgeMask(String age) {
    int length = age.length();

    StringBuilder mask = new StringBuilder();

    for (int i = 0; i < length - 1; i++) {
      mask.append(age.charAt(i));
    }
    mask.append("*");
    return mask.toString();
  }


  /**
   * 1. 가운데 자리 마스킹 <br> 2. 2글자 일경우 마지막자리 마스킹
   *
   * @param name
   * @return
   */
  private static String getNameMask(String name) {
    int length = name.length();

    if (length <= 1) {
      return name;
    }
    if (length == 2) {
      return name.charAt(0) + "*";
    }

    StringBuilder mask = new StringBuilder();
    mask.append(name.charAt(0));
    for (int i = 2; i < length; i++) {
      mask.append("*");
    }
    mask.append(name.charAt(length - 1));
    return mask.toString();
  }
}