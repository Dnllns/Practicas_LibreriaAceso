package NEGOCIO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import ACCESO.ConexionBBDD;
import java.util.ArrayList;

public class Cine {

    int id;
    String nombre;
    String direccion;
    String lenguaje;
    ArrayList<Pelicula> peliculas;

    public Cine(int id, String nombre, String direccion, String lenguaje, ArrayList<Pelicula> peliculas) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.lenguaje = lenguaje;
        this.peliculas = peliculas;
    }

    public Cine(int id) {
        this.id = id;
    }

    public boolean existePeliculaBBDD(int idPelicula) {

        boolean resultado = false;
        Statement consulta;
        ConexionBBDD conex = ConexionBBDD.iniciarConexion();
        try {
            // Crear objeto para la conexion
            consulta = conex.getConexion().createStatement();
            String instruccion = "select * from cinema_film where cinema_cod = " + this.id + " and film_cod = " + idPelicula;
            // Realizar la consulta a la base de datos
            ResultSet registros = consulta.executeQuery(instruccion);
            while (registros.next()) {
                resultado = true;
            }
            registros.close();
            consulta.close();
            conex.cerrar();
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
        }
        return resultado;

    }

    public boolean addPeliculaBBDD(Pelicula p) {

        boolean insertado = false;
        if (!this.existePeliculaBBDD(p.getId())) {
            Statement consulta;
            ConexionBBDD conex = ConexionBBDD.iniciarConexion();
            try {
                //Crear objeto para la conexion
                consulta = conex.getConexion().createStatement();
                String instruccion
                        = "INSERT INTO `sakila`.`cinema_film` (`cinema_cod`, `film_cod`) VALUES ('"
                        + this.id + "', '" + p.getId() + "')";
                //Realizar la consulta a la base de datos
                int registros = consulta.executeUpdate(instruccion);
                insertado = registros == 1; //Comprobacion booleana
                consulta.close();
                conex.cerrar();
            } catch (SQLException ex) {
                System.out.print(ex.getLocalizedMessage());
            }
        }
        return insertado;

    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

}
