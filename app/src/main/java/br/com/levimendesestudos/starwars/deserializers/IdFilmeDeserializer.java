package br.com.levimendesestudos.starwars.deserializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by 809778 on 03/02/2016.
 */

public class IdFilmeDeserializer implements JsonDeserializer<Object> {

    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject root = json.getAsJsonObject();

        JsonArray films = root.getAsJsonArray("results");
        JsonElement element = films.get(0);

        Integer retorno = element.getAsJsonObject().get("id").getAsInt();

        return retorno;
    }
}