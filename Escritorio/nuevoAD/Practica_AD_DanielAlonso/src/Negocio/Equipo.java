/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Acceso.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumnoDAM
 */
public class Equipo {

    private int id;
    private String nombre;
    private ArrayList<EntrenadorTemporada> entrenadores;
    private ArrayList<Competicion> competiciones;
    private Ciudad ciudad;

    //ID ultimo entrenador dado de alta en la base de datos
    //select cod_entrenador from equipo_entrenador where cod_equipo = 1 and temporada = (select max(temporada) from equipo_entrenador where cod_equipo = 1);
    public Equipo(int id) {
        this.id = id;
    }

    public void insertarCompeticion(int idCompeticion) {

        String instruccion = "INSERT INTO competicion_equipo VALUES "
                + "(" + id + ", " + idCompeticion + ")";
        ConexionBD conexion = ConexionBD.getBD();
        try {
            // Crear statement consulta
            Statement consulta = conexion.getConexion().createStatement();
            // Realizar la consulta a la base de dato
            consulta.executeUpdate(instruccion);
            consulta.close();
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
        }

    }

    /**
     * Carga el array de entrenadores con los entrenadores y su temporada
     */
    public void cargarEntrenadores() {

        entrenadores = new ArrayList();
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "select cod_entrenador, temporada from equipo_entrenador "
                + "where cod_equipo = " + id;
        try {
            consulta = conex.getConexion().createStatement();
            ResultSet respuesta = consulta.executeQuery(instruccion);
            while (respuesta.next()) {
                int idEntrenador = respuesta.getInt("cod_entrenador");
                int temporada = respuesta.getInt("temporada");
                Entrenador e = new Entrenador(idEntrenador);
                EntrenadorTemporada et = new EntrenadorTemporada(e, temporada);
                entrenadores.add(et);
            }
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que consulta a la base de datos y obtiene el entrenador de la
     * ultima temporada existente
     *
     * @return
     */
    public Entrenador getEntrenadorActual() {

        Entrenador e = null;
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "select cod_entrenador from equipo_entrenador "
                + "where cod_equipo = " + id + " and temporada = "
                + "(select max(temporada) from equipo_entrenador where cod_equipo = " + id + ")";

        try {
            consulta = conex.getConexion().createStatement();
            ResultSet respuesta = consulta.executeQuery(instruccion);
            if (respuesta.next()) {
                int idEntrenador = respuesta.getInt("cod_entrenador");
                e = new Entrenador(idEntrenador);
            }
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;

    }

 

    /**
     * Inserta el equipo en la base de datos
     */
    public void insertar() {

        ConexionBD conexion = ConexionBD.getBD();
        try {
            // Crear statement consulta
            Statement consulta = conexion.getConexion().createStatement();
            // Realizar la consulta a la base de datos
            String instruccion = "INSERT INTO equipo VALUES ( NULL, "
                    + "'" + this.nombre + "',"
                    + "'" + this.ciudad.getId() + "'" + ")";
            consulta.executeUpdate(instruccion, Statement.RETURN_GENERATED_KEYS);
            ResultSet primaryKey = consulta.getGeneratedKeys();
            if (primaryKey.next()) {
                this.id = primaryKey.getInt(1);
            }
            consulta.close();
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
        }
    }

    /**
     * Obtiene de la bd el entrenador correspondiente al id y carga sus datos
     */
    public void cargar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "select * from equipo where id = " + id;
        try {
            consulta = conex.getConexion().createStatement();
            ResultSet respuesta = consulta.executeQuery(instruccion);
            if (respuesta.next()) {
                String nombre = respuesta.getString("nombre");
                this.nombre = nombre;
                //obtener ciudad
                int idCiudad = Integer.parseInt(respuesta.getString("cod_ciudad"));
                this.ciudad = new Ciudad(idCiudad);
                //obtener entrenador

                //(Recargar el array de entrenador-temporada)
            }
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Modifica el registro correspondiente al id con los datos de las
     * estructuras
     */
    public void modificar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "UPDATE equipo SET nombre='" + nombre + "', "
                + "cod_ciudad='" + this.ciudad.getId() + "' "
                + " WHERE id=" + id;
        try {
            consulta = conex.getConexion().createStatement();
            consulta.executeUpdate(instruccion);
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Elimina el registro de la bd
     */
    public void eliminar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "DELETE FROM equipo WHERE id =" + id;
        try {
            consulta = conex.getConexion().createStatement();
            consulta.executeUpdate(instruccion);
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //GETTER Y SETTER
    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Entrenador getEntrenador() {
        return entrenadores.get(entrenadores.size() - 1).getEntrenador();
    }

    public void setEntrenador(Entrenador entrenador, int temporada) {
        this.entrenadores.add(new EntrenadorTemporada(entrenador, temporada));
    }

    public ArrayList<Competicion> getCompeticiones() {
        return competiciones;
    }

    public void setCompeticiones(ArrayList<Competicion> competiciones) {
        this.competiciones = competiciones;
    }

}
