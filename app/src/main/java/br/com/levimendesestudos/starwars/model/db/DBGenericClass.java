package br.com.levimendesestudos.starwars.model.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 809778 on 03/02/2017.
 */

public abstract class DBGenericClass {

    public SQLiteDatabase database;

    /**
     *
     * singleton
     *
     * @param context
     *
     */
    public DBGenericClass(Context context) {
        database = StarWarsDB.getInstance(context).getDatabase();
    }

    /**
     * init transaction
     */
    public void startTransaction(){
        database.beginTransaction();
    }

    /**
     * end transaction
     */
    public void commitChanges(){
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    /**
     * close cursor
     * @param cursor
     */
    public void fecharCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}