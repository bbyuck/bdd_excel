package bbyuck.dto;

public class CnpInputDto {
	// 수취인이름
	private String receiverName;
	// 수취인전화번호
	private String receiverPhone;
	// 수취인 주소
	private String receiverAddress;
	// 노출상품명(옵션명)
	private String orderContents;
	// 배송메세지
	private String remark;
	
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
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getOrderContents() {
		return orderContents;
	}
	public void setOrderContents(String orderContents) {
		this.orderContents = orderContents;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "CnpInputDto [수취인이름=" + receiverName + ", 수취인전화번호=" + receiverPhone + ", 수취인 주소="
				+ receiverAddress + ", 노출상품명(옵션명)=" + orderContents + ", 비고=" + remark + "]";
	}
	
}
