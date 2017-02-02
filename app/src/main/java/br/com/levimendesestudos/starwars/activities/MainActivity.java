package br.com.levimendesestudos.starwars.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

import br.com.levimendesestudos.starwars.R;
import br.com.levimendesestudos.starwars.mvp.contracts.MainMvp;
import br.com.levimendesestudos.starwars.mvp.presenter.MainPresenter;
import br.com.levimendesestudos.starwars.utils.EventBusUtil;
import br.com.levimendesestudos.starwars.utils.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvp.View {

    private MainPresenter mPresenter;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
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

    @Subscribe
    public void qrcode(String msg) {
        ToastUtil.showLong(this, msg);
        Log.e("qrcode", msg);
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
        rvPersonagens.setHasFixedSize(true);
    }
}
