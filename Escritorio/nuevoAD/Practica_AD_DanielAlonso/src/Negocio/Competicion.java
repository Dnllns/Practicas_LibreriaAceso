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
public class Competicion {

    private int id;
    private TipoCompeticion tipoCompeticion;
    private String nombre;
    private String temporada;
    private ArrayList<Equipo> participantes;

    public Competicion(int id) {
        this.id = id;
    }

    public Competicion() {
    }

    
        /**
     * Carga los equipos que participan en esta competicion en el arraylist de
     * participantes
     */
    public void cargarParticipantes() {
        participantes = new ArrayList();
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "select * from competicion_equipo where cod_competicion = " + id;
        try {
            consulta = conex.getConexion().createStatement();
            ResultSet respuesta = consulta.executeQuery(instruccion);
            while (respuesta.next()) {
                int codEquipo = Integer.parseInt(respuesta.getString("cod_equipo"));
                Equipo e = new Equipo(codEquipo);
                participantes.add(e);
            }
        } catch (NumberFormatException | SQLException e) {
        }
    }
    
    //METODOS ESTANDAR
    //-------------------------------------------
    
    /**
     * Inserta en la bd la competicion
     */
    public void insertar() {

        ConexionBD conexion = ConexionBD.getBD();
        try {
            Statement consulta = conexion.getConexion().createStatement();
            String instruccion = "INSERT INTO competicion VALUES ( NULL, "
                    + "'" + this.nombre + "',"
                    + "'" + this.temporada + "',"
                    + "'" + this.tipoCompeticion.getId() + "'" + ")";
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
     * Obtiene de la bd el regitro correspondiente a id y carga en las estructuras los datos 
     */
    public void cargar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "select * from competicion where id = " + id;
        try {
            consulta = conex.getConexion().createStatement();
            ResultSet respuesta = consulta.executeQuery(instruccion);
            if (respuesta.next()) {
                this.nombre = respuesta.getString("nombre");
                this.temporada = respuesta.getString("temporada");
                //Cargar tipo competicion
                int cod = Integer.parseInt(respuesta.getString("cod_tipo_competicion"));
                TipoCompeticion t = new TipoCompeticion(cod);
                this.tipoCompeticion = t;
            }
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    /**
     * Modifica el regitro correspondiente al id
     */
    public void modificar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "UPDATE competicion SET nombre='" + nombre + "', "
                + "temporada='" + this.temporada + "',"
                + "cod_tipo_competicion='" + this.tipoCompeticion.getId() + "' "
                + " WHERE id=" + id;
        try {
            consulta = conex.getConexion().createStatement();
            consulta.executeUpdate(instruccion);
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    Elimina el registro a partir del id
    */
    public void eliminar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "DELETE FROM competicion WHERE id =" + id;
        try {
            consulta = conex.getConexion().createStatement();
            consulta.executeUpdate(instruccion);
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Equipo> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(ArrayList<Equipo> participantes) {
        this.participantes = participantes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoCompeticion getTipoCompeticion() {
        return tipoCompeticion;
    }

    public void setTipoCompeticion(TipoCompeticion tipoCompeticion) {
        this.tipoCompeticion = tipoCompeticion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }
}
