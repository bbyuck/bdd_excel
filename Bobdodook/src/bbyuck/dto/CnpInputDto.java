package bbyuck.dto;

public class CnpInputDto {
	// �������̸�
	private String receiverName;
	// ��������ȭ��ȣ
	private String receiverPhone;
	// ������ �ּ�
	private String receiverAddress;
	// �����ǰ��(�ɼǸ�)
	private String orderContents;
	// ��۸޼���
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
		return "CnpInputDto [�������̸�=" + receiverName + ", ��������ȭ��ȣ=" + receiverPhone + ", ������ �ּ�="
				+ receiverAddress + ", �����ǰ��(�ɼǸ�)=" + orderContents + ", ���=" + remark + "]";
	}
	
}
