
package Negocio;

/**
 *
 * @author alumnoDAM
 */
public class EntrenadorTemporada {
    
    private Entrenador entrenador;
    private int temporada;

    public EntrenadorTemporada(Entrenador entrenador, int temporada) {
        this.entrenador = entrenador;
        this.temporada = temporada;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }
    
    
    
}
