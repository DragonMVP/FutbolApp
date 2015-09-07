package edu.unitec.futbolapp;

import java.io.Serializable;

/**
 * Created by nivx1 on 09/02/2015.
 */
public class Jugador implements Serializable{

    private int idJugador;
    private int idEquipo;
    private PosicionCampo posicion;
    private String nombreJugador;
    private int numeroJugador;
    //daemon: artificial para el bug de combobox
    private boolean selected;


    public Jugador(int idJugador, int idEquipo, PosicionCampo posicion, String nombreJugador, int numeroJugador) {
        this.idJugador = idJugador;
        this.idEquipo = idEquipo;
        this.posicion = posicion;
        this.nombreJugador = nombreJugador;
        this.numeroJugador = numeroJugador;
    }

    public Jugador(int idJugador, int idEquipo) {
        this.idJugador = idJugador;
        this.idEquipo = idEquipo;
    }

    public Jugador(int idJugador, int idEquipo, String nombreJugador, int numeroJugador) {
        this.nombreJugador = nombreJugador;
        this.numeroJugador = numeroJugador;
        this.idJugador = idJugador;
        this.idEquipo = idEquipo;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public PosicionCampo getPosicion() {
        return posicion;
    }

    public void setPosicion(PosicionCampo posicion) {
        this.posicion = posicion;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public int getNumeroJugador() {
        return numeroJugador;
    }

    public void setNumeroJugador(int numeroJugador) {
        this.numeroJugador = numeroJugador;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    public String toString() {
        return nombreJugador+","+numeroJugador;
    }
}
