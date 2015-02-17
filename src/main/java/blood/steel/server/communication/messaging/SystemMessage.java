package blood.steel.server.communication.messaging;

public class SystemMessage implements Message {

	private int code;
	private String text;
	
	public SystemMessage() {	}	
	
	public SystemMessage(int code, String text) {
		this.code = code;
		this.text = text;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
