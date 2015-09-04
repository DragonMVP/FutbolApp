package edu.unitec.futbolapp;

import java.util.List;

/**
 * Created by nivx1 on 09/02/2015.
 */
public class Equipo {
    private int idEquipo;
    private int idClub;
    private String nameEquipo;
    private List<Jugador> Jugadores;

    public Equipo(int idEquipo,String nameEquipo ,int idClub) {
        this.idEquipo = idEquipo;
        this.idClub = idClub;
        this.nameEquipo = nameEquipo;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getIdClub() {
        return idClub;
    }

    public void setIdClub(int idClub) {
        this.idClub = idClub;
    }

    public List<Jugador> getJugadores() {
        return Jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        Jugadores = jugadores;
    }

    public String getNameEquipo() {
        return nameEquipo;
    }

    public void setNameEquipo(String nameEquipo) {
        this.nameEquipo = nameEquipo;
    }

    public void addJugador(Jugador newPlayer){
        Jugadores.add(newPlayer);
    }

    @Override
    public String toString() {
        return nameEquipo;
    }
}
