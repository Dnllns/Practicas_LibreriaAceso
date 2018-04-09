package Acceso;

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

    /**
     * Crea el directorio si no existe
     */
    public void crearDirectorio() {
        if (!existe) {
            try {
                Files.createDirectory(ruta);
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
    }

    /**
     * Elimina un directorio y su contenido
     */
    public void eliminar() {
        if (existe) {
            for (File f : ruta.toFile().listFiles()) {
                if (f.isDirectory()) {
                    Directorio d = new Directorio(f.getAbsolutePath());
                    d.eliminar();
                } else {
                    f.delete();
                }
            }
            ruta.toFile().delete();
            limpiar();
        }
    }

    /**
     * Copia un directorio y su contenido a la ruta pasada por parametro Si el
     * directorio de destino no existe
     *
     * @param nuevaRuta
     */
    public void copiar(String nuevaRuta) {
        Directorio directorioDestino = new Directorio(nuevaRuta);
        if (!directorioDestino.existe) {
            directorioDestino.crearDirectorio();
            String[] contenidoDirectorio = ruta.toFile().list();
            for (int i = 0; i < contenidoDirectorio.length; i++) { //Contenido del directorio
                File ficheroHijo = new File(ruta + System.getProperty("file.separator") + contenidoDirectorio[i]);
                if (ficheroHijo.isDirectory()) { //Copiar directorio
                    Directorio directorioOriginal = new Directorio(ficheroHijo.getAbsolutePath());
                    Directorio nuevoDirectorio = new Directorio(directorioDestino.ruta + System.getProperty("file.separator") + ficheroHijo.getName());
                    directorioOriginal.copiar(nuevoDirectorio.ruta.toString());
                } else {    //Copiar fichero
                    try {
                        Files.copy(
                                Paths.get(ficheroHijo.getAbsolutePath()),
                                Paths.get(directorioDestino.ruta.toString() + System.getProperty("file.separator") + ficheroHijo.getName())
                        );
                    } catch (IOException ex) {
                    }
                }
            }
        }
    }

    /**
     * Renombra el directorio
     * @param nuevoNombre 
     */
    public void renombrar(String nuevoNombre) {
        try {
            ruta = Files.move(ruta, ruta.resolveSibling(nuevoNombre));
            nombre = ruta.getFileName().toString();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    /**
     * Corta un directorio y su contenido a la ruta pasada por parametro, si no
     * existe esta
     *
     * @param nuevaRuta
     */
    public void cortar(String nuevaRuta) {

        Path nueva = Paths.get(nuevaRuta);
        if (!nueva.toFile().exists()) { // El path de la nueva ruta esta libre
            try {
                Files.move(ruta, nueva);
                ruta = Paths.get(nueva.toString());
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
        actualizar(nuevaRuta);
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

    
    private void limpiar() {
        ruta = null;
        nombre = null;
        existe = false;
        directoriosInternos = null;
        ficherosInternos = null;
    }
    
    private void actualizar(String ruta){
        this.ruta = Paths.get(ruta);
        nombre = this.ruta.getFileName().toString();
    }
}
