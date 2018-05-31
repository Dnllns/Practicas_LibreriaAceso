package Acceso.Ficheros.Xml;

import Acceso.Ficheros.Fichero;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author n1tr0
 */
public class FicheroXml extends Fichero {

    private Document docXML;

    /**
     * Constructor
     *
     * @param rutaDocumentoXml
     */
    public FicheroXml(String rutaDocumentoXml) {
        super(rutaDocumentoXml);
    }

    /**
     * Carga el xml en la estuctura, si existe
     *
     */
    public void cargarDocumentXml() {
        if (this.existe()) {
            try {
                docXML = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(super.toFile());
            } catch (Exception ex) {
                System.out.print(ex.getLocalizedMessage());
            }
        }
    }

    /**
     * Devuelve el string correscpondiente al xpath pasado por parametro
     *
     * @param sentenciaXpath
     * @return
     */
    public String getValorNodo(String sentenciaXpath) {
        String string = null;
        try {
            string = (String) (XPathFactory.newInstance().newXPath().evaluate(sentenciaXpath, docXML, XPathConstants.STRING));
        } catch (XPathExpressionException e) {
        }
        return string;
    }

    /**
     * Devuelbe el nodo correspondiente al xpath pasado por parametro
     *
     * @param sentencia
     * @return
     */
    public Node getNodo(String sentencia) {
        try {
            return (Node) (XPathFactory.newInstance().newXPath().evaluate(sentencia, docXML, XPathConstants.NODE));
        } catch (XPathExpressionException e) {
            return null;
        }
    }

    /**
     * Devuelve un ArrayList con los nodos correspondientes al xpath pasado por
     * parametro
     *
     * @param sentencia
     * @return
     */
    public ArrayList<Node> getNodos(String sentencia) {

        NodeList nodos = null;
        try {
            nodos = (NodeList) (XPathFactory.newInstance().newXPath().evaluate(sentencia, docXML, XPathConstants.NODESET));
        } catch (XPathExpressionException ex) {
        }
        if (nodos != null) {
            ArrayList<Node> lista = new ArrayList();
            for (int i = 0; i < nodos.getLength(); i++) {
                lista.add(nodos.item(i));
            }
            return lista;
        } else {
            return null;
        }
    }

    /**
     * Devuelve el nodo raiz del Document
     *
     * @return
     */
    public Node getNodoRoot() {
        return docXML.getFirstChild();
    }

    /**
     * Devuelve el Document
     *
     * @return
     */
    public Document getDocXML() {
        return docXML;
    }

    /**
     * Guardado del Document
     */
    /**
     * Escribe el contenido del Document en el archivo xml al que pertenece
     */
    public void guardarDocumento() {
        try {
            TransformerFactory.newInstance().newTransformer().transform(
                    new DOMSource(this.docXML),
                    new StreamResult(super.toFile())
            );
        } catch (TransformerException ex) {
            Logger.getLogger(FicheroXml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
