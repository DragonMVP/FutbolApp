package edu.unitec.futbolapp;

import java.io.Serializable;

/**
 * Created by nivx1 on 09/04/2015.
 */
public class Accion implements Serializable {
    protected int idAccion;
    protected String nombreAccion;
    protected String abreviacionAccion;
    private int accionPortero;

    public int getAccionPortero() {
        return accionPortero;
    }

    public void setAccionPortero(int accionPortero) {
        this.accionPortero = accionPortero;
    }

    public String getAbreviacionAccion() {
        return abreviacionAccion;
    }

    public void setAbreviacionAccion(String abreviacionAccion) {
        this.abreviacionAccion = abreviacionAccion;
    }

    public Accion(String nombreAccion, String abreviacionAccion) {
        this.nombreAccion = nombreAccion;
        this.abreviacionAccion = abreviacionAccion;
        accionPortero = 0;
    }

    public Accion(String nombreAccion, String abreviacionAccion, int accionPortero) {
        this.nombreAccion = nombreAccion;
        this.abreviacionAccion = abreviacionAccion;
        this.accionPortero = accionPortero;
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
    public boolean equals(Object o) {
        if (o instanceof Accion)
            return (idAccion == ((Accion)o).idAccion);

        return false;
    }

    @Override
    public String toString() {
        return nombreAccion+",Accion";
    }
}
