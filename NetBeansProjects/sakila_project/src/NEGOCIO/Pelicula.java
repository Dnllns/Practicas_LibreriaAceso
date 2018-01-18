package NEGOCIO;

import org.w3c.dom.Element;

import ACCESO.FicheroXml;
import UTILIDADES.Configuracion.Configuracion;
import java.util.ArrayList;
import java.util.Iterator;

public class Pelicula {

    String id;
    String titulo;
    String descripcion;
    Lenguaje lenguaje;
    ArrayList<String> actorIds;

    private final static String CINE_XML = Configuracion.getConfiguracion().getProperty("CINE_XML");

    public Pelicula(String id, String titulo, String descripcion, Lenguaje lenguaje, ArrayList<String> actorIds) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.lenguaje = lenguaje;
        this.actorIds = actorIds;
    }

    public Pelicula(String id, String titulo, String descripcion, Lenguaje lenguaje) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.lenguaje = lenguaje;
    }

//---------------------------------------------------------
//  EJERCICIO 3 -----------------------------------------------

    /**
     * Metodo que genera una pelicula en estructura xml
     *
     * @param p
     * @return
     */
    public static Element generateElementPelicula(Pelicula p) {

        Element pelicula = FicheroXml.crearElemento("pelicula");
        pelicula.setAttribute("codigo", p.getId() + "");
        pelicula.setAttribute("lenguaje", p.getLenguaje().getId());
        Element titulo = FicheroXml.crearElemento("titulo", p.getTitulo());
        Element descripcion = FicheroXml.crearElemento("descripcion", p.getDescripcion());
        pelicula.appendChild(titulo);
        pelicula.appendChild(descripcion);

        Element actores = FicheroXml.crearElemento("actores");
        //Anadir los actores
        if (p.getActorIds() != null) {
            Iterator<String> iActorId = p.getActorIds().iterator();
            while (iActorId.hasNext()) {
                String id = iActorId.next();
                Element actor = FicheroXml.crearElemento("actor");
                actor.setAttribute("id", id);
                actores.appendChild(actor);
            }
            pelicula.appendChild(actores);
        }
        return pelicula;
    }

    /**
     * Metodo que comprueba si existe una pelicula en el xml
     *
     * @param p
     * @return
     */
    public boolean existePeliculaXml(Pelicula p) {
        boolean existe = false;
        FicheroXml f = new FicheroXml(CINE_XML);
        //Obtener los id de las peliculas del xml
        ArrayList<String> lIdPeliculas = f.getAtributesByXpath("/cine/peliculas/pelicula", "codigo");
            for (String pId : lIdPeliculas) {
                if (pId.equals(p.getId())) {
                    //La pelicula existe en el xml
                    existe = true;
                    break;
                }
            }
        
        return existe;
    }

    /**
     * Metodo que escribe una pelicula en el xml
     * Comprueba si existe y si no existe la inserta
     * 
     * @param p 
     */
    public void escribirPeliculaEnXml(Pelicula p) {
        if (!existePeliculaXml(p)) {
            FicheroXml f = new FicheroXml(CINE_XML);
            Element padre = f.leerNodo("/cine/peliculas");
            Element pelicula = generateElementPelicula(p);
            padre.appendChild(pelicula);
            f.getFile().delete();
            f.writeToFileN();
        }

    }

//------------------------------------------------------------
    
    
    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ArrayList<String> getActorIds() {
        return actorIds;
    }

    public Lenguaje getLenguaje() {
        return lenguaje;
    }

}
