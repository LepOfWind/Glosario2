package com.glind;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Luis on 06/07/2016.
 */
public class DbHelper  extends SQLiteOpenHelper{
    private static final String DB_NAME = "Glosario.sqlite";
    private static final int DB_VERSION = 1;
    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creacion de las Tablas de la base de datos
        db.execSQL(DataBaseManager.CREATE_ESPANIOL);
        db.execSQL(DataBaseManager.CREATE_CATEGORIA);
        db.execSQL(DataBaseManager.CREATE_GUNA);
        db.execSQL(DataBaseManager.CREATE_NGABERE);
        db.execSQL(DataBaseManager.CREATE_EMBERA);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
