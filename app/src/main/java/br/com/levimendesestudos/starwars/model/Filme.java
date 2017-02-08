package br.com.levimendesestudos.starwars.model;

import java.io.Serializable;

/**
 * Created by 809778 on 03/02/2017.
 */

public class Filme implements Serializable {

    public int idPersonagem;
    public String url;
    public String titulo;
    public String posterPath;
    public String overview;
    public int id;
    public String pathFile;

    @Override
    public String toString() {
        return "titulo";
    }
}
