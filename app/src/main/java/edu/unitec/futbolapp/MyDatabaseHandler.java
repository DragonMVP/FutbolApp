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

    public static final Pase DEFAULT_PASE_ACCION = new Pase(1,"Pase","PS");
    public static final TipoPase DEFAULT_TIPO_PASE = new TipoPase(1,"Corto");

    private static final String LOG = "MyDatabaseHandler";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "FutbolApp";

    private static final int DEFAULT_ACCION = 19;
    private static final int DEFAULT_PASE = 4;
    private static final int DEFAULT_FALTA = 4;

    // Tabla
    private static final String TABLE_USER="usuario";
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
    private static final String TABLE_TIPOPASE = "tipoPase";
    private static final String TABLE_CAMBIOPARTIDO = "cambioPartido";
    private static final String TABLE_FALTASPARTIDO = "faltasPartido";
    private static final String TABLE_PASEPARTIDO = "pasePartido";
    private static final String TABLE_ESQUEMA = "esquema";

    // Comunes

    private static final String TIEMPO_JUEGO = "tiempoJuego";

    //Usuarios
    private static final String NOMBRE_USER="nombreuser";
    private static final String ID_USER = "idusario";
    private static final String PASSWORD_USER="passworduser";
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
    private static final String FOTO_JUGADOR = "fotoJugadorLocation";

    // Partido
    private static final String ID_PARTIDO = "idPartido";
    private static final String FECHA_PARTIDO = "fecha";
    private static final String TIEMPO_ACTIVO = "tiempoActivo";
    private static final String TIEMPO_TOTAL = "tiempoTotal";
    private static final String POSESION_BALON = "posesionBalon";
    private static final String ACCION_PORTERO = "accionPortero";

    // AlineacionPartido

    // PasePartido
    private  static final String ID_PASEPARTIDO = "idPasePartido";
    private static final String ID_JUGADOR_ENVIA = "idJugadorEnvia";
    private static final String ID_JUGADOR_RECIBE = "idJugadorRecibe";
    private static final String ID_PASE = "idPase";

    private static final String ABREV_PASE = "abreviacionPase";
    private static final String NOMBRE_PASE = "nombrePase";

    //TipoPase
    private static final String ID_TIPOPASE = "idTipoPase";
    private static final String NOMBRE_TIPOPASE = "nombreTipo";

    // AccionPartido
    private static final String ID_ACCION = "idAccion";
    private static final String ABREV_ACCION = "abreviacionAccion";
    private  static final String NOMBRE_ACCION = "nombreAccion";

    // FaltasPartido
    private static final String ID_FALTA = "idFalta";
    private static final String ABREV_FALTA = "abreviacionFalta";
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
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + NOMBRE_USER + " TEXT," + PASSWORD_USER + " TEXT,"+ID_USER+" TEXT)";

    private static final String CREATE_TABLE_CLUB = "CREATE TABLE "
            + TABLE_CLUB + "(" + ID_CLUB + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + NOMBRE_CLUB
            + " TEXT," + FOTO + " TEXT)";

    private static final String CREATE_TABLE_EQUIPO = "CREATE TABLE "
            + TABLE_EQUIPO + "(" + ID_EQUIPO + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + NOMBRE_EQUIPO
            + " TEXT," + ID_CLUB + " INTEGER)";

    private static final String CREATE_TABLE_JUGADOR = "CREATE TABLE "
            + TABLE_JUGADOR + "(" + ID_JUGADOR + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + NOMBRE_JUGADOR
            + " TEXT," + ID_EQUIPO + " INTEGER," + ID_POSICION + " INTEGER,"+NUMERO_JUGADOR+" INTEGER, "+FOTO_JUGADOR+" BLOB)";

    private static final String CREATE_TABLE_PARTIDO = "CREATE TABLE "
            + TABLE_PARTIDO + "(" + ID_PARTIDO + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + FECHA_PARTIDO
            + " TEXT,"+POSESION_BALON+" TEXT,"+ TIEMPO_ACTIVO + " TEXT,"+ TIEMPO_TOTAL + " TEXT," + ID_EQUIPO + " INTEGER)";

    private static final String CREATE_TABLE_ALINEACIONPARTIDO = "CREATE TABLE "
            + TABLE_ALINEACIONPARTIDO + "(" + ID_PARTIDO + " INTEGER ," + ID_JUGADOR
            + " INTEGER," + ID_POSICION + " INTEGER," + TIEMPO_JUEGO + " INTEGER, PRIMARY KEY ("+ID_PARTIDO+","+ID_JUGADOR+"))";

    private static final String CREATE_TABLE_PASEPARTIDO = "CREATE TABLE "
            + TABLE_PASEPARTIDO + "("+ID_PASEPARTIDO+" INTEGER PRIMARY KEY AUTOINCREMENT," + ID_PARTIDO + " INTEGER ," + ID_JUGADOR_ENVIA
            + " INTEGER," + ID_JUGADOR_RECIBE+ " INTEGER,"+ TIEMPO_JUEGO+ " INTEGER," + ID_PASE + " INTEGER)";

    private static final String CREATE_TABLE_TIPOPASE = "CREATE TABLE "+
            TABLE_TIPOPASE+"("+ID_TIPOPASE+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NOMBRE_TIPOPASE+" TEXT)";

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
            + " TEXT, "+ABREV_ACCION+" TEXT,"+ACCION_PORTERO+" INTEGER)";

    private static final String CREATE_TABLE_FALTA = "CREATE TABLE "
            + TABLE_FALTA + "(" + ID_FALTA + " INTEGER PRIMARY KEY AUTOINCREMENT," + NOMBRE_FALTA
            + " TEXT, "+ABREV_FALTA+" TEXT,"+ACCION_PORTERO+" INTEGER)";

    private static final String CREATE_TABLE_PASE = "CREATE TABLE "
            + TABLE_PASE + "(" + ID_PASE + " INTEGER PRIMARY KEY AUTOINCREMENT," + NOMBRE_PASE
            + " TEXT, "+ABREV_PASE+" TEXT,"+ACCION_PORTERO+" INTEGER)";

    private static final String CREATE_TABLE_CAMBIOPARTIDO = "CREATE TABLE "
            + TABLE_CAMBIOPARTIDO + "(" + ID_PARTIDO + " INTEGER ," + ID_JUGADOR_SALE
            + " INTEGER," + ID_JUGADOR_ENTRA + " INTEGER, PRIMARY KEY ("+ID_PARTIDO+","+ID_JUGADOR_SALE+"))";

    private static final String CREATE_TABLE_ESQUEMA = "CREATE TABLE "
            + TABLE_ESQUEMA +"("+ID_ESQUEMA+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NUM_OFENSIVA
            +" INTEGER,"+NUM_MEDIOCAMPO+" INTEGER,"+NUM_DEFENSIVA+" INTEGER)";

    public MyDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void initDB(SQLiteDatabase db){
        String INITIAL_ACCION = "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Tiro Acertado','T.AC',0); " +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Tiro Fallido','T.FA',0); " +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('D. Aereo Ganado','DAGA',0); " +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('D. Aereo Fallido','DAFA',0); " +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Centro Acertado','C.AC',0); " +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Centro Fallido','C.FA',0); " +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Uno vrs Uno Ganado','U-UG',0); " +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Uno vrs Uno Fallido','U-UF',0); " +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Balon perdido','B.PE',0); " +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Goles', 'GOL.',0); " +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Asistencia','ASIS',0); " +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Intercepciones','INTE',0); " +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Balon Recuperado','B.RE',0); " +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Tiro Esquina','T.ES',0);";
        String INITIAL_FALTA ="INSERT INTO Falta('nombreFalta','abreviacionFalta','accionPortero') VALUES ('Falta Penal','F.PE',0); " +
                "INSERT INTO Falta('nombreFalta','abreviacionFalta','accionPortero') VALUES ('Falta Tiro Directo','F.TD',0); " +
                "INSERT INTO Falta('nombreFalta','abreviacionFalta','accionPortero') VALUES ('Falta Tiro Indirecto','F.TI',0); " +
                "INSERT INTO Falta('nombreFalta','abreviacionFalta','accionPortero') VALUES ('Falta','FALT',0);";
        String INITIAL_PASE ="INSERT INTO Pase('nombrePase','abreviacionPase','accionPortero') VALUES ('Pase','PASE',0); " +
                "INSERT INTO Pase('nombrePase','abreviacionPase','accionPortero') VALUES ('Pase Filtrado','P.FI',0); " +
                "INSERT INTO Pase('nombrePase','abreviacionPase','accionPortero') VALUES ('Pase Detras','P.DE',0); " +
                "INSERT INTO Pase('nombrePase','abreviacionPase','accionPortero') VALUES ('Saque Banda','S.BA',0);";
        String INITIAL_TIPO = "INSERT INTO TipoPase('nombreTipo') VALUES('Corto'); " +
                "INSERT INTO TipoPase('nombreTipo') VALUES('Medio'); " +
                "INSERT INTO TipoPase('nombreTipo') VALUES('Largo');";
        String INITIAL_POSICION = "INSERT INTO Posicion('nombrePosicion') VALUES('Portero');" +
                "INSERT INTO Posicion('nombrePosicion') VALUES('Defensa');" +
                "INSERT INTO Posicion('nombrePosicion') VALUES('Medio');" +
                "INSERT INTO Posicion('nombrePosicion') VALUES('Delantero')";


        String INITIAL_ACCION_PORTERO = "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Goles Concedidos','G.CO',1);" +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Tiro Atrapado','T.AT',1);" +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Tiro Desviado','T.DES',1);" +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Centro Atrapado','C.AT',1);" +
                "INSERT INTO ACCION('nombreAccion','abreviacionAccion','accionPortero') VALUES ('Mano a Mano','MaM.',1);";

        String INITIAl_USER="INSERT INTO usuario('passworduser','idusario','nombreuser') VALUES ('admin','admin','admin');";
        String[] split = INITIAL_ACCION.split(";");
        for (int i = 0; i < split.length; i++) {
            String tmp = split[i];
            db.execSQL(tmp);
        }
        for(String tmp : INITIAL_FALTA.split(";")){
            db.execSQL(tmp);
        }
        for(String tmp : INITIAL_PASE.split(";")){
            db.execSQL(tmp);
        }
        for(String tmp : INITIAL_TIPO.split(";")){
            db.execSQL(tmp);
        }

        for (String tmp:INITIAL_POSICION.split(";")){
            db.execSQL(tmp);
        }

        for (String tmp:INITIAL_ACCION_PORTERO.split(";")){
            db.execSQL(tmp);
        }
        db.execSQL(INITIAl_USER);
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
        db.execSQL(CREATE_TABLE_TIPOPASE);
        db.execSQL(CREATE_TABLE_USER);
        initDB(db);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPOPASE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // create new tables
        onCreate(db);
    }
    public List<Usuario> getAllUsers(){
        List<Usuario> retVal = new ArrayList();
        String Query = "SELECT "+NOMBRE_USER+", "+PASSWORD_USER+", "+ ID_USER +" FROM " + TABLE_USER+"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query,null);

        if (cursor.moveToFirst()){
            retVal.add(new Usuario(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            while(cursor.moveToNext()){
                retVal.add(new Usuario(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
            }
        }

        cursor.close();
        db.close();



        return retVal;
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
            retVal.add(new Equipo(cursor.getInt(0), cursor.getString(1), CLUB_PADRE));
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
        String Query = "SELECT "+ID_JUGADOR+","+ID_POSICION+", "+NOMBRE_JUGADOR+","+NUMERO_JUGADOR+","+FOTO_JUGADOR+" FROM " + TABLE_JUGADOR+" WHERE "+ID_EQUIPO+"="+EQUIPO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        if (cursor.moveToFirst()){
            retVal.add(new Jugador(cursor.getInt(0), EQUIPO, getPosicion(cursor.getInt(1)), cursor.getString(2), cursor.getInt(3), cursor.getBlob(4)));
            while(cursor.moveToNext()){
                retVal.add(new Jugador(cursor.getInt(0),EQUIPO,getPosicion(cursor.getInt(1)),cursor.getString(2),cursor.getInt(3),cursor.getBlob(4)));
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
        Cursor cursor = db.rawQuery(Query, null);

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
        values.put(ID_CLUB, newEquipo.getIdClub());
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
        if (Jugador.getPICTURE() == null){
            values.putNull(FOTO_JUGADOR);
        }else {
            values.put(FOTO_JUGADOR, Jugador.getPICTURE());
        }
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


    public List<Accion> getDefaultAccions(){
        List<Accion> retVal = new ArrayList();
        retVal.addAll(getJustDefaultPase());
        retVal.addAll(getJustDefaultAccion());
        retVal.addAll(getJustDefaultFalta());
        return retVal;
    }

    public List<Accion> getDefaultAccionPortero(){
        List<Accion> retVal = new ArrayList();
        retVal.addAll(getJustDefaultPase());
        retVal.addAll(getJustDefaultAccionPortero());
        retVal.addAll(getJustDefaultFalta());
        return retVal;
    }

    public List<Accion> getJustDefaultAccionPortero(){
        List<Accion> retVal = new ArrayList<>();
        String Query = "SELECT "+ID_ACCION+","+NOMBRE_ACCION+", "+ABREV_ACCION+" FROM "+TABLE_ACCION+" WHERE "+ID_ACCION +" <= "+ DEFAULT_ACCION +" AND "+ACCION_PORTERO+" = 1";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()){
            retVal.add(new Accion(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            while(cursor.moveToNext()){
                retVal.add(new Accion(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public List<Accion> getJustDefaultAccion(){
        List<Accion> retVal = new ArrayList<>();
        String Query = "SELECT "+ID_ACCION+","+NOMBRE_ACCION+", "+ABREV_ACCION+" FROM "+TABLE_ACCION+" WHERE "+ID_ACCION +" <= "+ DEFAULT_ACCION +" AND "+ACCION_PORTERO+" = 0";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()){
            retVal.add(new Accion(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            while(cursor.moveToNext()){
                retVal.add(new Accion(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public List<Falta> getJustDefaultFalta(){
        List<Falta> retVal = new ArrayList<>();
        String Query = "SELECT "+ID_FALTA+","+NOMBRE_FALTA+", "+ABREV_FALTA+" FROM "+TABLE_FALTA+" WHERE "+ID_FALTA +" <= "+ DEFAULT_FALTA;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()){
            retVal.add(new Falta(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            while(cursor.moveToNext()){
                retVal.add(new Falta(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public List<Pase> getJustDefaultPase(){
        List<Pase> retVal = new ArrayList<>();
        String Query = "SELECT "+ID_PASE+","+NOMBRE_PASE+","+ABREV_PASE+" FROM "+TABLE_PASE+" WHERE "+ID_PASE +" <= "+ DEFAULT_PASE;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(Query,null);
        if (cursor.moveToFirst()){
            retVal.add(new Pase(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            while(cursor.moveToNext()){
                retVal.add(new Pase(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public List<Accion> getUserAccions(){
        List<Accion> retVal = new ArrayList();
        retVal.addAll(getUserPase());
        retVal.addAll(getUserAccion());
        retVal.addAll(getUserFalta());
        return retVal;
    }

    public List<Accion> getUserAccionsPortero(){
        List<Accion> retVal = new ArrayList();
        retVal.addAll(getUserPase());
        retVal.addAll(getUserAccionPortero());
        retVal.addAll(getUserFalta());
        return retVal;
    }

    public List<Accion> getUserAccionPortero(){
        List<Accion> retVal = new ArrayList<>();
        String Query = "SELECT "+ID_ACCION+","+NOMBRE_ACCION+", "+ABREV_ACCION+" FROM "+TABLE_ACCION+" WHERE "+ID_ACCION +" > "+ DEFAULT_ACCION + " AND " +ACCION_PORTERO + " = 1";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(Query,null);
        if (cursor.moveToFirst()){
            retVal.add(new Accion(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            while(cursor.moveToNext()){
                retVal.add(new Accion(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public List<Accion> getUserAccion(){
        List<Accion> retVal = new ArrayList<>();
        String Query = "SELECT "+ID_ACCION+","+NOMBRE_ACCION+", "+ABREV_ACCION+" FROM "+TABLE_ACCION+" WHERE "+ID_ACCION +" > "+ DEFAULT_ACCION + " AND " +ACCION_PORTERO + " = 0";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(Query,null);
        if (cursor.moveToFirst()){
            retVal.add(new Accion(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            while(cursor.moveToNext()){
                retVal.add(new Accion(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public List<Falta> getUserFalta(){
        List<Falta> retVal = new ArrayList<>();
        String Query = "SELECT "+ID_FALTA+","+NOMBRE_FALTA+", "+ABREV_FALTA+" FROM "+TABLE_FALTA+" WHERE "+ID_FALTA +" > "+ DEFAULT_FALTA;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(Query,null);
        if (cursor.moveToFirst()){
            retVal.add(new Falta(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            while(cursor.moveToNext()){
                retVal.add(new Falta(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public List<Pase> getUserPase(){
        List<Pase> retVal = new ArrayList<>();
        String Query = "SELECT "+ID_PASE+","+NOMBRE_PASE+", "+ABREV_PASE+" FROM "+TABLE_PASE+" WHERE "+ID_PASE +" > "+ DEFAULT_PASE;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(Query,null);
        if (cursor.moveToFirst()){
            retVal.add(new Pase(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            while(cursor.moveToNext()){
                retVal.add(new Pase(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public void addAccion(Accion newAccion){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(ACCION_PORTERO, newAccion.getAccionPortero());
        if (newAccion instanceof Pase) {
            values.put(NOMBRE_PASE, newAccion.getNombreAccion());
            values.put(ABREV_PASE,newAccion.getAbreviacionAccion());
            db.insert(TABLE_PASE, null, values);
        }
        else if (newAccion instanceof Falta) {
            values.put(NOMBRE_FALTA,newAccion.getNombreAccion());
            values.put(ABREV_FALTA,newAccion.getAbreviacionAccion());
            db.insert(TABLE_FALTA, null, values);
        }else{
            values.put(NOMBRE_ACCION,newAccion.getNombreAccion());
            values.put(ABREV_ACCION,newAccion.getAbreviacionAccion());
            db.insert(TABLE_ACCION,null,values);
        }
        db.close();
    }

    public Accion getLatestAccion(){
        Accion retVal;
        String Query = "SELECT "+ID_ACCION+","+NOMBRE_ACCION+", "+ABREV_ACCION+" FROM "+TABLE_ACCION+" WHERE "+ID_ACCION +" > "+ DEFAULT_ACCION +" ORDER BY "+ID_ACCION+" DESC LIMIT 1 ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()){
            retVal = new Accion(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
        }else{
            retVal = null;
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public Pase getLatestPase(){
        Pase retVal;
        String Query = "SELECT "+ID_PASE+","+NOMBRE_PASE+", "+ABREV_PASE+" FROM "+TABLE_PASE+" WHERE "+ID_PASE +" > "+ DEFAULT_PASE +" ORDER BY "+ID_PASE+" DESC LIMIT 1 ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()){
            retVal = new Pase(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
        }else{
            retVal = null;
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public Falta getLatestFalta(){
        Falta retVal;
        String Query = "SELECT "+ID_FALTA+","+NOMBRE_FALTA+", "+ABREV_FALTA+" FROM "+TABLE_FALTA+" WHERE "+ID_FALTA +" > "+ DEFAULT_FALTA +" ORDER BY "+ID_FALTA+" DESC LIMIT 1 ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()){
            retVal = new Falta(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
        }else{
            retVal = null;
        }
        cursor.close();
        db.close();
        return retVal;
    }
    //ORDER BY idAccion DESC LIMIT 1
    public Club getLatestClub(){
        String Query = "SELECT "+ID_CLUB+", "+NOMBRE_CLUB+" FROM " + TABLE_CLUB+" ORDER BY "+ID_CLUB+" DESC LIMIT 1";
        Club retVal;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()){
            retVal = new Club(cursor.getInt(0),cursor.getString(1));
        }else{
            retVal = null;
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public Equipo getLatestEquipo(int CLUB_PADRE){
        String Query = "SELECT "+ID_EQUIPO+", "+NOMBRE_EQUIPO+" FROM " + TABLE_EQUIPO+" WHERE "+ID_CLUB+"="+CLUB_PADRE+ " ORDER BY "+ID_EQUIPO+" DESC LIMIT 1";
        Equipo retVal;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()){
            retVal = new Equipo(cursor.getInt(0),cursor.getString(1),CLUB_PADRE);
        }else{
            retVal = null;
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public Esquema getLatestEsquema(){
        Esquema retVal;
        String Query = "SELECT "+ID_ESQUEMA+","+NUM_OFENSIVA+","+NUM_MEDIOCAMPO+","+NUM_DEFENSIVA+" FROM "+TABLE_ESQUEMA +" ORDER BY "+ID_ESQUEMA+" DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()){
            retVal = (new Esquema(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3)));
        }else{
            retVal = null;
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public Jugador getLatestJugador(int EQUIPO){
        Jugador retVal;
        String Query = "SELECT "+ID_JUGADOR+","+ID_POSICION+", "+NOMBRE_JUGADOR+","+NUMERO_JUGADOR+","+FOTO_JUGADOR+" FROM " + TABLE_JUGADOR+" WHERE "+ID_EQUIPO+"="+EQUIPO +" ORDER BY "+ID_JUGADOR+" DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query,null);
        if (cursor.moveToFirst()){
            retVal = (new Jugador(cursor.getInt(0),EQUIPO,getPosicion(cursor.getInt(1)),cursor.getString(2),cursor.getInt(3),cursor.getBlob(4)));
        }else{
            retVal = null;
        }
        cursor.close();
        db.close();
        return retVal;
    }

    public List<TipoPase> getTipoPase(){
        List<TipoPase> retVal = new ArrayList<>();
        String Query = "SELECT "+ID_TIPOPASE+","+NOMBRE_TIPOPASE+" FROM " + TABLE_TIPOPASE+"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query,null);
        if (cursor.moveToFirst()){
            retVal.add(new TipoPase(cursor.getInt(0),cursor.getString(1)));
            while (cursor.moveToNext()){
                retVal.add(new TipoPase(cursor.getInt(0),cursor.getString(1)));
            }
        }
        return retVal;
    }

    public List<Accion> getAllAccion(){
        List<Accion> retVal = new ArrayList<>();
        retVal.addAll(getDefaultAccions());
        retVal.addAll(getUserAccions());
        return retVal;
    }

    public List<Accion> getAllAccionPortero(){
        List<Accion> retVal = new ArrayList<>();
        retVal.addAll(getDefaultAccionPortero());
        retVal.addAll(getUserAccionsPortero());
        return retVal;
    }
}