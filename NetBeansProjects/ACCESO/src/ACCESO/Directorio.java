package ACCESO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Directorio {

    Path ruta;
    String nombre;
    Boolean existe;
    ArrayList<Directorio> directoriosInternos;
    ArrayList<Fichero> ficherosInternos;

    /**
     * Constructor Recibe como parametro la ruta del directorio
     *
     * @param nuevaRuta
     */
    public Directorio(String nuevaRuta) {
        ruta = Paths.get(nuevaRuta);
        nombre = ruta.getFileName().toString();
        existe = Files.exists(ruta);
        directoriosInternos = new ArrayList<Directorio>();
        ficherosInternos = new ArrayList<Fichero>();
    }

    public void crearDirectorio() {
        if (!existe) {
            try {
                Files.createDirectory(ruta);
            } catch (IOException ex) {
                ex.getMessage();
            }
        } else {
            System.out.println("Ese directorio ya existe");
        }
    }

    public void eliminar() {
        if (existe) {
            for (File f : ruta.toFile().listFiles()) {
                f.delete();
            }
            ruta.toFile().delete();
        }
    }

    /**
     * Copia un directorio y su contenido a la ruta pasada por parametro
     *
     * @param nuevaRuta
     */
    public void copiar(String nuevaRuta) {
        Directorio destino = new Directorio(nuevaRuta);
        if (!destino.existe) {
            destino.crearDirectorio();
        }
        String[] ficheros = ruta.toFile().list();
        for (int x = 0; x < ficheros.length; x++) { //Contenido del directorio
            File f = new File(ruta + System.getProperty("file.separator") + ficheros[x]);
            if (f.isDirectory()) { //Copiar directorio
                Directorio original = new Directorio(f.getAbsolutePath());
                Directorio copia = new Directorio(destino.ruta + System.getProperty("file.separator") + f.getName());
                original.copiar(copia.ruta.toString());
            } else {    //Copiar fichero
                try {
                    Files.copy(
                            Paths.get(f.getAbsolutePath()),
                            Paths.get(destino.ruta.toString() + System.getProperty("file.separator") + f.getName())
                    );
                } catch (IOException ex) {
                }
            }
        }
    }

    public boolean renombrar(String nombre) {
        boolean resultado = false;
        try {
            ruta = Files.move(ruta, ruta.resolveSibling(nombre));

        } catch (IOException ex) {
            ex.getMessage();
        }
        String nombreDirectorio = ruta.getFileName().toString();
        if (nombreDirectorio.equals(nombre)) {
            resultado = true;
        }
        return resultado;
    }

    /**
     * Corta un directorio y su contenido a la ruta pasada por parametro, si no existe esta
     * @param nuevaRuta 
     */
    public void cortar(String nuevaRuta) {

        Path nueva = Paths.get(nuevaRuta);
        if (!nueva.toFile().exists()) { // Controlar si existe la nueva ruta
            if (Files.isDirectory(nueva.getParent())) {
                try {
                    Files.move(ruta, nueva);
                    ruta = Paths.get(nueva.toString());
                } catch (IOException ex) {
                    ex.getMessage();
                }
            }
        }
    }

    /**
     * Si existe el directorio carga su contenido
     */
    public void cargarContenido() {
        if (existe && Files.isDirectory(ruta)) {
            File contenido[] = ruta.toFile().listFiles();
            for (int i = 0; contenido.length > i; i++) {
                Directorio subdirectorio = null;
                Fichero fichero = null;
                if (Files.isDirectory(contenido[i].toPath())) {
                    subdirectorio = new Directorio(contenido[i].getAbsolutePath());
                    subdirectorio.cargarContenido();
                    this.directoriosInternos.add(subdirectorio);
                } else {
                    fichero = new Fichero(contenido[i].getAbsolutePath());
                    this.ficherosInternos.add(fichero);
                }
            }
        }

    }
}
