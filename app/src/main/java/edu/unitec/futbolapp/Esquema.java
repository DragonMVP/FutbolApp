package edu.unitec.futbolapp;

import java.io.Serializable;

/**
 * Created by nivx1 on 09/04/2015.
 */
public class Esquema implements Serializable{

    private int idEsquema;
    private int Delanteros;
    private int Medios;
    private int Defensas;

    public Esquema(int idEsquema, int delanteros, int medios, int defensas) {
        this.idEsquema = idEsquema;
        Delanteros = delanteros;
        Medios = medios;
        Defensas = defensas;
    }

    public Esquema(int delanteros, int medios, int defensas) {
        Delanteros = delanteros;
        Medios = medios;
        Defensas = defensas;
    }

    public int getIdEsquema() {
        return idEsquema;
    }

    public void setIdEsquema(int idEsquema) {
        this.idEsquema = idEsquema;
    }

    public int getDelanteros() {
        return Delanteros;
    }

    public void setDelanteros(int delanteros) {
        Delanteros = delanteros;
    }

    public int getMedios() {
        return Medios;
    }

    public void setMedios(int medios) {
        Medios = medios;
    }

    public int getDefensas() {
        return Defensas;
    }

    public void setDefensas(int defensas) {
        Defensas = defensas;
    }


    @Override
    public String toString() {
        return Delanteros+"-"+Medios+"-"+Defensas;
    }
}
