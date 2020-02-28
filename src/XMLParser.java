import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
	public static ArrayList<Slide> slides = new ArrayList<>();

	public  XMLParser(String fileDir) {
		xmlDoc = getDocument(fileDir);
		xmlDoc.getDocumentElement().normalize();
		
		System.out.println("Root element: " + xmlDoc.getDocumentElement().getNodeName());
		
		Node documentsInfo = xmlDoc.getElementsByTagName("documentinfo").item(0);
		System.out.println("Root element: " + documentsInfo.getNodeName());
		NodeList docInfoNodeList = documentsInfo.getChildNodes();
		Node docInfoNode;
		for(int i=0;i<docInfoNodeList.getLength();i++) {
			docInfoNode = docInfoNodeList.item(i);
			if(docInfoNode instanceof Element) {
				System.out.println(docInfoNode.getNodeName() + ": " + docInfoNode.getTextContent());
			}
		}
		System.out.println("==============================");

		NodeList defaults = xmlDoc.getElementsByTagName("defaults");
		System.out.println("Root element: " + defaults.item(0).getNodeName());
		NodeList defaultsNodeList = defaults.item(0).getChildNodes();
		Node defaultNode;
		for(int i=0;i<defaultsNodeList.getLength();i++) {
			defaultNode = defaultsNodeList.item(i);
			if(defaultNode.getChildNodes().getLength() > 1) {
				System.out.println(defaultNode.getNodeName() + " has child nodes");
				if(defaultNode.getFirstChild().getChildNodes().getLength()>1) {
					System.out.println(defaultNode.getFirstChild().getNodeName() + " has child nodes");
				}
			}else{
				if(defaultNode instanceof Element) {
					System.out.println(defaultNode.getNodeName() + ": " + defaultNode.getTextContent());
				}
			}
		}
		
		NodeList slideList = xmlDoc.getElementsByTagName("slide");
		
		System.out.println("==============================");
		System.out.println("Number of slides: " + slideList.getLength());
		
		/*System.out.println("Root element: " + slideList.item(0).getNodeName());
		NodeList slideNodeList = slideList.item(0).getChildNodes();
		Node slideNode;*/
		for(int j=0;j<slideList.getLength();j++) {
			System.out.println("Root element: " + slideList.item(0).getNodeName());
			NodeList slideNodeList = slideList.item(j).getChildNodes();
			Node slideNode;
			for(int i=0;i<slideNodeList.getLength();i++) {
				slideNode = slideNodeList.item(i);
				if(slideNode.getChildNodes().getLength() > 1) {
					System.out.println(slideNode.getNodeName() + " has child nodes");
					NodeList subSlideNodes = slideNode.getChildNodes();
					for(int k=0;k<slideNode.getChildNodes().getLength();k++) {
						Node subSlideNode = subSlideNodes.item(k);
						if(subSlideNode.getChildNodes().getLength()>1) {
							System.out.println("\t" + subSlideNode.getNodeName() + " has child nodes");
						}
					}
				}else{
					if(slideNode instanceof Element) {
						System.out.println(slideNode.getNodeName() + ": " + slideNode.getTextContent());
					}
				}
			}
			System.out.println("==============================");
		}
		
		/*for(int i=0; i<slideList.getLength();i=4) {
			Node slideNode = slideList.item(i);
			System.out.println("Current Element: " + slideNode.getNodeName());
			if(slideNode.getNodeType() == Node.ELEMENT_NODE) {
				Element slideElement = (Element) slideNode;
				System.out.println("ID: " + slideElement.getElementsByTagName("id").item(0).getTextContent());
				System.out.println("DURATION: " + slideElement.getElementsByTagName("duration").item(0).getTextContent());
				
				System.out.println(slideNode.getChildNodes().getLength());
				System.out.println(slideNode.getFirstChild().getNodeName());
				System.out.println(slideNode.getLastChild().getNodeName());
				System.out.println("==============================");

				
				
				System.out.println("TEXT: " + slideElement.getElementsByTagName("text").item(0).getTextContent());
				System.out.println("TEXT: " + slideElement.getNextSibling().getNodeName());
		*/
				
				

				
				/*System.out.println("Slide id: " + slideElement.getElementsByTagName("id").item(0).getTextContent());
				String id = slideElement.getElementsByTagName("id").item(0).getTextContent();
				
				System.out.println("Slide duration: " + slideElement.getElementsByTagName("duration").item(0).getTextContent());
				String duration = slideElement.getElementsByTagName("duration").item(0).getTextContent();
				
				System.out.println("Slide text: " + slideElement.getElementsByTagName("text").item(0).getTextContent());
				String string = slideElement.getElementsByTagName("text").item(0).getTextContent();
				String[] textString = string.trim().split("\\s+");
				
				SlideText text = new SlideText(textString);
				
				System.out.println("Slide line: " + slideElement.getElementsByTagName("line").item(0).getTextContent());
				string = slideElement.getElementsByTagName("line").item(0).getTextContent();
				String[] lineProperties = string.trim().split("\\s+");
				Line line = new Line(lineProperties);

				System.out.println("Slide shape: " + slideElement.getElementsByTagName("shape").item(0).getTextContent());
				String[] shapeProperties = string.trim().split("\\s+");
				Shape shape = new Shape(shapeProperties);
				
				System.out.println("Slide audio: " + slideElement.getElementsByTagName("audio").item(0).getTextContent());
				String[] audioProperties = string.trim().split("\\s+");
				Audio audio = new Audio(audioProperties);
				
				System.out.println("Slide image: " + slideElement.getElementsByTagName("image").item(0).getTextContent());
				String[] imageProperties = string.trim().split("\\s+");
				Image image = new Image(imageProperties);
				
				System.out.println("Slide video: " + slideElement.getElementsByTagName("video").item(0).getTextContent());
				String[] videoProperties = string.trim().split("\\s+");
				Video video = new Video(videoProperties);
				
				Slide slide = new Slide(id, Integer.parseInt(duration), text, line, shape, audio, image, video);
				slides.add(slide);*/
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
		new XMLParser(xmlName);
	}
	
}
