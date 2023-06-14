

[![version](https://img.shields.io/badge/poi-5.2.3-00bfb3?style=flat)]()
[![version](https://img.shields.io/badge/poi_ooxml-5.2.3-00bfb3?style=flat)]()


## Model2Excel 
excel 의 row 에 작성될 데이터 모델을 넘긴다.
@ExcelField 의 순서대로 sheet 의 row 타이틀이 된다.

```java
@Getter
public class OrderModel {
	@ExcelField("주문 ID")
	private Long orderId;
	@ExcelField("구매자 이름")
	private String BuyerName; 
	@ExcelField("수량")
	private Long count;
}
```

모델 안의 또다른 모델이 필요한 경우 
@ExcelFieldModel 를 통해 지정한다

```java
@Getter
public class OrderModel {
	@ExcelField("주문 ID")
	private Long orderId;
	@ExcelField("구매자 이름")
	private String BuyerName;
	@ExcelField("총 가격")
	private Long totalPrice;
	@ExcelFieldModel
	private List<OrderLineModel> orderLine;

	@Getter
	public class OrderLineModel {
		@ExcelField("상품 이름")
		private String productName;
		@ExcelField("수량")
		private Long count;
	}
}
```