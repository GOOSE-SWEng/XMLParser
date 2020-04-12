import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
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
		//Store the document in memory
		xmlDoc = getDocument(fileDir); 
		xmlDoc.getDocumentElement().normalize();
		
		System.out.println("Root element: " + xmlDoc.getDocumentElement().getNodeName());
		System.out.println("==============================");

		//Get docInfo tag
		Node documentsInfo = xmlDoc.getElementsByTagName("documentinfo").item(0); 
		System.out.println("Root element: " + documentsInfo.getNodeName());
		
		//Create nodelist of subtags
		NodeList docInfoNodeList = documentsInfo.getChildNodes();
		
		//Initialise node
		Node docInfoNode; 
		
		/*
		 * For loop loops through amount of subtags
		 * stores the current node
		 * and stores it if it is a printable element 
		 */
		for(int i=0;i<docInfoNodeList.getLength();i++) { 
			docInfoNode = docInfoNodeList.item(i); 
			if(docInfoNode instanceof Element) { 
				//STORE HERE
				System.out.println(docInfoNode.getNodeName() + ": " + docInfoNode.getTextContent());
			}
		}
		System.out.println("==============================");

		//Get defaults tag
		NodeList defaults = xmlDoc.getElementsByTagName("defaults"); 
		//Gets name of default tag
		System.out.println("Root element: " + defaults.item(0).getNodeName()); 
		//Create nodelist of subtags
		NodeList defaultsNodeList = defaults.item(0).getChildNodes(); 
		
		//Initialise node
		Node defaultNode; 
		
		/*
		 * For loop loops through amount of subtags
		 * stores the current node
		 * and stores it if it is a printable element 
		 */
		for(int i=0;i<defaultsNodeList.getLength();i++) { 
			defaultNode = defaultsNodeList.item(i); 
			if(defaultNode instanceof Element) { 
				System.out.println(defaultNode.getNodeName() + ": " + defaultNode.getTextContent());
			}
		}
		
		//Create list of slide tags
		NodeList slideList = xmlDoc.getElementsByTagName("slide"); 
		
		//Show number of slides
		System.out.println("==============================");
		System.out.println("Number of slides: " + slideList.getLength());
		
		/*
		 * Cycles through each slide tag
		 * Gets first tag name
		 * Gets tags from slides
		 * Creates a node for each slide
		 */
		for(int j=0;j<slideList.getLength();j++) { 
			System.out.println("Root element: " + slideList.item(0).getNodeName()); 
			NodeList slideNodeList = slideList.item(j).getChildNodes(); 
			Node slideNode; 
			
			/*
			 * Cycles through tags in a slide tag
			 * Gets current node in slide
			 */
			for(int i=0;i<slideNodeList.getLength();i++) { 
				slideNode = slideNodeList.item(i); 
				
				if(slideNode.getNodeName() == "video") {
					videoParse(slideNode);
				}else if(slideNode.getNodeName() == "text") {
					textParse(slideNode);
				}else if(slideNode.getNodeName() == "audio") {
					audioParse(slideNode);
				}else if(slideNode.getNodeName() == "image") {
					imageParse(slideNode);
				}else if(slideNode.getNodeName() == "shape") {
					shapeParse(slideNode);
				}else if(slideNode.getNodeName() == "line") {
					lineParse(slideNode);
				}else if(slideNode.getNodeName() == "3dmodel") {
					modelParse(slideNode);
				}else{
					if(slideNode instanceof Element) {
						System.out.println(slideNode.getNodeName() + " is an unrecognised node name");
					}
				}
				//If current node has sub nodes E.g Text has font...
				if(slideNode.getChildNodes().getLength() > 1) { 
					System.out.println(slideNode.getNodeName() + ":");
					
					//Store sub nodes in a list
					NodeList subSlideNodeList = slideNode.getChildNodes(); 
					Node subSlideNode;
					
					/*
					 * Cycle through sub tags
					 * If there is a sub sub tag E.g Shape has shading, which has colour
					 * Create list of sub sub tags
					 */
					for(int k=0;k<subSlideNodeList.getLength();k++) { 
						subSlideNode = subSlideNodeList.item(k);
						if(subSlideNode.getChildNodes().getLength()>1) { 
							System.out.println("\t" + subSlideNode.getNodeName() + ":");
							NodeList subSubSlideNodes = subSlideNode.getChildNodes(); 
							Node subSubSlideNode;
							
							/*
							 * Cycle through sub sub tags
							 */
							for(int z=0;z<subSubSlideNodes.getLength();z++) { 
								subSubSlideNode = subSubSlideNodes.item(z);
								//If the tag has printable elements
								if(subSubSlideNode instanceof Element) { 
									System.out.println("\t\t" + subSubSlideNode.getNodeName() + ": " + subSubSlideNode.getTextContent());
								}
							}
						}
						//If the sub tag has no sub sub tags and it has a printable elements
						else if(subSlideNode instanceof Element) { 
							System.out.println("\t" + subSlideNode.getNodeName() + ": " + subSlideNode.getTextContent());
						}
					}
				}
				//If the tag has no sub tags and it has a printable elements
				else if(slideNode instanceof Element) { 
					System.out.println(slideNode.getNodeName() + ": " + slideNode.getTextContent());
				}
			}
			System.out.println("==============================");
		}
	}
	
	/*
	 * Create a factory to build a document
	 * Method ignores commented out XML and whitespace
	 * takes the directory for the XML file as a parameter
	 * returns a parsed document
	 */

	public static Document getDocument(String name) {
		try {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true); 
			//factory.setValidating(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new InputSource(name)); 
		}
		catch(ParserConfigurationException | IOException | SAXException e) {
			System.out.println(e);
		}
		return null;
	}
	
	public void videoParse(Node slideNode) {
		if(slideNode.getChildNodes().getLength() > 1) { 
			System.out.println(slideNode.getNodeName() + ":");
			
			//Store sub nodes in a list
			NodeList subSlideNodeList = slideNode.getChildNodes(); 
			Node subSlideNode;
			
			/*
			 * Cycle through sub tags
			 * If there is a sub sub tag E.g Shape has shading, which has colour
			 * Create list of sub sub tags
			 */
			for(int k=0;k<subSlideNodeList.getLength();k++) { 
				subSlideNode = subSlideNodeList.item(k);
				//If the sub tag has no sub sub tags and it has a printable elements
				if(subSlideNode instanceof Element) { 
					System.out.println("\t" + subSlideNode.getNodeName() + ": " + subSlideNode.getTextContent());
				}
			}
		}
	}
	public void imageParse(Node slideNode) {
		if(slideNode.getChildNodes().getLength() > 1) { 
			System.out.println(slideNode.getNodeName() + ":");
			
			//Store sub nodes in a list
			NodeList subSlideNodeList = slideNode.getChildNodes(); 
			Node subSlideNode;
			
			/*
			 * Cycle through sub tags
			 * If there is a sub sub tag E.g Shape has shading, which has colour
			 * Create list of sub sub tags
			 */
			for(int k=0;k<subSlideNodeList.getLength();k++) { 
				subSlideNode = subSlideNodeList.item(k);
				//If the sub tag has no sub sub tags and it has a printable elements
				if(subSlideNode instanceof Element) { 
					System.out.println("\t" + subSlideNode.getNodeName() + ": " + subSlideNode.getTextContent());
				}
			}
		}
	}
	public void shapeParse(Node slideNode) {
		if(slideNode.getChildNodes().getLength() > 1) { 
			System.out.println(slideNode.getNodeName() + ":");
			
			//Store sub nodes in a list
			NodeList subSlideNodeList = slideNode.getChildNodes(); 
			Node subSlideNode;
			
			/*
			 * Cycle through sub tags
			 * If there is a sub sub tag E.g Shape has shading, which has colour
			 * Create list of sub sub tags
			 */
			for(int k=0;k<subSlideNodeList.getLength();k++) { 
				subSlideNode = subSlideNodeList.item(k);
				if(subSlideNode.getChildNodes().getLength()>1) { 
					System.out.println("\t" + subSlideNode.getNodeName() + ":");
					NodeList subSubSlideNodes = subSlideNode.getChildNodes(); 
					Node subSubSlideNode;
					
					/*
					 * Cycle through sub sub tags
					 */
					for(int z=0;z<subSubSlideNodes.getLength();z++) { 
						subSubSlideNode = subSubSlideNodes.item(z);
						//If the tag has printable elements
						if(subSubSlideNode instanceof Element) { 
							System.out.println("\t\t" + subSubSlideNode.getNodeName() + ": " + subSubSlideNode.getTextContent());
						}
					}
				}
				//If the sub tag has no sub sub tags and it has a printable elements
				else if(subSlideNode instanceof Element) { 
					System.out.println("\t" + subSlideNode.getNodeName() + ": " + subSlideNode.getTextContent());
				}
			}
		}
	}
	public void textParse(Node slideNode) {
		if(slideNode.getChildNodes().getLength() > 1) { 
			System.out.println(slideNode.getNodeName() + ":");
			
			//Store sub nodes in a list
			NodeList subSlideNodeList = slideNode.getChildNodes(); 
			Node subSlideNode;
			
			/*
			 * Cycle through sub tags
			 * If there is a sub sub tag E.g Shape has shading, which has colour
			 * Create list of sub sub tags
			 */
			for(int k=0;k<subSlideNodeList.getLength();k++) { 
				subSlideNode = subSlideNodeList.item(k);
				//If the sub tag has no sub sub tags and it has a printable elements
				if(subSlideNode instanceof Element) { 
					System.out.println("\t" + subSlideNode.getNodeName() + ": " + subSlideNode.getTextContent());
				}
			}
		}
	}
	public void audioParse(Node slideNode) {
		if(slideNode.getChildNodes().getLength() > 1) { 
			System.out.println(slideNode.getNodeName() + ":");
			
			//Store sub nodes in a list
			NodeList subSlideNodeList = slideNode.getChildNodes(); 
			Node subSlideNode;
			
			/*
			 * Cycle through sub tags
			 * If there is a sub sub tag E.g Shape has shading, which has colour
			 * Create list of sub sub tags
			 */
			for(int k=0;k<subSlideNodeList.getLength();k++) { 
				subSlideNode = subSlideNodeList.item(k);
				//If the sub tag has no sub sub tags and it has a printable elements
				if(subSlideNode instanceof Element) { 
					System.out.println("\t" + subSlideNode.getNodeName() + ": " + subSlideNode.getTextContent());
				}
			}
		}
	}
	public void lineParse(Node slideNode) {
		if(slideNode.getChildNodes().getLength() > 1) { 
			System.out.println(slideNode.getNodeName() + ":");
			
			//Store sub nodes in a list
			NodeList subSlideNodeList = slideNode.getChildNodes(); 
			Node subSlideNode;
			
			/*
			 * Cycle through sub tags
			 * If there is a sub sub tag E.g Shape has shading, which has colour
			 * Create list of sub sub tags
			 */
			for(int k=0;k<subSlideNodeList.getLength();k++) { 
				subSlideNode = subSlideNodeList.item(k);
				//If the sub tag has no sub sub tags and it has a printable elements
				if(subSlideNode instanceof Element) { 
					System.out.println("\t" + subSlideNode.getNodeName() + ": " + subSlideNode.getTextContent());
				}
			}
		}
	}
	public void modelParse(Node slideNode) {
		if(slideNode.getChildNodes().getLength() > 1) { 
			System.out.println(slideNode.getNodeName() + ":");
			
			//Store sub nodes in a list
			NodeList subSlideNodeList = slideNode.getChildNodes(); 
			Node subSlideNode;
			
			/*
			 * Cycle through sub tags
			 * If there is a sub sub tag E.g Shape has shading, which has colour
			 * Create list of sub sub tags
			 */
			for(int k=0;k<subSlideNodeList.getLength();k++) { 
				subSlideNode = subSlideNodeList.item(k);
				//If the sub tag has no sub sub tags and it has a printable elements
				if(subSlideNode instanceof Element) { 
					System.out.println("\t" + subSlideNode.getNodeName() + ": " + subSlideNode.getTextContent());
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new XMLParser(xmlName);
	}
	
}
