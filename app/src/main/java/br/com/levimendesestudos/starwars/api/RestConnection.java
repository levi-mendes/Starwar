package br.com.levimendesestudos.starwars.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import br.com.levimendesestudos.starwars.api.Swapi;
import br.com.levimendesestudos.starwars.deserializers.PersonagemDeserializer;
import br.com.levimendesestudos.starwars.model.Personagem;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by 809778 on 06/02/2017.
 */

public class RestConnection {

    private String mUrl;

    public RestConnection(String url) {
        mUrl = url;
    }

    public Swapi providesSwapi() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Personagem.class, new PersonagemDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(mUrl)
                .client(providesOkHttoClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(Swapi.class);
    }

    private OkHttpClient providesOkHttoClient() {
        //altera timeout para 30 segundos
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(30,    TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain
                        .request()
                        .newBuilder()
                        .build();

                return chain.proceed(request);
            }
        });

        return okHttpClient;
    }
}
