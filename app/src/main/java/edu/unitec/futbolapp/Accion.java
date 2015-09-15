package edu.unitec.futbolapp;

/**
 * Created by nivx1 on 09/04/2015.
 */
public class Accion {
    protected int idAccion;
    protected String nombreAccion;
    protected String abreviacionAccion;

    public String getAbreviacionAccion() {
        return abreviacionAccion;
    }

    public void setAbreviacionAccion(String abreviacionAccion) {
        this.abreviacionAccion = abreviacionAccion;
    }

    public Accion(String nombreAccion, String abreviacionAccion) {
        this.nombreAccion = nombreAccion;
        this.abreviacionAccion = abreviacionAccion;
    }

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

    public Accion(int idAccion, String nombreAccion, String abreviacionAccion) {
        this.idAccion = idAccion;
        this.nombreAccion = nombreAccion;
        this.abreviacionAccion = abreviacionAccion;
    }

    @Override
    public String toString() {
        return nombreAccion+",Accion";
    }
}
