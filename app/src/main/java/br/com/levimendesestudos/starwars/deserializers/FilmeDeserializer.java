package br.com.levimendesestudos.starwars.deserializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import br.com.levimendesestudos.starwars.model.Filme;

/**
 * Created by 809778 on 03/02/2016.
 */

public class FilmeDeserializer implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Filme retorno = new Filme();
        JsonObject root = json.getAsJsonObject();

        JsonArray films = root.getAsJsonArray("results");

        JsonElement element = films.get(0);

        int id            = element.getAsJsonObject().get("id").getAsInt();
        String posterPath = element.getAsJsonObject().get("poster_path").getAsString();
        String overview   = element.getAsJsonObject().get("overview").getAsString();

        retorno.id         = id;
        retorno.posterPath = posterPath.replace("/", "");
        retorno.overview   = overview;

        return retorno;
    }
}