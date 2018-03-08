
package ACCESO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FicheroTxt extends Fichero {

    public FicheroTxt(String filePath) {
        super(filePath);
    }

    
    public ArrayList<String> leerFichero() {
        BufferedReader bf;
        Stream<String> lineas;
        final ArrayList<String> out = new ArrayList<>();

        if (super.existe()) {
            try {
                bf = new BufferedReader(new FileReader(super.getFilePath().toString()));
                lineas = bf.lines();
                lineas.forEach(linea -> {
                    out.add(linea);
                });
                bf.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("Error: No existe el documento " + super.getFilePath().toString());
        }
        return out;
    }

    public boolean escribirInformacion(String info) {
        try {
            FileWriter writer = new FileWriter(this.getFilePath().toFile(), true);
            BufferedWriter objEscritura = new BufferedWriter(writer);
            objEscritura.write(info);
            objEscritura.close();
            writer.close();
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean escribirInformacion(ArrayList<String> info) {
        try {
            FileWriter writer = new FileWriter(this.getFilePath().toFile(), true);
            BufferedWriter objEscritura = new BufferedWriter(writer);
            for (String l : info) {
                objEscritura.write("\n" + l);
            }
            objEscritura.close();
            writer.close();
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public void sobreescribirInformacion(ArrayList<String> info){
        
        this.eliminarFichero();
        this.crearFichero();
        this.escribirInformacion(info);
    }

}
