/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.model;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.xpath.*;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
/**
 *
 * @author Jarvis
 */
public class XmlParsing {
    public String getDataString(File file, String query) throws XPathExpressionException{//exception передается наверх ибо вызывающий виновать
        String data;    
        XPathExpression xPathExpression = getExpression(query);
        data = (String) xPathExpression.evaluate(getDoc(file), XPathConstants.STRING);
        return data;
    }
    public String[] getDataList(File file, String query) throws XPathExpressionException{
        String[] data;   
        XPathExpression xPathExpression = getExpression(query);
        NodeList nodeList = (NodeList) xPathExpression.evaluate(getDoc(file), XPathConstants.NODESET);
        data = new String[nodeList.getLength()];
        for (int i = 0; i < nodeList.getLength(); i++)
            data[i]=(nodeList.item(i).getNodeValue());
        return data;
    }
    private Node getNode(Document doc, String query) throws XPathExpressionException{
        Node data;    
        XPathExpression xPathExpression = getExpression(query);
        data = (Node) xPathExpression.evaluate(doc, XPathConstants.NODE);
        return data;
    }
    /**
     * 
     * @param file
     * @param nodeName
     * @param attrs - map of names and values of attributes
     * @throws java.lang.Exception invalid attributes
     */
    public void addNode(File file,String nodeName, Map<String,String> attrs) throws Exception{
        Document doc = getDoc(file);
        Node node = doc.getElementsByTagName(nodeName).item(0);
        Node newNode = node.cloneNode(true);
        setNewAttrs(newNode,attrs);
        node.getParentNode().appendChild(newNode);
        saveData(doc,file);
    }
    public void deleteNode(File file, String query) throws XPathExpressionException{
        Document doc = getDoc(file);
        Node node = getNode(doc,query);
        node.getParentNode().removeChild(node);
        saveData(doc,file);
    }
    public void setAttr(File file, String pathToAttr, String value) throws XPathExpressionException{
        Document doc = getDoc(file);
        Node attr = getNode(doc,pathToAttr);System.out.println(attr);
        attr.setTextContent(value);
        saveData(doc,file);
    }
    public void modifyNode(File file, String query, String value){
        
    }
    private boolean checkSufficiencyArgs(Node copiedNode, Map<String,String> attrs){
        boolean result=true;
        NamedNodeMap nodeAttrs = copiedNode.getAttributes();
        if(!copiedNode.hasAttributes())result = false;
        else if(nodeAttrs.getLength()!=attrs.size())result=false;
        
        if(result){
            String[] keysAttrs = new String[attrs.size()]; 
            attrs.keySet().toArray(keysAttrs);
            for(int i=0;i<attrs.size();i++){
                if(((Element) copiedNode).getAttribute(keysAttrs[i]).isEmpty())result=false;
            }
        }
        return result;
    }
    private void setNewAttrs(Node newNode, Map<String,String> attrs) throws Exception{
        if(checkSufficiencyArgs(newNode, attrs)){
            String[] keysAttrs = new String[attrs.size()]; 
            attrs.keySet().toArray(keysAttrs);
            for(int i=0;i<attrs.size();i++){
                ((Element) newNode).setAttribute(keysAttrs[i], attrs.get(keysAttrs[i]));
            }
        } else throw new Exception("Invalid attributes");
    }
    
    private void saveData(Document doc, File file){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XmlParsing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XmlParsing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Document getDoc(File file){
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        // включаем поддержку пространства имен XML
        builderFactory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        try {
            builder = builderFactory.newDocumentBuilder();
            doc = builder.parse(file);
            
        } catch (ParserConfigurationException | SAXException | IOException e) {
        }
        return doc;
    }
    private XPathExpression getExpression(String query) throws XPathExpressionException{
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        XPathExpression xPathExpression = xpath.compile(query);
        return xPathExpression;
    }
}
