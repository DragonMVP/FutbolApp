package edu.unitec.futbolapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nivx1 on 09/11/2015.
 */
public class PartidoMemoria {
    private List<AccionPartido> Acciones;
    private List<CambiosPartido> Cambios;
    private List<PasePartido> Pases;
    private List<FaltaPartido> Faltas;
    private List<JugadorPartido> Jugadores;

    public PartidoMemoria(){
        Acciones = new ArrayList();
        Cambios = new ArrayList();
        Pases = new ArrayList();
        Faltas = new ArrayList();
        Jugadores = new ArrayList();
    }

    public void initJugadoresCancha(List<Jugador> EnCancha){
        for(Jugador tmp: EnCancha){
            Jugadores.add(new JugadorPartido(0,tmp.getIdJugador(),""));
        }
    }

    public void updateTiempoJuegoJugador(Chronometer Cronometro){
        for (JugadorPartido tmp: Jugadores){
            if (tmp.getTIME().equals(""))
                tmp.setTIME(Cronometro.getDifference(tmp.getTiempoEntrada()));
        }
    }

    public void CambioJugador(Jugador Entra,Jugador Sale, Chronometer Cronometro){
        for (JugadorPartido tmp: Jugadores){
            if (tmp.getIdJugador() == Sale.getIdJugador()){
                tmp.setTIME(Cronometro.getTime());
                break;
            }
        }
        Jugadores.add(new JugadorPartido(Cronometro.getTimePassed(),Entra.getIdJugador(),""));
        Cambios.add(new CambiosPartido(Entra.getIdJugador(),Sale.getIdJugador(),Cronometro.getTime()));
    }

    public void PaseJugador(Pase Pa,TipoPase TipoPa,Jugador Envia,Jugador Recibe, Chronometer Cronometro){
        if (Recibe != null)
            Pases.add(new PasePartido(Pa.getIdAccion(),TipoPa.getId(),Envia.getIdJugador(),Recibe.getIdJugador(),Cronometro.getTime()));
        else
            Pases.add(new PasePartido(Pa.getIdAccion(),TipoPa.getId(),Envia.getIdJugador(),-1,Cronometro.getTime()));
    }

    public void FaltaJugador(Falta Fa,Jugador Player,int Cometida,int Tarjeta, Chronometer Cronometro){
        Faltas.add(new FaltaPartido(Fa.getIdAccion(),Player.getIdJugador(),Cometida,Tarjeta,Cronometro.getTime()));
        PartidoCanchaActivity.ENVIA_PASE = null;
    }

    public void AccionJugador(Accion Action,Jugador Player,Chronometer Cronometro){
        Acciones.add(new AccionPartido(Action.getIdAccion(),Player.getIdJugador(),Cronometro.getTime()));
    }


}

class CambiosPartido{
    int idJugadorEntra;
    int idJugadorSale;
    String TIME;

    public CambiosPartido(int idJugadorEntra, int idJugadorSale, String TIME) {
        this.idJugadorEntra = idJugadorEntra;
        this.idJugadorSale = idJugadorSale;
        this.TIME = TIME;
    }

    public int getIdJugadorEntra() {
        return idJugadorEntra;
    }

    public void setIdJugadorEntra(int idJugadorEntra) {
        this.idJugadorEntra = idJugadorEntra;
    }

    public int getIdJugadorSale() {
        return idJugadorSale;
    }

    public void setIdJugadorSale(int idJugadorSale) {
        this.idJugadorSale = idJugadorSale;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }
}
class AccionPartido{
    int idAccion;
    int idJugador;
    String TIME;

    public AccionPartido(int idAccion, int idJugador, String TIME) {
        this.idAccion = idAccion;
        this.idJugador = idJugador;
        this.TIME = TIME;
    }

    public int getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(int idAccion) {
        this.idAccion = idAccion;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }
}
class FaltaPartido{
    int idFalta;
    int idJugador;
    int Cometida; // 0 = RECIBIDA; 1 = COMETIDA
    int Tarjeta; // 0 | NULL = Nada; 1 = Amarilla; 2 = Roja;
    String TIME;

    public FaltaPartido(int idFalta, int idJugador, int cometida, int tarjeta, String TIME) {
        this.idFalta = idFalta;
        this.idJugador = idJugador;
        Cometida = cometida;
        Tarjeta = tarjeta;
        this.TIME = TIME;
    }

    public int getIdFalta() {
        return idFalta;
    }

    public void setIdFalta(int idFalta) {
        this.idFalta = idFalta;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public int getCometida() {
        return Cometida;
    }

    public void setCometida(int cometida) {
        Cometida = cometida;
    }

    public int getTarjeta() {
        return Tarjeta;
    }

    public void setTarjeta(int tarjeta) {
        Tarjeta = tarjeta;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }
}
class PasePartido{
    int idPase;
    int idTipoPase;
    int idJugadorEnvia;
    int idJugadorRecibe; // -1 | NULL = FALLO
    String TIME;

    public PasePartido(int idPase, int idTipoPase, int idJugadorEnvia, int idJugadorRecibe, String TIME) {
        this.idPase = idPase;
        this.idTipoPase = idTipoPase;
        this.idJugadorEnvia = idJugadorEnvia;
        this.idJugadorRecibe = idJugadorRecibe;
        this.TIME = TIME;
    }

    public int getIdJugadorEnvia() {
        return idJugadorEnvia;
    }

    public void setIdJugadorEnvia(int idJugadorEnvia) {
        this.idJugadorEnvia = idJugadorEnvia;
    }

    public int getIdJugadorRecibe() {
        return idJugadorRecibe;
    }

    public void setIdJugadorRecibe(int idJugadorRecibe) {
        this.idJugadorRecibe = idJugadorRecibe;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }
}
class JugadorPartido{
    long tiempoEntrada;
    int idJugador;
    String TIME;

    public JugadorPartido(long tiempoEntrada, int idJugador, String TIME) {
        this.tiempoEntrada = tiempoEntrada;
        this.idJugador = idJugador;
        this.TIME = TIME;
    }

    public long getTiempoEntrada() {
        return tiempoEntrada;
    }

    public void setTiempoEntrada(long tiempoEntrada) {
        this.tiempoEntrada = tiempoEntrada;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }
}