import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javafx.scene.paint.Color;

public class XMLParser {
	//Initialise the schema and xml for parsing
	static String schemaName = "src/schema.xsd";
	static Schema schema = null;
	static String xmlName = "src/xml.xml";
	static Document xmlDoc;
	int currentSlide = 0;
	public static ArrayList<Slide> slides = new ArrayList<>();
	public static ArrayList<AudioLayer> audioLayers = new ArrayList<>();
	public static ArrayList<VideoLayer> videoLayers = new ArrayList<>();
	public static ArrayList<TextLayer> textLayers = new ArrayList<>();
	public static ArrayList<ImageLayer> imageLayers = new ArrayList<>();
	public static ArrayList<Graphics2D> g2dLayers = new ArrayList<>();
	public static ArrayList<Graphics3DLayer> graphics3DLayers = new ArrayList<>();

	public XMLParser(String fileDir, ArrayList<AudioLayer> audioLayers, ArrayList<VideoLayer> videoLayers, ArrayList<TextLayer> textLayers, ArrayList<ImageLayer> imageLayers,ArrayList<Graphics2D> g2dLayers, ArrayList<Graphics3DLayer> graphics3DLayers) {

		this.audioLayers = audioLayers;
		this.videoLayers = videoLayers;
		this.textLayers = textLayers;
		this.imageLayers = imageLayers;
		this.g2dLayers = g2dLayers;
		this.graphics3DLayers = graphics3DLayers;
		
		//Store the document in memory
		xmlDoc = getDocument(fileDir); 
		xmlDoc.getDocumentElement().normalize();
		/*try {
			validateXML(xmlDoc);
		}catch(SAXException|IOException e) {
			System.out.println("XML file could not be validated");
		}*/
		
		System.out.println("Root element: " + xmlDoc.getDocumentElement().getNodeName());
		System.out.println("==============================");

		///////////////////////////DOC INFO/////////////////////////////
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

		///////////////////////DEFAULTS//////////////////////
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
		
		/////////////////// SLIDES ///////////////////
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
			System.out.println("CurrentSlide: " + currentSlide);
			System.out.println("Root element: " + slideList.item(0).getNodeName());
			Node currentNode = slideList.item(j);
			if(currentNode.hasAttributes()) {
				NamedNodeMap attMap = currentNode.getAttributes();
				for(int i=0; i< attMap.getLength();i++) {
					Node attNode = attMap.item(i);
					System.out.println(attNode.getNodeName() + ": " + attNode.getTextContent());
				}
			}else{
				System.out.println("No attributes");
			}
			if(currentNode.hasChildNodes()) {
				getSubNodes(currentNode.getChildNodes());
			}
			else {
				System.out.println(currentNode.getNodeName() + " has no sub nodes");
			}
			
			/*
			 * Cycles through tags in a slide tag
			 * Gets current node in slide
			 */
			System.out.println("==============================");
			currentSlide++;
		}
	}
	
	public void getSubNodes(NodeList subNodeList) {
		for(int i =0;i<subNodeList.getLength();i++) {
			Node currentNode = subNodeList.item(i);
			if(currentNode instanceof Element) {
				if(currentNode.getNodeName() == "video") {
					videoParse(currentNode);
				}else if(currentNode.getNodeName() == "text") {
					textParse(currentNode);
				}else if(currentNode.getNodeName() == "audio") {
					audioParse(currentNode);
				}else if(currentNode.getNodeName() == "image") {
					imageParse(currentNode);
				}else if(currentNode.getNodeName() == "shape") {
					shapeParse(currentNode);
				}else if(currentNode.getNodeName() == "line") {
					lineParse(currentNode);
				}else if(currentNode.getNodeName() == "3dmodel") {
					modelParse(currentNode);
				}else{
					if(currentNode instanceof Element) {
						System.out.println(currentNode.getNodeName() + " is an unrecognised slide node name");
					}
				}
			}
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
			factory.setValidating(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new InputSource(name)); 
		}
		catch(ParserConfigurationException | IOException | SAXException e) {
			System.out.println(e);
		}
		return null;
	}
	
	public void validateXML(Document xmlDoc) throws SAXException, IOException {
		try {
			  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
			  SchemaFactory factory = SchemaFactory.newInstance(language);
			  schema = factory.newSchema(new File(schemaName));
		} catch (Exception e) {
			    e.printStackTrace();
		}
		Validator validator = schema.newValidator();
		validator.validate(new DOMSource(xmlDoc));
	}
	
	public void videoParse(Node currentNode) {
		String urlName = "";
		Boolean loop = false;
		int xStart = 0;
		int startTime = 0;
		System.out.println(currentNode.getNodeName());
		if(currentNode.hasAttributes()) {
			NamedNodeMap attMap = currentNode.getAttributes();
			System.out.print("Attributes= ");
			for(int j=0 ; j<attMap.getLength();j++) {
				System.out.print(attMap.item(j).getNodeName() + ": " + attMap.item(j).getNodeValue() + " | ");
				if(attMap.item(j).getNodeName().equals("urlname")) {
					urlName = attMap.item(j).getNodeValue();
				}else if(attMap.item(j).getNodeName().equals("loop")) {
					loop = Boolean.parseBoolean(attMap.item(j).getNodeValue());
				}
				else if(attMap.item(j).getNodeName().equals("xstart")) {
					xStart = Integer.parseInt(attMap.item(j).getNodeValue());
				}
				else if(attMap.item(j).getNodeName().equals("starttime")) {
					startTime = Integer.parseInt(attMap.item(j).getNodeValue());
				}else{
					System.out.print(attMap.item(j).getNodeName() + "is not recognized in the video tag");
				}
			}
			System.out.println("");
		}else{}
		if(currentNode.hasChildNodes()) {
			getSubNodes(currentNode.getChildNodes());
		}else{}
		videoLayers.get(currentSlide).add(urlName, startTime, loop, xStart, 0);
	}
	
	public void imageParse(Node currentNode) {
		String urlName = "";
		int xStart=0;
		int yStart=0;
		int width=0;
		int height=0;
		int startTime=0;
		int endTime=0;
		System.out.println(currentNode.getNodeName());
		if(currentNode.hasAttributes()) {
			NamedNodeMap attMap = currentNode.getAttributes();
			System.out.print("Attributes= ");
			for(int j=0 ; j<attMap.getLength();j++) {
				System.out.print(attMap.item(j).getNodeName() + ": " + attMap.item(j).getNodeValue() + " | ");
				if(attMap.item(j).getNodeName().equals("urlname")) {
					urlName = attMap.item(j).getNodeValue();
				}else if(attMap.item(j).getNodeName().equals("xstart")) {
					xStart = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("ystart")) {
					yStart = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("endtime")) {
					endTime = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("starttime")) {
					startTime = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("width")) {
					width = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("height")) {
					height = Integer.parseInt(attMap.item(j).getNodeValue());
				}else{
					System.out.print(attMap.item(j).getNodeName() + "is not recognized in the image tag");
				}
			}
			System.out.println("");
		}else{}
		if(currentNode.hasChildNodes()) {
			getSubNodes(currentNode.getChildNodes());
		}else{}
		imageLayers.get(currentSlide).add(urlName, xStart,yStart,width,height,startTime,endTime,currentSlide);
	}
	public void shapeParse(Node currentNode) {
		Boolean shading = false;
		
		
		
		
		
		String type=null;
		String id = null;
		float xStart=0;
		float yStart=0;
		float width=0;
		float height=0;
		String fillColor = null;
		int startTime=0;
		int endTime=0;
		System.out.println(currentNode.getNodeName());
		if(currentNode.hasAttributes()) {
			NamedNodeMap attMap = currentNode.getAttributes();
			System.out.print("Attributes= ");
			for(int j=0 ; j<attMap.getLength();j++) {
				System.out.print(attMap.item(j).getNodeName() + ": " + attMap.item(j).getNodeValue() + " | ");
				if(attMap.item(j).getNodeName().equals("type")) {
					if(attMap.item(j).getTextContent() == "rectangle" | attMap.item(j).getTextContent() == "oval") {
						type = attMap.item(j).getNodeValue();
					}
				}else if(attMap.item(j).getNodeName().equals("width")) {
					width = Integer.parseInt(attMap.item(j).getNodeValue());
				}
				else if(attMap.item(j).getNodeName().equals("height")) {
					height = Integer.parseInt(attMap.item(j).getNodeValue());
				}
				else if(attMap.item(j).getNodeName().equals("xstart")) {
					xStart = Integer.parseInt(attMap.item(j).getNodeValue());
				}
				else if(attMap.item(j).getNodeName().equals("ystart")) {
					yStart = Integer.parseInt(attMap.item(j).getNodeValue());
				}
				else if(attMap.item(j).getNodeName().equals("starttime")) {
					startTime = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("endtime")) {
					endTime = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("fillcolor")) {
					fillColor = attMap.item(j).getNodeValue();
				}else{
					System.out.print(attMap.item(j).getNodeName() + "is not recognized in the shape tag");
				}
			}
			System.out.println("");
		}else{}
		if(currentNode.hasChildNodes()) {
			getSubNodes(currentNode.getChildNodes());
		}else{}
		
		if(type.equals("oval") && !shading) {
			g2dLayers.get(currentSlide).registerOval(xStart, yStart,width, height, fillColor, startTime, endTime, currentSlide);
		}else if(type.equals("rectangle") && !shading) {
			g2dLayers.get(currentSlide).registerRectangle(xStart, yStart,width, height, fillColor, id, startTime, endTime, currentSlide);
		}else if(type.equals("oval") && shading) {
			g2dLayers.get(currentSlide).registerOval(xStart, yStart,width, height, fillColor, startTime, endTime, currentSlide);
		}else if(type.equals("rectangle") && shading) {
			g2dLayers.get(currentSlide).registerRectangle(xStart, yStart,width, height, fillColor, id, startTime, endTime, currentSlide);
		}else {}
	}
	public void textParse(Node currentNode) {
		textLayers.get(currentSlide).add(currentNode, currentSlide);
	}
	
	public void audioParse(Node currentNode) {
		String urlName=null;
		int startTime = 0;
		Boolean loop = null;
		
		System.out.println(currentNode.getNodeName());
		if(currentNode.hasAttributes()) {
			NamedNodeMap attMap = currentNode.getAttributes();
			System.out.print("Attributes= ");
			for(int j=0 ; j<attMap.getLength();j++) {
				System.out.print(attMap.item(j).getNodeName() + ": " + attMap.item(j).getNodeValue() + " | ");
				if(attMap.item(j).getNodeName().equals("urlname")) {
					urlName = attMap.item(j).getNodeValue();
				}else if(attMap.item(j).getNodeName().equals("starttime")) {
					startTime = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("loop")) {
					loop = Boolean.parseBoolean(attMap.item(j).getNodeValue());
				}else{
					System.out.print(attMap.item(j).getNodeName() + "is not recognized in the audio tag");
				}
			}
			System.out.println("");
		}else{}
		if(currentNode.hasChildNodes()) {
			getSubNodes(currentNode.getChildNodes());
		}else{}
		audioLayers.get(currentSlide).add();
	}
	public void lineParse(Node currentNode) {
		float xStart=0;
		float yStart=0;
		float xEnd=0;
		float yEnd=0;
		String lineColor=null;
		int startTime=0;
		int endTime=0;
		System.out.println(currentNode.getNodeName());
		if(currentNode.hasAttributes()) {
			NamedNodeMap attMap = currentNode.getAttributes();
			System.out.print("Attributes= ");
			for(int j=0 ; j<attMap.getLength();j++) {
				System.out.print(attMap.item(j).getNodeName() + ": " + attMap.item(j).getNodeValue() + " | ");
				if(attMap.item(j).getNodeName().equals("xend")) {
					xEnd = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("xstart")) {
					xStart = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("ystart")) {
					yStart = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("endtime")) {
					endTime = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("starttime")) {
					startTime = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("yend")) {
					yEnd = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("linecolor")) {
					lineColor = attMap.item(j).getNodeValue();
				}else{
					System.out.print(attMap.item(j).getNodeName() + "is not recognized in the line tag");
				}
			}
			System.out.println("");
		}else{}
		if(currentNode.hasChildNodes()) {
			getSubNodes(currentNode.getChildNodes());
		}else{}
		g2dLayers.get(currentSlide).registerLine(xStart,xEnd, yStart, yEnd,lineColor,startTime, endTime, currentSlide);
	}
	public void modelParse(Node currentNode) {
		
		/*System.out.println(currentNode.getNodeName());
		if(currentNode.hasAttributes()) {
			NamedNodeMap attMap = currentNode.getAttributes();
			System.out.print("Attributes= ");
			for(int j=0 ; j<attMap.getLength();j++) {
				System.out.print(attMap.item(j).getNodeName() + ": " + attMap.item(j).getNodeValue() + " | ");
				if(attMap.item(j).getNodeName().equals("urlname")) {
					urlName = attMap.item(j).getNodeValue();
				}else if(attMap.item(j).getNodeName().equals("xstart")) {
					xStart = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("ystart")) {
					yStart = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("endtime")) {
					endTime = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("starttime")) {
					startTime = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("width")) {
					width = Integer.parseInt(attMap.item(j).getNodeValue());
				}else if(attMap.item(j).getNodeName().equals("height")) {
					height = Integer.parseInt(attMap.item(j).getNodeValue());
				}else{
					System.out.print(attMap.item(j).getNodeName() + "is not recognized in the image tag");
				}
			}
			System.out.println("");
		}else{}
		if(currentNode.hasChildNodes()) {
			getSubNodes(currentNode.getChildNodes());
		}else{}
		graphics3DLayers.get(currentSlide).add();*/
	}
	
	public static void main(String[] args) {
		new XMLParser(xmlName, audioLayers,videoLayers, textLayers, imageLayers, g2dLayers, graphics3DLayers);
	}
	

}
