package br.com.levimendesestudos.starwars.mvp.presenter;

import android.content.pm.PackageManager;
import android.util.Log;

import com.squareup.okhttp.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import br.com.levimendesestudos.starwars.R;
import br.com.levimendesestudos.starwars.api.TmdbApi;
import br.com.levimendesestudos.starwars.api.TmdbApiFactory;
import br.com.levimendesestudos.starwars.dagger.DaggerInjector;
import br.com.levimendesestudos.starwars.model.Filme;
import br.com.levimendesestudos.starwars.model.Personagem;
import br.com.levimendesestudos.starwars.model.db.FilmeDB;
import br.com.levimendesestudos.starwars.model.db.PersonagemDB;
import br.com.levimendesestudos.starwars.mvp.contracts.MainMvp;
import br.com.levimendesestudos.starwars.api.RestConnection;
import br.com.levimendesestudos.starwars.utils.FileUtil;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static br.com.levimendesestudos.starwars.api.TmdbApi.BASE_URL_DOWNLOAD_IAMGE;
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

        if (list.size() == 0) {
            mView.showTvStatus(R.string.lista_vazia);
        }
    }

    /**
     *
     * busca os dados do personagem na api pela url
     *
     * @param url
     *
     */
    @Override
    public void buscarESalvarPersonagem(String url) {
        mView.showLoading();

        RestConnection conn = new RestConnection(url);
        conn.providesSwapi().people()
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
                        //if (mPersonagemDB.jaExiste(p)) {
                          //  mView.showToast(R.string.personagem_ja_existe);
                            //return;
                        //}

                        //mPersonagemDB.inserir(p);
                        //mView.adicionarItemNaLista(p);

                        buscarESalvarFilmes(p.urlFilmes);
                    }
                });
    }

    @Override
    public void buscarESalvarFilmes(List<String> urls) {
        Map<String, String> params = new HashMap<>();

        /*
        https://api.themoviedb.org
        /3/search/movie
                ?api_key=38bbb298e8e249fab64461f12ada6c81
                &language=
                &query=a%20new%20hope
                &page=1
                &include_adult=false
                &year=1977
        */

        params.put("language",      "en-US");
        params.put("api_key",       "38bbb298e8e249fab64461f12ada6c81");
        params.put("query",         "A%20new%20Hope");
        params.put("page",          "1");
        params.put("include_adult", "false");
        params.put("year",          "1977");

        TmdbApiFactory tmdb = new TmdbApiFactory();
        tmdb.providesThemoviedbApi()
                .search(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Filme>() {

                    @Override
                    public void onCompleted() {
                        Log.e("onCompleted", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.getMessage(), e);
                    }

                    @Override
                    public void onNext(Filme f) {
                        int a = 10;
                        Log.e("onNext", f.toString() + " a value: " + a) ;

                        //https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg

                        // TODO criar arquivo e atribuir o path ao atributo  f.pathFile

                        //getRetrofitImage("https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg");
                        getRetrofitImage(f.posterPath);
                    }
                });




        for (String url : urls) {
            //mFilmeDB.inserir(url);
        }
    }

    /*
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
     */



    private void getRetrofitImage(String image) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_DOWNLOAD_IAMGE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TmdbApi service = retrofit.create(TmdbApi.class);

        Call<ResponseBody> call = service.downloadFileWithDynamicUrlAsync(image);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {

                try {

                    Log.e("onResponse", "Response came from server");
                    boolean FileDownloaded = FileUtil.writeResponseBodyToDisk(response.body());

                    Log.e("onResponse", "Image is downloaded and saved ? " + FileDownloaded);

                } catch (Exception e) {
                    Log.e("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

}