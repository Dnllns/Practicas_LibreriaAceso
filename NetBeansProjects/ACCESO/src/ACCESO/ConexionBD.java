package ACCESO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBD {

    

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    /**
     * Metodo que inicia una conexion con la BBDD
     *
     * @return
     */
    public static ConexionBD iniciarConexion(String BBDD_URL, String BBDD_USER, String BBDD_PASS) {
        ConexionBD bd = new ConexionBD();
        // validar la conexion
        boolean resultado = bd.validarConexion(BBDD_URL, BBDD_USER, BBDD_PASS);
        if (resultado) {
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
            Class.forName("com.mysql.jdbc.Driver"); // Driver sql
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
    public boolean validarConexion(String BBDD_URL, String BBDD_USER, String BBDD_PASS) {

        if (registrarDriver()) {
            return conectarConBBDD(BBDD_URL, BBDD_USER, BBDD_PASS);
        } else {
            return false;
        }
    }

    /**
     * Metodo que intenta conectar con la base de datos
     *
     * @return true, false
     */
    public boolean conectarConBBDD(String BBDD_URL, String BBDD_USER, String BBDD_PASS) {
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
     
    public int insertGetKey(String sqlSentence) {
        PreparedStatement consulta = null;
        ConexionBD conex = ConexionBD.iniciarConexion();
        int key = -1;
        try {
            consulta = conex.getConexion().prepareStatement(sqlSentence, PreparedStatement.RETURN_GENERATED_KEYS);
            consulta.executeUpdate();
            ResultSet registros = consulta.getGeneratedKeys();
            if (registros != null && registros.next()) {
                key = registros.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("");
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return key;
    }
    */
}
