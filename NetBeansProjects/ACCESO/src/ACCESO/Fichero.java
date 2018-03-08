package ACCESO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Fichero {

    private Path filePath;
    private Boolean existe;

    public Fichero(String filePath) {

        this.filePath = Paths.get(filePath);
        existe = this.filePath.toFile().exists();

    }

    public boolean crearFichero() {
        
        boolean resultado = false;
        if (!existe()) {
            try {
                Files.createFile(filePath);
            } catch (IOException ex) {
                ex.getMessage();
            }
            resultado = true;
        } else {
            System.out.println("El fichero ya existe");
            resultado = false;
        }
        return resultado;
    }

    public boolean eliminarFichero() {
        
        try {
            this.filePath.toFile().delete();
            return true;
        } catch (Exception x) {
            return false;
        }
    }

    public void copiar(String nuevaRuta) {
        
        Fichero nuevo = new Fichero(nuevaRuta);
        if (!Files.isDirectory(nuevo.getFilePath())) {
            if (!nuevo.existe) {
                nuevo.crearFichero();
            }
            try {
                Files.copy(this.getFilePath(), nuevo.getFilePath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
            }
        } else {
            System.out.print(nuevaRuta + " Es un directorio");
        }
    }

    public void cortar(String nuevaRuta) {
        
        Fichero nuevo = new Fichero(nuevaRuta);
        if (nuevo.existe) {
            nuevo.eliminarFichero();
        }
        this.copiar(nuevaRuta);
        this.eliminarFichero();
        this.filePath = Paths.get(nuevaRuta);
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getNombre() {
        return this.filePath.getFileName().toString();
    }

    public String getPadre() {
        return this.filePath.getParent().toString();
    }

    public String getExtension() {
        String extension;
        int index = this.filePath.toFile().getName().lastIndexOf(".") + 1;
        extension = this.filePath.toFile().getName().substring(index);
        System.out.println(extension);
        return extension;
    }

    public Boolean existe() {
        return existe;
    }

}
