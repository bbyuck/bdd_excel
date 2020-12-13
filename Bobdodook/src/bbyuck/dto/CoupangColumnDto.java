package bbyuck.dto;

public class CoupangColumnDto {
	// ��ȣ
	private Integer num;
	// ������۹�ȣ
	private String shippingNum;
	// �ֹ���ȣ
	private String orderNum;
	// �ù��
	private String courier;
	// ������ȣ
	private String waybillNum;
	// �и���� Y/N
	/* 
	 * N
	 * Y
	 * �и���� �Ұ�
	 */
	private String separateDelivery;
	// �и���� �������
	private String separateExpectedDeliveryDate;
	// �ֹ��� �������
	private String expectedDeliveryDate;
	// �����(�߼���)
	private String deliveryDate;
	// �ֹ���
	private String orderDate;
	// ��ϻ�ǰ��
	private String productName;
	// ��ϿɼǸ�
	private String optionName;
	// �����ǰ��
	private String displayedProductName;
	// �����ǰID
	private String displayedProductId;
	// �ɼ�ID
	private String optionId;
	// ���ʵ�ϿɼǸ�
	private String firstOptionName;
	// ��ü��ǰ�ڵ�
	private String productCode;
	// ���ڵ�
	private String barcode;
	// ������
	private Integer payment;
	// ��ۺ񱸺�
	private String deliveryFeeFlag;
	// ��ۺ�
	private Integer deliveryFee;
	// �����갣 �߰���ۺ�
	private Integer additionalDeliveryFee;
	// ���ż�(����)
	private Integer quantity;
	// �ɼ��ǸŰ�(�ǸŴܰ�)
	private Integer unitPrice;
	// ������
	private String customerName;
	// �������̸���
	private String customerEmail;
	// ��������ȭ��ȣ
	private String customerPhone;
	// �������̸�
	private String receiverName;
	// ��������ȭ��ȣ
	private String receiverPhone;
	// �����ȣ
	private String postNum;
	// ������ �ּ�
	private String receiverAddress;
	// ��۸޼���
	private String deliveryMessage;
	// ��ǰ�� �߰��޽���
	private String additionalMessagePerItem;
	// �ֹ��� �߰��޽���
	private String ordererAdditionalMessage;
	// ��ۿϷ���
	private String deliveryCompleteDate;
	// ����Ȯ������
	private String confirmationPurchaseDate;
	// ���������ȣ(PCCC)
	private String pccc;
	// ����뱸������ȭ��ȣ
	private String buyerPhoneNumForCustomsClearance;
	// ��Ÿ
	private String etc;
	// ������ġ
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
