package blood.steel.server.communication.request;

import java.util.*;
import java.util.Map.Entry;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class RequestAdapter extends XmlAdapter<RequestXML, Map<String, String>> {
	
	public Map<String, String> unmarshal(RequestXML v) throws Exception {	
		Map<String, String> myMap = new HashMap<String, String>();
		for (RequestParameter rp : v.getParameter()) {
			myMap.put(rp.getName(), rp.getValue());
		}		
		return myMap;
	}

	public RequestXML marshal(Map<String, String> v) throws Exception {	
		RequestParameter[] parameters = new RequestParameter[v.size()];
		int i = 0;
		for (Entry<String, String> entry : v.entrySet()) {
			RequestParameter rp = new RequestParameter();
			rp.setName(entry.getKey());
			rp.setValue(entry.getValue());
			parameters[i++] = rp;
		}
		RequestXML xml = new RequestXML();
		xml.setParameter(parameters);
		return xml;
	}	

}
