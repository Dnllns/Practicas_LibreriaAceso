/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package INTERFACE;

import ACCESO.ConexionBD;
import ACCESO.FicheroXml;
import NEGOCIO.Cine;
import NEGOCIO.Lenguaje;
import NEGOCIO.Pelicula;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        /*
    	
    	//INICIALIZAR DATOS

        //AUTORES
        ArrayList<String> autoresId = new ArrayList();
        autoresId.add("1");
        autoresId.add("2");
        //PELICULAS
        ArrayList<Pelicula> pelis = new ArrayList();
        Pelicula p1 = new Pelicula("1", "Pelicula 1", "Descripcion 1", "1", autoresId);
        Pelicula p2 = new Pelicula("2", "Pelicula 2", "Descripcion 2", "1", autoresId);
        pelis.add(p1);
        pelis.add(p2);
        //CINE
        Cine cs = new Cine("1", "Cine 1", "Direccion 1", "1", pelis);
    	
        
        //Crear fichero xml
        FicheroXml doc = null;
        doc = new FicheroXml("C:\\Users\\alumnoDAM\\Desktop\\pACCD\\cine.xml");
        crearEstructuraCine(doc, cs);

	
        Cine cs = new Cine("1");
        cs.setLenguaje(new Lenguaje("1", ""));
        
        ArrayList<Pelicula> pIdioma = cs.getPeliculasMismoIdiomaCine();
        pIdioma.forEach(p ->{
        	System.out.println(p.getTitulo());
        });

        
        
        
        Pelicula p2 = new Pelicula("3", "Pelicula 2", "Descripcion 2", new Lenguaje("1", ""), new ArrayList());
        p2.escribirPeliculaEnXml(p2);
       
         */
        Cine c = new Cine(
                "1",
                "C1",
                "D1",
                new Lenguaje("1", ""),
                new ArrayList()
        );

        ej6(c);

    }

//-------------------------------------------------------------------------------
//Ejercicio 5 -------------------------------------------------------------------
    /**
     * Metodo que crea la estructura del cine
     *
     * @param doc
     * @param c
     */
    public static Element crearEstructuraCine(Cine c) {

        //Crear el archivo Xml
        Element cine = FicheroXml.crearElemento("cine");
        Element nombre = FicheroXml.crearElemento("nombre", c.getNombre());
        Element lenguaje = FicheroXml.crearElemento("lenguaje", c.getLenguaje().getId());
        Element direccion = FicheroXml.crearElemento("direccion", c.getDireccion());
        Element peliculas = FicheroXml.crearElemento("peliculas");

        //Anadir las peliculas
        Iterator<Pelicula> iPeliculas = c.getPeliculas().iterator();
        while (iPeliculas.hasNext()) {
            Pelicula p = iPeliculas.next();
            Element pelicula = Pelicula.generateElementPelicula(p);
            peliculas.appendChild(pelicula);
        }
        cine.appendChild(nombre);
        cine.appendChild(lenguaje);
        cine.appendChild(direccion);
        cine.appendChild(peliculas);

        return cine;
    }

//-----------------------------------------------------------------------    
//Ejercicio 6 --------------------------------------------------------
    
    public static void ej6(Cine c) {
        
        ArrayList<String> ids = getIdPeliculasCine(c);
        ArrayList<Pelicula> pelis = getPeliculasIdBD(c, ids);
        imprimePeliculas(pelis);
        
    }

    public static ArrayList<String> getIdPeliculasCine(Cine c) {

        Statement consulta;
        ArrayList<Pelicula> pelicula = new ArrayList();
        ConexionBD conex = ConexionBD.iniciarConexion();
        String instruccion;
        ResultSet registros;
        ArrayList<String> idPeliculas = new ArrayList();
        try {
            // Obtener ids de la tabla cinema_film
            // Crear objeto para la conexion
            consulta = conex.getConexion().createStatement();
            instruccion = "select * from cinema_film where cinema_cod =" + c.getId();
            // Realizar la consulta a la base de datos
            registros = consulta.executeQuery(instruccion);
            idPeliculas = new ArrayList();
            while (registros.next()) {
                idPeliculas.add(registros.getString("film_cod"));
            }
            registros.close();
            consulta.close();
            conex.cerrar();

        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
        }
        return idPeliculas;
    }

    public static ArrayList<Pelicula> getPeliculasIdBD(Cine c, ArrayList<String> lId) {

        Statement consulta;
        ArrayList<Pelicula> peliculas = new ArrayList();
        ConexionBD conex = ConexionBD.iniciarConexion();
        String instruccion;
        ResultSet registros;
        try {
            for (String pId : lId) {
                // Consulta del lenguaje de la pelicula en la tabla film
                consulta = conex.getConexion().createStatement();
                instruccion = "select * from film where film_id =" + pId;
                // Realizar la consulta a la base de datos
                registros = consulta.executeQuery(instruccion);
                while (registros.next()) {
                    Pelicula p = new Pelicula(
                            registros.getString("film_id"),
                            registros.getString("title"),
                            registros.getString("description"),
                            new Lenguaje(registros.getString("language_id"), "")
                    );
                    peliculas.add(p);
                }

            }

        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
        }
        return peliculas;
    }
    
    public static void imprimePeliculas(ArrayList <Pelicula> lPeliculas){
    
        lPeliculas.forEach( p ->{
            System.out.println(
            "-----------\n"+
            "\nPelicula: " + p.getTitulo()+
            "\nLenguaje: " + p.getLenguaje().getId()
            
            );
        });
    
    }

}
