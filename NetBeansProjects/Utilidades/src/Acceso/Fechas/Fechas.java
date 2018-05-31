package Acceso.Fechas;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author n1tr0
 */
public class Fechas {

    private int dia, mes, ano;
    private static int diasMes[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static String nombreMes[] = {
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
        "Agosto", "Septimbre", "Octubre", "Noviembre", "Diciembre"
    };

    /**
     *
     */
    public Fechas() {
        Calendar fhoy = new GregorianCalendar();
        dia = fhoy.get(Calendar.DAY_OF_MONTH);
        mes = fhoy.get(Calendar.MONTH) + 1;
        ano = fhoy.get(Calendar.YEAR);
    }

    /**
     *
     * @param dia
     * @param mes
     * @param ano
     */
    public Fechas(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    /**
     * Metodo que comprueba si una fecha es correcta
     *
     * @return true si es correcta
     *
     */
    public boolean combrobarFecha() {
        boolean correcto = false;
        if (ano > 1990 && ano < 2100) {
            calcularBisiesto(ano);
            if (mes >= 1 && mes <= 12) {
                if (dia >= 1 && dia <= diasMes[mes - 1]) {
                    correcto = true;
                }
            }
        }
        return correcto;
    }

    /**
     * Metodo que calcula si el año es bisiesto
     */
    public void calcularBisiesto() {
        if (ano % 4 == 0 && ano % 100 != 0 || ano % 400 == 0) {
            diasMes[1] = 29;
        }
    }

    /**
     * Metodo que calcula si ano es bisiesto
     *
     * @param ano
     */
    public static void calcularBisiesto(int ano) {
        if (ano % 4 == 0 && ano % 100 != 0 || ano % 400 == 0) {
            diasMes[1] = 29;
        }
    }

    /**
     * Metodo que compara la fecha con una fecha pasada por parametro
     *
     * @param f
     * @return 1 si la fecha objeto es mayor, -1 menor y 0 si es igual
     */
    public int compararFechas(Fechas f) {

        if (this.ano > f.getAno()) {
            return 1;
        } else if (this.ano < f.getAno()) {
            return -1;
        } else if (this.mes > f.getMes()) {//Los años son iguales
            return 1;
        } else if (this.mes < f.getMes()) {
            return -1;
        } else if (this.dia > f.getDia()) { //meses iguales
            return 1;
        } else if (this.dia < f.getDia()) {
            return -1;
        } else { //las fechas coinciden
            return 0;
        }

    }

    /**
     * Calcula los años que han transcurrido hasta la actualidad
     *
     * @return
     */
    public int calcularAnos() {
        int anos;
        Fechas factual = new Fechas();
        anos = factual.getAno() - this.ano;
        //(Si el mes actual es menor que el mes ó es igual) y el dia actual es menor que el dia
        if (factual.getMes() < this.mes || factual.getMes() == this.mes && factual.getDia() < this.dia) {
            anos--;
        }
        return anos;
    }

    /**
     * Calcula el numero de trienios que han transcurrido hasta la fecha a ctual
     *
     * @return
     */
    public int calculoTrienios() {
        return calcularAnos() / 3;
    }

    /**
     * Metodo que visualiza la fecha
     *
     * @return
     */
    public String visualizar() {
        return dia + " de " + nombreMes[mes - 1] + " de " + ano;
    }

    /**
     * Calcula el numero de dia en el que estas del año
     *
     * @return
     */
    public int calcularOrden() {
        int dias = 0;
        calcularBisiesto();
        int mesAux = 1;
        while (mesAux < this.mes) {
            dias = dias + diasMes[mesAux - 1];
            mesAux++;
        }
        return this.dia + dias;
    }

    /**
     * Calcula los dias que hay entre dos fechas del mismo año
     *
     * @param f
     * @return
     */
    public int calcularOrdenEntreFechas(Fechas f) {
        f.calcularBisiesto();
        int diasIntermedios = 0;
        int diasQuedanMes = diasMes[this.mes - 1] - this.dia;
        int diaDelMes = f.getDia();
        int mesAux = this.mes + 1;
        //mientras el mes sea menor que el mes de la segunda fecha
        while (mesAux < f.getMes()) {
            diasIntermedios = diasMes[mesAux - 1] + diasIntermedios;
            mesAux++;
        }

        return diasQuedanMes + diasIntermedios + diaDelMes;

    }

    /**
     * Calcula los dias que tiene el año
     *
     * @return
     */
    public int numeroDiasAno() {
        int numero = 365;
        if (this.ano % 4 == 0 && this.ano % 100 != 0 || this.ano % 400 == 0) {
            numero++;
        }
        return numero;
    }

    /**
     * Calcula los dias que tiene el año pasado como param
     *
     * @param ano
     * @return Los dias que tiene el año
     */
    public int numeroDiasAno(int ano) {
        int numero = 365;
        if (ano % 4 == 0 && ano % 100 != 0 || ano % 400 == 0) {
            numero++;
        }
        return numero;
    }

    /**
     * Calcula los dias que quedan hasta final de año
     *
     * @return
     */
    public int diasHastaFinAno() {
        return numeroDiasAno() - calcularOrden();
    }

    /**
     * Calcula los dias que han transcurrido entre dos fechas
     * @param f2
     * @return 
     */
    public int diasTranscurridosEntre(Fechas f2) {

        Fechas fMayor, fMenor;
        int anoAux = 0;
        int diasHastaFinAno = 0, diasDesdePrincipioAno = 0, diasMesesIntermedios = 0;

        //comparo para ver que fecha es la mayor y la menor
        if (this.compararFechas(f2) == 1) {
            fMayor = this;
            fMenor = f2;
        } else {
            fMayor = f2;
            fMenor = this;
        }
        //Si los años son distintos
        if (fMayor.getAno() != fMenor.getAno()) {
            diasHastaFinAno = fMenor.diasHastaFinAno(); //dias hasta fin de año de la fecha menor
            diasDesdePrincipioAno = fMayor.calcularOrden(); //dias transcurridos desde el comienzo del año de la fecha mayor hasta la fecha mayor
            anoAux = fMenor.getAno() + 1;
            //Calculo de los dias de los meses intermedios
            while (anoAux < fMayor.getAno()) {
                diasMesesIntermedios = diasMesesIntermedios + fMenor.numeroDiasAno(anoAux);
                anoAux++;
            }
            return diasHastaFinAno + diasDesdePrincipioAno + diasMesesIntermedios;

            //Es el mismo año
        } else {
            //Si el mes es el mismo
            if (fMenor.getMes() == fMayor.getMes()) {
                return fMayor.getDia() - fMenor.getDia();
            } else {
                return fMenor.calcularOrdenEntreFechas(fMayor);
            }
        }
    }

    /**
     *
     * @return dia
     */
    public int getDia() {
        return dia;
    }

    /**
     *
     * @param dia
     */
    public void setDia(int dia) {
        this.dia = dia;
    }

    /**
     *
     * @return mes
     */
    public int getMes() {
        return mes;
    }

    /**
     *
     * @param mes
     */
    public void setMes(int mes) {
        this.mes = mes;
    }

    /**
     *
     * @return
     */
    public int getAno() {
        return ano;
    }

    /**
     *
     * @param ano
     */
    public void setAno(int ano) {
        this.ano = ano;
    }

    /**
     * Calcula los dias que tiene un mes del año
     *
     * @param indice posicion del array que equivale al mes
     * @return
     */
    public static int getDiasMes(int indice) {
        return diasMes[indice];
    }

}
