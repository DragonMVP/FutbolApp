package edu.unitec.futbolapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daemonaeon on 09-02-15.
 */
public class MyDatabaseHandler extends SQLiteOpenHelper {

    private static final String LOG = "MyDatabaseHandler";
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
    private static final String TABLE_ESQUEMA = "esquema";

    // Comunes

    private static final String TIEMPO_JUEGO = "tiempoJuego";


    // Club
    private static final String ID_CLUB = "idClub";
    private  static final String NOMBRE_CLUB = "nombreClub";
    private static final String FOTO = "fotoClub";

    // Equipo
    private static final String ID_EQUIPO = "idEquipo";
    private  static final String NOMBRE_EQUIPO = "nombreEquipo";

    // Jugador
    private static final String ID_JUGADOR = "idJugador";
    private  static final String NOMBRE_JUGADOR = "nombreJugador";
    private static final String NUMERO_JUGADOR = "numeroJugador";

    // Partido
    private static final String ID_PARTIDO = "idPartido";
    private static final String FECHA_PARTIDO = "fecha";
    private static final String TIEMPO_ACTIVO = "tiempoActivo";
    private static final String TIEMPO_TOTAL = "tiempoTotal";

    // AlineacionPartido

    // PasePartido
    private static final String ID_JUGADOR_ENVIA = "idJugadorEnvia";
    private static final String ID_JUGADOR_RECIBE = "idJugadorRecibe";
    private static final String ID_PASE = "idPase";

    // AccionPartido
    private static final String ID_ACCION = "idAccion";
    private  static final String NOMBRE_ACCION = "nombreAccion";

    // FaltasPartido
    private static final String ID_FALTA = "idFalta";
    private  static final String NOMBRE_FALTA = "nombreFalta";
    private static final String TARJETA_RECIBIDA = "tarjetaRecibida";

    //CambioPartido
    private static final String ID_JUGADOR_SALE = "idJugadorSale";
    private static final String ID_JUGADOR_ENTRA = "idJugadorEntra";

    //POSICON
    private static final String ID_POSICION = "idPosicion";
    private  static final String NOMBRE_POSICION = "nombrePosicion";

    //ESQUEMA
    private static final String ID_ESQUEMA= "idEsquema";
    private static final String NUM_OFENSIVA= "numeroOfensiva";
    private static final String NUM_MEDIOCAMPO = "numeroMedioCampo";
    private static final String NUM_DEFENSIVA = "numeroDefensiva";

    // Crear Tablas
    private static final String CREATE_TABLE_CLUB = "CREATE TABLE "
            + TABLE_CLUB + "(" + ID_CLUB + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + NOMBRE_CLUB
            + " TEXT," + FOTO + " TEXT)";

    private static final String CREATE_TABLE_EQUIPO = "CREATE TABLE "
            + TABLE_EQUIPO + "(" + ID_EQUIPO + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + NOMBRE_EQUIPO
            + " TEXT," + ID_CLUB + " INTEGER)";

    private static final String CREATE_TABLE_JUGADOR = "CREATE TABLE "
            + TABLE_JUGADOR + "(" + ID_JUGADOR + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + NOMBRE_JUGADOR
            + " TEXT," + ID_EQUIPO + " INTEGER," + ID_POSICION + " INTEGER,"+NUMERO_JUGADOR+" INTEGER)";

    private static final String CREATE_TABLE_PARTIDO = "CREATE TABLE "
            + TABLE_PARTIDO + "(" + ID_PARTIDO + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + FECHA_PARTIDO
            + " TEXT,"+ TIEMPO_ACTIVO + " TEXT,"+ TIEMPO_TOTAL + " TEXT," + ID_EQUIPO + " INTEGER)";

    private static final String CREATE_TABLE_ALINEACIONPARTIDO = "CREATE TABLE "
            + TABLE_ALINEACIONPARTIDO + "(" + ID_PARTIDO + " INTEGER ," + ID_JUGADOR
            + " INTEGER," + ID_POSICION + " INTEGER," + TIEMPO_JUEGO + " INTEGER, PRIMARY KEY ("+ID_PARTIDO+","+ID_JUGADOR+"))";

    private static final String CREATE_TABLE_PASEPARTIDO = "CREATE TABLE "
            + TABLE_PASEPARTIDO + "(" + ID_PARTIDO + " INTEGER ," + ID_JUGADOR_ENVIA
            + " INTEGER," + ID_JUGADOR_RECIBE+ " INTEGER,"+ TIEMPO_JUEGO+ " INTEGER," + ID_PASE + " INTEGER, PRIMARY KEY ("+TIEMPO_JUEGO+"))";

    private static final String CREATE_TABLE_ACCIONPARTIDO = "CREATE TABLE "
            + TABLE_ACCIONPARTIDO + "(" + ID_PARTIDO + " INTEGER ," + ID_JUGADOR
            + " INTEGER," + ID_ACCION + " INTEGER," + TIEMPO_JUEGO + " TEXT , PRIMARY KEY ("+ID_PARTIDO+"))";

    private static final String CREATE_TABLE_FALTASPARTIDO = "CREATE TABLE "
            + TABLE_FALTASPARTIDO+ "(" + ID_PARTIDO + " INTEGER ," + ID_JUGADOR
            + " INTEGER," + ID_FALTA + " INTEGER," + TARJETA_RECIBIDA + " INTEGER, PRIMARY KEY ("+ID_PARTIDO+"))";

    private static final String CREATE_TABLE_POSICION = "CREATE TABLE "
            + TABLE_POSICION + "(" + ID_POSICION + " INTEGER PRIMARY KEY AUTOINCREMENT," + NOMBRE_POSICION
            + " TEXT)";

    private static final String CREATE_TABLE_ACCION = "CREATE TABLE "
            + TABLE_ACCION + "(" + ID_ACCION + " INTEGER PRIMARY KEY AUTOINCREMENT," + NOMBRE_ACCION
            + " TEXT)";

    private static final String CREATE_TABLE_FALTA = "CREATE TABLE "
            + TABLE_FALTA + "(" + ID_FALTA + " INTEGER PRIMARY KEY AUTOINCREMENT," + NOMBRE_FALTA
            + " TEXT)";

    private static final String CREATE_TABLE_PASE = "CREATE TABLE "
            + TABLE_PASE + "(" + ID_PASE + " INTEGER PRIMARY KEY AUTOINCREMENT," + NOMBRE_FALTA
            + " TEXT)";

    private static final String CREATE_TABLE_CAMBIOPARTIDO = "CREATE TABLE "
            + TABLE_CAMBIOPARTIDO + "(" + ID_PARTIDO + " INTEGER ," + ID_JUGADOR_SALE
            + " INTEGER," + ID_JUGADOR_ENTRA + " INTEGER, PRIMARY KEY ("+ID_PARTIDO+","+ID_JUGADOR_SALE+"))";

    private static final String CREATE_TABLE_ESQUEMA = "CREATE TABLE "
            + TABLE_ESQUEMA +"("+ID_ESQUEMA+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NUM_OFENSIVA
            +" INTEGER,"+NUM_MEDIOCAMPO+" INTEGER,"+NUM_DEFENSIVA+" INTEGER)";

    public MyDatabaseHandler(Context context) {
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
        db.execSQL(CREATE_TABLE_ESQUEMA);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESQUEMA);

        // create new tables
        onCreate(db);
    }

    public List<Club> getAllClubs(){
        List<Club> retVal = new ArrayList();
        String Query = "SELECT "+ID_CLUB+", "+NOMBRE_CLUB+" FROM " + TABLE_CLUB+"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query,null);

        if (cursor.moveToFirst()){
            retVal.add(new Club(cursor.getInt(0),cursor.getString(1)));
            while(cursor.moveToNext()){
                retVal.add(new Club(cursor.getInt(0),cursor.getString(1)));
            }
        }

        cursor.close();
        db.close();



        return retVal;
    }

    public List<Equipo> getAllEquipos(Club CLUB_PADRE){
        List<Equipo> retVal = new ArrayList();
        String Query = "SELECT "+ID_EQUIPO+", "+NOMBRE_EQUIPO+" FROM " + TABLE_EQUIPO+" WHERE "+ID_CLUB+"="+CLUB_PADRE.getIdClub();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query,null);

        if (cursor.moveToFirst()){
            retVal.add(new Equipo(cursor.getInt(0),cursor.getString(1),CLUB_PADRE.getIdClub()));
            while(cursor.moveToNext()){
                retVal.add(new Equipo(cursor.getInt(0),cursor.getString(1),CLUB_PADRE.getIdClub()));
            }
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public List<Equipo> getAllEquipos(int CLUB_PADRE){
        List<Equipo> retVal = new ArrayList();
        String Query = "SELECT "+ID_EQUIPO+", "+NOMBRE_EQUIPO+" FROM " + TABLE_EQUIPO+" WHERE "+ID_CLUB+"="+CLUB_PADRE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query,null);

        if (cursor.moveToFirst()){
            retVal.add(new Equipo(cursor.getInt(0),cursor.getString(1),CLUB_PADRE));
            while(cursor.moveToNext()){
                retVal.add(new Equipo(cursor.getInt(0),cursor.getString(1),CLUB_PADRE));
            }
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public List<Jugador> getAllPlayers(int EQUIPO){
        List<Jugador> retVal = new ArrayList();
        String Query = "SELECT "+ID_JUGADOR+","+ID_POSICION+", "+NOMBRE_JUGADOR+","+NUMERO_JUGADOR+" FROM " + TABLE_JUGADOR+" WHERE "+ID_JUGADOR+"="+EQUIPO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query,null);

        if (cursor.moveToFirst()){
            retVal.add(new Jugador(cursor.getInt(0),EQUIPO,getPosicion(cursor.getInt(1)),cursor.getString(2),cursor.getInt(3)));
            while(cursor.moveToNext()){
                retVal.add(new Jugador(cursor.getInt(0),EQUIPO,getPosicion(cursor.getInt(1)),cursor.getString(2),cursor.getInt(3)));
            }
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public PosicionCampo getPosicion(int idPosicion){
        PosicionCampo retVal = new PosicionCampo(idPosicion);
        String Query = "SELECT "+ID_POSICION+","+NOMBRE_POSICION+" FROM "+TABLE_POSICION+" WHERE "+ID_POSICION+"="+idPosicion;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query,null);

        if (cursor.moveToFirst()){
            retVal.setIdPosicion(idPosicion);
            retVal.setDescripcionPosicion(cursor.getString(1));
        }

        cursor.close();
        db.close();
        return retVal;
    }

    public void addClub(Club newClub){
        String Query = "INSERT INTO "+TABLE_CLUB+"("+NOMBRE_CLUB+") VALUES('"+newClub.getNameClub().trim()+"')";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(Query);
        db.close();
    }

    public void addEquipo(Equipo newEquipo){
        ContentValues values = new ContentValues();
        values.put(ID_CLUB,newEquipo.getIdClub());
        values.put(NOMBRE_EQUIPO,newEquipo.getNameEquipo());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EQUIPO, null, values);
        db.close();
    }

    public void addJugador(Jugador Jugador){
        ContentValues values = new ContentValues();
        values.put(ID_EQUIPO,Jugador.getIdEquipo());
        values.put(ID_POSICION,Jugador.getPosicion().getIdPosicion());
        values.put(NUMERO_JUGADOR,Jugador.getNumeroJugador());
        values.put(NOMBRE_JUGADOR,Jugador.getNombreJugador());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_JUGADOR, null, values);
        db.close();
    }

    public void addJugadorNoPosicion(Jugador Jugador){
        ContentValues values = new ContentValues();
        values.put(ID_EQUIPO,Jugador.getIdEquipo());
        values.put(NUMERO_JUGADOR,Jugador.getNumeroJugador());
        values.put(NOMBRE_JUGADOR,Jugador.getNombreJugador());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_JUGADOR, null, values);
        db.close();
    }


    public List<Esquema> getAllEsquema(){
        List<Esquema> retVal = new ArrayList();

        String Query = "SELECT "+ID_ESQUEMA+","+NUM_OFENSIVA+","+NUM_MEDIOCAMPO+","+NUM_DEFENSIVA+" FROM "+TABLE_ESQUEMA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        if (cursor.moveToFirst()){
            retVal.add(new Esquema(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3)));
            while (cursor.moveToNext()){
                retVal.add(new Esquema(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3)));
            }
        }
        cursor.close();
        db.close();

        return retVal;
    }

    public void addEsquema(Esquema newEsquema){
        ContentValues values = new ContentValues();
        values.put(NUM_OFENSIVA,newEsquema.getDelanteros());
        values.put(NUM_DEFENSIVA,newEsquema.getDefensas());
        values.put(NUM_MEDIOCAMPO,newEsquema.getMedios());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ESQUEMA, null, values);
        db.close();
    }

    public int getClubID(String clName){
        int retVal = 0;

        String Query = "SELECT "+ID_CLUB+" FROM "+TABLE_CLUB+" WHERE "+NOMBRE_CLUB+" = "+"\""+clName+"\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        if (cursor.moveToFirst()){
            retVal = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return retVal;
    }

    public int getEquipoID(String eqName){
        int retVal = 0;

        String Query = "SELECT "+ID_EQUIPO+" FROM "+TABLE_EQUIPO+" WHERE "+NOMBRE_EQUIPO+" = "+"\""+eqName+"\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        if (cursor.moveToFirst()){
            retVal = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return retVal;
    }


}