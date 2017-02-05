package br.com.levimendesestudos.starwars.mvp.presenter;

import android.content.pm.PackageManager;
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
import br.com.levimendesestudos.starwars.model.Filme;
import br.com.levimendesestudos.starwars.model.Personagem;
import br.com.levimendesestudos.starwars.model.db.FilmeDB;
import br.com.levimendesestudos.starwars.model.db.PersonagemDB;
import br.com.levimendesestudos.starwars.mvp.contracts.MainMvp;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static br.com.levimendesestudos.starwars.mvp.contracts.MainMvp.View.REQUEST_ACCES_FINE_LOCATION_PERMISSION;
import static br.com.levimendesestudos.starwars.mvp.contracts.MainMvp.View.REQUEST_CAMERA_PERMISSION;
import static br.com.levimendesestudos.starwars.mvp.contracts.MainMvp.View.REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION;

/**
 * Created by 809778 on 02/02/2017.
 */

public class MainPresenter implements MainMvp.Presenter {

    private MainMvp.View mView;
    @Inject
    PersonagemDB mPersonagemDB;
    @Inject
    FilmeDB mFilmeDB;

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

        checkPermissions();
    }

    @Override
    public boolean checkPermissions() {
        if (!mView.accessFineLocation()) {
            mView.requesAccessFineLocation();
            return false;
        }

        if (!mView.writeExternalStorage()) {
            mView.requesWriteExternalStorage();
            return false;
        }

        if (!mView.camera()) {
            mView.requesCamera();
            return false;
        }

        return true;
    }

    @Override
    public void itemSelected(int itemId) {
        if (itemId == R.id.itemLerQRCode) {
            mView.callCameraActivity();
        }
    }

    private boolean granted(int[] grantResults) {
        return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestPermissionsResult(int requestCode, int[] grantResults) {
        switch (requestCode) {

            case REQUEST_ACCES_FINE_LOCATION_PERMISSION:

                if (granted(grantResults)) {
                    checkPermissions();

                } else {
                    mView.showToast(R.string.permissao_para_obtencao_da_localizacao_foi_negada);
                    mView.finalizar();
                }

                break;

            case REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION:

                if (granted(grantResults)) {
                    checkPermissions();

                } else {
                    mView.showToast(R.string.permissao_para_acessar_o_sdcard_foi_negada);
                    mView.finalizar();
                }

                break;

            case REQUEST_CAMERA_PERMISSION:

                if (granted(grantResults)) {
                    checkPermissions();

                } else {
                    mView.showToast(R.string.permissao_para_acessar_a_camera_foi_negada);
                    mView.finalizar();
                }

                break;

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
                        if (mPersonagemDB.jaExiste(p)) {
                            mView.showToast(R.string.personagem_ja_existe);
                        }

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


                for (Filme f : p.films) {
                    mFilmeDB.inserir(f);
                }

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