package br.com.levimendesestudos.starwars.mvp.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import br.com.levimendesestudos.starwars.R;
import br.com.levimendesestudos.starwars.api.Swapi;
import br.com.levimendesestudos.starwars.dagger.DaggerInjector;
import br.com.levimendesestudos.starwars.deserializers.PersonagemDeserializer;
import br.com.levimendesestudos.starwars.model.Personagem;
import br.com.levimendesestudos.starwars.model.db.PersonagemDB;
import br.com.levimendesestudos.starwars.mvp.contracts.MainMvp;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 809778 on 02/02/2017.
 */

public class MainPresenter implements MainMvp.Presenter {

    private MainMvp.View mView;
    @Inject
    PersonagemDB mPersonagemDB;
    //@Inject
    //Swapi mSwapi;

    public MainPresenter(MainMvp.View view) {
        mView = view;

        DaggerInjector.get().inject(this);
    }

    @Override
    public void init() {
        mView.configureList();
        buscarDadosNoBanco();
    }

    @Override
    public void itemSelected(int itemId) {
        if (itemId == R.id.itemLerQRCode) {
            mView.callCameraActivity();
        }
    }

    private void buscarDadosNoBanco() {
        List<Personagem> list = mPersonagemDB.listar();
        mView.carregarLista(list);
    }

    /**
     *
     * busca os dados do personagem na api pela url
     *
     * @param url
     *
     */
    @Override
    public void buscarESalvar(String url) {
        mView.showLoading();

        providesSwapi(url).people()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Personagem>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.getMessage(), e);
                    }

                    @Override
                    public void onNext(Personagem p) {
                        inserirPersonagem(p);
                        mView.adicionarItemNaLista(p);
                    }
                });
    }

    private void inserirPersonagem(final Personagem p) {
        AsyncTask<Void, Void, Void> atInserir = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mPersonagemDB.inserir(p);

                return null;
            }
        };
        atInserir.execute();
    }

    Swapi providesSwapi(String url) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Personagem.class, new PersonagemDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .client(providesOkHttoClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(Swapi.class);
    }

    public OkHttpClient providesOkHttoClient() {
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