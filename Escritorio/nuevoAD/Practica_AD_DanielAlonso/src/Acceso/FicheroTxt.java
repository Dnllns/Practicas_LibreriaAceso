package Acceso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FicheroTxt extends Fichero {

    private ArrayList<String> contenido;

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
                bufferedReader = Files.newBufferedReader(this.getFilePath());
                //bufferedReader = new BufferedReader(new FileReader(this.getRuta()));
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
        if (this.existe()) {
            try {
                //FileWriter fileWriter = new FileWriter(this.toFile(), true);   // v1
                //BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); //v1
                BufferedWriter bufferedWriter = Files.newBufferedWriter(this.getFilePath(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
                bufferedWriter.write(contenido);
                bufferedWriter.close();
                //fileWriter.close(); //v1
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Escribe el ArrayList String pasado por parametro en una nueva linea del
     * archivo
     *
     * @param contenidoDocumento
     */
    public void escribirInformacion(ArrayList<String> contenidoDocumento) {
        if (this.existe()) {
            try {
                //FileWriter fileWriter = new FileWriter(this.toFile(), true);
                //BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                BufferedWriter bufferedWriter = Files.newBufferedWriter(this.getFilePath(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
                for (String linea : contenidoDocumento) {
                    bufferedWriter.write("\n" + linea);
                }
                bufferedWriter.close();
                //fileWriter.close();
            } catch (IOException ex) {
            }

        }
    }

    public void sobreescribirInformacion(ArrayList<String> contenidoDocumento) {
        if (this.existe()) {
            try {
                //FileWriter fileWriter = new FileWriter(this.toFile(), true);
                //BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                BufferedWriter bufferedWriter = Files.newBufferedWriter(this.getFilePath(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                for (String linea : contenidoDocumento) {
                    bufferedWriter.write("\n" + linea);
                }
                bufferedWriter.close();
                //fileWriter.close();
            } catch (IOException ex) {
            }

        }
    }

}
