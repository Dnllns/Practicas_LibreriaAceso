/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Acceso.ConexionBD;
import Negocio.Equipo;

/**
 *
 * @author alumnoDAM
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConexionBD.getBD().abrirConexion();
        
        
        

        Equipo e = new Equipo(1);
        e.cargar();
        e.cargarEntrenadores();
       

        int i = 0;

        ConexionBD.getBD().cerrarConexion();

    }

}
