package edu.unitec.futbolapp;

/**
 * Created by nivx1 on 09/04/2015.
 */
public class Accion {
    protected int idAccion;
    protected String nombreAccion;

    public int getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(int idAccion) {
        this.idAccion = idAccion;
    }

    public String getNombreAccion() {
        return nombreAccion;
    }

    public void setNombreAccion(String nombreAccion) {
        this.nombreAccion = nombreAccion;
    }

    public Accion(String nombreAccion) {

        this.nombreAccion = nombreAccion;
    }

    public Accion(int idAccion, String nombreAccion) {

        this.idAccion = idAccion;
        this.nombreAccion = nombreAccion;
    }

    @Override
    public String toString() {
        return nombreAccion+",Accion";
    }
}
