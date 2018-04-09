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
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author alumnoDAM
 */
public class TipoCompeticion {

    private int id;
    private String nombre;

    
    public TipoCompeticion(String nombre){
            this.nombre = nombre;

    }
    public TipoCompeticion(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoCompeticion(int id) {
        this.id = id;
    }
    
    
    /**
     * Inserta el tipo en la base de datos
     */
    public void insertar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "INSERT INTO tipo_competicion VALUES ( NULL, '" + this.nombre + "')";

        try {
            // Crear objeto para la conexion
            consulta = conex.getConexion().createStatement();
            // Realizar la consulta a la base de datos
            consulta.executeUpdate(instruccion, Statement.RETURN_GENERATED_KEYS);
            ResultSet primaryKey = consulta.getGeneratedKeys();
            if(primaryKey.next()){
                this.id = primaryKey.getInt(1);
            }
            consulta.close();
        } catch (SQLException ex) {
            System.out.print(ex.getLocalizedMessage());
        }
    }
    
    /**
     * carga las estructuras a partir del regustro de la bd con el correspondiente id
     */
    public void cargar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "select * from tipo_competicion where id = " + id;
        try {
            consulta = conex.getConexion().createStatement();
            ResultSet respuesta = consulta.executeQuery(instruccion);
            if (respuesta.next()) {
                String nombre = respuesta.getString("nombre");
                this.nombre = nombre;
            }
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Modifica el registro de la base de datos con los valores actuales
     */
    public void modificar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "UPDATE tipo_competicion SET nombre='" + nombre + "' WHERE id=" + id;
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
        String instruccion = "DELETE FROM tipo_competicion WHERE id =" + id;
        try {
            consulta = conex.getConexion().createStatement();
            consulta.executeUpdate(instruccion);
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Getter

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
