/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso.Ficheros.Xml;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author n1tr0
 *
 * Clase especial para el control de nos nodos y la extracion de datos de los
 * mismos
 */
public class NodoXml {

    Node nodo;

    /**
     *
     * @param nodo
     */
    public NodoXml(Node nodo) {
        this.nodo = nodo;
    }

    /**
     * Devuelve un ArrayList con los atributos del nodo
     *
     * @return
     */
    public ArrayList<String> getAtributos() {
        if (nodo.hasAttributes()) {
            ArrayList<String> atributos = new ArrayList<>();
            for (int i = 0; nodo.getAttributes().getLength() > i; i++) {
                atributos.add(nodo.getAttributes().item(i).toString());
            }
            return atributos;
        } else {
            return null;
        }
    }

    /**
     * Devuelve un ArrayList con los atributos del nodo
     *
     * @param nombreAtributo
     * @return
     */
    public String getAtributo(String nombreAtributo) {
        if (nodo.hasAttributes()) {
            return nodo.getAttributes().getNamedItem(nombreAtributo).
                    toString().substring(
                            nombreAtributo.length() + 2,
                            nodo.getAttributes().getNamedItem(nombreAtributo).toString().length() - 1
                    );
        } else {
            return null;
        }
    }

    /**
     * Devuelve un ArrayList con los hijos del nodo
     *
     * @return
     */
    public ArrayList<Node> getHijos() {

        if (nodo.getChildNodes().getLength() != 0) {
            ArrayList<Node> lista = new ArrayList<>();
            for (int i = 0; i < nodo.getChildNodes().getLength(); i++) {
                lista.add(nodo.getChildNodes().item(i));
            }
            return lista;
        } else {
            return null;
        }
    }

    /**
     * Devuelve un arraylist con los nodos hijos cuyo nombre coincide con el pasado
     * por parametro
     * @param nombre
     * @return 
     */
    public ArrayList<Node> getHijos(String nombre) {
        if (nodo.getChildNodes().getLength() != 0) {
            ArrayList<Node> lista = new ArrayList<>();
            for (int i = 0; i < nodo.getChildNodes().getLength(); i++) {
                if (nodo.getChildNodes().item(i).getNodeName().equals(nombre)) {
                    lista.add(nodo.getChildNodes().item(i));
                }
            }
            return lista;
        } else {
            return null;
        }
    }

    /**
     * Devuelve el nodo hijo
     *
     * @param nombreHijo
     * @return
     */
    public Node getHijo(String nombreHijo) {
        Node buscado = null;
        for (Node hijo : this.getHijos()) {
            if (hijo.getNodeName().equals(nombreHijo)) {
                buscado = hijo;
            }
        }
        return buscado;
    }

    /**
     * Devuelve el valor del nodo hijo del nodo pasado por parametro
     *
     * @param nombreHijo
     * @return
     */
    public String getValorHijo(String nombreHijo) {
        return ((Element) nodo).getElementsByTagName(nombreHijo).item(0).getTextContent();
    }

    /**
     *
     * @return
     */
    public String getValorNodo() {
        return ((Element)nodo).getTextContent();
    }

    /**
     * Devuelve un Element con el nombre y el valor pasado por parametro
     *
     * @param name
     * @param valor
     * @return
     */
    public Element crearElemento(String name, String valor) {

        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(NodoXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        Element e = document.createElement(name);
        if (!valor.isEmpty()) {
            e.appendChild(document.createTextNode(valor));
        }
        return e;
    }

    /**
     * Modifica el valor del atributo coincidente con el nombre pasado por
     * parametro
     *
     * @param nombreAtributo
     * @param nuevoValor
     */
    public void modificarAtributo(String nombreAtributo, String nuevoValor) {
        ((Element) nodo).setAttribute(nombreAtributo, nuevoValor);
    }

    /**
     * Elimina el atributo del elemento con el nombre pasado por parametro
     *
     * @param nombreAtributo
     */
    public void eliminarAtributo(String nombreAtributo) {
        ((Element) nodo).removeAttribute(nombreAtributo);
    }

    /**
     * Añade el nodo hijo pasado por parametro como hijo del nodo padre pasado
     * por parametro
     *
     * @param hijo
     */
    public void insertarHijo(Node hijo) {
        ((Element) nodo).appendChild(hijo);
    }

    /**
     * Elimina el nodo pasado por parametro del padre
     *
     * @param nombreHijo
     */
    public void eliminarHijo(String nombreHijo) {
        ((Element) nodo).getParentNode().removeChild(this.getHijo(nombreHijo));
    }

    /**
     * Elimina todos los hijos del nodo
     *
     */
    public void borrarHijos() {
        this.getHijos().forEach((hijo) -> {
            nodo.removeChild(hijo);
        });
    }

    /**
     * Modifica el valor del nodo con el valor pasado
     *
     * @param valor
     */
    public void editarValorNodo(String valor) {
        nodo.setTextContent(valor);
    }

    /**
     * Reemplaza el nodo viejo con el nuevo en el nodo padre
     *
     * @param viejo
     * @param nuevo
     */
    public void reemplazarHijo(Element viejo, Element nuevo) {
        ((Element) nodo).replaceChild(nuevo, viejo);
    }

    /**
     *
     * @return
     */
    public Node getNodo() {
        return nodo;
    }

    
    
}
