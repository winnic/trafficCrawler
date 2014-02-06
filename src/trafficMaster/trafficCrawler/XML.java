package trafficMaster.trafficCrawler;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.collections.map.MultiValueMap;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XML {
	public static final int RETURN_VALUE = 0;
	public static final int RETURN_BLOCK = 1;
	public static final int RETURN_LIST_OF_BLOCKS = 2;
	
	public static Object get(String url, String path, int type) {
		Object result = null;
		try {
		    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    Document document = builder.parse(new URL(url).openStream());
		    XPath xPath =  XPathFactory.newInstance().newXPath();
		    switch(type) {
			    case(RETURN_VALUE):
			    	result = xPath.compile(path).evaluate(document);
			    	break;
			    case(RETURN_BLOCK):
			    	MultiValueMap map = new MultiValueMap();
			    	Node node = (Node) xPath.compile(path).evaluate(document, XPathConstants.NODE);
			    	if (node != null) {
			    	    NodeList nodeList = node.getChildNodes();
			    	    for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
			    	        Node attribute = nodeList.item(i);
			    	        if (attribute.getNodeType() == Node.ELEMENT_NODE) {
			    	        	map.put(nodeList.item(i).getNodeName(), attribute.getFirstChild().getNodeValue());
			    	        }
			    	    }
			    	}
			    	result = map;
			    	break;
			    case(RETURN_LIST_OF_BLOCKS):
				    NodeList blocks = (NodeList) xPath.compile(path).evaluate(document, XPathConstants.NODESET);
			    	Object o[] = new Object[blocks.getLength()];
				    for (int i = 0; i < blocks.getLength(); i++) {
				    	MultiValueMap map1 = new MultiValueMap();
				    	Node node2 = blocks.item(i);
				    	if (node2 != null) {
				    	    NodeList nodeList = node2.getChildNodes();
				    	    for (int j = 0; nodeList != null && j < nodeList.getLength(); j++) {
				    	        Node attribute = nodeList.item(j);
				    	        if (attribute.getNodeType() == Node.ELEMENT_NODE) {
				    	        	map1.put(nodeList.item(j).getNodeName(), attribute.getFirstChild().getNodeValue());
				    	        }
				    	    }
				    	}
				    	o[i] = map1;
				    }
			    	result = o;
			    	break;
		    }
		} catch (Exception e) {
		    e.printStackTrace();  
		}
		return result;
	}
}
