package edu.unitec.futbolapp;

/**
 * Created by nivx1 on 09/04/2015.
 */
public class Pase extends Accion {
    public Pase(String nombreAccion) {
        super(nombreAccion);
    }

    public Pase(int idAccion, String nombreAccion) {
        super(idAccion, nombreAccion);
    }


    @Override
    public String toString() {
        return nombreAccion+",Pase";
    }
}
