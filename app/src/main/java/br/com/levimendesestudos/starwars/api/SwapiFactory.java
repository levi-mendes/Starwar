package br.com.levimendesestudos.starwars.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import java.util.concurrent.TimeUnit;
import br.com.levimendesestudos.starwars.deserializers.PersonagemDeserializer;
import br.com.levimendesestudos.starwars.model.Personagem;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by 809778 on 06/02/2017.
 */

public class SwapiFactory {

    public static Swapi providesSwapi(String url) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Personagem.class, new PersonagemDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .client(providesOkHttoClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(Swapi.class);
    }

    private static OkHttpClient providesOkHttoClient() {
        //altera timeout para 30 segundos
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(30,    TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.networkInterceptors().add(chain -> {

            Request request = chain
                    .request()
                    .newBuilder()
                    .build();

            return chain.proceed(request);
        });

        return okHttpClient;
    }
}