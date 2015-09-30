package edu.unitec.futbolapp;

import java.io.Serializable;

/**
 * Created by nivx1 on 09/05/2015.
 */
public class Falta extends Accion  {
    public Falta(String nombreAccion, String abreviacionAccion) {
        super(nombreAccion, abreviacionAccion);
    }

    public Falta(int idAccion, String nombreAccion, String abreviacionAccion) {
        super(idAccion, nombreAccion, abreviacionAccion);
    }

    @Override
    public String toString() {
        return nombreAccion+",Falta";
    }
}
