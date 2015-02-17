package blood.steel.server.communication.request;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="parameters")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestXML {

	private RequestParameter[] parameter;

	public RequestParameter[] getParameter() {
		return parameter;
	}
	public void setParameter(RequestParameter[] parameter) {
		this.parameter = parameter;
	}
	
}
