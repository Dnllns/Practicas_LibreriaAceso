/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import Acceso.ConexionBD;

/**
 *
 * @author alumnoDAM
 */
public class Entrenador {

    private int id;
    private String nombre;
    private int edad;

    public Entrenador(int id) {
        this.id = id;
    }
    
    public Entrenador(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    /**
     * Inserta en la bd el entrenador
     */
    public void insertar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "INSERT INTO entrenador VALUES ( NULL, '" + this.nombre + "', " + this.edad + ")";

        try {
            consulta = conex.getConexion().createStatement();
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
     * Obtiene de la bd el entrenador correspondiente al id y carga sus datos
     */
    public void cargar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "select * from entrenador where id = " + id;
        try {
            consulta = conex.getConexion().createStatement();
            ResultSet respuesta = consulta.executeQuery(instruccion);
            if (respuesta.next()) {
                this.nombre = respuesta.getString("nombre");
                this.edad = respuesta.getInt("edad");
            }
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Modifica el registro correspondiente al id con los datos de las estructuras
     */
    public void modificar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "UPDATE entrenador SET nombre='" + nombre + "' , edad= "+ this.edad +"  WHERE id=" + id;
        try {
            consulta = conex.getConexion().createStatement();
            consulta.executeUpdate(instruccion);
            consulta.close();

        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Elimina el registro correspondiente a este id
     */
    public void eliminar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "DELETE FROM entrenador WHERE id =" + id;
        try {
            consulta = conex.getConexion().createStatement();
            consulta.executeUpdate(instruccion);
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
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
}
