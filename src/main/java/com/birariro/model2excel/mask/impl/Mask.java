package com.birariro.model2excel.mask.impl;


import com.birariro.model2excel.annotation.ExcelField.MaskingType;

public interface Mask {

  boolean supports(final MaskingType type);

  String run(final String origin);

}
