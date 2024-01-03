package com.birariro.model2excel.mask;

import static java.util.Arrays.asList;

import java.util.List;

import com.birariro.model2excel.annotation.ExcelField.MaskingType;
import com.birariro.model2excel.mask.impl.AgeMask;
import com.birariro.model2excel.mask.impl.BankAccountNumberMask;
import com.birariro.model2excel.mask.impl.EmailMask;
import com.birariro.model2excel.mask.impl.Mask;
import com.birariro.model2excel.mask.impl.NameMask;
import com.birariro.model2excel.mask.impl.PhoneMask;

public class Masker {

  static List<Mask> masks = asList(
      new NameMask(),
      new AgeMask(),
      new EmailMask(),
      new BankAccountNumberMask(),
      new PhoneMask()
  );

  public static String mask(MaskingType type, String origin) {

    return masks.stream()
        .filter(mask -> mask.supports(type))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new)
        .run(origin);
  }
}