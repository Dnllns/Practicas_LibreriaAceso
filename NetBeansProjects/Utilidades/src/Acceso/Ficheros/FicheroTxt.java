package Acceso.Ficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Fichero de texto
 * @author n1tr0
 */
public class FicheroTxt extends Fichero {

    private ArrayList<String> contenido;

    /**
     *
     * @param rutaArchivo
     */
    public FicheroTxt(String rutaArchivo) {
        super(rutaArchivo);
    }

    /**
     * Abre el archivo, lee su contenido y lo carga en la estructura
     */
    public void leerFichero() {
        if (this.existe()) {
            BufferedReader bufferedReader;
            Stream<String> lineasTxt;
            try {
                bufferedReader = Files.newBufferedReader(super.getFilePath());
                lineasTxt = bufferedReader.lines();
                contenido = new ArrayList<>();
                lineasTxt.forEach(linea -> {
                    contenido.add(linea);
                });
                bufferedReader.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Escribe el string pasado por parametro en una nueva linea del archivo
     *
     * @param contenido
     */
    public void escribirInformacion(String contenido) {
        if (super.existe()) {
            try {
                BufferedWriter bufferedWriter = Files.newBufferedWriter(
                        super.getFilePath(),
                        StandardOpenOption.WRITE,
                        StandardOpenOption.APPEND
                );
                bufferedWriter.write(contenido);
                bufferedWriter.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Escribe el ArrayList String pasado por parametro en una nueva linea del
     * archivo
     *
     * @param contenidoAnadido
     */
    public void escribirInformacion(ArrayList<String> contenidoAnadido) {
        if (this.existe()) {
            try {
                BufferedWriter bufferedWriter = Files.newBufferedWriter(
                        this.getFilePath(),
                        StandardOpenOption.WRITE,
                        StandardOpenOption.APPEND
                );
                for (String linea : contenidoAnadido) {
                    bufferedWriter.write("\n" + linea);
                }
                bufferedWriter.close();
            } catch (IOException ex) {
            }

        }
    }

    /**
     * Sobreescribe el contenido del documento con lo que haya cargado en 
     * la estructura de contenido
     */
    public void sobreescribirInformacion() {
        if (this.existe()) {
            try {
                BufferedWriter bufferedWriter = Files.newBufferedWriter(
                        this.getFilePath(), 
                        StandardOpenOption.WRITE, 
                        StandardOpenOption.TRUNCATE_EXISTING
                );
                for (String linea : contenido) {
                    bufferedWriter.write("\n" + linea);
                }
                bufferedWriter.close();
            } catch (IOException ex) {
            }

        }
    }

    /**
     * Devuelve un arraylist en cada posicion contiene una linea del fichero
     * @return
     */
    public ArrayList<String> getContenido() {
        return contenido;
    }

    /**
     * Actualiza la estructura de contenido del fichero
     * @param contenido
     */
    public void setContenido(ArrayList<String> contenido) {
        this.contenido = contenido;
    }
    
    
    

}
