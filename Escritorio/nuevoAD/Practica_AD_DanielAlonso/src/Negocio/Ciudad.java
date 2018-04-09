/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Acceso.ConexionBD;

/**
 *
 * I/C/M/E Cargar equipos
 *
 * @author Daniel Alonso
 */
public class Ciudad {

    private int id;
    private String nombre;
    private ArrayList<Equipo> equipos;

    public Ciudad() {
    }

    public Ciudad(int id) {
        this.id = id;
    }

    public Ciudad(String nombre) {
        this.nombre = nombre;
    }

    public Ciudad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /*
    Insterta la ciudad en la bd
     */
    public void insertar() {

        if (this.nombre == null) {
            System.out.println("La ciudad no puede ser insertada ya que carece de nombre");
        } else {
            ConexionBD conexion = ConexionBD.getBD();
            try {
                // Crear statement consulta
                Statement consulta = conexion.getConexion().createStatement();
                // Realizar la consulta a la base de datos
                String instruccion = "INSERT INTO ciudad VALUES ( NULL, '" + this.nombre + "')";
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
    }

    /*
    Carga la ciudad en la estructura a partir del id
     */
    public void cargar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "select * from ciudad where id = " + id;
        try {
            consulta = conex.getConexion().createStatement();
            ResultSet respuesta = consulta.executeQuery(instruccion);
            if (respuesta.next()) {
                this.nombre = respuesta.getString("nombre");
            }
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    Busca en la base de datos los equipos que son de esta ciudady los carga
    en el arraylist de equipos
     */
    public void cargarEquipos() {
        equipos = new ArrayList();
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "select * from equipo where cod_ciudad = " + id;
        try {
            consulta = conex.getConexion().createStatement();
            ResultSet respuesta = consulta.executeQuery(instruccion);
            while (respuesta.next()) {
                int idEquipo = respuesta.getInt("id");
                Equipo e = new Equipo(idEquipo);
                equipos.add(e);
            }
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Modifica el registro correspondiente a este id
     */
    public void modificar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "UPDATE ciudad SET nombre='" + nombre + "' WHERE id=" + id;
        try {
            consulta = conex.getConexion().createStatement();
            consulta.executeUpdate(instruccion);
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Elimina el registro correspondiente con el id
     */
    public void eliminar() {
        Statement consulta;
        ConexionBD conex = ConexionBD.getBD();
        String instruccion = "DELETE FROM ciudad WHERE id =" + id;
        try {
            consulta = conex.getConexion().createStatement();
            consulta.executeUpdate(instruccion);
            consulta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(ArrayList<Equipo> equipos) {
        this.equipos = equipos;
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
