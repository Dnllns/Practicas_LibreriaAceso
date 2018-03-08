
import ACCESO.Directorio;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author n1tr0
 */
public class MAIN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Directorio d = new Directorio("/home/n1tr0/Escritorio/Pruebas");
        d.cortar("/home/n1tr0/Escritorio/Pruebas2");
        int a = 0;
    }
    
}
