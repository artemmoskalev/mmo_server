package blood.steel.server.model.items;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.*;

import org.junit.Test;
import org.xml.sax.SAXException;

import blood.steel.server.model.util.WeaponParser;

public class XMLTest {

	@Test
	public void testWeapons() throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		
		WeaponParser handler = new WeaponParser();
		parser.parse(new File("weapons.xml"), handler);
	}
	
}
