package br.com.levimendesestudos.starwars.model.db;

import android.content.Context;

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
}