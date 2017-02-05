package br.com.levimendesestudos.starwars.model.db;

/**
 * Created by 809778 on 02/02/2017.
 */

public class ScriptDatabase {

    public static final String CREATE_TB_PERSONAGEM = "CREATE TABLE IF NOT EXISTS " + PersonagemDB.TB_NAME  + " (" +
            PersonagemDB.ID         + " INT PRIMARY KEY NOT NULL, " +
            PersonagemDB.NOME       + " TEXT NOT NULL, "  +
            PersonagemDB.LINK       + " REAL NOT NULL, "  +
            PersonagemDB.HEIGHT     + " INT NULL, "  +
            PersonagemDB.MASS       + " INT NULL, "  +
            PersonagemDB.HAIR_COLOR + " TEXT NULL, " +
            PersonagemDB.SKIN_COLOR + " TEXT NULL, " +
            PersonagemDB.EYE_COLOR  + " TEXT NULL, " +
            PersonagemDB.BIRTH_YEAR + " TEXT NULL, " +
            PersonagemDB.GENDER     + " TEXT NULL, " +
            PersonagemDB.CREATED    + " TEXT NULL, " +
            PersonagemDB.EDITED     + " TEXT NULL, " +
            PersonagemDB.LATITUDE   + " REAL NULL,"  +
            PersonagemDB.LONGITUDE  + " REAL NULL);";

    public static final String CREATE_TB_FILME = "CREATE TABLE IF NOT EXISTS " + FilmeDB.TB_NAME  + " (" +
            FilmeDB.ID_PERSONAGEM  + " INT NOT NULL, " +
            FilmeDB.URL            + " TEXT NOT NULL);";

    public static final String DROP_TB_PERSONAGEM = "DROP TABLE IF EXISTS " + PersonagemDB.TB_NAME;
    public static final String DROP_TB_FILME      = "DROP TABLE IF EXISTS " + FilmeDB.TB_NAME;
}
