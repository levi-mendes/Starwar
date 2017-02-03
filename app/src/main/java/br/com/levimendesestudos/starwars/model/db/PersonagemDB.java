package br.com.levimendesestudos.starwars.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.levimendesestudos.starwars.model.Personagem;

/**
 * Created by 809778 on 02/02/2017.
 */

public class PersonagemDB extends DBGenericClass {

    public static final String TB_NAME   = "TB_PERSONAGEM";

    public static final String NOME      = "NOME";
    public static final String LINK      = "LINK";
    public static final String LATITUDE  = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";

    public PersonagemDB(Context context) {
        super(context);
    }

    public boolean inserir(Personagem p) {
        ContentValues values = new ContentValues();

        values.put(NOME, p.nome);
        values.put(LINK, p.link);

        boolean retorno = database.insert(TB_NAME, null, values) > 0;

        return retorno;
    }

    public List<Personagem> listar() {
        List<Personagem> retorno = new ArrayList<>();
        Cursor cursor = database.query(TB_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Personagem p = new Personagem();

            p.nome = cursor.getString(cursor.getColumnIndex(NOME));
            p.link = cursor.getString(cursor.getColumnIndex(LINK));

            retorno.add(p);
        }

        fecharCursor(cursor);

        return retorno;
    }
}