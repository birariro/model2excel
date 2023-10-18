

[![version](https://img.shields.io/badge/poi-5.2.3-00bfb3?style=flat)]()
[![version](https://img.shields.io/badge/poi_ooxml-5.2.3-00bfb3?style=flat)]()


## Model2Excel 
excel 의 row 에 작성될 데이터 모델을 넘긴다.

### @ExcelField
@ExcelField 의 value 가 row 의 타이틀로 들어간다 </br>
@ExcelField 가 있는 맴버 변수가 row 의 데이터로 들어간다 </br>
각 맴버 변수는 Getter 가 있어야한다

```java
@Getter
public class OrderModel {
	@ExcelField("주문 ID")
	private Long orderId;
	@ExcelField("상품 이름")
	private String productName; 
	@ExcelField("수량")
	private Long count;
}
```

| 주문 ID             | 상품 이름 | 수량  | 
|-------------------|-------|-----|
| 20221011ABC       | 사과    | 10  |
| 20221011ABC           | 양파    | 15  |
| 20221011ABC        | 신발    | 2   |


### @ExcelSum
```java
@ExcelSum(fields = {"수량"}, remainSpace = {
    @RemainSpace(fields = {"주문 ID"}, attach = "합계")
})
@Getter
public class OrderModel {
	@ExcelField("주문 ID")
	private Long orderId;
	@ExcelField("상품 이름")
	private String productName; 
	@ExcelField("수량")
	private Long count;
}
```
@ExcelSum 의 fields 로 지정된 타이틀의 데이터들은 합산 하여 가장 하단에 들어간다. </br>
fields 로 지정되지 않은 타이틀 하단은 빈공간 으로 남게 되며 </br>
@RemainSpace 의 fields 로 타이틀을 지정시 해당 타이틀의 빈 공간을 다른 텍스트로 채워 넣을 수 있다. </br>
@RemainSpace 의 fields 에 여러 타이틀을 지정시 해당 타이틀의 cell 을 병합하여 채워 넣는다. </br>

| 주문 ID      | 상품 이름 | 수량  | 
|------------|-------|-----|
| 20221011ABC | 사과    | 10  |
| 20221011ABC | 양파    | 15  |
| 20221011ABC | 신발    | 2   |
| 합계          |       | 27    |
