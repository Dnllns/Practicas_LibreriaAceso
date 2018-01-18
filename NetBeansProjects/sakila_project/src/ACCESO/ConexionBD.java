package ACCESO;

import NEGOCIO.Pelicula;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import UTILIDADES.Configuracion.Configuracion;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBD {

    private final static String BBDD_DRIVER = Configuracion.getConfiguracion().getProperty("BBDD_DRIVER");
    private final static String BBDD_URL = Configuracion.getConfiguracion().getProperty("BBDD_URL");
    private final static String BBDD_USER = Configuracion.getConfiguracion().getProperty("BBDD_USER");
    private final static String BBDD_PASS = Configuracion.getConfiguracion().getProperty("BBDD_PASS");

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    /**
     * Metodo que inicia una conexion con la BBDD
     *
     * @return
     */
    public static ConexionBD iniciarConexion() {
        ConexionBD bd = new ConexionBD();
        // validar la conexion
        boolean resultado = bd.validarConexion();
        if (resultado) {
            System.out.println("Conexion correcta con la base de datos");
            return bd;
        } else {
            System.out.println("Conexion Incorrecta");
            return null;
        }
    }

    /**
     * Metodo para registrar el driver de mysql
     *
     * @return
     */
    public boolean registrarDriver() {
        boolean resultado = false;
        try {
            Class.forName(BBDD_DRIVER); // Driver sql
            resultado = true;
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver: " + ex.getLocalizedMessage());
        }
        return resultado;
    }

    /**
     * Metodo que comprueba si la conexion es correcta
     *
     * @return true, false
     */
    public boolean validarConexion() {

        if (registrarDriver()) {
            return conectarConBBDD();
        } else {
            return false;
        }
    }

    /**
     * Metodo que intenta conectar con la base de datos
     *
     * @return true, false
     */
    public boolean conectarConBBDD() {
        boolean resultado = false;
        try {
            // Abrir canal con la bbdd
            conexion = DriverManager.getConnection(BBDD_URL, BBDD_USER, BBDD_PASS);
            resultado = true;
        } catch (SQLException ex) {// Error en la conexion con la bbdd
            System.out.print("Error al conectar con la base de datos: " + ex.getLocalizedMessage());
        } catch (Exception ex) {
            System.out.print(ex.getLocalizedMessage());
        }
        return resultado;
    }

    /**
     * Cerrar conexion con BBDD
     */
    public void cerrar() {
        try {
            // Cerrar conexion con la base de datos
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    /**
     * Metodo que devulve la clave primaria obtenida al ejecutar un insert
     * @param sqlSentence
     * @return 
     */
    public String getAutomaticKeys(String sqlSentence) {
        Statement consulta = null;
        ConexionBD conex = ConexionBD.iniciarConexion();
        String key = "-1";
        ResultSet registros;
        
        try {
            consulta = conex.getConexion().createStatement();
            consulta.executeUpdate(sqlSentence, Statement.RETURN_GENERATED_KEYS);
            registros = consulta.getGeneratedKeys();
            if (registros.next()) {
                key = registros.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return key;
    }
}
