package bbyuck.event;

public class TransformationComplete extends Throwable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "변환 완료!";
	
	public String getMessage() {
		return MESSAGE;
	}
	
}
