/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ACCESO;

/**
 *
 * @author alumnoDAM
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Directorio {

    Path ruta;
    ArrayList<Directorio> subcarpeta = new ArrayList<>();
    ArrayList<Fichero> subfichero = new ArrayList<>();

    public Directorio() {

    }

    public Directorio(String rutan) throws IOException {
        if (Files.isDirectory(Paths.get(rutan))) {
            ruta = Paths.get(rutan);
        } else {
            throw new IOException();
        }
    }

    public Directorio(Path ruta) {

        this.ruta = ruta;
    }

    public Path getPath() {
        return this.ruta;
    }

    public boolean Nuevo() {
        boolean resultado = false;
        if (!Existe()) {
            try {
                Files.createDirectory(ruta);
            } catch (IOException ex) {
                ex.getMessage();
            }
            resultado = true;
        } else {
            System.out.println("Ese directorio ya existe");
            resultado = false;
        }
        return resultado;
    }

    public boolean Existe() {
        boolean resultado = false;
        if (Files.exists(ruta)) {
            resultado = true;
        }
        return resultado;
    }

    public boolean Existe(Path nueva) {
        boolean resultado = false;
        if (Files.exists(ruta)) {
            resultado = true;
        }
        return resultado;
    }

    public boolean Eliminar() {
        boolean resultado = false;
        if (Existe()) {
            try {
                actualizarContenido();
                if (this.subcarpeta.isEmpty() && this.subfichero.isEmpty()) {
                    Files.delete(ruta);
                } else {
                    for (int i = 0; this.subcarpeta.size() > i;) {
                        this.subcarpeta.get(i).actualizarContenido();
                        if (!this.subcarpeta.get(i).subcarpeta.isEmpty()) {
                            while (!this.subcarpeta.get(i).subcarpeta.isEmpty()) {
                                this.subcarpeta.get(i).Eliminar();
                            }
                        }
                    }
                    Files.delete(ruta);
                }
            } catch (IOException ex) {
                ex.getMessage();
            }
            resultado = true;
        } else {
            System.out.println("El directorio no ha podido eliminarse");
            resultado = false;
        }
        return resultado;
    }

    public boolean Copiar(String nuevaRuta) {
        boolean resultado = false;
        Path nueva = Paths.get(nuevaRuta);
        if (Existe(nueva.getParent())) {
            if (Files.isDirectory(nueva.getParent())) {
                try {
                    actualizarContenido();
                    if (this.subcarpeta.isEmpty() && this.subfichero.isEmpty()) {
                        Files.copy(ruta, nueva);
                        resultado = true;
                    } else {
                        Files.copy(ruta, nueva);
                        for (int a = 0; this.subfichero.size() > a; a++) {
                            Files.copy(this.subfichero.get(a).getFilePath(),
                                    Paths.get(nueva.toString() + "\\" + this.subfichero.get(a).getFilePath().getFileName().toString()));
                        }
                        for (int i = 0; subcarpeta.size() > i; i++) {
                            Files.copy(this.subcarpeta.get(i).getPath(),
                                    Paths.get(nueva.toString() + "\\" + this.subcarpeta.get(i).getPath().getFileName().toString()));
                            Directorio subdi = subcarpeta.get(i);
                            subdi.CargarContenido();
                            if (!subdi.subcarpeta.isEmpty()) {
                                for (int j = 0; subdi.subcarpeta.size() > j; j++) {
                                    subdi.subcarpeta.get(j).Copiar(nueva.toString()
                                            + "\\" + subdi.getPath().getFileName().toString() + "\\"
                                            + subdi.subcarpeta.get(j).getPath().getFileName().toString());
                                }
                            }
                            resultado = true;
                        }
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    resultado = false;
                }
            }
        }
        return resultado;
    }

    public boolean Cortar(String nuevaRuta) {
        boolean resultado = false;
        if (Existe()) {
            resultado = Copiar(nuevaRuta);
            Eliminar();
            ruta = Paths.get(nuevaRuta);
            resultado = true;
        }
        return resultado;
    }

    public void CargarContenido() {
        for (int i = 0; this.ruta.toFile().list().length > i; i++) {
            Directorio subdirectorio = null;
            Fichero fichero = null;
            try {
                if (Files.isDirectory(Paths.get(this.ruta.toString() + "\\" + this.ruta.toFile().list()[i]))) {
                    subdirectorio = new Directorio(this.ruta.toString() + "\\" + this.ruta.toFile().list()[i]);
                    this.subcarpeta.add(subdirectorio);
                } else {
                    fichero = new Fichero(this.ruta.toString() + "\\" + this.ruta.toFile().list()[i]);
                    this.subfichero.add(fichero);
                }
            } catch (IOException ex) {

                ex.getMessage();
            }
        }
    }

    public void verContenido() {
        if (!subcarpeta.isEmpty()) {
            for (int i = 0; subcarpeta.size() > i; i++) {
                System.out.println(subcarpeta.get(i).getPath().getFileName().toString());
            }
        }
    }

    public boolean Renombrar(String nombre) {
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

    public boolean Mover(String nuevaRuta) {
        boolean resultado = false;
        Path nueva = Paths.get(nuevaRuta);
        if (Files.isDirectory(nueva.getParent())) {
            try {
                Files.move(ruta, nueva);
                resultado = true;
                ruta = Paths.get(nueva.toString());
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
        return resultado;
    }

    public void actualizarContenido() {
        if (!this.subcarpeta.isEmpty()) {
            for (int i = 0; this.subcarpeta.size() > i; i++) {
                this.subcarpeta.remove(i);
            }
        }
        if (!this.subfichero.isEmpty()) {
            for (int i = 0; this.subfichero.size() > i; i++) {
                this.subfichero.remove(i);
            }
        }
        CargarContenido();
    }
}


