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
	//Initialise the schema and xml for parsing
	static String schemaName = "src/schema.xsd";
	static String xmlName = "src/xml.xml";
	static Document xmlDoc;
	//public static ArrayList<Slide> slides = new ArrayList<>();

	public  XMLParser(String fileDir) {
		xmlDoc = getDocument(fileDir); //Store the document in memory
		xmlDoc.getDocumentElement().normalize();
		
		System.out.println("Root element: " + xmlDoc.getDocumentElement().getNodeName());
		System.out.println("==============================");

		Node documentsInfo = xmlDoc.getElementsByTagName("documentinfo").item(0); //Get docInfo tag
		System.out.println("Root element: " + documentsInfo.getNodeName());
		NodeList docInfoNodeList = documentsInfo.getChildNodes(); //Create nodelist of subtags
		Node docInfoNode; //Initialise node
		for(int i=0;i<docInfoNodeList.getLength();i++) { //Loop through amount of subtags
			docInfoNode = docInfoNodeList.item(i); //Store current node
			if(docInfoNode instanceof Element) { //If there is a printable element
				//STORE HERE
				System.out.println(docInfoNode.getNodeName() + ": " + docInfoNode.getTextContent());
			}
		}
		System.out.println("==============================");

		NodeList defaults = xmlDoc.getElementsByTagName("defaults"); //Get defaults tag
		System.out.println("Root element: " + defaults.item(0).getNodeName()); //Gets name of default tag
		NodeList defaultsNodeList = defaults.item(0).getChildNodes(); //Create nodelist of subtags
		Node defaultNode; //Initialise node
		for(int i=0;i<defaultsNodeList.getLength();i++) { //Loop through amount of subtags
			defaultNode = defaultsNodeList.item(i); //Store current node
			if(defaultNode instanceof Element) { //If the tag has printable elements
				System.out.println(defaultNode.getNodeName() + ": " + defaultNode.getTextContent());
			}
		}
		
		NodeList slideList = xmlDoc.getElementsByTagName("slide"); //Create list of slide tags
		
		System.out.println("==============================");
		System.out.println("Number of slides: " + slideList.getLength()); //Show number of slides
		
		for(int j=0;j<slideList.getLength();j++) { //Cycle through each slide tag
			System.out.println("Root element: " + slideList.item(0).getNodeName()); //Get first tag name
			NodeList slideNodeList = slideList.item(j).getChildNodes(); //Gets tags from slides
			Node slideNode; //Node for a slide
			for(int i=0;i<slideNodeList.getLength();i++) { //Cycles through tags in a slide tag
				slideNode = slideNodeList.item(i); //Gets current node in slide
				if(slideNode.getChildNodes().getLength() > 1) { //If current node has sub nodes E.g Text has font...
					System.out.println(slideNode.getNodeName() + ":");
					NodeList subSlideNodeList = slideNode.getChildNodes(); //Store sub nodes in a list
					Node subSlideNode;
					for(int k=0;k<subSlideNodeList.getLength();k++) { //Cycle through sub tags
						subSlideNode = subSlideNodeList.item(k);
						if(subSlideNode.getChildNodes().getLength()>1) { //If there is a sub sub tag E.g Shape has shading, which has colour
							System.out.println("\t" + subSlideNode.getNodeName() + ":");
							NodeList subSubSlideNodes = subSlideNode.getChildNodes(); //Create list of sub sub tags
							Node subSubSlideNode;
							for(int z=0;z<subSubSlideNodes.getLength();z++) { //Cycle through sub sub tags
								subSubSlideNode = subSubSlideNodes.item(z);
								if(subSubSlideNode instanceof Element) { //If the tag has printable elements
									System.out.println("\t\t" + subSubSlideNode.getNodeName() + ": " + subSubSlideNode.getTextContent());
								}
							}
						}
						else if(subSlideNode instanceof Element) { //If the sub tag has no sub sub tags and it has a printable elements
							System.out.println("\t" + subSlideNode.getNodeName() + ": " + subSlideNode.getTextContent());
						}
					}
				}else if(slideNode instanceof Element) { //If the tag has no sub tags and it has a printable elements
					System.out.println(slideNode.getNodeName() + ": " + slideNode.getTextContent());
				}
			}
			System.out.println("==============================");
		}
	}
	
	public static Document getDocument(String name) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //Create factory to build a documents
			factory.setIgnoringComments(true); //Ignores commented out XML
			factory.setIgnoringElementContentWhitespace(true); //Ignore whitespace
			factory.setValidating(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new InputSource(name)); //Return a parsed document
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
