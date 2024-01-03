package com.birariro.model2excel.mask.impl;


import com.birariro.model2excel.annotation.ExcelField.MaskingType;

public class EmailMask implements Mask {

  @Override
  public boolean supports(MaskingType type) {
    return type == MaskingType.EMAIL;
  }


  /**
   * 이메일 맨 앞 두자리 노출, 이후 마스킹
   *
   * @param origin
   * @return
   */
  @Override
  public String run(String origin) {
    int length = origin.length();
    int atIndex = origin.indexOf('@');
    if (atIndex == -1) {
      return origin;
    }

    StringBuilder mask = new StringBuilder();

    // 앞 두글자 노출 (a@naver.com의 경우, a***@naver.com으로 보이게 처리)
    for (int i = 0; i < Math.min(2, length); i++) {
      mask.append(origin.charAt(i));
    }

    // @ 전까지 *로 마스킹
    for (int i = 2; i < atIndex; i++) {
      mask.append("*");
    }

    // @ 이후는 노출
    for (int i = atIndex; i < length; i++) {
      mask.append(origin.charAt(i));
    }

    return mask.toString();
  }

}
