package br.com.levimendesestudos.starwars.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 809778 on 02/02/2017.
 */

public class StarWarsDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "sw.db";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase mDatabase;

    private static StarWarsDB starWarsDB;

    private StarWarsDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        mDatabase = getWritableDatabase();
    }

    /**
     *
     * Singleton da classe
     *
     * @return
     *
     */
    public static StarWarsDB getInstance(Context context) {
        if (starWarsDB == null || !starWarsDB.mDatabase.isOpen()) {
            starWarsDB = new StarWarsDB(context);
        }

        return starWarsDB;
    }

    /**
     *
     * Metodo chamado na criacao do banco de dados
     *
     * @param db
     *
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptDatabase.DROP_TB_PERSONAGEM);
        db.execSQL(ScriptDatabase.DROP_TB_FILME);

        db.execSQL(ScriptDatabase.CREATE_TB_PERSONAGEM);
        db.execSQL(ScriptDatabase.CREATE_TB_FILME);
    }

    /**
     * metodo chamado toda vez que a versao do banco banco de dados e alterada
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     *
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }
}
