package bbyuck.dto;

public class CnpOutputDto {
	// 운송장번호
	private String waybillNum;
	// 받는분
	private String receiverName;
	//받는분 전화번호
	private String receiverPhone;
	public String getWaybillNum() {
		return waybillNum;
	}
	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
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
	
	
}
