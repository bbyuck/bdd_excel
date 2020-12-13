package bbyuck.dto;

public class CoupangColumnDto {
	// 번호
	private Integer num;
	// 묶음배송번호
	private String shippingNum;
	// 주문번호
	private String orderNum;
	// 택배사
	private String courier;
	// 운송장번호
	private String waybillNum;
	// 분리배송 Y/N
	/* 
	 * N
	 * Y
	 * 분리배송 불가
	 */
	private String separateDelivery;
	// 분리배송 출고예정일
	private String separateExpectedDeliveryDate;
	// 주문시 출고예정일
	private String expectedDeliveryDate;
	// 출고일(발송일)
	private String deliveryDate;
	// 주문일
	private String orderDate;
	// 등록상품명
	private String productName;
	// 등록옵션명
	private String optionName;
	// 노출상품명
	private String displayedProductName;
	// 노출상품ID
	private String displayedProductId;
	// 옵션ID
	private String optionId;
	// 최초등록옵션명
	private String firstOptionName;
	// 업체상품코드
	private String productCode;
	// 바코드
	private String barcode;
	// 결제액
	private Integer payment;
	// 배송비구분
	private String deliveryFeeFlag;
	// 배송비
	private Integer deliveryFee;
	// 도서산간 추가배송비
	private Integer additionalDeliveryFee;
	// 구매수(수량)
	private Integer quantity;
	// 옵션판매가(판매단가)
	private Integer unitPrice;
	// 구매자
	private String customerName;
	// 구매자이메일
	private String customerEmail;
	// 구매자전화번호
	private String customerPhone;
	// 수취인이름
	private String receiverName;
	// 수취인전화번호
	private String receiverPhone;
	// 우편번호
	private String postNum;
	// 수취인 주소
	private String receiverAddress;
	// 배송메세지
	private String deliveryMessage;
	// 상품별 추가메시지
	private String additionalMessagePerItem;
	// 주문자 추가메시지
	private String ordererAdditionalMessage;
	// 배송완료일
	private String deliveryCompleteDate;
	// 구매확정일자
	private String confirmationPurchaseDate;
	// 개인통관번호(PCCC)
	private String pccc;
	// 통관용구매자전화번호
	private String buyerPhoneNumForCustomsClearance;
	// 기타
	private String etc;
	// 결제위치
	private String paymentLocation;
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getShippingNum() {
		return shippingNum;
	}
	public void setShippingNum(String shippingNum) {
		this.shippingNum = shippingNum;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
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
	public String getSeparateDelivery() {
		return separateDelivery;
	}
	public void setSeparateDelivery(String separateDelivery) {
		this.separateDelivery = separateDelivery;
	}
	public String getSeparateExpectedDeliveryDate() {
		return separateExpectedDeliveryDate;
	}
	public void setSeparateExpectedDeliveryDate(String separateExpectedDeliveryDate) {
		this.separateExpectedDeliveryDate = separateExpectedDeliveryDate;
	}
	public String getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}
	public void setExpectedDeliveryDate(String expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public String getDisplayedProductName() {
		return displayedProductName;
	}
	public void setDisplayedProductName(String displayedProductName) {
		this.displayedProductName = displayedProductName;
	}
	public String getDisplayedProductId() {
		return displayedProductId;
	}
	public void setDisplayedProductId(String displayedProductId) {
		this.displayedProductId = displayedProductId;
	}
	public String getOptionId() {
		return optionId;
	}
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	public String getFirstOptionName() {
		return firstOptionName;
	}
	public void setFirstOptionName(String firstOptionName) {
		this.firstOptionName = firstOptionName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public Integer getPayment() {
		return payment;
	}
	public void setPayment(Integer payment) {
		this.payment = payment;
	}
	public String getDeliveryFeeFlag() {
		return deliveryFeeFlag;
	}
	public void setDeliveryFeeFlag(String deliveryFeeFlag) {
		this.deliveryFeeFlag = deliveryFeeFlag;
	}
	public Integer getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(Integer deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public Integer getAdditionalDeliveryFee() {
		return additionalDeliveryFee;
	}
	public void setAdditionalDeliveryFee(Integer additionalDeliveryFee) {
		this.additionalDeliveryFee = additionalDeliveryFee;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getPostNum() {
		return postNum;
	}
	public void setPostNum(String postNum) {
		this.postNum = postNum;
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
	public String getAdditionalMessagePerItem() {
		return additionalMessagePerItem;
	}
	public void setAdditionalMessagePerItem(String additionalMessagePerItem) {
		this.additionalMessagePerItem = additionalMessagePerItem;
	}
	public String getOrdererAdditionalMessage() {
		return ordererAdditionalMessage;
	}
	public void setOrdererAdditionalMessage(String ordererAdditionalMessage) {
		this.ordererAdditionalMessage = ordererAdditionalMessage;
	}
	public String getDeliveryCompleteDate() {
		return deliveryCompleteDate;
	}
	public void setDeliveryCompleteDate(String deliveryCompleteDate) {
		this.deliveryCompleteDate = deliveryCompleteDate;
	}
	public String getConfirmationPurchaseDate() {
		return confirmationPurchaseDate;
	}
	public void setConfirmationPurchaseDate(String confirmationPurchaseDate) {
		this.confirmationPurchaseDate = confirmationPurchaseDate;
	}
	public String getPccc() {
		return pccc;
	}
	public void setPccc(String pccc) {
		this.pccc = pccc;
	}
	public String getBuyerPhoneNumForCustomsClearance() {
		return buyerPhoneNumForCustomsClearance;
	}
	public void setBuyerPhoneNumForCustomsClearance(String buyerPhoneNumForCustomsClearance) {
		this.buyerPhoneNumForCustomsClearance = buyerPhoneNumForCustomsClearance;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public String getPaymentLocation() {
		return paymentLocation;
	}
	public void setPaymentLocation(String paymentLocation) {
		this.paymentLocation = paymentLocation;
	}
	@Override
	public String toString() {
		return "CoupangColumnDto [num=" + num + ", shippingNum=" + shippingNum + ", orderNum=" + orderNum + ", courier="
				+ courier + ", waybillNum=" + waybillNum + ", separateDelivery=" + separateDelivery
				+ ", separateExpectedDeliveryDate=" + separateExpectedDeliveryDate + ", expectedDeliveryDate="
				+ expectedDeliveryDate + ", deliveryDate=" + deliveryDate + ", orderDate=" + orderDate
				+ ", productName=" + productName + ", optionName=" + optionName + ", displayedProductName="
				+ displayedProductName + ", displayedProductId=" + displayedProductId + ", optionId=" + optionId
				+ ", firstOptionName=" + firstOptionName + ", productCode=" + productCode + ", barcode=" + barcode
				+ ", payment=" + payment + ", deliveryFeeFlag=" + deliveryFeeFlag + ", deliveryFee=" + deliveryFee
				+ ", additionalDeliveryFee=" + additionalDeliveryFee + ", quantity=" + quantity + ", unitPrice="
				+ unitPrice + ", customerName=" + customerName + ", customerEmail=" + customerEmail + ", customerPhone="
				+ customerPhone + ", receiverName=" + receiverName + ", receiverPhone=" + receiverPhone + ", postNum="
				+ postNum + ", receiverAddress=" + receiverAddress + ", deliveryMessage=" + deliveryMessage
				+ ", additionalMessagePerItem=" + additionalMessagePerItem + ", ordererAdditionalMessage="
				+ ordererAdditionalMessage + ", deliveryCompleteDate=" + deliveryCompleteDate
				+ ", confirmationPurchaseDate=" + confirmationPurchaseDate + ", pccc=" + pccc
				+ ", buyerPhoneNumForCustomsClearance=" + buyerPhoneNumForCustomsClearance + ", etc=" + etc
				+ ", paymentLocation=" + paymentLocation + ", getNum()=" + getNum() + ", getShippingNum()="
				+ getShippingNum() + ", getOrderNum()=" + getOrderNum() + ", getCourier()=" + getCourier()
				+ ", getWaybillNum()=" + getWaybillNum() + ", getSeparateDelivery()=" + getSeparateDelivery()
				+ ", getSeparateExpectedDeliveryDate()=" + getSeparateExpectedDeliveryDate()
				+ ", getExpectedDeliveryDate()=" + getExpectedDeliveryDate() + ", getDeliveryDate()="
				+ getDeliveryDate() + ", getOrderDate()=" + getOrderDate() + ", getProductName()=" + getProductName()
				+ ", getOptionName()=" + getOptionName() + ", getDisplayedProductName()=" + getDisplayedProductName()
				+ ", getDisplayedProductId()=" + getDisplayedProductId() + ", getOptionId()=" + getOptionId()
				+ ", getFirstOptionName()=" + getFirstOptionName() + ", getProductCode()=" + getProductCode()
				+ ", getBarcode()=" + getBarcode() + ", getPayment()=" + getPayment() + ", getDeliveryFeeFlag()="
				+ getDeliveryFeeFlag() + ", getDeliveryFee()=" + getDeliveryFee() + ", getAdditionalDeliveryFee()="
				+ getAdditionalDeliveryFee() + ", getQuantity()=" + getQuantity() + ", getUnitPrice()=" + getUnitPrice()
				+ ", getCustomerName()=" + getCustomerName() + ", getCustomerEmail()=" + getCustomerEmail()
				+ ", getCustomerPhone()=" + getCustomerPhone() + ", getReceiverName()=" + getReceiverName()
				+ ", getReceiverPhone()=" + getReceiverPhone() + ", getPostNum()=" + getPostNum()
				+ ", getReceiverAddress()=" + getReceiverAddress() + ", getDeliveryMessage()=" + getDeliveryMessage()
				+ ", getAdditionalMessagePerItem()=" + getAdditionalMessagePerItem()
				+ ", getOrdererAdditionalMessage()=" + getOrdererAdditionalMessage() + ", getDeliveryCompleteDate()="
				+ getDeliveryCompleteDate() + ", getConfirmationPurchaseDate()=" + getConfirmationPurchaseDate()
				+ ", getPccc()=" + getPccc() + ", getBuyerPhoneNumForCustomsClearance()="
				+ getBuyerPhoneNumForCustomsClearance() + ", getEtc()=" + getEtc() + ", getPaymentLocation()="
				+ getPaymentLocation() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
