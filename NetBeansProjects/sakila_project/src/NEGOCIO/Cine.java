package NEGOCIO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import ACCESO.ConexionBD;

import java.util.ArrayList;

public class Cine {

    String id;
    String nombre;
    String direccion;
    Lenguaje lenguaje;
    ArrayList<Pelicula> peliculas;

    public Cine(String id, String nombre, String direccion, Lenguaje lenguaje, ArrayList<Pelicula> peliculas) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.lenguaje = lenguaje;
        this.peliculas = peliculas;
    }

    public Cine(String id, Lenguaje l) {
        this.id = id;
    }

//---------------------------------------------------------------------------
    public boolean existePeliculaBD(String idPelicula) {

        boolean resultado = false;
        Statement consulta;
        ConexionBD conex = ConexionBD.iniciarConexion();
        try {
            // Crear objeto para la conexion
            consulta = conex.getConexion().createStatement();
            String instruccion = "select * from cinema_film where cinema_cod = " + this.id + " and film_cod = "
                    + idPelicula;
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

    public boolean addPeliculaBD(Pelicula p) {

        boolean insertado = false;
        if (!this.existePeliculaBD(p.getId())) {
            Statement consulta;
            ConexionBD conex = ConexionBD.iniciarConexion();
            try {
                // Crear objeto para la conexion
                consulta = conex.getConexion().createStatement();
                String instruccion = "INSERT INTO `sakila`.`cinema_film` (`cinema_cod`, `film_cod`) VALUES ('" + this.id
                        + "', '" + p.getId() + "')";
                // Realizar la consulta a la base de datos
                int registros = consulta.executeUpdate(instruccion);
                insertado = registros == 1; // Comprobacion booleana
                consulta.close();
                conex.cerrar();
            } catch (SQLException ex) {
                System.out.print(ex.getLocalizedMessage());
            }
        }
        return insertado;

    }

//------------------------------------------------------------
//-------------------------------EJERCICIO 4------------------
    
    public void ej4(){
        escribirPeliculasEnXml(getPeliculasMismoIdiomaCine());
    }
    
    /**
     * Obtener las peliculas del mismo idioma de la base de datos
     * @return 
     */
    public ArrayList<Pelicula> getPeliculasMismoIdiomaCine() {

        Statement consulta;
        ArrayList<Pelicula> peliculasIdioma = new ArrayList();
        ConexionBD conex = ConexionBD.iniciarConexion();
        String instruccion;
        ResultSet registros;
        try {
            // Consulta del lenguaje de la pelicula en la tabla film
            consulta = conex.getConexion().createStatement();
            instruccion = "select * from film where language_id =" + this.getLenguaje().getId();
            // Realizar la consulta a la base de datos
            registros = consulta.executeQuery(instruccion);
            while (registros.next()) {
                Pelicula p = new Pelicula(
                        registros.getString("film_id"),
                        registros.getString("title"),
                        registros.getString("description"),
                        new Lenguaje(registros.getString("language_id"), "")
                );
                peliculasIdioma.add(p);
            }

        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
        }
        return peliculasIdioma;
    }

    
    public void escribirPeliculasEnXml(ArrayList <Pelicula> lPeliculas){
        
        lPeliculas.forEach(p ->{
            p.escribirPeliculaEnXml(p);
        });
        
    }

    //----------------------------------------------------
    //-----------------FINAL-EJERCICIO-4------------------
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Lenguaje getLenguaje() {
        return lenguaje;
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setLenguaje(Lenguaje lenguaje) {
        this.lenguaje = lenguaje;
    }

    public void setPeliculas(ArrayList<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    //METODO extra
    public ArrayList<String> getIdPeliculasCine() {

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
            instruccion = "select * from cinema_film where cinema_cod =" + this.getId();
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

}
