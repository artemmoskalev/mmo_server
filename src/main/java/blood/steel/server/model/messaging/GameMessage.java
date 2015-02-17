package blood.steel.server.model.messaging;

import javax.xml.bind.annotation.*;

import blood.steel.server.communication.messaging.Message;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class GameMessage implements Message {
	
	private Integer code;

	public Integer getCode() {
		return this.code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}

}
