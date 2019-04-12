package com.glind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by Luis on 06/07/2016.
 */
public class DataBaseManager {
    // Declaracion de todos los atributos necesario para las tablas, columnas y sentencias de sql para la creacion de la base de datos.
    public static final String TABLE_ESPANIOL = "spanish";
    public static final String TABLE_GUNA = "guna";
    public static final String TABLE_NGABERE = "ngabere";
    public static final String TABLE_EMBERA = "embera";
    public static final String TABLE_SECCION = "section";
    public static final String GUNA = "text_guna";
    public static final String NGABERE = "text_ngabere";
    public static final String EMBERA = "text_embera";
    public static final String TABLE_CATEGORIA = "section_ID";
    public static final String ID = "phrase_ID";
    public static final String Espaniol = "text_spanish";
    public static final String Audio = "audio_spanish";
    public static final String Audioguna = "audio_guna";
    public static final String Audiongabere = "audio_ngabere";
    public static final String Audioembera = "audio_embera";
    public static final String Imagen = "picture";
    public static final String Categoria = "section_ID";
    public static final String nombrecategoria = "section_name";
    public static final String USO = "most_used";
    public static final String CREATE_ESPANIOL = "create table " + TABLE_ESPANIOL +
            " (" + Categoria + " integer," + ID + " integer primary key autoincrement," + Espaniol + " text not null,"
            + Audio + " text not null, " + Imagen + " text not null," + USO +  " integer);";
    public static final String CREATE_GUNA = "create table " + TABLE_GUNA +
            " (" + Categoria + " integer," + ID + " integer primary key autoincrement," + GUNA + " text not null,"
            + Audioguna + " text not null);";
    public static final String CREATE_NGABERE = "create table " + TABLE_NGABERE +
            " (" + Categoria + " integer," + ID + " integer primary key autoincrement," + NGABERE + " text not null,"
            + Audiongabere + " text not null);";
    public static final String CREATE_EMBERA = "create table " + TABLE_EMBERA +
            " (" + Categoria + " integer," + ID + " integer primary key autoincrement," + EMBERA + " text not null,"
            + Audioembera + " text not null);";
    public static final String CREATE_CATEGORIA = "create table " + TABLE_CATEGORIA +
            " (" + Categoria + " integer primary key autoincrement," + nombrecategoria + " text not null);";
    private  DbHelper helper;
    private  SQLiteDatabase db;

    // Creacion del objeto encargado de la base de datos.
    public DataBaseManager(Context context) {

        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }
   // Metodo para insertar las diferentes secciones del contenido.
    // se hace uso de un query para verificar que la tabla de la base de datos este vacia.
    //si lo está se inserta los datos.
    public void insertar (){
        String z= "Select Count(*) from "+TABLE_CATEGORIA;
        Cursor f  = db.rawQuery(z, null);
        f.moveToFirst();
        int i = f.getInt(0);
        if (i == 0) {
            db.execSQL("INSERT INTO " + TABLE_CATEGORIA + " (" + nombrecategoria + ") VALUES ('Saludos - Recibimientos - Relaciones') ");
            db.execSQL("INSERT INTO " + TABLE_CATEGORIA + " (" + nombrecategoria + ") VALUES ('Peticiones para exámenes') ");
            db.execSQL("INSERT INTO " + TABLE_CATEGORIA + " (" + nombrecategoria + ") VALUES ('Procedimientos') ");
            db.execSQL("INSERT INTO " + TABLE_CATEGORIA + " (" + nombrecategoria + ") VALUES ('Historia clínica') ");
            db.execSQL("INSERT INTO " + TABLE_CATEGORIA + " (" + nombrecategoria + ") VALUES ('Medicinas') ");
            db.execSQL("INSERT INTO " + TABLE_CATEGORIA + " (" + nombrecategoria + ") VALUES ('Sobre medicina tradicional') ");
            db.execSQL("INSERT INTO " + TABLE_CATEGORIA + " (" + nombrecategoria + ") VALUES ('Recomendaciones generales') ");
        }
    }

    // Metodo para generar una lista con las seccion o capitulos disponibles de la app.
    public Cursor Query (){
        String [] Seccion = new String[]{ID, Categoria};
        return db.rawQuery("select rowid _id,* from "+ TABLE_CATEGORIA, null);
    }
    // Metodo para generar la lista de frases de la seccion o capitulo elegido por el usuario.
    public Cursor Queryfrases ( String a){
        String tipo = a;
        int numero = Integer.parseInt(tipo);
        return db.rawQuery("select rowid _id,"+Espaniol+" from "+ TABLE_ESPANIOL +" where "+Categoria+" = " +numero, null);
    }

    //Metodo para generar el contenido perdido por el usuario, es decir la frase selecionada.
    //Se recibe un parametro para verificar en qué idioma se va a mostrar la frase selecionada.
    public Cursor Queryfinal (String a, int b){
        String nombre = a;
        int numero;
        String nume;
        String f = "";
        int idi = b;
        if (idi == 2){
            f = TABLE_GUNA;
        }
        else if (idi == 1){
            f = TABLE_NGABERE;
        }
        else if (idi == 3){
            f= TABLE_EMBERA;

        }
        Cursor num ;
       // int ho = db.rawQuery("select * from "+ TABLE_ESPANIOL +", "+f +" where "+ ID +" ="+ f+"", null);
        num = db.rawQuery("select "+ID+" from "+ TABLE_ESPANIOL +" where spanish."+ Espaniol +" = '"+ nombre+"';", null);
        num.moveToFirst();
        nume = num.getString(num.getColumnIndex(ID));
        numero = Integer.parseInt(nume);
        return db.rawQuery("select * from "+ TABLE_ESPANIOL +", "+f +" where spanish."+ ID +" ="+ numero+" and "+f+"."+ID+" = "+ numero+";", null);
    }

// Metodo para actualizar el contador de usos de las frases -most_used- *No Implementado*.
    public void updatequery ( String m){
        int auxa, auxb = 1,auxc;
        Cursor g;
        String fr,op= "";
        fr = m;
        int zx = Integer.parseInt(fr);
        g = db.rawQuery("select * from "+ TABLE_ESPANIOL +" where "+ ID +" = "+ zx+"", null);
        if (g!= null && g.moveToFirst()) {

            while (g.isAfterLast() == false) {
                op = g.getString(g.getColumnIndex("most_used"));
               // Toast.makeText(get
            }
            }
        auxa= Integer.parseInt(op);
        auxc = auxa + auxb;
        db.rawQuery("UPDATE "+ TABLE_ESPANIOL+" SET " +USO+" = "+auxc+ " WHERE "+ID+" = "+ fr, null);

    }
// Metodo que inserta los datos a la base de datos, revisa que la misma este vacia, si lo está realiza la inserción.
public void act (String a){
    db.execSQL(a);
}
    public void insertardatos (){
        String z= "Select Count(*) from "+TABLE_ESPANIOL;
        Cursor f  = db.rawQuery(z, null);
        f.moveToFirst();
        int i = f.getInt(0);
        if (i == 0) {
            //Capitulo 1
            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'Buenos dias',  '/raw/audioesp1_1', '/drawable/salud', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'Anna, be nuedi',  '/raw/audiogun1_1') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Köbö koin dego',  '/raw/audiongo1_1') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Mera',  '/raw/audioemb1_1') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'Buenas tardes',  '/raw/audioesp1_2', '/drawable/salud', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'Anna, be nuedi',  '/raw/audiogun1_2') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Köbö koin dere',  '/raw/audiongo1_2') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Euaris kebara',  '/raw/audioemb1_2') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'Pase adelante, siéntese',  '/raw/audioesp1_3', '/drawable/imagen1_3', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'Daggwele, be sige',  '/raw/audiogun1_3') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Nöin gwo, toge tägrä',  '/raw/audiongo1_3') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Zeta nama no cuare',  '/raw/audioemb1_3') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, '¿Que hay de nuevo?',  '/raw/audioesp1_4', '/drawable/imagen1_4', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, '¿Igi neg gunai?',  '/raw/audiogun1_4') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Dre kugwe?',  '/raw/audiongo1_4') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Kareta para idi',  '/raw/audioemb1_4') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, '¿Cómo está?',  '/raw/audioesp1_5', '/drawable/imagen1_5', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, '¿Be nuedi?',  '/raw/audiogun1_5') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Mä nibi ño? / Mä toa ño?',  '/raw/audiongo1_5') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Za wa bura buma',  '/raw/audioemb1_5') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, '¿Cómo se llama?',  '/raw/audioesp1_6', '/drawable/imagen1_6', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, '¿Igi be nuga?',  '/raw/audiogun1_6') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Mä kä ño?',  '/raw/audiongo1_6') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Bu kasa trin jara bada',  '/raw/audioemb1_6') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, '¿Cuantos años tiene?',  '/raw/audioesp1_7', '/drawable/imagen1_7', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, '¿Birga bigwa be nigga?',  '/raw/audiogun1_7') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Kä kwäbe mäya?',  '/raw/audiongo1_7') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Bu Ksa Trïwa tama Pu cuanto año erobuma',  '/raw/audioemb1_7') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, '¿Habla bien el español?',  '/raw/audioesp1_8', '/drawable/imagen1_8', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, '¿waggayaba be nued summaggge?',  '/raw/audiogun1_8') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Mä blitde kwin suliarea?',  '/raw/audiongo1_8') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Pu cuanto año erobuma',  '/raw/audioemb1_8') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'Por favor',  '/raw/audioesp1_9', '/drawable/imagen1_9', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'Doggus nued',  '/raw/audiogun1_9') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Ngöbö ngwäregri (delante de Dios)',  '/raw/audiongo1_9') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Kira chupuriata',  '/raw/audioemb1_9') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'Gracias',  '/raw/audioesp1_10', '/drawable/imagen1_10', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'Doggus nued',  '/raw/audiogun1_10') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Ngöbögwe koin bien mäe',  '/raw/audiongo1_10') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Biakirua',  '/raw/audioemb1_10') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'Estoy aqui contigo',  '/raw/audioesp1_11', '/drawable/imagen1_11', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'Wegi an bebo gudi',  '/raw/audiogun1_11') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Ti mägwe tä netde. Ti tä netde mäben',  '/raw/audiongo1_11') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Nama bume bua',  '/raw/audioemb1_11') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'No te pienses. No te preocupes ',  '/raw/audioesp1_12', '/drawable/imagen1_12', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'Mel buggib binsae',  '/raw/audiogun1_12') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Mä ñan töbigai. Ma ñan tebai. ',  '/raw/audiongo1_12') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Kincharata mancua',  '/raw/audioemb1_12') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'Te voy ayudar / Apoyar ',  '/raw/audioesp1_13', '/drawable/imagen1_13', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'An be bendaggoe',  '/raw/audiogun1_13') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Ti bige ja di bien mäe',  '/raw/audiongo1_13') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Mua Pura carepaita',  '/raw/audioemb1_13') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, '¿Con quién vienes?',  '/raw/audioesp1_14', '/drawable/imagen1_14', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, '¿Doabo be noniggi?',  '/raw/audiogun1_14') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Mä gi nireben?',  '/raw/audiongo1_14') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Kaime seburu',  '/raw/audioemb1_14') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'Hasta Luego ',  '/raw/audioesp1_15', '/drawable/imagen1_15', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'Gusarmalo',  '/raw/audiogun1_15') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Ja toaida mare',  '/raw/audiongo1_15') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Mura wa',  '/raw/audioemb1_15') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'Hasta mañana',  '/raw/audioesp1_16', '/drawable/imagen1_16', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'Banemalo',  '/raw/audiogun1_16') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Ja toaida jetdebe',  '/raw/audiongo1_16') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Nu seya',  '/raw/audioemb1_16') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'Cuídese Bien',  '/raw/audioesp1_17', '/drawable/imagen1_17', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'Nued be na san aggwo',  '/raw/audiogun1_17') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Ja ngübüani koin. ngibiani koin ',  '/raw/audiongo1_17') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Kiná kuita bata',  '/raw/audioemb1_17') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'Adiós',  '/raw/audioesp1_18', '/drawable/imagen1_18', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'Degimalo',  '/raw/audiogun1_18') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Ja toaida ',  '/raw/audiongo1_18') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Atanú',  '/raw/audioemb1_18') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'Que le vaya bien.',  '/raw/audioesp1_19', '/drawable/imagen1_19', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'Nueganbi nao',  '/raw/audiogun1_19') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Mä riga koin.',  '/raw/audiongo1_19') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Bia wada',  '/raw/audioemb1_19') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'Dios va contigo. Vaya con Dios.',  '/raw/audioesp1_20', '/drawable/imagen1_20', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'Bab beba nadabbi Bab bo be naoe',  '/raw/audiogun1_20') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Ngöbö riga mäben. Mä riga Ngöböben. ',  '/raw/audiongo1_20') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Ankoreme wata',  '/raw/audioemb1_20') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 1, 'Dios se queda contigo ',  '/raw/audioesp1_21', '/drawable/imagen1_21', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 1, 'Bab beba gudi',  '/raw/audiogun1_21') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 1, 'Ngöbö reba mäben',  '/raw/audiongo1_21') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 1, 'Ankorera burme bua',  '/raw/audioemb1_21') ");
// Capitulo 2
            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Vamos a examinarte ',  '/raw/audioesp2_1', '/drawable/imagen2_1', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Anmar be san daggolo',  '/raw/audiogun212') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Ani mä trä täen',  '/raw/audiongo2_1') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Bu cacuara',  '/raw/audioemb2_1') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Voy a examinarte',  '/raw/audioesp2_2', '/drawable/imagen2_2', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'An be san daggolo',  '/raw/audiogun2_2') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Ti noin mä trä täen',  '/raw/audiongo2_2') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Bu ka kuara a kidia',  '/raw/audioemb2_2') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Voy a sentir tus pulmones y corazón ',  '/raw/audioesp2_3', '/drawable/imagen2_3', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Be bunnoed an iddogoe Be gwage an iddogoe',  '/raw/audiogun2_3') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Ti mä bügron botdä mä brugowó ngö noen',  '/raw/audiongo2_3') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Bu pulmoneta akudia y puzota',  '/raw/audioemb2_3') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Se quita la camisa y los zapatos (la nagua)',  '/raw/audioesp2_4', '/drawable/imagen2_4', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Mor be unge, sabbad be unge',  '/raw/audiogun2_4') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Duänkwini angwane sabatda tigede jabotdä (duängutdu)',  '/raw/audiongo2_4') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Bu camisara eraita y pu zapatota o pu pantalota o pu wata',  '/raw/audioemb2_4') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Abra la boca, diga aaaa',  '/raw/audioesp2_5', '/drawable/imagen2_5', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Gaya egae, aaaabe be soge',  '/raw/audiogun2_5') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Kada ngiengo, aaaa niere',  '/raw/audiongo2_5') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Itera ñadua jaradua a',  '/raw/audioemb2_5') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Saque la lengua',  '/raw/audioesp2_6', '/drawable/imagen2_6', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Gwabin onoe',  '/raw/audiogun2_6') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Tϋdrä ngögö kwin. Tidrä ngögö kwin.',  '/raw/audiongo2_6') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Akidia bu kirame',  '/raw/audioemb2_6') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Acuéstese en la cama',  '/raw/audioesp2_7', '/drawable/imagen2_7', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Gamagi be mege',  '/raw/audiogun2_7') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Neme jänbitdi tibien',  '/raw/audiongo2_7') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Kirare tabeta',  '/raw/audioemb2_7') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Levante el brazo (el brazo)',  '/raw/audioesp2_8', '/drawable/imagen2_8', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Saggwa onaggwe, mali onaggwe',  '/raw/audiogun2_8') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Küde gaingo kwin (ngúre gaingo kwin)',  '/raw/audiongo2_8') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Bu ozozocara jiratua',  '/raw/audioemb2_8') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Baje la pierna (la pierna)',  '/raw/audioesp2_9', '/drawable/imagen2_9', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Mali odege, saggwa odege',  '/raw/audiogun2_9') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Ngüre mige kwin timon (küde mige kwin timon)',  '/raw/audiongo2_9') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Bu bacara bajatua',  '/raw/audioemb2_9') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Suba a la camilla',  '/raw/audioesp2_10', '/drawable/imagen2_10', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Gamiyagi naggwe',  '/raw/audiogun2_10') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Neme jänbitdi tibien',  '/raw/audiongo2_10') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Inta jiratua camillara',  '/raw/audioemb2_10') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Aspire bastante (expire) ',  '/raw/audioesp2_11', '/drawable/imagen2_11', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Binna sur bunnoe (burwa onie)',  '/raw/audiogun2_11') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Büre jäge kri (büre täge)',  '/raw/audiongo2_11') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Aire jiratua üntá',  '/raw/audioemb2_11') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Voy a ver sus oídos (su nariz)',  '/raw/audioesp2_12', '/drawable/imagen2_12', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Be uaya an daggolo (be asu)',  '/raw/audiogun2_12') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Ti mä olo toende (ti mä ison toende)',  '/raw/audiongo2_12') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Mua pura bu kurura',  '/raw/audioemb2_12') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Levántese',  '/raw/audioesp2_13', '/drawable/imagen2_13', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Gwisggue',  '/raw/audiogun2_13') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Näen krö',  '/raw/audiongo2_13') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Pira trúta',  '/raw/audioemb2_13') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Respire despacio ',  '/raw/audioesp2_14', '/drawable/imagen2_14', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Binna bunnnoge',  '/raw/audiogun2_14') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Büre jäge botdäre',  '/raw/audiongo2_14') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Pianka inñanbadua',  '/raw/audioemb2_14') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Baje de la camilla',  '/raw/audioesp2_15', '/drawable/imagen2_15', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Gamiyagi aidege',  '/raw/audiogun2_15') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Näen jänbitdi timon',  '/raw/audiongo2_15') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Eda bajatua camillara',  '/raw/audioemb2_15') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Levante la manga',  '/raw/audioesp2_16', '/drawable/imagen2_16', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Mor saggwa ebaye',  '/raw/audiogun2_16') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Duänküde kaingä',  '/raw/audiongo2_16') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Inta jiratua pu manga camisara',  '/raw/audioemb2_16') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Voltéese',  '/raw/audioesp2_17', '/drawable/imagen2_17', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Bignibe sae',  '/raw/audiogun2_17') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Ja kwitdegä',  '/raw/audiongo2_17') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Voltiata pu cuacuara',  '/raw/audioemb2_17') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Agarre mi mano',  '/raw/audioesp2_18', '/drawable/imagen2_18', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'An argan gae',  '/raw/audiogun2_18') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Ti küse ngögö tie',  '/raw/audiongo2_18') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Gitatia mu jigua',  '/raw/audioemb2_18') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 2, 'Baje la cabeza',  '/raw/audioesp2_19', '/drawable/imagen2_19', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 2, 'Non be duggin sae',  '/raw/audiogun2_19') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 2, 'Dogwä mige timongwäre',  '/raw/audiongo2_19') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 2, 'Barruata bu borora',  '/raw/audioemb2_19') ");
//Capitulo 3
            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'Vaya con la enfermera',  '/raw/audioesp3_1', '/drawable/imagen3_1', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'Enfermerase be nae',  '/raw/audiogun3_1') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Noen meri krägä biangaben (enfermeraben)',  '/raw/audiongo3_1') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Wuata enfermera me acuwide',  '/raw/audioemb3_1') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'Voy a sacarle sangre del brazo',  '/raw/audioesp3_2', '/drawable/imagen3_2', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'An be saggwagi abe suo',  '/raw/audiogun3_2') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Ti bige küde därie dende',  '/raw/audiongo3_2') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Wata sacadia',  '/raw/audioemb3_2') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'Deme el brazo ',  '/raw/audioesp3_3', '/drawable/imagen3_3', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'Be saggwa sedage',  '/raw/audiogun3_3') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Küde ngwen tie',  '/raw/audiongo3_3') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Diata pujigua',  '/raw/audioemb3_3') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'Apriete el puño',  '/raw/audioesp3_4', '/drawable/imagen3_4', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'Be argan guur sae',  '/raw/audiogun3_4') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Küse kwain',  '/raw/audiongo3_4') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Fuerza baratuatua',  '/raw/audioemb3_4') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'No se mueva',  '/raw/audioesp3_5', '/drawable/imagen3_5', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'Boo be sige',  '/raw/audiogun3_5') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Ñan betade',  '/raw/audiongo3_5') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Moveratua',  '/raw/audioemb3_5') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'Doble el brazo',  '/raw/audioesp3_6', '/drawable/imagen3_6', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'Be saggwa edwe',  '/raw/audiogun3_6') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Küde dötöga ',  '/raw/audiongo3_6') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Jiratua dobata unta',  '/raw/audioemb3_6') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'No toque la herida',  '/raw/audioesp3_7', '/drawable/imagen3_7', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'Mer be san bonigwaled ebuge',  '/raw/audiogun3_7') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Ñan Träen nua',  '/raw/audiongo3_7') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Tocoa natua erirara',  '/raw/audioemb3_7') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'Vamos a lavar la herida',  '/raw/audioesp3_8', '/drawable/imagen3_8', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'Be san boniggwaled anmar enuggoe',  '/raw/audiogun3_8') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Ani träen bätetde',  '/raw/audiongo3_8') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Sigüta',  '/raw/audioemb3_8') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'Hay que coser la herida ',  '/raw/audioesp3_9', '/drawable/imagen3_9', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'Be san boniggwaled maggoe',  '/raw/audiogun3_9') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Träen nebe gudigadre',  '/raw/audiongo3_9') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Cocedía Erírara',  '/raw/audioemb3_9') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'Le voy a poner anestesia',  '/raw/audioesp2_10', '/drawable/imagen3_10', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'An bega iggo yoe gabedga',  '/raw/audiogun3_10') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Anestesia miga bige mäe',  '/raw/audiongo3_10') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Budía enesteciata',  '/raw/audioemb3_10') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'Corte de puntos en ocho días',  '/raw/audioesp3_11', '/drawable/imagen3_11', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'Ib baabagse maglesad e signoniggoe',  '/raw/audiogun3_11') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Kö tigadregä kwärera erere ',  '/raw/audiongo3_11') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'ochodiade túdiada',  '/raw/audioemb3_11') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'Si está roja, venga al Centro',  '/raw/audioesp3_12', '/drawable/imagen3_12', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'Be nagbigwed gigindagleged, yoi be dagar',  '/raw/audiogun3_12') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Rabai tain, ne ngwane mä jatda krägä juetde ',  '/raw/audiongo3_12') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Pua unusira pueridata puriabu puraseita centrodea',  '/raw/audioemb3_12') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'Si tiene pus, venga al Centro ',  '/raw/audioesp3_13', '/drawable/imagen3_13', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'Duled nigga, wes dagbalo',  '/raw/audiogun3_13') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Rabai ngumüene, nengwane mä jatda krägä juetde',  '/raw/audiongo3_13') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Pua unisira pusta erububuru seita centrodea',  '/raw/audioemb3_13') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'Hay que ponerle una inyección',  '/raw/audioesp3_14', '/drawable/imagen3_14', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'Iggo yoer gebe',  '/raw/audiogun3_14') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Agu migadre mä kwatdatde',  '/raw/audiongo3_14') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'inyeccionta budía',  '/raw/audioemb3_14') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, '¿Qué lo mordió?',  '/raw/audioesp3_15', '/drawable/imagen3_15', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, '¿Ibu be gussa?',  '/raw/audiogun3_15') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Dre kägwe kwitdi?',  '/raw/audiongo3_15') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Caneba casi',  '/raw/audioemb3_15') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, '¿Qué lo picó?',  '/raw/audioesp3_16', '/drawable/imagen3_16', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, '¿Ibu be sigga gusa?',  '/raw/audiogun3_16') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Dre kägwe täritde? ',  '/raw/audiongo3_16') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Kaneba casita o plantaba o animaraba',  '/raw/audioemb3_16') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, '¿Le duele mucho?',  '/raw/audioesp3_17', '/drawable/imagen3_17', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, '¿Be yaissur nummagge?',  '/raw/audiogun3_17') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Nibi tare digaro mäya?',  '/raw/audiongo3_17') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Puanuma',  '/raw/audioemb3_17') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, '¿Qué siente?',  '/raw/audioesp3_18', '/drawable/imagen3_18', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, '¿Igi be iddoge?',  '/raw/audiogun3_18') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Räen ño mäya?',  '/raw/audiongo3_18') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Puasawa sentibu',  '/raw/audioemb3_18') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, '¿Con qué se quemó?',  '/raw/audioesp3_19', '/drawable/imagen3_19', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, '¿Ibugi be gunmagsa?',  '/raw/audiogun3_19') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Drebitdi mä nugwi?',  '/raw/audiongo3_19') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Kaneba püra pasi',  '/raw/audioemb3_19') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 3, 'Traiga un poco de orina ',  '/raw/audioesp3_20', '/drawable/imagen3_20', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 3, 'Be winni isse be sedago',  '/raw/audiogun3_20') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 3, 'Intranin jän jända chi',  '/raw/audiongo3_20') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 3, 'Eneta Chiwa',  '/raw/audioemb3_20') ");
//Capitulo 4
            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Dónde vives?',  '/raw/audioesp4_1', '/drawable/imagen4_1', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Bia be mai',  '/raw/audiogun4_1') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä tä nüne medende?',  '/raw/audiongo4_1') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Zama ba baris',  '/raw/audioemb4_1') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Cuándo naciste?',  '/raw/audioesp4_', '/drawable/imagen4_2', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Sana be gwalulesa?',  '/raw/audiogun4_2') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä därebare ñongwane?',  '/raw/audiongo4_2') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Zabeda tocida',  '/raw/audioemb4_2') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Cuántos años tienes?',  '/raw/audioesp4_3', '/drawable/imagen4_3', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Birga bigwa be nigga?',  '/raw/audiogun4_3') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Kä kräbe mäbitdi?',  '/raw/audiongo4_3') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua cuanto año erobuta',  '/raw/audioemb4_3') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Sabes leer? (Sí / No)',  '/raw/audioesp4_4', '/drawable/imagen4_4', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be nabir sabga absoged wisi? Eye/SuIi',  '/raw/audiogun4_4') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ñäge gare täräbotdä mäya? (Jän / Ñan)',  '/raw/audiongo4_4') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bua levarika? Mai/Leka o Mua Atobua',  '/raw/audioemb4_4') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Sabes escribir?',  '/raw/audioesp4_5', '/drawable/imagen4_5', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be nabir sabga narmagged wisi? Eye/Suli',  '/raw/audiogun4_5') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Tärätdiga gare mäya?',  '/raw/audiongo4_5') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua escribi barikara',  '/raw/audioemb4_5') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Cuántos viven en la casa?',  '/raw/audioesp4_6', '/drawable/imagen4_6', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be neggi dule warbigwa gudii?',  '/raw/audiogun4_6') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ni tä nibe nüne mä gwiretde?',  '/raw/audiongo4_6') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bu dikida junmaza fanabata',  '/raw/audioemb4_6') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Hasta qué grado llegaste  en la escuela?',  '/raw/audioesp4_7', '/drawable/imagen4_7', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Escuelagi nomba bigwa be imagsa?',  '/raw/audiogun4_7') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä jänänbare kä kräbe dirijuetde?',  '/raw/audiongo4_7') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua kanë gradode jüesía',  '/raw/audioemb4_7') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿De qué religión eres?',  '/raw/audioesp4_8', '/drawable/imagen4_8', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Bab igar iddoged nega, bia be nanae?',  '/raw/audiogun4_8') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ngöbö kugwe meden mägwe?',  '/raw/audiongo4_8') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bua kanë religione buta',  '/raw/audioemb4_8') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Estás soltero/a? ¿Casado/a?',  '/raw/audioesp4_9', '/drawable/imagen4_9', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be ome nigga? ¿Be massered nigga?',  '/raw/audiogun4_9') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä kaibea? Mä gurea?',  '/raw/audiongo4_9') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bura deubuka? Kima bara bukara?',  '/raw/audioemb4_9') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Eres fumador?',  '/raw/audioesp4_10', '/drawable/imagen4_10', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be sigali ue?',  '/raw/audiogun4_10') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä sö doagaya?',  '/raw/audiongo4_10') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bura cigarillo dobakika?',  '/raw/audioemb4_10') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿¿Eres tomador? (chicha fuerte)',  '/raw/audioesp4_11', '/drawable/imagen4_11', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be sisa gobe?',  '/raw/audiogun4_11') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä dö kwaga ñagaya?',  '/raw/audiongo4_11') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bua itua tobarika?',  '/raw/audioemb4_11') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Has estado hospitalizada?',  '/raw/audioesp4_12', '/drawable/imagen4_12', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Osbidalgi be megisa?',  '/raw/audiogun4_12') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä tärä nebe opitalitdea?',  '/raw/audiongo4_12') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bura hospitale babarikara?',  '/raw/audioemb4_12') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Te han operado?',  '/raw/audioesp4_13', '/drawable/imagen4_13', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be saban marisa?',  '/raw/audiogun4_13') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä operabarea?',  '/raw/audiongo4_13') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pura operabatakara?',  '/raw/audioemb4_13') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Te han puesto sangre?',  '/raw/audioesp4_14', '/drawable/imagen4_14', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Abe bega uglesa?',  '/raw/audiogun4_14') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Därie miga tärä mä kwatdatdea?',  '/raw/audiongo4_14') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bura uara buataka?',  '/raw/audioemb4_14') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Eres alérgica a alguna medicina?',  '/raw/audioesp4_15', '/drawable/imagen4_15', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Ibu ina begad suli?',  '/raw/audiogun4_15') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Krägä tärä matde tare mäbotdäya?',  '/raw/audiongo4_15') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua alergico mediacentode',  '/raw/audioemb4_15') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Alguien de la familia es alérgico?',  '/raw/audioesp4_16', '/drawable/imagen4_16', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be gwenadgan ina egad suli nigga?',  '/raw/audiogun4_16') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ni mä mrägärebotdä krägä tärä matde tarea?',  '/raw/audiongo4_16') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pu familiade caita alergico',  '/raw/audioemb4_16') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Has perdido peso?',  '/raw/audioesp4_17', '/drawable/imagen4_17', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be aidiggued aidesa?',  '/raw/audiogun4_17') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä dobo nögrötdea?',  '/raw/audiongo4_17') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bu pesora eda bajauarika?',  '/raw/audioemb4_17') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Te cuidas para no tener niños?',  '/raw/audioesp4_18', '/drawable/imagen4_18', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be san nued aggwedi, mer goe nigguega',  '/raw/audiogun4_18') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä tärä ja ngübüare ñan chi ngebeadre garea?',  '/raw/audiongo4_18') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pura cuidabuka uarra neabama?',  '/raw/audioemb4_18') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Te ha dado fiebre?',  '/raw/audioesp4_19', '/drawable/imagen4_19', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be uelesa?',  '/raw/audiogun4_19') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Drangwa tärä nüge mäbotdäya?',  '/raw/audiongo4_19') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pura kuabukana',  '/raw/audioemb4_19') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Desde cuándo?',  '/raw/audioesp4_20', '/drawable/imagen4_20', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿ibbigwa gusa?',  '/raw/audiogun4_20') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ñongwarera?',  '/raw/audiongo4_20') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Kane ebarite kubasi?',  '/raw/audioemb4_20') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Dolor de cabeza?',  '/raw/audioesp4_21', '/drawable/imagen4_21', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be sagla nunmagge?',  '/raw/audiogun4_21') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä dogwä nibi tarea?',  '/raw/audiongo4_21') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua poro fua numukara',  '/raw/audioemb4_21') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Tos?',  '/raw/audioesp4_22', '/drawable/imagen4_22', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be oo nigga?',  '/raw/audiogun4_22') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Müra jurorea?',  '/raw/audiongo4_22') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua osonumukara',  '/raw/audioemb4_22') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Moco por la nariz?',  '/raw/audioesp4_23', '/drawable/imagen4_23', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Asa yabba oo nigga?',  '/raw/audiogun4_23') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Müra ngätdäre isondea?',  '/raw/audiongo4_23') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Kempute erosonumuka?',  '/raw/audioemb4_23') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Dónde siente el dolor?',  '/raw/audioesp4_24', '/drawable/imagen4_24', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Bia be nunmagge?',  '/raw/audiogun4_24') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä giere nibi brenya?',  '/raw/audiongo4_24') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Sama fuanumucana',  '/raw/audioemb4_24') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Dolor de huesos?',  '/raw/audioesp4_25', '/drawable/imagen4_25', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be gar be nunmagge?',  '/raw/audiogun4_25') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Kro tarea? (brenya?)',  '/raw/audiongo4_25') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pu Buru puanuká?',  '/raw/audioemb4_25') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Dolor de garganta?',  '/raw/audioesp4_26', '/drawable/imagen4_26', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be gammu be nunmagge?',  '/raw/audiogun4_26') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Roro tarea? (brenya?)',  '/raw/audiongo4_26') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Ichuru puanuma?',  '/raw/audioemb4_26') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Dolor de barriga? (¿cintura? ¿brazo? ¿pierna? ¿rodilla? ¿ojos? ¿estómago? ¿pies?)',  '/raw/audioesp4_27', '/drawable/imagen4_27', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be saban be nunmagge?(¿saggwa? ¿mali? ¿yoggor? ¿naga?)',  '/raw/audiogun4_27') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Büle tarea? (mregaraya? küdea? ngürea? ngotdogwäya? ogwätdea? ngrieya? ngotoya?',  '/raw/audiongo4_27') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bi fua? Rürru fua? Jigüa fua numä? Jiru fuanuma? Coracora fuanuma? Tauta fuanuma?',  '/raw/audioemb4_27') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Tiene diarrea?',  '/raw/audioesp4_28', '/drawable/imagen4_28', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be sadibgunai?',  '/raw/audiogun4_28') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Roro tarea? (brenya?)',  '/raw/audiongo4_28') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Mä nibi grierebotdäya? Mä nibi griereya?',  '/raw/audioemb4_28') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Diarrea con sangre?',  '/raw/audioesp4_29', '/drawable/imagen4_29', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be abe abe dise be nanae?',  '/raw/audiogun4_29') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Grie däribenya? Grie därirea?',  '/raw/audiongo4_29') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Amine numu cara wuapara',  '/raw/audioemb4_29') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Hace cuántos días?',  '/raw/audioesp4_30', '/drawable/imagen4_30', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Ibbigwa gusa?',  '/raw/audiogun4_30') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Köböbera?',  '/raw/audiongo4_30') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Cuantos dias buta angabu?',  '/raw/audioemb4_30') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Tiene vómito?',  '/raw/audioesp4_31', '/drawable/imagen4_31', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be age?',  '/raw/audiogun4_31') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä nibi yenbotdäya?',  '/raw/audiongo4_31') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Webua?',  '/raw/audioemb4_31') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Está vomitando?',  '/raw/audioesp4_32', '/drawable/imagen4_32', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be saggar saggar be age?',  '/raw/audiogun4_32') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Tä yaerea?',  '/raw/audiongo4_32') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pura we bucara?',  '/raw/audioemb4_32') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿De qué color es la diarrea? ¿blanca, amarilla, negra, roja, chocolate?',  '/raw/audioesp4_33', '/drawable/imagen4_33', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Igi daglege be sadued? ¿Sibbugwad, gorogwad, sissid,ginnid, guddurgwagwa?',  '/raw/audiogun4_33') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä bren ñongwane? (bomo kratdi näre, sö kratdi näre, kä kratdi näre…)',  '/raw/audiongo4_33') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Kare colorde angabua? Torrua, Cuara, paima, purea, engorokiaca?',  '/raw/audioemb4_33') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿De qué color es el vómito?',  '/raw/audioesp4_34', '/drawable/imagen4_34', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be aged igi daglege?',  '/raw/audiogun4_34') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Yaniri abogon bä ño? Ya bä ño?',  '/raw/audiongo4_34') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Saua we numucara?',  '/raw/audioemb4_34') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Tose con sangre?',  '/raw/audioesp4_35', '/drawable/imagen4_35', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Abebi be dollomagge?',  '/raw/audiogun4_35') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Müra jurore däribenya?',  '/raw/audiongo4_35') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Wuapara we numucara?',  '/raw/audioemb4_35') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Se te han hinchado los pies?',  '/raw/audioesp4_36', '/drawable/imagen4_36', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be mali bega engusa?',  '/raw/audiogun4_36') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä ngoto nuen jögräya? Mä ngoto tärä nuenya?',  '/raw/audiongo4_36') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pu jirurá ogoro barikara?',  '/raw/audioemb4_36') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Te ha dado un ataque al corazón?',  '/raw/audioesp4_37', '/drawable/imagen4_37', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Gwage boni uggiar be gusa?',  '/raw/audiogun4_37') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä tärä nigrengä tibienya?',  '/raw/audiongo4_37') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Puzora fayavuka?',  '/raw/audioemb4_37') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Tuberculosis',  '/raw/audioesp4_38', '/drawable/imagen4_38', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Isddar wiagged',  '/raw/audiogun4_38') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Bügrän bren (pulmón enfermo)',  '/raw/audiongo4_38') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua erobucaetico?',  '/raw/audioemb4_38') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Diabetes',  '/raw/audioesp4_39', '/drawable/imagen4_39', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Abe ossi',  '/raw/audiogun4_39') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Asukar därietde (azúcar en la sangre) Därie mane (sangre dulce)',  '/raw/audiongo4_39') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua erobucana azucar?',  '/raw/audioemb4_39') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Presión alta',  '/raw/audioesp4_40', '/drawable/imagen4_40', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Abe irmagged binna suli',  '/raw/audiogun4_40') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Därie ne jume (sangre rápida-alterada)',  '/raw/audiongo4_40') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua presion sufrivaricana chïtá?',  '/raw/audioemb4_40') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Presión baja',  '/raw/audioesp4_41', '/drawable/imagen4_41', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Abe irmagged nollogwa',  '/raw/audiogun4_41') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Därie ne bätdäre (sangre lenta)',  '/raw/audiongo4_41') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua presion bajavaricana?',  '/raw/audioemb4_41') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Epilepsia',  '/raw/audioesp4_42', '/drawable/imagen4_42', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Dia dia gued (issaar gued)',  '/raw/audiogun4_42') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ningrengä tibien (estremecerse en el suelo)',  '/raw/audiongo4_42') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua ataque tiabarika?',  '/raw/audioemb4_42') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Ha estado enferma antes? (hace una semana, un mes, un año…)',  '/raw/audioesp4_43', '/drawable/imagen4_43', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be idu uelesa? (iddoged ir gwen gusa, ni wargwen gusa, birga gwen gusa)',  '/raw/audiogun4_43') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä bren ñongwane? (bomo kratdi näre, sö kratdi näre, kä kratdi näre…)',  '/raw/audiongo4_43') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pura nae na enferma basicana? Pura semana ne enferma basicana? Pura gerecore enferma basicana? Pura añone enferma basicana?',  '/raw/audioemb4_43') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Está tomando alguna medicina? ¿Está tomando alguna pastilla?',  '/raw/audioesp4_44', '/drawable/imagen4_44', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be ina donai? ',  '/raw/audiogun4_44') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä tä krägä ñaenya?',  '/raw/audiongo4_44') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua naena tobasica medician o patiyata?',  '/raw/audioemb4_44') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Tiene zumbido en el oído?',  '/raw/audioesp4_45', '/drawable/imagen4_45', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Waya swii be iddoge?',  '/raw/audiogun4_45') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Kä ngä räen mä olotdea? Kä ngä mä olotdea?',  '/raw/audiongo4_45') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua kürure zombidora eroguka?',  '/raw/audioemb4_45') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Ve lucecitas?',  '/raw/audioesp4_46', '/drawable/imagen4_46', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be daggar daggar addagge?',  '/raw/audiogun4_46') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Kä trä nende mä ogwätdea? Idro toen mä ogwätdea?',  '/raw/audiongo4_46') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua cucuyora unuwarikana?',  '/raw/audioemb4_46') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Cuántos hijos tiene o ha tenido?',  '/raw/audioesp4_47', '/drawable/imagen4_47', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Mimmigan war bigwa nigga?',  '/raw/audiogun4_47') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä ngobogre tärä nibe?',  '/raw/audiongo4_47') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua cuanto warra tosita?',  '/raw/audioemb4_47') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Cuántos años (meses) tiene su  último hijo?',  '/raw/audioesp4_48', '/drawable/imagen4_48', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Birgwa (nii) bigwa be nabbi nusa nigga?',  '/raw/audiogun4_48') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Kä (sö) kräbe ngobogre mräbitdi?',  '/raw/audiongo4_48') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Puarrara cuantos años erobuta? Puarra cuantos gereco buta?',  '/raw/audioemb4_48') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Cuántos niños han fallecido?',  '/raw/audioesp4_49', '/drawable/imagen4_49', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿War bigwagus be mimmigan burgwe?',  '/raw/audiogun4_49') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ngobogre mägwe krütdani nibe?',  '/raw/audiongo4_49') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua cuantos warra peusidata?',  '/raw/audioemb4_49') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Quién le atendió en el parto?',  '/raw/audioesp4_50', '/drawable/imagen4_50', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Doa bega abingasa be mimmi niggunagu?',  '/raw/audiogun4_50') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Nire tä kwidetde mä bren ngwane?',  '/raw/audiongo4_50') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua caiba uarra tobisika?',  '/raw/audioemb4_50') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Está embarazada?',  '/raw/audioesp4_51', '/drawable/imagen4_51', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be bonigwale?',  '/raw/audiogun4_51') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä dobogoa? Mä ñan mobea? Mä ñan kratdia?',  '/raw/audiongo4_51') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pura biogobucara?',  '/raw/audioemb4_51') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Desde cuándo está embarazada?',  '/raw/audioesp4_52', '/drawable/imagen4_52', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Ni war bigwa gusa be bonigwale?',  '/raw/audiogun4_52') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Sö krobera mä dobogoa?',  '/raw/audiongo4_52') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua caunto gedeco buta?',  '/raw/audioemb4_52') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Cuándo fue la última regla / sangrado?',  '/raw/audioesp4_53', '/drawable/imagen4_53', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Ingu nabbi igar be dagsa?',  '/raw/audiogun4_53') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ñongwane sö kä namaningo  mäbitdi?',  '/raw/audiongo4_53') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Carégedecore unusi?',  '/raw/audioemb4_53') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Ha tenido dolores en el embarazo?',  '/raw/audioesp4_54', '/drawable/imagen4_54', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be bonigwar gudigu be nunmagged iddosa?',  '/raw/audiogun4_54') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä tärä ja bren nigea?',  '/raw/audiongo4_54') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua viogobude fuabaricara?',  '/raw/audioemb4_54') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Le voy a examinar el vientre',  '/raw/audioesp4_55', '/drawable/imagen4_55', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Be saban an daggolo',  '/raw/audiogun4_55') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ti bige mä büle trä toen',  '/raw/audiongo4_55') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua vira acudía sawabu cabaya?',  '/raw/audioemb4_55') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Ha tenido dos niños en la barriga?',  '/raw/audioesp4_56', '/drawable/imagen4_56', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be sabangi goemar warbo be niggusa?',  '/raw/audiogun4_56') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä tärä mugine ngübüarea?',  '/raw/audiongo4_56') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua umé tobarícara',  '/raw/audioemb4_56') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Te han cortado la barriga?',  '/raw/audioesp4_57', '/drawable/imagen4_57', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be saban marisa?',  '/raw/audiogun4_57') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä büle tigaga täräya?',  '/raw/audiongo4_57') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Konsidaka bu bira',  '/raw/audioemb4_57') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Cuántos niños pariste en casa?',  '/raw/audioesp4_58', '/drawable/imagen4_58', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Mimmigan war bigwa neggi be niggusa?',  '/raw/audiogun4_58') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ngobogre därebare nibe gwi?',  '/raw/audiongo4_58') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Warra jumazawa tosi dikida',  '/raw/audioemb4_58') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Cuántos niños pariste en el hospital?',  '/raw/audioesp4_59', '/drawable/imagen4_59', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Mimmigan war bigwa osbidalgi be niggusa?',  '/raw/audiogun4_59') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ngobogre därebare nibe jutdatde?',  '/raw/audiongo4_59') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua cuanto tosita hospitale?',  '/raw/audioemb4_59') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Tienes dolores en los senos?',  '/raw/audioesp4_60', '/drawable/imagen4_60', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Mam be nunmagge?',  '/raw/audiogun4_60') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä kean brenya?',  '/raw/audiongo4_60') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Puanumukara pu ju',  '/raw/audioemb4_60') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Tienes dificultad para respirar?',  '/raw/audioesp4_61', '/drawable/imagen4_61', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Be aggu aggu bunnoge?',  '/raw/audiogun4_61') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä nibi müre jäge tarea?',  '/raw/audiongo4_61') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bura problema barabuka nova sira za?',  '/raw/audioemb4_61') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Tienes dolores en la vagina?',  '/raw/audioesp4_62', '/drawable/imagen4_62', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Muusira be nunmagge?',  '/raw/audiogun4_62') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ja taretde bren (tibien-señalar) ',  '/raw/audiongo4_62') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Puanumukara bu vaginanara?',  '/raw/audioemb4_62') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Cada cuánto tiempo tiene relaciones sexuales?',  '/raw/audioesp4_63', '/drawable/imagen4_63', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Ila bigwa be aiga be gue?',  '/raw/audiogun4_63') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ñongwane ngwane mä noin jadüge mugoben? Bobe mä neme ni brareben?',  '/raw/audiongo4_63') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Puanumukara bu vaginanara?',  '/raw/audioemb4_63') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Toma alguna medicina natural para esto?',  '/raw/audioesp4_64', '/drawable/imagen4_64', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Wega dule ina be gobe?',  '/raw/audiogun4_64') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä tä krägä konsenda ñain ne  grägea? ',  '/raw/audiongo4_64') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Torarikara chiruara meaema?',  '/raw/audioemb4_64') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Ha ido con el curandero para el examen?',  '/raw/audioesp4_65', '/drawable/imagen4_65', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Inaduledse be nanae be san daggega?',  '/raw/audiogun4_65') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä nigi krägä deanga mä trä täenya? ',  '/raw/audiongo4_65') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua jua zi kara jaivana má?',  '/raw/audioemb4_65') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Toma alguna medicina después del parto?',  '/raw/audioesp4_66', '/drawable/imagen4_66', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Goe niggusad sorba ina be gobe?',  '/raw/audiogun4_66') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Krägä ño ñatda mägwe chi rabara mä küsetde? ',  '/raw/audiongo4_66') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pu warra toda kanea medicina tobarita?',  '/raw/audioemb4_66') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Cuándo te hiciste el PAP?',  '/raw/audioesp4_67', '/drawable/imagen4_67', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Ingu be imagsa PAP?',  '/raw/audiogun4_67') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ñongwane mägwe PAP sribebare?',  '/raw/audiongo4_67') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Zaunbeda akubivasi bu kakuara',  '/raw/audioemb4_67') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Vamos a examinar al bebé',  '/raw/audioesp4_68', '/drawable/imagen4_68', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Anmar goe daggolo?',  '/raw/audiogun4_68') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ani chi trä täen ',  '/raw/audiongo4_68') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Puwawära akudia?',  '/raw/audioemb4_68') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Voy a medir la barriga para ver el tamaño del bebé',  '/raw/audioesp4_69', '/drawable/imagen4_69', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Be saban wilub sagwelo, goe bule dungue daggega',  '/raw/audiogun4_69') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ti bige mä büle ñäge chi mägwe nuäi gai gäre ',  '/raw/audiongo4_69') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bu bira zadia kabadi karea bu wa wara',  '/raw/audioemb4_69') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Voy a escuchar el corazón del bebé',  '/raw/audioesp4_70', '/drawable/imagen4_70', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Goe gwage iddogwelo',  '/raw/audiogun4_70') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ti bige chi brugwä ngö noen',  '/raw/audiongo4_70') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bu wawa zoda urindia ',  '/raw/audioemb4_70') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Voy a examinarte las mamas',  '/raw/audioesp4_71', '/drawable/imagen4_71', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Ti bige mä chuyu trä täen',  '/raw/audiogun4_71') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Be mam daggolo',  '/raw/audiongo4_71') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bu juta enzaminania?',  '/raw/audioemb4_71') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Quítese el panti',  '/raw/audioesp4_72', '/drawable/imagen4_72', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Bandi unge',  '/raw/audiogun4_72') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Duän ja kwatdabotdä tigetde jabotdä ',  '/raw/audiongo4_72') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pantira eratua?',  '/raw/audioemb4_72') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Voy a hacer el PAP',  '/raw/audioesp4_73', '/drawable/imagen4_73', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'PAP an sagwelo',  '/raw/audiogun4_73') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ti bige PAP sribere ',  '/raw/audiongo4_73') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Para oría?',  '/raw/audioemb4_73') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'La barriga está muy grande',  '/raw/audioesp4_74', '/drawable/imagen4_74', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Saban bibbi suli',  '/raw/audiogun4_74') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä büle kri digaro ',  '/raw/audiongo4_74') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bu bira waitabe jirabu',  '/raw/audioemb4_74') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Tienes dos niños adentro',  '/raw/audioesp4_75', '/drawable/imagen4_75', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Goe warbo daniggi',  '/raw/audiogun4_75') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mägwe mungi ngüabüadi (vas a cuidar dos niños) ',  '/raw/audiongo4_75') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pua umé warrara erobúa?',  '/raw/audioemb4_75') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'La barriga está muy chiquita',  '/raw/audioesp4_76', '/drawable/imagen4_76', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Be saban bibbigwa',  '/raw/audiogun4_76') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Mä büle tä chi digaro ',  '/raw/audiongo4_76') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pu vira kaipe wa?',  '/raw/audioemb4_76') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'El bebé está desnutrido',  '/raw/audioesp4_77', '/drawable/imagen4_77', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Goe nued masgunsuli',  '/raw/audiogun4_77') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Chi kräre digaro',  '/raw/audiongo4_77') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pu bebera kayakirua?',  '/raw/audioemb4_77') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'El bebé está de cabeza',  '/raw/audioesp4_78', '/drawable/imagen4_78', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Goe non siggi mai',  '/raw/audiogun4_78') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Chi tä dogwäre ',  '/raw/audiongo4_78') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pu wawara porara eda kirua?',  '/raw/audioemb4_78') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'El bebé está de nalga',  '/raw/audioesp4_79', '/drawable/imagen4_79', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Goe sorsig mai',  '/raw/audiogun4_79') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Chi tä unyire ',  '/raw/audiongo4_79') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bu chumua?',  '/raw/audioemb4_79') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'El bebé está atravesado',  '/raw/audioesp4_80', '/drawable/imagen4_80', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Goe birigwa mai',  '/raw/audiogun4_80') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Chi tä mötda ',  '/raw/audiongo4_80') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pu warrara atravezabua?',  '/raw/audioemb4_80') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Hay que llevarla al hospital',  '/raw/audioesp4_81', '/drawable/imagen4_81', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Osbidalse seder gebe',  '/raw/audiogun4_81') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Jänrigadre opitalitde ',  '/raw/audiongo4_81') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bu ra etidia hospitaldea',  '/raw/audioemb4_81') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Está sangrando?',  '/raw/audioesp4_82', '/drawable/imagen4_82', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Igar dagde?',  '/raw/audiogun4_82') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Nibi därie ngetdiegäya? ',  '/raw/audiongo4_82') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bura wa ba ta bua?',  '/raw/audioemb4_82') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, '¿Desde cuándo?',  '/raw/audioesp4_83', '/drawable/imagen4_83', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, '¿Sanawa?',  '/raw/audiogun4_83') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ñongwane jatdani? ',  '/raw/audiongo4_83') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bua cuanto día buta',  '/raw/audioemb4_83') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Es un aborto',  '/raw/audioesp4_84', '/drawable/imagen4_84', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Mellesa',  '/raw/audiogun4_84') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Ngobogre mürie ketde ',  '/raw/audiongo4_84') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Bu warrara eritigasi',  '/raw/audioemb4_84') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Perdió al bebé',  '/raw/audioesp4_85', '/drawable/imagen4_85', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Chi nibi küre gon',  '/raw/audiogun4_85') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Goe igarba galaggusa',  '/raw/audiongo4_85') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pu warra puchasia',  '/raw/audioemb4_85') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 4, 'Deme la tarjeta del embarazo',  '/raw/audioesp4_86', '/drawable/imagen4_86', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 4, 'Bonigwar e sabga anga udage',  '/raw/audiogun4_86') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 4, 'Tärä ñan mobegwe ngwen tie ',  '/raw/audiongo4_86') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 4, 'Pu control tiata acudikanea',  '/raw/audioemb4_86') ");
            //Capitulo 5
            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 5, 'Tome una pastilla cada día, durante 10 dias ',  '/raw/audioesp5_1', '/drawable/imagen5_1', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 5, 'Iba ambe wuilub, iba irbali ina doe',  '/raw/audiogun5_1') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 5, 'Krägä ñain kwatdi köböitdire köböitdire, köböjotdo näre',  '/raw/audiongo5_1') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 5, 'Pua patillara toya diezdias kanea',  '/raw/audioemb5_1') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 5, 'Tome una pastilla cada 8 horas, durante 4 días ',  '/raw/audioesp5_2', '/drawable/imagen5_2', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 5, 'Iba bagge wilub, wassi ir baabag gi ina doe',  '/raw/audiogun5_2') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 5, 'Krägä ñain kwatdi ñänä ogwä krokwä näre, köböitdire kräbogon näre',  '/raw/audiongo5_2') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 5, 'Pua toita patillara cara ocho horas sa, evarita kimani',  '/raw/audioemb5_2') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 5, 'Tome una pastilla cada 12 horas, durante 5 días',  '/raw/audioesp5_3', '/drawable/imagen5_3', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 5, 'Iba addar wilub, wassi il ambe gagga bogi ina doe',  '/raw/audiogun5_3') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 5, 'Krägä ñain kwatdi ñänä ogwä kräjotdo bitdi krobu, köböitdire krärige näre',  '/raw/audiongo5_3') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 5, 'Pua toita patillara cara doce horas, cinco diade toita',  '/raw/audioemb5_3') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 5, 'Tome una cucharita cada 6 horas, durante 7 dias',  '/raw/audioesp5_4', '/drawable/imagen5_4', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 5, 'Iba gugle wilub wassi ila nergwagi wesar bibigi ina gobe',  '/raw/audiogun5_4') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 5, 'Kuyara chi ñain kwatdi ñänä ogwä kräti, köböitdire kräügon näre',  '/raw/audiongo5_4') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 5, 'Pua toita kuzarraba sei hora sa por siete dia de',  '/raw/audioemb5_4') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 5, 'Tome media pastilla cada 4 horas, durante tres días',  '/raw/audioesp5_5', '/drawable/imagen5_5', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 5, 'Ib baa wilub, wassi irbaggegi ina marar doe',  '/raw/audiogun5_5') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 5, 'Krägä ötdare kwatdi ñain ñänä ogwä kräbogon näre, köböitdire krämä näre',  '/raw/audiongo5_5') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 5, 'Pua patillara partipera toita cara, cuatro horas por tres dias toita',  '/raw/audioemb5_5') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 5, 'Tome en la mañana, al mediodía, en la tarde y en la noche ',  '/raw/audioesp5_6', '/drawable/imagen5_6', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 5, 'Waidar yalagi, yorruggugi, sedogi, muddiggi ina doe (ina gobe)',  '/raw/audiogun5_6') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 5, 'Krägä ñain dego, ñänä ruäre, dere, deo',  '/raw/audiongo5_6') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 5, 'Tiapede toita patillara umatifa, quebara y queuda kanea',  '/raw/audioemb5_6') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 5, 'Venga dentro de un mes / la próxima semana / en tres semanas',  '/raw/audioesp5_7', '/drawable/imagen5_7', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 5, 'Ni gwengi be dagbaloe / Iddoged paidse / iddoged ir baase',  '/raw/audiogun5_7') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 5, 'Mä jataita sö kratdi näre / bomo kratdi näre / kratdi näre / bomo krämä näre',  '/raw/audiongo5_7') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 5, 'Gedekode pura seita cudikanea, pura seita semanane, pura seita tre semane.',  '/raw/audioemb5_7') ");
//Capitulo 6
            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 6, '¿Ha consultado con el sukia (Ngäbere) o nele (Guna)?',  '/raw/audioesp6_1', '/drawable/imagen6_1', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 6, '¿Ner be dagsa?',  '/raw/audiogun6_1') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 6, 'Mägwe blitdani sukiabenya?',  '/raw/audiongo6_1') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 6, 'Pua consultabucara botanicome',  '/raw/audioemb6_1') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 6, '¿Ha consultado con el curandero?¿Ha consultado con el inaduledi? (Guna)',  '/raw/audioesp6_2', '/drawable/imagen6_2', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 6, '¿Inaduledse be arbi?',  '/raw/audiogun6_2') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 6, 'Mägwe blitdani krägä biangabenya?',  '/raw/audiongo6_2') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 6, 'Pua consutazica cuaranderome',  '/raw/audioemb6_2') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 6, '¿Ha tomado alguna hierba medicinal? ¿Cuál?',  '/raw/audioesp6_3', '/drawable/imagen6_3', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 6, '¿Dule ina be gobsa?  ¿Ibu ina?',  '/raw/audiogun6_3') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 6, 'Mä krägä konseni ñariraya? Meden?',  '/raw/audiongo6_3') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 6, 'Bua tosikana plantara? Kare palntata tosita',  '/raw/audioemb6_3') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 6, '¿Está en ayunas?',  '/raw/audioesp6_4', '/drawable/imagen6_4', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 6, '',  '/raw/audiogun6_4') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 6, 'Mä tä ja boinea?',  '/raw/audiongo6_4') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 6, 'Necoembucara?',  '/raw/audioemb6_4') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 6, '¿Hay alguna embarazada en la casa?',  '/raw/audioesp6_5', '/drawable/imagen6_5', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 6, '¿Be neggi ome bonigwuar gudi?',  '/raw/audiogun6_5') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 6, 'Meri dobogo tä gwia?',  '/raw/audiongo6_5') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 6, 'Te de alguna werata biogobucara?',  '/raw/audioemb6_5') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 6, '¿Hay algún picado de culebra en la casa?',  '/raw/audioesp6_6', '/drawable/imagen6_6', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 6, '¿Neguyaggi dule wargwen dubgi warmagsad gudi?',  '/raw/audiogun6_6') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 6, 'Ni ngadabare tärä illa gwia?',  '/raw/audiongo6_6') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 6, 'Tiguida neeca embera tamaba kadata?',  '/raw/audioemb6_6') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 6, '¿Están tomando cacao en la casa?',  '/raw/audioesp6_7', '/drawable/imagen6_7', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 6, '¿Be neggi siagwa nis gobmala?',  '/raw/audiogun6_7') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 6, 'Mun tä kä ñai ja gwiretdea?',  '/raw/audiongo6_7') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 6, 'Kaita cacao di qui da tobari',  '/raw/audioemb6_7') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 6, '¿Cuántas veces han tomado?',  '/raw/audioesp6_8', '/drawable/imagen6_8', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 6, '¿Ir bigwa gobmala?',  '/raw/audiogun6_8') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 6, 'Mägwe nibe kä ñarira?',  '/raw/audiongo6_8') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 6, 'Evaride cuanto tovari',  '/raw/audioemb6_8') ");
//Capitulo 7
            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Ve a trabajar pero cuídate',  '/raw/audioesp7_1', '/drawable/imagen7_1', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Be arbane, nue daoe arbaedgi',  '/raw/audiogun7_1') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Nigi siribikre kogwe mogredre',  '/raw/audiongo7_1') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Pua trabajo de warata tiguida weita',  '/raw/audioemb7_1') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Antes de ir a trabajar sea prevenido',  '/raw/audioesp7_', '/drawable/imagen7_2', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Be arbanaed idu, iddoaggua be naoe',  '/raw/audiogun7_2') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Ja mige mogre siribikrä konengri',  '/raw/audiongo7_2') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Ju trabajore wa urude camista trasua y gorrata',  '/raw/audioemb7_2') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Reflexiona antes de ir a trabajar',  '/raw/audioesp7_3', '/drawable/imagen7_3', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'binsagwele arbaned idu',  '/raw/audiogun7_3') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Mä trodre ao jabtaba siribikrä konengri',  '/raw/audiongo7_3') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Jura descansata tiguida',  '/raw/audioemb7_3') ");

            //db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'No se haga el bobo',  '/raw/audioesp7_4', '/drawable/imagen7_4', '0') ");
            //db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Mer be gegegwa na sae',  '/raw/audiogun7_4') ");
          //  db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Ña jamigatdre dogwai',  '/raw/audiongo7_4') ");
        //    db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Loco quinata garata',  '/raw/audioemb7_4') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Aconseje a su familia antes  de migrar',  '/raw/audioesp7_5', '/drawable/imagen7_5', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Be gwenadganga neggi be sunmaggwele baidsig naed idu.',  '/raw/audiogun7_5') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Mä trodtdre jamorogo nigwebatda gana konengri',  '/raw/audiongo7_5') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Ju familiara aconsejaita te guaratruade warama nea',  '/raw/audioemb7_5') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Evite mala gavilla',  '/raw/audioesp7_6', '/drawable/imagen7_6', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Aimar isggana mer niggue',  '/raw/audiogun7_6') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Ña jaguetde ni komeme',  '/raw/audiongo7_6') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Embera cachirua barata',  '/raw/audioemb7_6') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'No pases vergüenza entre extraños.',  '/raw/audioesp7_7', '/drawable/imagen7_7', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Mer be bingeggunai sae aimar aggudagmalad abargi',  '/raw/audiogun7_7') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Ña ja toa ganandre chuitre',  '/raw/audiongo7_7') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Embera barade bua verguanza pazarata',  '/raw/audioemb7_7') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Junta dinero para cosas útiles',  '/raw/audioesp7_8', '/drawable/imagen7_8', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Ibmar nuedga mani be sabodii sae',  '/raw/audiogun7_8') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Ngwian ügegro jondron koin gro',  '/raw/audiongo7_8') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Juar farata guagata cosanei ta kanea',  '/raw/audioemb7_8') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Cuando estés fuera de casa busca al médico',  '/raw/audioesp7_9', '/drawable/imagen7_9', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Be galimbaba gudiile ner be amie',  '/raw/audiogun7_9') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Mentdogware setde ja krogoe migaga konene',  '/raw/audiongo7_9') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Pua teguara truade cude bura doctor ma waita.',  '/raw/audioemb7_9') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Cuando estés fuera de casa busca el centro de salud.',  '/raw/audioesp7_10', '/drawable/imagen7_10', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Be galimbaba gudiile galu sanggwed be amie.',  '/raw/audiogun7_10') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Mendogware setde krogo jue konene',  '/raw/audiongo7_10') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Pua teguara truade cude bura centro de saud',  '/raw/audioemb7_10') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Si su esposa esta embazada hagan las consultas antes de alguna complicación.',  '/raw/audioesp7_11', '/drawable/imagen7_11', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Be ome gurgin nigga gudile, be nue egise mer bonigan nigga gunai guega.',  '/raw/audiogun7_11') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Ja mugo ña mobe kodriere bren konengri',  '/raw/audiongo7_11') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Puqui mara biogo bude waita consulta ode complequeli nana.',  '/raw/audioemb7_11') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Atiéndase antes de enfermarse de gravedad',  '/raw/audioesp7_12', '/drawable/imagen7_12', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Be na san bendagge wis be gegue iddoger',  '/raw/audiogun7_12') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Mogretdre brentare Konengri',  '/raw/audiongo7_12') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Pua atendeides waita enfermera camala ante de gaveide.',  '/raw/audioemb7_12') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Todos los niños y niñas nacidos en Costa Rica deben ser registrados.',  '/raw/audioesp7_13', '/drawable/imagen7_13', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Mimmimar gwable gwalulesmalad costa Rica neggweburgi igar nigga nug na mesergebe',  '/raw/audiogun7_13') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Ngäbakre darebare Kotarika erere kä tädre tikani täräbta jökrä',  '/raw/audiongo7_13') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Parara emberara comarca de vena toda canae registradita.',  '/raw/audioemb7_13') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Qué necesito para hacer mis trámites migratorios.',  '/raw/audioesp7_14', '/drawable/imagen7_14', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Ibu an nigguergebe nabir an igar nigga sabga baidneggweburgi gudii saega.',  '/raw/audiogun7_14') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Tära ükatedre dikaka krä kä kröbti',  '/raw/audiongo7_14') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Pura triburalde waita.',  '/raw/audioemb7_14') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Declaración jurada.',  '/raw/audioesp7_15', '/drawable/imagen7_15', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Ibmar iniggigwadba barsoged',  '/raw/audiogun7_15') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Ja niedre metre etere.',  '/raw/audiongo7_15') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Registrad marea',  '/raw/audioemb7_15') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Certificado de nacimiento.',  '/raw/audioesp7_16', '/drawable/imagen7_16', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Gwalulesad sabga',  '/raw/audiogun7_16') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Tära ni dorere ngware',  '/raw/audiongo7_16') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Pu nacimiento saca da marea.',  '/raw/audioemb7_16') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Pasaporte.',  '/raw/audioesp7_17', '/drawable/imagen7_17', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Eneggamba igar nigga naed sabga.',  '/raw/audiogun7_17') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Basaborte',  '/raw/audiongo7_17') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Pu papel sacara marea.',  '/raw/audioemb7_17') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Salvoconducto',  '/raw/audioesp7_18', '/drawable/imagen7_18', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Sabga igarnigga gudii gued.',  '/raw/audiogun7_18') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Salbacondubto',  '/raw/audiongo7_18') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Pu conducto oda marea.',  '/raw/audioemb7_18') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Carnet para trabajar',  '/raw/audioesp7_19', '/drawable/imagen7_19', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Sabga igar nigga arbamaladgad',  '/raw/audiogun7_19') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Karne siribikrä',  '/raw/audiongo7_19') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Pu carnet sacaita traja marea.',  '/raw/audioemb7_19') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Para garantizarme acceso a servicios públicos',  '/raw/audioesp7_20', '/drawable/imagen7_20', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Nabir igar nigga naega galumar dulamar abindaggedse.',  '/raw/audiogun7_20') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Ni tuarabadre känti jutabti Kukwe jö krabitdi.',  '/raw/audiongo7_20') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Pu man papelta juma eroboburo servicio ne trajaita bua',  '/raw/audioemb7_20') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Para realizar tramites necesarios',  '/raw/audioesp7_21', '/drawable/imagen7_21', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Igar sogedba sabgamar onoega',  '/raw/audiogun7_21') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Tärä ukra terabadre.',  '/raw/audiongo7_21') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, '',  '/raw/audioemb7_21') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Para defender nuestros derechos',  '/raw/audioesp7_22', '/drawable/imagen7_22', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'An gudidbina urwega igar anmarga maidba dule guedgi.',  '/raw/audiogun7_22') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Kukwe kwin bta ja Kwatarabadre',  '/raw/audiongo7_22') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Pu derechora defendeita bua',  '/raw/audioemb7_22') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Para identificarnos.',  '/raw/audioesp7_23', '/drawable/imagen7_23', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'An nabir na maga dagmalga an doa dule.',  '/raw/audiogun7_23') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Kö okwä nikwe',  '/raw/audiongo7_23') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Mura emberata',  '/raw/audioemb7_23') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Defendamos nuestros  derechos laborales.',  '/raw/audioesp7_24', '/drawable/imagen7_24', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Na buraggwa anmar na gandig gwisgumala  anmar arbaebina.',  '/raw/audiogun7_24') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Sribi ütiä biandre kwin abko bta ja kwatadre',  '/raw/audiongo7_24') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Mu trabajora repetadita',  '/raw/audioemb7_24') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Tome mucha agua',  '/raw/audioesp7_25', '/drawable/imagen7_25', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Gaddig be di goboe',  '/raw/audiogun7_25') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Ñö ñain digaro.',  '/raw/audiongo7_25') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Paita tota guaibua',  '/raw/audioemb7_25') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Lávese las manos antes de comer',  '/raw/audioesp7_26', '/drawable/imagen7_26', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'masgnned iduar argan be enuggo',  '/raw/audiogun7_26') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Küse bätetde mrö ngämi känengri',  '/raw/audiongo7_26') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Jiguara seguta ne coi nena',  '/raw/audioemb7_26') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Báñese todos los días',  '/raw/audioesp7_27', '/drawable/imagen7_27', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Bane bane be oboe',  '/raw/audiogun7_27') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Ja jüben köbö kwatdire kwatdire',  '/raw/audiongo7_27') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Jura cuita evarisa',  '/raw/audioemb7_27') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Use pañuelo para toser',  '/raw/audioesp7_28', '/drawable/imagen7_28', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Dollomader, gaya gao samorgi',  '/raw/audiogun7_28') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Duän kugwän müra ngediengä ngwane',  '/raw/audiongo7_28') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Jua pañuelo ra usaita tosabrusa',  '/raw/audioemb7_28') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Descanse',  '/raw/audioesp7_29', '/drawable/imagen7_29', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Nued be obunno',  '/raw/audiogun7_29') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Jadüge',  '/raw/audiongo7_29') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Descansaita',  '/raw/audioemb7_29') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Lave la herida con agua y jabón',  '/raw/audioesp7_30', '/drawable/imagen7_30', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Nailigwaled digi morgaugbo onugguo',  '/raw/audiogun7_30') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Träen bätetdeñö botdä jabonde',  '/raw/audiongo7_30') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Puassequilla heridara paitume y jangonaba',  '/raw/audioemb7_30') ");
//--
            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'No tome guarapo',  '/raw/audioesp7_31', '/drawable/imagen7_31', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Mer sisa gobo',  '/raw/audiogun7_3') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Ibia döe ñan ñain',  '/raw/audiongo7_31') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Tuarata ituara',  '/raw/audioemb7_31') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Use zapatos',  '/raw/audioesp7_32', '/drawable/imagen7_32', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Sabbad be yoo',  '/raw/audiogun7_32') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Sabatdo ngwandre ngotobotdä',  '/raw/audiongo7_32') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Zapatara jiña',  '/raw/audioemb7_32') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Mantenga al día las vacunas',  '/raw/audioesp7_33', '/drawable/imagen7_33', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Igar maidba bonigan iduar iggo yoe',  '/raw/audiogun7_33') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Bakuna migadre tätde',  '/raw/audiongo7_33') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Vacunara al dia vaita',  '/raw/audioemb7_33') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Tome las medicinas',  '/raw/audioesp7_34', '/drawable/imagen7_34', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Ina doe (gobe)',  '/raw/audiogun7_34') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Krägä ñadre jögrä',  '/raw/audiongo7_34') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Medicina natoita',  '/raw/audioemb7_34') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'No se preocupe',  '/raw/audioesp7_35', '/drawable/imagen7_35', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Mer buggib binsae',  '/raw/audiogun7_35') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Ñan töbigadre',  '/raw/audiongo7_35') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Preocuparata',  '/raw/audioemb7_35') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Siga los consejos',  '/raw/audioesp7_36', '/drawable/imagen7_36', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Unaedba be dao',  '/raw/audiogun7_36') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Dirire migadre ütdiätde',  '/raw/audiongo7_36') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Consejora urita',  '/raw/audioemb7_36') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Mantenga las uñas cortadas',  '/raw/audioesp7_37', '/drawable/imagen7_37', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Gonunasi be siggo',  '/raw/audiogun7_37') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Küsedaba tadre tiganingä angwane botäninde',  '/raw/audiongo7_37') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Ichivira tiita',  '/raw/audioemb7_37') ");

            db.execSQL("INSERT INTO " + TABLE_ESPANIOL + " (" + Categoria + ", " + Espaniol + ", " + Audio + ", " + Imagen + ", "+USO+ ") VALUES ( 7, 'Mantenga la ropa limpia',  '/raw/audioesp7_38', '/drawable/imagen7_38', '0') ");
            db.execSQL("INSERT INTO " + TABLE_GUNA + " (" + Categoria + ", " + GUNA + ", " + Audioguna + ") VALUES ( 7, 'Mor nuedgwa be aggo',  '/raw/audiogun7_38') ");
            db.execSQL("INSERT INTO " + TABLE_NGABERE + " (" + Categoria + ", " + NGABERE + ", " + Audiongabere + ") VALUES ( 7, 'Duängutdu tädre botäninde',  '/raw/audiongo7_38') ");
            db.execSQL("INSERT INTO " + TABLE_EMBERA + " (" + Categoria + ", " + EMBERA + ", " + Audioembera + ") VALUES ( 7, 'Puguara siguivalla.',  '/raw/audioemb7_38') ");

        }

    }
}



