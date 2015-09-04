package edu.unitec.futbolapp;

import java.util.List;

/**
 * Created by nivx1 on 09/02/2015.
 */
public class Club {
    private int idClub;
    private String nameClub;
    private List<Equipo> Equipos;

    public List<Equipo> getEquipos() {
        return Equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        Equipos = equipos;
    }

    public Club(int idClub, String nameClub) {
        this.idClub = idClub;
        this.nameClub = nameClub;
    }

    public int getIdClub() {
        return idClub;
    }

    public void setIdClub(int idClub) {
        this.idClub = idClub;
    }

    public String getNameClub() {
        return nameClub;
    }

    public void setNameClub(String nameClub) {
        this.nameClub = nameClub;
    }

    @Override
    public String toString() {
        return nameClub;
    }
}
