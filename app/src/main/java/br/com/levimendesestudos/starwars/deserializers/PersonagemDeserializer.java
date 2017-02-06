package br.com.levimendesestudos.starwars.deserializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import br.com.levimendesestudos.starwars.model.Filme;
import br.com.levimendesestudos.starwars.model.Personagem;

/**
 * Created by 809778 on 03/02/2016.
 */

public class PersonagemDeserializer implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Personagem retorno = new Personagem();
        List<Filme> filmes = new ArrayList<>();
        JsonObject root = json.getAsJsonObject();

        retorno.id        = id(root);
        retorno.name      = root.get("name").getAsString();
        retorno.link      = root.get("url").getAsString();
        retorno.height    = root.get("height").getAsInt();
        retorno.mass      = root.get("mass").getAsInt();
        retorno.hairColor = root.get("hair_color").getAsString();
        retorno.skinColor = root.get("skin_color").getAsString();
        retorno.eyeColor  = root.get("eye_color").getAsString();
        retorno.birthYear = root.get("birth_year").getAsString();
        retorno.gender    = root.get("gender").getAsString();
        retorno.created   = root.get("created").getAsString();
        retorno.edited    = root.get("edited").getAsString();

        JsonArray films = root.getAsJsonArray("films");

        for (JsonElement element : films) {
            String url = element.getAsString();

            Filme filme = new Filme();

            filme.idPersonagem = retorno.id;
            filme.url          = url;

            filmes.add(filme);
        }

        retorno.films = filmes;

        return retorno;
    }

    /**
     *
     * pega o ultimo caracter para usar como PK
     *
     * @param jo
     *
     * @return
     *
     */
    private int id(JsonObject jo) {
        String s = jo.get("url").getAsString();
        int id = Integer.parseInt(s.substring(s.length() - 2, s.length() - 1));

        return id;
    }
}