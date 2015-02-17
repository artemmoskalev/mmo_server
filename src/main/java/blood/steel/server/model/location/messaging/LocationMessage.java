package blood.steel.server.model.location.messaging;

import javax.xml.bind.annotation.*;

import blood.steel.server.model.location.LocationType;
import blood.steel.server.model.messaging.GameMessage;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationMessage extends GameMessage {

	private LocationType location;

	public LocationType getLocation() {
		return location;
	}
	public void setLocation(LocationType location) {
		this.location = location;
	}
	
}
