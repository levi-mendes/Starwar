package br.com.levimendesestudos.starwars.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.levimendesestudos.starwars.model.Filme;
import br.com.levimendesestudos.starwars.model.Personagem;

import static java.lang.String.valueOf;

/**
 * Created by 809778 on 02/02/2017.
 */

public class PersonagemDB extends DBGenericClass {

    public static final String TB_NAME   = "TB_PERSONAGEM";

    public static final String ID         = "ID";
    public static final String NOME       = "NOME";
    public static final String LINK       = "LINK";
    public static final String LATITUDE   = "LATITUDE";
    public static final String LONGITUDE  = "LONGITUDE";
    public static final String HEIGHT     = "HEIGHT";
    public static final String MASS       = "MASS";
    public static final String HAIR_COLOR = "HAIR_COLOR";
    public static final String SKIN_COLOR = "SKIN_COLOR";
    public static final String EYE_COLOR  = "EYE_COLOR";
    public static final String BIRTH_YEAR = "BIRTH_YEAR";
    public static final String GENDER     = "GENDER";
    public static final String CREATED    = "CREATED";
    public static final String EDITED     = "EDITED";

    private Context mContext;

    public PersonagemDB(Context context) {
        super(context);

        mContext = context;
    }

    public boolean inserir(Personagem p) {
        ContentValues values = new ContentValues();

        values.put(ID,       p.id);
        values.put(NOME,       p.name);
        values.put(LINK,       p.link);
        values.put(HEIGHT,     p.height);
        values.put(MASS,       p.mass);
        values.put(HAIR_COLOR, p.hairColor);
        values.put(SKIN_COLOR, p.skinColor);
        values.put(EYE_COLOR,  p.eyeColor);
        values.put(BIRTH_YEAR, p.birthYear);
        values.put(GENDER,     p.gender);
        values.put(CREATED,    p.created);
        values.put(EDITED,     p.edited);

        boolean retorno = database.insert(TB_NAME, null, values) > 0;

        return retorno;
    }

    public List<Personagem> listar() {
        List<Personagem> retorno = new ArrayList<>();
        Cursor cursor = database.query(TB_NAME, null, null, null, null, null, null);

        FilmeDB filmeDB = new FilmeDB(mContext);

        while (cursor.moveToNext()) {
            Personagem p = new Personagem();

            p.id        = cursor.getInt(cursor.getColumnIndex(ID));
            p.name      = cursor.getString(cursor.getColumnIndex(NOME));
            p.link      = cursor.getString(cursor.getColumnIndex(LINK));
            p.height    = cursor.getInt(cursor.getColumnIndex(HEIGHT));
            p.mass      = cursor.getInt(cursor.getColumnIndex(MASS));
            p.hairColor = cursor.getString(cursor.getColumnIndex(HAIR_COLOR));
            p.skinColor = cursor.getString(cursor.getColumnIndex(SKIN_COLOR));
            p.eyeColor  = cursor.getString(cursor.getColumnIndex(EYE_COLOR));
            p.birthYear = cursor.getString(cursor.getColumnIndex(BIRTH_YEAR));
            p.gender    = cursor.getString(cursor.getColumnIndex(GENDER));
            p.created   = cursor.getString(cursor.getColumnIndex(CREATED));
            p.edited    = cursor.getString(cursor.getColumnIndex(EDITED));

            List<Filme> films = filmeDB.listar(p.id);

            //p.films = films;

            retorno.add(p);
        }

        fecharCursor(cursor);

        return retorno;
    }

    public boolean jaExiste(Personagem p) {
        boolean retorno = false;
        String [] param = new String[]{valueOf(p.id)};
        Cursor cursor = database.query(TB_NAME, null, ID + " = ?", param, null, null, null, null);

        if (cursor.moveToFirst()) {
            retorno = true;
        }

        fecharCursor(cursor);
        return retorno;
    }
}