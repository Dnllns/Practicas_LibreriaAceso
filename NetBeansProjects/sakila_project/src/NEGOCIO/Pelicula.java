package NEGOCIO;

import org.w3c.dom.Element;

import ACCESO.FicheroXml;
import java.util.ArrayList;

public class Pelicula {

    int id;
    String titulo;
    String descripcion;
    String idLenguaje;
    ArrayList <String> actorIds;

    public Pelicula(int id, String titulo, String descripcion, String idLenguaje, ArrayList<String> actorIds) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.idLenguaje = idLenguaje;
        this.actorIds = actorIds;
    }

   

    public int getId() {
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

    public String getIdLenguaje() {
        return idLenguaje;
    }






}
