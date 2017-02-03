package br.com.levimendesestudos.starwars.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.greenrobot.eventbus.Subscribe;
import java.util.List;
import br.com.levimendesestudos.starwars.R;
import br.com.levimendesestudos.starwars.adapters.ListaPersonagensAdapter;
import br.com.levimendesestudos.starwars.model.Personagem;
import br.com.levimendesestudos.starwars.mvp.contracts.MainMvp;
import br.com.levimendesestudos.starwars.mvp.presenter.MainPresenter;
import br.com.levimendesestudos.starwars.utils.EventBusUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvp.View {

    private MainPresenter mPresenter;
    private ListaPersonagensAdapter mAdapter;

    @BindView(R.id.rvPersonagens)
    RecyclerView rvPersonagens;
    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;
    @BindView(R.id.tvListaVazia)
    TextView tvListaVazia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mPresenter = new MainPresenter(this);
        mPresenter.init();
        //mPresenter.buscarESalvar("http://swapi.co/api/people/1/");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public void carregarLista(List<Personagem> list) {
        mAdapter = new ListaPersonagensAdapter(this, list);
        rvPersonagens.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.itemSelected(item.getItemId());

        return true;
    }

    @Override
    public void callCameraActivity() {
        Intent intent = new Intent(this, QRCodeReaderActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBusUtil.register(this);
    }

    /**
     *
     * metodo chamado apos a leitura do QRCode
     *
     * @param url
     *
     */
    @Subscribe
    public void qrcode(String url) {
        //busca os dados na api pela url e salva os dados no SQLite
        mPresenter.buscarESalvar(url);
    }

    /**
     * Adiciona o novo Personagem  na lista
     * @param p
     */
    @Override
    public void adicionarItemNaLista(Personagem p) {
        mAdapter.adicionarItem(p);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unRegister(this);
    }

    @Override
    public void configureList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvPersonagens.setLayoutManager(layoutManager);
        rvPersonagens.setItemAnimator(new DefaultItemAnimator());
        //rvPersonagens.setHasFixedSize(true);
    }

    @Override
    public void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showListaVazia() {
        tvListaVazia.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void hideListaVazia() {
        tvListaVazia.setVisibility(View.GONE);
    }
}
