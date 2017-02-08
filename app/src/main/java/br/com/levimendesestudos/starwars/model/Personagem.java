package br.com.levimendesestudos.starwars.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 809778 on 02/02/2017.
 */

public class Personagem implements Serializable {

    public int id;
    public String link;
    public String name;
    public List<String> urlFilmes;
    public int height;
    public int mass;
    public String hairColor;
    public String skinColor;
    public String eyeColor;
    public String birthYear;
    public String gender;
    public String created;
    public String edited;
}
