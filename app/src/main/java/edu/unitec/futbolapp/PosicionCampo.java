package edu.unitec.futbolapp;

/**
 * Created by nivx1 on 09/02/2015.
 */
public class PosicionCampo {
    private int idPosicion;
    private String descripcionPosicion;

    public PosicionCampo(int idPosicion) {
        this.idPosicion = idPosicion;
    }

    public PosicionCampo(int idPosicion, String descripcionPosicion) {
        this.idPosicion = idPosicion;
        this.descripcionPosicion = descripcionPosicion;
    }

    public int getIdPosicion() {
        return idPosicion;
    }

    public void setIdPosicion(int idPosicion) {
        this.idPosicion = idPosicion;
    }

    public String getDescripcionPosicion() {
        return descripcionPosicion;
    }

    public void setDescripcionPosicion(String descripcionPosicion) {
        this.descripcionPosicion = descripcionPosicion;
    }
}
