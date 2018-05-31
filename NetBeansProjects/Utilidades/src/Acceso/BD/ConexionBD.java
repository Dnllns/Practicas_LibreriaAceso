package Acceso.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author n1tr0
 */
public class ConexionBD {

    private Connection conexion;
    private Statement statement;
    private ResultSet resultset;

    /**
     *
     */
    public ConexionBD() {
    }

    /**
     *
     * @return
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Metodo que intenta conectar con la base de datos
     *
     * @param BD_URL
     * @param BD_USER
     * @param BD_PASS
     * @return true, false
     */
    public boolean abrirConexion(String BD_URL, String BD_USER, String BD_PASS) {
        boolean resultado = false;
        try {
            // Abrir canal con la bbdd
            conexion = DriverManager.getConnection(BD_URL, BD_USER, BD_PASS);
            resultado = true;
        } catch (SQLException ex) {// Error en la conexion con la bbdd
            System.out.print("Error al conectar con la base de datos: " + ex.getLocalizedMessage());
        }
        return resultado;
    }

    /**
     * Cerrar conexion con BBDD
     */
    public void cerrarConexion() {
        try {
            statement.close(); //cerrar statement
            conexion.close(); // Cerrar conexion con la base de datos
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    /**
     * Metodo para registrar el driver de mysql
     *
     * @param BD_DRIVER
     * @return
     */
    public boolean registrarDriver(String BD_DRIVER) {
        boolean resultado = false;
        try {
            Class.forName(BD_DRIVER); // Driver sql
            resultado = true;
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver: " + ex.getLocalizedMessage());
        }
        return resultado;
    }

    /**
     * Metodo para insertar que ejecuta la sentencia pasada por parametro y
     * devuelve la clave primaria
     *
     * @param sql
     * @return
     */
    public int insertarGetKey(String sql) {
        int key = -1;
        try {
            statement = conexion.createStatement();
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            resultset = statement.getGeneratedKeys();
            if (resultset.next()) {
                key = resultset.getInt(1);
            }
            statement.close();
            resultset.close();
        } catch (SQLException ex) {
        }
        return key;
    }

    /**
     * Ejecuta la sentencia pasada por parametro
     *
     * @param sql
     */
    public void ejecutarSentencia(String sql) {
        try {
            statement = conexion.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException ex) {
        }
    }

    /**
     *
     * Ejecuta una consulta en la base de datos y devuelve el
     * resultset, despues habria que cerrar el sesultset
     *
     * @param sql
     */
    public void ejecutarConsulta(String sql) {
        try {
            statement = conexion.createStatement();
            resultset = statement.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * devuelve el resultset de la ultima consulta si no ha sido cerrada
     * @return 
     */
    public ResultSet getResultset(){
        return resultset;
    }
    
    /**
     *
     * @return
     */
    public Statement getStatement(){
     return statement;
    }
    
    /**
     * Cierra el resultset
     */
    public void resultsetClose(){
        try {
            resultset.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
