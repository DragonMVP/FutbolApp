package edu.unitec.futbolapp;

/**
 * Created by carlo on 10/6/2015.
 */
public class Usuario {
    private String Nomrbe;
    private String Password;
    private String User;

    public Usuario(String nomrbe, String password, String user) {
        Nomrbe = nomrbe;
        Password = password;
        User = user;
    }

    public String getNomrbe() {
        return Nomrbe;
    }

    public void setNomrbe(String nomrbe) {
        Nomrbe = nomrbe;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    @Override
    public String toString() {
        return Nomrbe;
    }
}
