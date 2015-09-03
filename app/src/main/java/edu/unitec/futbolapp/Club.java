package edu.unitec.futbolapp;

/**
 * Created by nivx1 on 09/02/2015.
 */
public class Club {
    private int idClub;
    private String nameClub;

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
