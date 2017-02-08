package br.com.levimendesestudos.starwars.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import java.util.concurrent.TimeUnit;

import br.com.levimendesestudos.starwars.deserializers.FilmeDeserializer;
import br.com.levimendesestudos.starwars.model.Filme;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by 809778 on 06/02/2017.
 */

public class TmdbApiFactory {

    //https://api.themoviedb.org/3/search/movie?api_key=38bbb298e8e249fab64461f12ada6c81&language=en-US&query=a%20new%20hope&page=1&include_adult=false&year=1977


    public TmdbApi providesThemoviedbApi() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Filme.class, new FilmeDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(TmdbApi.BASE_URL)
                .client(providesOkHttoClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(TmdbApi.class);
    }

    private OkHttpClient providesOkHttoClient() {
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
