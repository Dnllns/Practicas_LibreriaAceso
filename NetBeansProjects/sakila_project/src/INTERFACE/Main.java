/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package INTERFACE;

import ACCESO.FicheroXml;
import NEGOCIO.Cine;
import NEGOCIO.Pelicula;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author n1tr0
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Cine c = new Cine(1);
        //boolean res = c.addPeliculaBBDD(new Pelicula(7, "holaaa"));
        //System.out.println(res);
        ArrayList<String> aid = new ArrayList();
        aid.add("1");
        aid.add("1");

        Pelicula p = new Pelicula(1, "a", "d", "1", aid);
        ArrayList<Pelicula> pelis = new ArrayList();
        pelis.add(p);
        pelis.add(p);
        pelis.add(p);

        Cine cs = new Cine(1, "nam", "dir", "es", pelis);
        //Crear fichero xml
        FicheroXml doc = null;
        doc = new FicheroXml("/home/n1tr0/Escritorio/cine.xml");
        crearEstructuraCine(doc, cs);

    }

    public static void crearEstructuraCine(FicheroXml doc, Cine c) {

        //Crear el archivo Xml
        Element cine = doc.crearElemento("cine");
        Element nombre = doc.crearElemento("nombre", c.getNombre());
        Element lenguaje = doc.crearElemento("lenguaje", c.getLenguaje());
        Element direccion = doc.crearElemento("direccion", c.getDireccion());
        Element peliculas = doc.crearElemento("peliculas");

        //Añadir las peliculas
        Iterator<Pelicula> iPeliculas = c.getPeliculas().iterator();
        while (iPeliculas.hasNext()) {
            Pelicula p = iPeliculas.next();
            Element pelicula = doc.crearElemento("pelicula");
            pelicula.setAttribute("codigo", p.getId() + "");
            pelicula.setAttribute("lenguaje", p.getIdLenguaje());
            Element titulo = doc.crearElemento("titulo", p.getTitulo());
            Element descripcion = doc.crearElemento("descripcion", p.getDescripcion());

            Element actores = doc.crearElemento("actores");
            //Añadir los actores
            Iterator<String> iActorId = p.getActorIds().iterator();
            while (iActorId.hasNext()) {
                String id = iActorId.next();
                Element actor = doc.crearElemento("actor");
                actor.setAttribute("id", id);
                actores.appendChild(actor);
            }
            pelicula.appendChild(titulo);
            pelicula.appendChild(descripcion);
            pelicula.appendChild(actores);
            peliculas.appendChild(pelicula);
        }
        cine.appendChild(nombre);
        cine.appendChild(lenguaje);
        cine.appendChild(direccion);
        cine.appendChild(peliculas);

        doc.getNodoRoot().appendChild(cine);
        doc.writeToFileN();

    }

}
