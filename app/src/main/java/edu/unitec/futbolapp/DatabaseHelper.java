package edu.unitec.futbolapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by daemonaeon on 09-02-15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FutbolApp";

    // Tablas
    private static final String TABLE_ACCION = "accion";
    private static final String TABLE_CLUB = "club";
    private static final String TABLE_JUGADOR = "jugador";
    private static final String TABLE_POSICION = "posicion";
    private static final String TABLE_ACCIONPARTIDO = "accionPartido";
    private static final String TABLE_EQUIPO = "equipo";
    private static final String TABLE_PARTIDO = "partido";
    private static final String TABLE_ALINEACIONPARTIDO = "alineacionPartido";
    private static final String TABLE_FALTA = "falta";
    private static final String TABLE_PASE = "pase";
    private static final String TABLE_CAMBIOPARTIDO = "cambioPartido";
    private static final String TABLE_FALTASPARTIDO = "faltasPartido";
    private static final String TABLE_PASEPARTIDO = "pasePartido";

    // Comunes
    private static final String ID = "id";
    private static final String NOMBRE = "nombre";
    private static final String ID_JUGADOR = "idJugador";
    private static final String ID_EQUIPO = "idEquipo";
    private static final String ID_PARTIDO = "idPartido";
    private static final String TIEMPO_JUEGO = "tiempoJuego";
    private static final String ID_POSICION = "idPosicion";

    // Club
    private static final String FOTO = "foto";

    // Equipo
    private static final String ID_CLUB = "idClub";

    // Jugador
    private static final String NUMERO = "numero";

    // Partido
    private static final String FECHA = "fecha";
    private static final String TIEMPO_ACTIVO = "tiempoActivo";
    private static final String TIEMPO_TOTAL = "tiempoTotal";

    // AlineacionPartido

    // PasePartido
    private static final String ID_JUGADOR_ENVIA = "idJugadorEnvia";
    private static final String ID_JUGADOR_RECIBE = "idJugadorRecibe";
    private static final String ID_PASE = "idPase";

    // AccionPartido
    private static final String ID_ACCION = "idAccion";

    // FaltasPartido
    private static final String ID_FALTA = "idFalta";
    private static final String TARJETA_RECIBIDA = "tarjetaRecibida";

    //CambioPartido
    private static final String ID_JUGADOR_SALE = "idJugadorSale";
    private static final String ID_JUGADOR_ENTRA = "idJugadorEntra";


    // Crear Tablas
    private static final String CREATE_TABLE_CLUB = "CREATE TABLE "
            + TABLE_CLUB + "(" + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + NOMBRE
            + " TEXT," + FOTO + " TEXT)";

    private static final String CREATE_TABLE_EQUIPO = "CREATE TABLE "
            + TABLE_EQUIPO + "(" + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + NOMBRE
            + " TEXT," + ID_CLUB + " INTEGER)";

    private static final String CREATE_TABLE_JUGADOR = "CREATE TABLE "
            + TABLE_JUGADOR + "(" + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + NOMBRE
            + " TEXT," + ID_EQUIPO + " INTEGER," + ID_POSICION + "INTEGER,"+NUMERO+"INTEGER, PRIMARY KEY ("+ID+","+ID_EQUIPO+"))";

    private static final String CREATE_TABLE_PARTIDO = "CREATE TABLE "
            + TABLE_PARTIDO + "(" + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + FECHA
            + " TEXT,"+ TIEMPO_ACTIVO + " TEXT,"+ TIEMPO_TOTAL + " TEXT," + ID_EQUIPO + " INTEGER)";

    private static final String CREATE_TABLE_ALINEACIONPARTIDO = "CREATE TABLE "
            + TABLE_ALINEACIONPARTIDO + "(" + ID_PARTIDO + " INTEGER ," + ID_JUGADOR
            + " INTEGER," + ID_POSICION + " INTEGER," + TIEMPO_JUEGO + "INTEGER, PRIMARY KEY ("+ID_PARTIDO+","+ID_JUGADOR+"))";

    private static final String CREATE_TABLE_PASEPARTIDO = "CREATE TABLE "
            + TABLE_PASEPARTIDO + "(" + ID_PARTIDO + " INTEGER ," + ID_JUGADOR_ENVIA
            + " INTEGER," + ID_JUGADOR_RECIBE+ " INTEGER,"+ TIEMPO_JUEGO+ " INTEGER," + ID_PASE + "INTEGER, PRIMARY KEY ("+ID_PARTIDO+","+ID_JUGADOR+"))";

    private static final String CREATE_TABLE_ACCIONPARTIDO = "CREATE TABLE "
            + TABLE_ACCIONPARTIDO + "(" + ID_PARTIDO + " INTEGER ," + ID_JUGADOR
            + " INTEGER," + ID_ACCION + " INTEGER," + TIEMPO_JUEGO + "TEXT , PRIMARY KEY ("+ID_PARTIDO+"))";

    private static final String CREATE_TABLE_FALTASPARTIDO = "CREATE TABLE "
            + TABLE_FALTASPARTIDO+ "(" + ID_PARTIDO + " INTEGER ," + ID_JUGADOR
            + " INTEGER," + ID_FALTA + " INTEGER," + TARJETA_RECIBIDA + "INTEGER, PRIMARY KEY ("+ID_PARTIDO+"))";

    private static final String CREATE_TABLE_POSICION = "CREATE TABLE "
            + TABLE_POSICION + "(" + ID + " INTEGER," + NOMBRE
            + " TEXT)";

    private static final String CREATE_TABLE_ACCION = "CREATE TABLE "
            + TABLE_ACCION + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NOMBRE
            + " TEXT)";

    private static final String CREATE_TABLE_FALTA = "CREATE TABLE "
            + TABLE_FALTA + "(" + ID + " INTEGER," + NOMBRE
            + " TEXT)";

    private static final String CREATE_TABLE_PASE = "CREATE TABLE "
            + TABLE_PASE + "(" + ID + " INTEGER," + NOMBRE
            + " TEXT)";

    private static final String CREATE_TABLE_CAMBIOPARTIDO = "CREATE TABLE "
            + TABLE_CAMBIOPARTIDO + "(" + ID_PARTIDO + " INTEGER ," + ID_JUGADOR_SALE
            + " INTEGER," + ID_JUGADOR_ENTRA + " INTEGER, PRIMARY KEY ("+ID_PARTIDO+","+ID_JUGADOR_SALE+"))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_CLUB);
        db.execSQL(CREATE_TABLE_EQUIPO);
        db.execSQL(CREATE_TABLE_JUGADOR);
        db.execSQL(CREATE_TABLE_PARTIDO);
        db.execSQL(CREATE_TABLE_ALINEACIONPARTIDO);
        db.execSQL(CREATE_TABLE_PASEPARTIDO);
        db.execSQL(CREATE_TABLE_ACCIONPARTIDO);
        db.execSQL(CREATE_TABLE_FALTASPARTIDO);
        db.execSQL(CREATE_TABLE_POSICION);
        db.execSQL(CREATE_TABLE_ACCION);
        db.execSQL(CREATE_TABLE_FALTA);
        db.execSQL(CREATE_TABLE_PASE);
        db.execSQL(CREATE_TABLE_CAMBIOPARTIDO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLUB);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JUGADOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTIDO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALINEACIONPARTIDO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASEPARTIDO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCIONPARTIDO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FALTASPARTIDO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSICION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FALTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAMBIOPARTIDO);

        // create new tables
        onCreate(db);
    }
}