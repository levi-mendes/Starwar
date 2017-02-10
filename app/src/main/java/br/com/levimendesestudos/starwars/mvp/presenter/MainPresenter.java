package br.com.levimendesestudos.starwars.mvp.presenter;

import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.ResponseBody;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import br.com.levimendesestudos.starwars.R;
import br.com.levimendesestudos.starwars.api.Swapi;
import br.com.levimendesestudos.starwars.api.TmdbApi;
import br.com.levimendesestudos.starwars.api.TmdbImageApi;
import br.com.levimendesestudos.starwars.dagger.DaggerInjector;
import br.com.levimendesestudos.starwars.deserializers.IdFilmeDeserializer;
import br.com.levimendesestudos.starwars.model.Filme;
import br.com.levimendesestudos.starwars.model.Personagem;
import br.com.levimendesestudos.starwars.model.db.FilmeDB;
import br.com.levimendesestudos.starwars.model.db.PersonagemDB;
import br.com.levimendesestudos.starwars.mvp.contracts.MainMvp;
import br.com.levimendesestudos.starwars.api.SwapiFactory;
import br.com.levimendesestudos.starwars.utils.FileUtil;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
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
    @Inject
    TmdbApi mTmdbApi;
    @Inject
    TmdbImageApi mTmdbImageApi;
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

        SwapiFactory.providesSwapi(url).people()
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

                        buscarIdFilme(p.urlFilmes);
                    }
                });
    }

    @Override
    public void buscarIdFilme(List<String> urls) {
            /*
    Observable<List<Parecer>> obs = Observable.create(new Observable.OnSubscribe<List<Parecer>>() {
        @Override
        public void call(Subscriber<? super List<Parecer>> subscriber) {
            mView.showProgressBar();

            List<Parecer> retorno = mParecerDB.listarFinalizados(mView.usuario().usuario);
            List<Parecer> sortedSolicitacoes = new ArrayList<>(retorno);

            Collections.sort(sortedSolicitacoes, (soli1, soli2) -> Integer.valueOf(soli1.idSolicitacao).compareTo(soli2.idSolicitacao));
            subscriber.onNext(sortedSolicitacoes);
            subscriber.onCompleted();
        }
    });
    */

        Gson gson = new GsonBuilder().registerTypeAdapter(Integer.class, new IdFilmeDeserializer()).create();

        for (String url : urls) {

            Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

            retrofit.create(TmdbApi.class)
                .buscaId()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e("onCompleted", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onCompleted", e.getMessage(), e);
                    }

                    @Override
                    public void onNext(Integer id) {
                        String s = "levi";
                        Log.e("onCompleted", s+ "  id: " + id);
                    }
                });
        }
    }



    private String year(String s) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(s);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            return String.valueOf(cal.get(Calendar.YEAR));

        } catch (ParseException e) {
            Log.e("year", e.getMessage(), e);
        }

        return "";
    }

    private void pesquisarDetalhesFilme(Filme f) {
        Map<String, String> params = new HashMap<>();

        params.put("language",      "en-US");
        params.put("api_key",       TmdbApi.API_KEY);
        params.put("query",         f.title);
        params.put("page",          "1");
        params.put("include_adult", "false");
        params.put("year",          year(f.releaseDate));//1977-05-25

        /*
        mTmdbApi.search(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onCompleted() {
                        Log.e("onCompleted", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.getMessage(), e);
                    }

                    @Override
                    public void onNext(Integer id) {
                        String absolutePath = Environment.getExternalStorageDirectory().getPath() + File.separator + f.posterName;
                        getRetrofitImage(f.posterName, absolutePath);
                        f.pathFile = absolutePath;
                        mFilmeDB.inserir(f);
                    }
                });
                */
    }



    private void getRetrofitImage(String image, String absolutePath) {
        mView.showTvStatus(R.string.buscando_poster);

        Call<ResponseBody> call = mTmdbImageApi.downloadFileWithDynamicUrlAsync(image);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try {
                    FileUtil.writeResponseBodyToDisk(response.body(), absolutePath);

                } catch (Exception e) {
                    Log.e("onResponse", e.getMessage(), e);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("onFailure", t.toString(), t);
            }
        });
    }

}