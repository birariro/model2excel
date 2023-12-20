

[![version](https://img.shields.io/badge/poi-5.2.3-00bfb3?style=flat)]()
[![version](https://img.shields.io/badge/poi_ooxml-5.2.3-00bfb3?style=flat)]()


## Model2Excel 
excel 의 row 에 작성될 데이터 모델을 넘긴다.

### @ExcelField
@ExcelField 의 value 가 row 의 타이틀로 들어간다 </br>
@ExcelField 가 있는 맴버 변수가 row 의 데이터로 들어간다 </br>
각 맴버 변수는 Getter 가 있어야한다

> **Note**  
> mask 를 사용하여 데이터를 마스킹 할 수 있다.
> sum 을 사용하여 fields 로 지정된 맴버 변수의 데이터들을 합산 하여 가장 하단에 들어간다.


#### common example
```java
@Getter
public class OrderModel {
  @ExcelField("주문 ID")
  private Long orderId;

  @ExcelField("상품 이름")
  private String productName;

  @ExcelField("가격")
  private Long price;

  @ExcelField("수량")
  private Long count;
}
```

<p>
    <img src="https://github.com/birariro/model2excel/blob/master/image/ExcelField_Image.png?raw=true"/>
</p>


#### sum example
```java
@Getter
public class OrderModel {
  @ExcelField("주문 ID")
  private Long orderId;

  @ExcelField("상품 이름")
  private String productName;

  @ExcelField("가격")
  private Long price;

  @ExcelField(value = "수량", sum = true)
  private Long count;
}
```
<p>
    <img src="https://github.com/birariro/model2excel/blob/master/image/ExcelField_sum_Image.png?raw=true"/>
</p>


@ExcelSum 의 fields 로 지정된 맴버 변수의 데이터들은 합산 하여 가장 하단에 들어간다. </br>
fields 로 지정되지 않은 타이틀 하단은 빈공간 으로 남게 된다 </br>


#### array title value example


array 를 사용하여 여러 타이틀을 생성 할 수 있다.
```java

@Getter
public class OrderModel {
  @ExcelField(value = {"주문 ID"})
  private Long orderId;

  @ExcelField(value = {"상품 이름"})
  private String productName;
  
  @ExcelField(value = {"그룹","가격"})
  private Long price;
  
  @ExcelField(value = {"그룹","수량"}, sum = true)
  private Long count;
}
```
<p>
    <img src="https://github.com/birariro/model2excel/blob/master/image/ExcelField_sum_group_Image.png?raw=true"/>
</p>
