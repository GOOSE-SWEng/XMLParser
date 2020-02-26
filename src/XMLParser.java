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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XMLParser {
	static String schemaName = "src/schema.xsd";
	static String xmlName = "src/xml.xml";
	static Document xmlDoc;

	public static void XMLParser(String fileDir) {
		xmlDoc = getDocument(fileDir);
		xmlDoc.getDocumentElement().normalize();
		
		System.out.println("Root element: " + xmlDoc.getDocumentElement().getNodeName());

		NodeList slideList = xmlDoc.getElementsByTagName("slide");
		System.out.println("Number of slides: " + slideList.getLength());
		
		
		for(int i=0; i<slideList.getLength();i++) {
			Node slideNode = slideList.item(i);
			System.out.println("Current Element: " + slideNode.getNodeName());
			if(slideNode.getNodeType() == Node.ELEMENT_NODE) {
				Element slideElement = (Element) slideNode;
				System.out.println("Slide id: " + slideElement.getElementsByTagName("id").item(0).getTextContent());
				System.out.println("Slide duration: " + slideElement.getElementsByTagName("duration").item(0).getTextContent());
				
				//System.out.println("Slide text: " + slideElement.getElementsByTagName("text").item(0).getTextContent());
				System.out.println(slideElement.getFirstChild().getNodeName());
				
				
				/*while(slideElement.getNextSibling() != null) {
					try{
						slideElement = (Element) slideElement.getNextSibling();
						System.out.println("\t "+ slideElement.getNextSibling().getNodeName() + slideElement.getNextSibling().getTextContent());
					}
					catch(Exception e) {
						break;
					}
				}*/
				
				
				
				/*System.out.println("Slide line: " + slideElement.getElementsByTagName("line").item(0).getTextContent());
				System.out.println("Slide id: " + slideElement.getElementsByTagName("id").item(0).getTextContent());
				
				System.out.println("Slide shape: " + slideElement.getElementsByTagName("shape").item(0).getTextContent());
				System.out.println("Slide id: " + slideElement.getElementsByTagName("id").item(0).getTextContent());
				
				System.out.println("Slide audio: " + slideElement.getElementsByTagName("audio").item(0).getTextContent());
				System.out.println("Slide id: " + slideElement.getElementsByTagName("id").item(0).getTextContent());
				
				System.out.println("Slide image: " + slideElement.getElementsByTagName("image").item(0).getTextContent());
				System.out.println("Slide id: " + slideElement.getElementsByTagName("id").item(0).getTextContent());
				
				System.out.println("Slide video: " + slideElement.getElementsByTagName("video").item(0).getTextContent());
				System.out.println("Slide id: " + slideElement.getElementsByTagName("id").item(0).getTextContent());*/

				
				
				
				
				
				
				
			}
			
			
			
		}
		
		
		
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
	
	public static void main(String[] args) {
		//Schema schema = loadSchema(schemaName);
		//xmlDoc = getDocument(xmlName);
		//validateXML(xmlDoc, schema);
		XMLParser(xmlName);
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
	

}
