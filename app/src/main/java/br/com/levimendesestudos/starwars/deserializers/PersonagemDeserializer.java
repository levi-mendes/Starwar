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
        retorno.nome = root.get("name").getAsString();

        JsonArray films = root.getAsJsonArray("films");

        for (JsonElement element : films) {
            String url = element.getAsString();

            Filme filme = new Filme();

            filme.url = url;

            filmes.add(filme);
        }

        retorno.filmes = filmes;

        return retorno;
    }
}