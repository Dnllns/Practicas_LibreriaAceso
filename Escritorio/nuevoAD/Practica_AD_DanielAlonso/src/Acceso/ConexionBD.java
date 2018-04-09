package Acceso;

import Utilidades.Configuracion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private Connection conexion;
    private static ConexionBD BD;

    //Parametros de conexion
    private final String BD_URL = Configuracion.getConfiguracion().getProperty("BD_URL");
    private final String BD_USER = Configuracion.getConfiguracion().getProperty("BD_USER");
    private final String BD_PASS = Configuracion.getConfiguracion().getProperty("BD_PASS");
    private final String BD_DRIVER = Configuracion.getConfiguracion().getProperty("BD_DRIVER");

    public ConexionBD() {

    }


    /**
     * Metodo de acceso a la BD para la capa de negocio
     * @return 
     */
    public static ConexionBD getBD() {
        
        if (BD == null) {
            ConexionBD bd = new ConexionBD();            
            if (bd.registrarDriver()) {
                BD = bd;
            } else {
                System.out.println("Conexion Incorrecta");
                BD = null;
            }
        }
        return BD;
    }

    public Connection getConexion() {
        return conexion;
    }

    /**
     * Metodo que intenta conectar con la base de datos
     *
     * @return true, false
     */
    public boolean abrirConexion() {
        boolean resultado = false;
        try {
            // Abrir canal con la bbdd
            conexion = DriverManager.getConnection(BD_URL, BD_USER, BD_PASS);
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
    public void cerrarConexion() {
        try {
            // Cerrar conexion con la base de datos
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    /**
     * Metodo para registrar el driver de mysql
     *
     * @return
     */
    private boolean registrarDriver() {
        boolean resultado = false;
        try {
            Class.forName(BD_DRIVER); // Driver sql
            resultado = true;
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver: " + ex.getLocalizedMessage());
        }
        return resultado;
    }

}
