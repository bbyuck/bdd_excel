package bbyuck.dto;

public class NaverColumnDto {
	// 상품주문번호
	private String productOrderNum;
	// 주문번호
	private String orderNum;
	// 배송방법
	private String shippingMethod;
	// 택배사
	private String courier;
	// 송장번호
	private String waybillNum;
	// 발송일
	private String shippingDate;
	// 수취인명
	private String receiverName;
	// 상품명
	private String productName;
	// 옵션정보
	private String optionInfo;
	// 수량
	private Integer quantity;
	// 배송비 형태
	private String shippingCostForm;
	// 수취인연락처1
	private String receiverPhone1;
	// 배송지
	private String receiverAddress;
	// 배송메세지
	private String deliveryMessage;
	// 출고지
	private String shipFrom;
	// 결제수단
	private String methodOfPayment;
	// 수수료 과금구분
	private String feeChargingCategory;
	// 수수료결제방식
	private String feePaymentMethod;
	// 결제수수료
	private Integer paymentFee;
	// 매출연동 수수료
	private Integer salesLinkageFee;
	// 정산예정금액
	private Integer estimatedTotalAmount;
	// 유입경로
	private String channel;
	// 구매자 주민등록번호
	private String buyerSSN;
	// 개인통관고유부호
	private String pccc;
	// 주문일시
	private String orderDateTime;
	// 1년 주문건수
	private Integer numOfOrdersPerYear;
	// 구매자ID
	private String buyerId;
	// 구매자명
	private String buyerName;
	// 결제일
	private String paymentDate;
	// 상품종류
	private String productType;
	// 주문세부상태
	private String orderDetailStatus;
	// 주문상태
	private String orderStatus;
	// 상품번호
	private String itemNum;
	// 배송속성
	private String deliveryProperty;
	// 배송희망일
	private String wantDeliveryDate;
	// (수취인연락처1)
	private String _receiverPhone1;
	// (수취인연락처2)
	private String _receiverPhone2;
	// (우편번호)
	private String zipcode;
	// (기본주소)
	private String receiverAddress1;
	// (상세주소)
	private String receiverAddress2;
	// (구매자연락처)
	private String buyerPhone;
	public String getProductOrderNum() {
		return productOrderNum;
	}
	public void setProductOrderNum(String productOrderNum) {
		this.productOrderNum = productOrderNum;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	public String getCourier() {
		return courier;
	}
	public void setCourier(String courier) {
		this.courier = courier;
	}
	public String getWaybillNum() {
		return waybillNum;
	}
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}
	public String getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOptionInfo() {
		return optionInfo;
	}
	public void setOptionInfo(String optionInfo) {
		this.optionInfo = optionInfo;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getShippingCostForm() {
		return shippingCostForm;
	}
	public void setShippingCostForm(String shippingCostForm) {
		this.shippingCostForm = shippingCostForm;
	}
	public String getReceiverPhone1() {
		return receiverPhone1;
	}
	public void setReceiverPhone1(String receiverPhone1) {
		this.receiverPhone1 = receiverPhone1;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getDeliveryMessage() {
		return deliveryMessage;
	}
	public void setDeliveryMessage(String deliveryMessage) {
		this.deliveryMessage = deliveryMessage;
	}
	public String getShipFrom() {
		return shipFrom;
	}
	public void setShipFrom(String shipFrom) {
		this.shipFrom = shipFrom;
	}
	public String getMethodOfPayment() {
		return methodOfPayment;
	}
	public void setMethodOfPayment(String methodOfPayment) {
		this.methodOfPayment = methodOfPayment;
	}
	public String getFeeChargingCategory() {
		return feeChargingCategory;
	}
	public void setFeeChargingCategory(String feeChargingCategory) {
		this.feeChargingCategory = feeChargingCategory;
	}
	public String getFeePaymentMethod() {
		return feePaymentMethod;
	}
	public void setFeePaymentMethod(String feePaymentMethod) {
		this.feePaymentMethod = feePaymentMethod;
	}
	public Integer getPaymentFee() {
		return paymentFee;
	}
	public void setPaymentFee(Integer paymentFee) {
		this.paymentFee = paymentFee;
	}
	public Integer getSalesLinkageFee() {
		return salesLinkageFee;
	}
	public void setSalesLinkageFee(Integer salesLinkageFee) {
		this.salesLinkageFee = salesLinkageFee;
	}
	public Integer getEstimatedTotalAmount() {
		return estimatedTotalAmount;
	}
	public void setEstimatedTotalAmount(Integer estimatedTotalAmount) {
		this.estimatedTotalAmount = estimatedTotalAmount;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getBuyerSSN() {
		return buyerSSN;
	}
	public void setBuyerSSN(String buyerSSN) {
		this.buyerSSN = buyerSSN;
	}
	public String getPccc() {
		return pccc;
	}
	public void setPccc(String pccc) {
		this.pccc = pccc;
	}
	public String getOrderDateTime() {
		return orderDateTime;
	}
	public void setOrderDateTime(String orderDateTime) {
		this.orderDateTime = orderDateTime;
	}
	public Integer getNumOfOrdersPerYear() {
		return numOfOrdersPerYear;
	}
	public void setNumOfOrdersPerYear(Integer numOfOrdersPerYear) {
		this.numOfOrdersPerYear = numOfOrdersPerYear;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getOrderDetailStatus() {
		return orderDetailStatus;
	}
	public void setOrderDetailStatus(String orderDetailStatus) {
		this.orderDetailStatus = orderDetailStatus;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getItemNum() {
		return itemNum;
	}
	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}
	public String getDeliveryProperty() {
		return deliveryProperty;
	}
	public void setDeliveryProperty(String deliveryProperty) {
		this.deliveryProperty = deliveryProperty;
	}
	public String getWantDeliveryDate() {
		return wantDeliveryDate;
	}
	public void setWantDeliveryDate(String wantDeliveryDate) {
		this.wantDeliveryDate = wantDeliveryDate;
	}
	public String get_receiverPhone1() {
		return _receiverPhone1;
	}
	public void set_receiverPhone1(String _receiverPhone1) {
		this._receiverPhone1 = _receiverPhone1;
	}
	public String get_receiverPhone2() {
		return _receiverPhone2;
	}
	public void set_receiverPhone2(String _receiverPhone2) {
		this._receiverPhone2 = _receiverPhone2;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getReceiverAddress1() {
		return receiverAddress1;
	}
	public void setReceiverAddress1(String receiverAddress1) {
		this.receiverAddress1 = receiverAddress1;
	}
	public String getReceiverAddress2() {
		return receiverAddress2;
	}
	public void setReceiverAddress2(String receiverAddress2) {
		this.receiverAddress2 = receiverAddress2;
	}
	public String getBuyerPhone() {
		return buyerPhone;
	}
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	@Override
	public String toString() {
		return "NaverColumnDto [productOrderNum=" + productOrderNum + ", orderNum=" + orderNum + ", shippingMethod="
				+ shippingMethod + ", courier=" + courier + ", waybillNum=" + waybillNum + ", shippingDate="
				+ shippingDate + ", receiverName=" + receiverName + ", productName=" + productName + ", optionInfo="
				+ optionInfo + ", quantity=" + quantity + ", shippingCostForm=" + shippingCostForm + ", receiverPhone1="
				+ receiverPhone1 + ", receiverAddress=" + receiverAddress + ", deliveryMessage=" + deliveryMessage
				+ ", shipFrom=" + shipFrom + ", methodOfPayment=" + methodOfPayment + ", feeChargingCategory="
				+ feeChargingCategory + ", feePaymentMethod=" + feePaymentMethod + ", paymentFee=" + paymentFee
				+ ", salesLinkageFee=" + salesLinkageFee + ", estimatedTotalAmount=" + estimatedTotalAmount
				+ ", channel=" + channel + ", buyerSSN=" + buyerSSN + ", pccc=" + pccc + ", orderDateTime="
				+ orderDateTime + ", numOfOrdersPerYear=" + numOfOrdersPerYear + ", buyerId=" + buyerId + ", buyerName="
				+ buyerName + ", paymentDate=" + paymentDate + ", productType=" + productType + ", orderDetailStatus="
				+ orderDetailStatus + ", orderStatus=" + orderStatus + ", itemNum=" + itemNum + ", deliveryProperty="
				+ deliveryProperty + ", wantDeliveryDate=" + wantDeliveryDate + ", _receiverPhone1=" + _receiverPhone1
				+ ", _receiverPhone2=" + _receiverPhone2 + ", zipcode=" + zipcode + ", receiverAddress1="
				+ receiverAddress1 + ", receiverAddress2=" + receiverAddress2 + ", buyerPhone=" + buyerPhone
				+ ", getProductOrderNum()=" + getProductOrderNum() + ", getOrderNum()=" + getOrderNum()
				+ ", getShippingMethod()=" + getShippingMethod() + ", getCourier()=" + getCourier()
				+ ", getWaybillNum()=" + getWaybillNum() + ", getShippingDate()=" + getShippingDate()
				+ ", getReceiverName()=" + getReceiverName() + ", getProductName()=" + getProductName()
				+ ", getOptionInfo()=" + getOptionInfo() + ", getQuantity()=" + getQuantity()
				+ ", getShippingCostForm()=" + getShippingCostForm() + ", getReceiverPhone1()=" + getReceiverPhone1()
				+ ", getReceiverAddress()=" + getReceiverAddress() + ", getDeliveryMessage()=" + getDeliveryMessage()
				+ ", getShipFrom()=" + getShipFrom() + ", getMethodOfPayment()=" + getMethodOfPayment()
				+ ", getFeeChargingCategory()=" + getFeeChargingCategory() + ", getFeePaymentMethod()="
				+ getFeePaymentMethod() + ", getPaymentFee()=" + getPaymentFee() + ", getSalesLinkageFee()="
				+ getSalesLinkageFee() + ", getEstimatedTotalAmount()=" + getEstimatedTotalAmount() + ", getChannel()="
				+ getChannel() + ", getBuyerSSN()=" + getBuyerSSN() + ", getPccc()=" + getPccc()
				+ ", getOrderDateTime()=" + getOrderDateTime() + ", getNumOfOrdersPerYear()=" + getNumOfOrdersPerYear()
				+ ", getBuyerId()=" + getBuyerId() + ", getBuyerName()=" + getBuyerName() + ", getPaymentDate()="
				+ getPaymentDate() + ", getProductType()=" + getProductType() + ", getOrderDetailStatus()="
				+ getOrderDetailStatus() + ", getOrderStatus()=" + getOrderStatus() + ", getItemNum()=" + getItemNum()
				+ ", getDeliveryProperty()=" + getDeliveryProperty() + ", getWantDeliveryDate()="
				+ getWantDeliveryDate() + ", get_receiverPhone1()=" + get_receiverPhone1() + ", get_receiverPhone2()="
				+ get_receiverPhone2() + ", getZipcode()=" + getZipcode() + ", getReceiverAddress1()="
				+ getReceiverAddress1() + ", getReceiverAddress2()=" + getReceiverAddress2() + ", getBuyerPhone()="
				+ getBuyerPhone() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
}
