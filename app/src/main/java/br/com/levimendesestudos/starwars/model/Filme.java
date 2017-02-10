package br.com.levimendesestudos.starwars.model;

import java.io.Serializable;

/**
 * Created by 809778 on 03/02/2017.
 */

public class Filme implements Serializable {

    public int idPersonagem;//Foreign Key
    public String url;
    public String title;
    public String posterName;
    public String overview;
    public int id;
    public String pathFile;
    public String homepage;
    public String releaseDate;

    @Override
    public String toString() {
        return "titulo";
    }
}
