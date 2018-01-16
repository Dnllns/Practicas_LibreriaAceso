/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ACCESO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author n1tr0
 */
public class FicheroXml extends Fichero {

    private final Document docXML;

    public FicheroXml(String filePath) {
        super(filePath);
        if (super.existe()) {
            docXML = lecturaDocument(filePath);
        } else {
            try {
                super.getFile().createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FicheroXml.class.getName()).log(Level.SEVERE, null, ex);
            }
            docXML = genDocument();

        }

    }

//------Principales Metodos-----------------------------------------------------------
//------------------------------------------------------------------------------------
    /**
     * Generar Document a partir de la ruta del Xml
     *
     * @param ruta
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private Document lecturaDocument(String ruta) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
            return db.parse(new File(ruta));
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(FicheroXml.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     * Generar Document a partir de la ruta del Xml pasada como parametro
     *
     * @return 
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public static Document genDocument() {
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(FicheroXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        DOMImplementation implementation = builder.getDOMImplementation();
        return document = implementation.createDocument(null, "xml", null);
    }

//------LECTURA XML-----------------------------------------------------------
//------------------------------------------------------------------------------------
    /**
     * Metodo que obtiene un arraylist con los valores coincidentes con el xpath
     *
     * @param xpath
     * @return
     * @throws XPathExpressionException
     */
    public ArrayList<String> getElementValuesByXpath(String xpath) throws XPathExpressionException {

        NodeList n = leerListaNodos(xpath);
        ArrayList<String> x = new ArrayList<>();
        for (int i = 0; i < n.getLength(); i++) {
            Element e = (Element) n.item(i);
            x.add(e.getTextContent());
        }
        return x;
    }

    /**
     * Metodo que obtiene un arraylist con los valores de los atributos buscando
     * mediante el xpath y el nombre del atributo
     *
     * @param xpath
     * @param atributo
     * @return
     * @throws XPathExpressionException
     */
    public ArrayList<String> getAtributesByXpath(String xpath, String atributo) throws XPathExpressionException {
        NodeList n = leerListaNodos(xpath);
        ArrayList<String> x = new ArrayList<>();
        for (int i = 0; i < n.getLength(); i++) {
            Element e = (Element) n.item(i);
            if (e.hasAttribute(atributo)) {
                x.add(e.getAttribute(atributo));
            } else {
                x.add(null);
            }
        }
        return x;
    }

    /**
     * Obtiene un arraylist con los node de un xpath
     *
     * @param xpath
     * @return
     * @throws XPathExpressionException
     */
    public ArrayList<Node> getListaNodosByXpath(String xpath) throws XPathExpressionException {
        NodeList n = leerListaNodos(xpath);
        ArrayList<Node> lista = new ArrayList<Node>();
        for (int i = 0; i < n.getLength(); i++) {
            Element e = (Element) n.item(i);
            lista.add(e);
        }
        return lista;
    }

//------ESCRITURA XML-----------------------------------------------------------
//------------------------------------------------------------------------------------    
    public void writeToFile() {

        try {
            // escribimos el contenido en un archivo .xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(this.docXML);
            StreamResult result = new StreamResult(new File(this.getFilePath().toString()));
            transformer.transform(source, result);

        } catch (TransformerException ex) {
            Logger.getLogger(FicheroXml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que escribe el archivo xml
     */
    public void writeToFileN() {

        Transformer trans = null;
        try {
            TransformerFactory transFact = TransformerFactory.newInstance();

            //Formateamos el fichero. Añadimos sangrado y la cabecera de XML
            transFact.setAttribute("indent-number", new Integer(3));
            trans = transFact.newTransformer();
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(FicheroXml.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        StringWriter sw = new StringWriter();
        StreamResult sr = new StreamResult(sw);
        DOMSource domSource = new DOMSource(this.docXML);
        try {
            trans.transform(domSource, sr);
        } catch (TransformerException ex) {
            Logger.getLogger(FicheroXml.class.getName()).log(Level.SEVERE, null, ex);
        }

        String datosXml = sw.toString();

        super.writeInfo(datosXml);
    }

//------CONTROLAR NODOS-----------------------------------------------------------
//------------------------------------------------------------------------------------
    /**
     *
     * @param name
     * @param valor
     * @return
     */
    public Element crearElemento(String name, String valor) {
        Element e = docXML.createElement(name);
        Text t = docXML.createTextNode(valor);
        e.appendChild(t);
        return e;
    }

    /**
     *
     * @param name
     * @return
     */
    public Element crearElemento(String name) {
        return docXML.createElement(name);
    }

    /**
     *
     * @param nombre
     * @param valor
     * @return
     */
    public Attr crearAtributo(String nombre, String valor) {
        Document d = docXML;
        Attr atrib = d.createAttribute(nombre);
        atrib.setValue(valor);
        return atrib;
    }

    /**
     *
     * @param padre
     * @param hijo
     */
    public static void insertarHijo(Node padre, Node hijo) {
        padre.appendChild(hijo);
    }

    /*
    
     */
    public static void editarValorNodo(Node n, String v) {
        n.setTextContent(v);
    }

    public static void reemplazarElemento(Node padre, Node viejo, Node nuevo) {
        padre.replaceChild(nuevo, viejo);
    }

    public static boolean isElement(Node n) {
        return n.getNodeType() == Node.ELEMENT_NODE;
    }

    public Document getDocXML() {
        return docXML;
    }

    /**
     * Devuelve el nodo Root del documento
     *
     * @return
     */
    public Node getNodoRoot() {
        return docXML.getFirstChild();
    }

    /**
     * Devuelve un ArrayList con todos los atributos
     *
     * @param n
     * @return
     */
    public static ArrayList<Node> getAtributos(Node n) {
        NamedNodeMap attributes = n.getAttributes();
        ArrayList<Node> lista = null;
        if (attributes.getLength() != 0) {
            lista = new ArrayList<Node>();
            for (int i = 0; i < attributes.getLength(); i++) {
                lista.add(attributes.item(i));
            }
        }
        return lista;
    }

    public static ArrayList<Node> getHijos(Node n) {
        NodeList childNodes = n.getChildNodes();
        ArrayList<Node> lista = null;
        if (childNodes.getLength() != 0) {
            lista = new ArrayList<Node>();
            for (int i = 0; i < childNodes.getLength(); i++) {
                lista.add(childNodes.item(i));
            }
        }
        return lista;
    }

    public static void imprimeValores(ArrayList<Node> lista, String info) {
        if (lista != null) {
            System.out.println(info);
            lista.forEach(element -> {
                System.out.println("\t" + element);
            });
        }
    }

    public static void imprimeAtributos(Node n) {
        ArrayList< Node> atribs = FicheroXml.getAtributos(n);
        FicheroXml.imprimeValores(atribs, "Atributos:");
    }

    public static void imprimeHijos(Node n) {
        ArrayList<Node> hijos = FicheroXml.getHijos(n);
        System.out.println("Hijos:");
        hijos.forEach(hijo -> {
            System.out.println("\t" + hijo.getNodeName());
        });
    }

//------------Metodos Elvira---------------------------------------------------
//-----------------------------------------------------------------------------    
    public String leerString(String strXPath) throws XPathExpressionException {
        return (String) (XPathFactory.newInstance().newXPath().evaluate(strXPath, docXML, XPathConstants.STRING));
    }

    public Element leerNodo(String strXPath) {
        try {
            return (Element) (XPathFactory.newInstance().newXPath().evaluate(strXPath, docXML, XPathConstants.NODE));
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public NodeList leerListaNodos(String strXPath) throws XPathExpressionException {
        return (NodeList) (XPathFactory.newInstance().newXPath().evaluate(strXPath, docXML, XPathConstants.NODESET));
    }

}
