package edu.unitec.futbolapp;

/**
 * Created by nivx1 on 09/04/2015.
 */
public class Pase extends Accion {
    public Pase(String nombreAccion, String abreviacionAccion) {
        super(nombreAccion, abreviacionAccion);
    }

    public Pase(int idAccion, String nombreAccion, String abreviacionAccion) {
        super(idAccion, nombreAccion, abreviacionAccion);
    }

    @Override
    public String toString() {
        return nombreAccion+",Pase";
    }
}
