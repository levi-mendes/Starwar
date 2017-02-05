package br.com.levimendesestudos.starwars.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.levimendesestudos.starwars.model.Filme;

/**
 * Created by 809778 on 03/02/2017.
 */

public class FilmeDB extends DBGenericClass {

    public static final String TB_NAME = "TB_FILME";

    public static final String ID_PERSONAGEM  = "ID_PERSONAGEM";//chave estranjeira
    public static final String URL            = "URL";

    public FilmeDB(Context context) {
        super(context);
    }

    public boolean inserir(Filme f) {
        ContentValues values = new ContentValues();

        values.put(ID_PERSONAGEM,  f.idPersonagem);
        values.put(URL,            f.url);

        boolean retorno = database.insert(TB_NAME, null, values) > 0;

        return retorno;
    }

    public List<Filme> listar(int pIdPersonagem) {
        String [] param = new String[]{String.valueOf(pIdPersonagem)};
        List<Filme> retorno = new ArrayList<>();
        Cursor cursor = database.query(TB_NAME, null, ID_PERSONAGEM + " = ?", param, null, null, null);

        while (cursor.moveToNext()) {
            Filme f = new Filme();

            int idPersonagem = cursor.getInt(cursor.getColumnIndex(ID_PERSONAGEM));
            String url = cursor.getString(cursor.getColumnIndex(URL));

            f.idPersonagem  = idPersonagem;
            f.url           = url;

            retorno.add(f);
        }

        return retorno;
    }
}
