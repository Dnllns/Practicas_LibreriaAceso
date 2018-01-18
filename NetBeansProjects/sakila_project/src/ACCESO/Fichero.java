package ACCESO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;



/**
 *
 * @author alumnoDAM
 */
public class Fichero {
    
    private final String nombre;
    private final Path filePath;
    private final Boolean existe;
    private final File fichero;

    public Fichero(String filePath) {
        
        this.filePath = Paths.get(filePath);
        nombre = this.filePath.toFile().getName();
        existe = this.filePath.toFile().exists();
        fichero = Paths.get(filePath).toFile();
        
    }
    
    public void eliminar(){
        this.filePath.toFile().delete();
    }

    public String getNombre() {
        return nombre;
    }

    public Path getFilePath() {
        return filePath;
    }

    public Boolean existe() {
        return existe;
    }

    public File getFile() {
        return fichero;
    }
    
    /**
     * Devuelve un arraylist que en cada posicion contiene una linea del
     * documento
     * @return arraylist, si
     */
    public ArrayList<String> getLineas() {
        BufferedReader bf;
        Stream<String> lineas;
        final ArrayList<String> out = new ArrayList<>();

        
        if (this.getFilePath().toFile().exists()) {
                try {
                    bf = new BufferedReader(new FileReader(this.getFilePath().toString()));
                    lineas = bf.lines();
                    lineas.forEach(linea -> {
                        out.add(linea);
                    });
                } catch (FileNotFoundException ex) {////No existe
                    System.out.println(ex.getMessage());
                }
        }
        else{
            System.out.println("Error: No existe el documento "+this.getFilePath().toString());
        }
        return out;
    }
    
    /**
     * Metodo que escribe la info pasada por parametro en el archivo
     * @param info
     * @return 
     */
    public boolean writeInfo(String info){
        try {
            FileWriter writer = new FileWriter(this.getFilePath().toFile(),true);
            BufferedWriter objEscritura=new BufferedWriter(writer);
            objEscritura.write(info);
            objEscritura.close();
            writer.close();
            return true;
        } catch (IOException ex) {
                System.out.println(ex.getMessage());
            return false;
        }
    
    
    }
    
    public boolean writeInfo2(String info) {
   	 boolean correcto;
    
        try {
	        	
	            //Creamos fichero para escribir en modo texto
	            PrintWriter writer = new PrintWriter(new FileWriter(filePath.toString()));
	            //Escribimos la info
	            writer.println(info);
	            //Cerramos el fichero
	            writer.close();
	            correcto = true;
	        	
	        } catch (IOException e) {
	            e.printStackTrace();
	            correcto = false;
	        }
		return correcto;
   
   }
    
    
    
    
    

}