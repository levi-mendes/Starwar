package br.com.levimendesestudos.starwars.model.db;

import android.content.Context;

/**
 * Created by 809778 on 03/02/2017.
 */

public class FilmeDB extends DBGenericClass {

    public static final String TB_NAME = "TB_FILME";

    public static final String ID  = "ID";
    public static final String URL = "URL";

    public FilmeDB(Context context) {
        super(context);
    }
}
