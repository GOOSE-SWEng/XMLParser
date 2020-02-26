import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XMLParser {
	static String schemaName = "src/schema.xsd";
	static String xmlName = "src/xml.xml";
	static Document xmlDoc;

	
	public static void XMLParser(String fileDir) {
		xmlDoc = getDocument(fileDir);
		Element root = xmlDoc.getDocumentElement();
		Element slide = (Element)root.getFirstChild();
		Slide s;
		while(slide != null) {
			s = getSlide(slide);
			System.out.println(s.title + "," + s.slideNo);
		}
		
	}
	
	public static Document getDocument(String name) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new InputSource(name));
		}
		catch(ParserConfigurationException | IOException | SAXException e) {
			System.out.println(e);
		}
		return null;
	}
	
	private static Slide getSlide(Element e) {
		String slideTitle = e.getAttribute("title");
		String slideNoString = e.getAttribute("slideNo");
		int slideNo = Integer.parseInt(slideNoString);
		
		return new Slide(slideTitle, slideNo);
		
	}
	
	private static class Slide{
		String title;
		int slideNo;
		public Slide(String title, int slideNo) {
			this.title = title;
			this.slideNo = slideNo;
		}
	}
	
	public static void validateXML(Document xml, Schema schema) {
		try {
			Validator validator = schema.newValidator();
			validator.validate(new DOMSource(xml));
			System.out.println("Validation Passed...");
		}catch(Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	public static Schema loadSchema(String schemaName) {
		Schema schema = null;
		try {
			String language = XMLConstants.DEFAULT_NS_PREFIX;
			SchemaFactory factory = SchemaFactory.newInstance(language);
			schema = factory.newSchema(new File(schemaName));
		}catch(Exception e) {
			System.out.println(e.toString());
		}
		return schema;
	}
	
	public static void main(String[] args) {
		//Schema schema = loadSchema(schemaName);
		xmlDoc = getDocument(xmlName);
		//validateXML(xmlDoc, schema);
		
		XMLParser(xmlName);
	}
}
