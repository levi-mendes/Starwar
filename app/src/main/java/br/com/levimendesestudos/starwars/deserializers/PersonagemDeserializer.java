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

            filme.url = url;

            filmes.add(filme);
        }

        retorno.films = filmes;

        return retorno;
    }

    /*
	"homeworld": "http://swapi.co/api/planets/1/",
	"films": [
		"http://swapi.co/api/films/6/",
		"http://swapi.co/api/films/3/",
		"http://swapi.co/api/films/2/",
		"http://swapi.co/api/films/1/",
		"http://swapi.co/api/films/7/"
	],
	"species": [
		"http://swapi.co/api/species/1/"
	],
	"vehicles": [
		"http://swapi.co/api/vehicles/14/",
		"http://swapi.co/api/vehicles/30/"
	],
	"starships": [
		"http://swapi.co/api/starships/12/",
		"http://swapi.co/api/starships/22/"
	],
	"created": "2014-12-09T13:50:51.644000Z",
	"edited": "2014-12-20T21:17:56.891000Z",
	"url": "http://swapi.co/api/people/1/"
     */
}