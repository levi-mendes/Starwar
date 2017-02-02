package br.com.levimendesestudos.starwars.model.db;

/**
 * Created by 809778 on 02/02/2017.
 */

public class ScriptDatabase {

    public static final String CREATE_TB_PERSONAGEM = "CREATE TABLE IF NOT EXISTS (" + PersonagemDB.TB_NAME  + " (" +
            PersonagemDB.NOME      + " TEXT PRIMARY KEY NOT NULL, " +
            PersonagemDB.LINK      + " REAL NOT NULL,             "  +
            PersonagemDB.LATITUDE  + " REAL NOT NULL,             "  +
            PersonagemDB.LONGITUDE + " REAL NOT NULL);";

    public static final String DROP_TB_PERSONAGEM = "DROP TABLE IF EXISTS " + PersonagemDB.TB_NAME;
}
