package br.com.levimendesestudos.starwars.dagger.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import java.util.concurrent.TimeUnit;

import br.com.levimendesestudos.starwars.api.TmdbApi;
import br.com.levimendesestudos.starwars.deserializers.FilmeDeserializer;
import br.com.levimendesestudos.starwars.model.Filme;
import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by 809778 on 06/02/2017.
 */

@Module
public class TmdbApiModule {


    @Provides
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
