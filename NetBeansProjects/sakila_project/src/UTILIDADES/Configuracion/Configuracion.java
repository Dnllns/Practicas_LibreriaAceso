
package UTILIDADES.Configuracion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase que carga un archivo de configuracion.properties implementando el patron singleton
 * @author alumnoDAM
 */
public class Configuracion { 
    
//Archivo de configuracion
    public final static String CONFIG_FILE_NAME = "./src/UTILIDADES/Configuracion/configuracion.properties";
    private Properties properties = null;
    private static Configuracion miconfigurador;
    

    /**
     * Constructor
     */
    private Configuracion() {
        this.properties = new Properties();
        InputStream entrada = null;
        try {
            //lectura del archivo de configuracion
            entrada = new FileInputStream(CONFIG_FILE_NAME);
            properties.load(entrada);
        } catch (FileNotFoundException e) {
            System.out.println("Error, El archivo no exite");
        } catch (IOException e) {
            System.out.println("Error, No se puede leer el archivo");
        }
    }

    /**
     * Metodo que obtiene la configuracion del proyecto
     * @return
     */
    public static Configuracion getConfiguracion() {

        if (miconfigurador == null) {
            miconfigurador = new Configuracion();
        }
        return miconfigurador;
    }

    /**
     * Metodo para recuperar una propiedad usando su key
     * @param key
     * @return
     */
    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }//getProperty
}
