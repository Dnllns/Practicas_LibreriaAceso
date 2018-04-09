package Acceso;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class FicheroXml extends Fichero {

    private Document docXML;

    /**
     * Constructor
     *
     * @param rutaDocumentoXml
     */
    public FicheroXml(String rutaDocumentoXml) {
        super(rutaDocumentoXml);
        cargarDocumentXml(rutaDocumentoXml);
    }

    /**
     * Carga el xml en la estuctura, si existe
     *
     * @param ruta
     */
    private void cargarDocumentXml(String ruta) {
        if (this.existe()) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            try {
                db = dbf.newDocumentBuilder();
                docXML = db.parse(new File(ruta));
            } catch (Exception ex) {
                System.out.print(ex.getLocalizedMessage());
            }
        }
    }

//------LECTURA XML-----------------------------------------------------------
//------------------------------------------------------------------------------------
    public String getString(String strXPath) {
        String s = null;
        try {
            s = (String) (XPathFactory.newInstance().newXPath().evaluate(strXPath, docXML, XPathConstants.STRING));
        } catch (XPathExpressionException e) {
        }
        return s;
    }

    public Node getNodo(String strXPath) {
        try {
            return (Element) (XPathFactory.newInstance().newXPath().evaluate(strXPath, docXML, XPathConstants.NODE));
        } catch (XPathExpressionException e) {
            return null;
        }
    }

    public ArrayList<Node> getListaNodos(String strXPath) {

        NodeList list = null;
        ArrayList<Node> l = new ArrayList();
        try {
            list = (NodeList) (XPathFactory.newInstance().newXPath().evaluate(strXPath, docXML, XPathConstants.NODESET));
            for (int i = 0; i < list.getLength(); i++) {
                l.add(list.item(i));
            }
        } catch (XPathExpressionException e) {
        }
        return l;
    }

    public ArrayList getAtributos(Node n) {

        ArrayList<String> atributos = new ArrayList<>();
        if (n.hasAttributes()) {
            for (int i = 0; n.getAttributes().getLength() > i; i++) {
                atributos.add(n.getAttributes().item(i).toString());
            }
        }
        return atributos;
    }

    public ArrayList<Node> getHijos(Node n) {

        NodeList childNodes = n.getChildNodes();
        ArrayList<Node> lista = null;
        if (childNodes.getLength() != 0) {
            lista = new ArrayList<>();
            for (int i = 0; i < childNodes.getLength(); i++) {
                lista.add(childNodes.item(i));
            }
        }
        return lista;
    }

    public Node getNodoRoot() {
        return docXML.getFirstChild();
    }

    public Document getDocXML() {
        return docXML;
    }

//------ESCRITURA XML-----------------------------------------------------------
//------------------------------------------------------------------------------
    public void guardarDocumento() {

        try {
            // escribimos el contenido en un archivo .xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(this.docXML);
            StreamResult result = new StreamResult(super.getFilePath().toFile());
            transformer.transform(source, result);

        } catch (TransformerException ex) {
            Logger.getLogger(FicheroXml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//------CONTROLAR NODOS-----------------------------------------------------------
//--------------------------------------------------------------------------------
//
    public Element crearElemento(String name, String valor) {
        Element e = docXML.createElement(name);
        Text t = docXML.createTextNode(valor);
        e.appendChild(t);
        return e;
    }

    public Element crearElemento(String strNombre) {
        return docXML.createElement(strNombre);
    }

    public void setAtributo(Element e, String nombre, String valor) {
        e.setAttribute(nombre, valor);
    }

    public void eliminarAtributo(Element e, String nombre) {
        e.removeAttribute(nombre);
    }

    public void setHijo(Node padre, Node hijo) {
        padre.appendChild(hijo);
    }

    public void borrarNodo(Node hijo, Node padre) {
        padre.removeChild(hijo);
    }

    public void borrarHijos(Node nodo) {
        ArrayList<Node> hijos = this.getHijos(nodo);
        for (Node h : hijos) {
            nodo.removeChild(h);
        }
    }

    public void editarValorNodo(Node n, String v) {
        n.setTextContent(v);
    }

    public void reemplazarNodo(Node padre, Node viejo, Node nuevo) {
        padre.replaceChild(nuevo, viejo);
    }

}
