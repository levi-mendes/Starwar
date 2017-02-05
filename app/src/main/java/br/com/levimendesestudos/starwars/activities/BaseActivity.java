package br.com.levimendesestudos.starwars.activities;

import android.support.v7.app.AppCompatActivity;

import br.com.levimendesestudos.starwars.mvp.contracts.BasicView;
import br.com.levimendesestudos.starwars.utils.ToastUtil;

/**
 * Created by 809778 on 02/02/2017.
 */

public class BaseActivity extends AppCompatActivity implements BasicView {

    @Override
    public void showToast(int id) {
        ToastUtil.showShort(this, id);
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showShort(this, msg);
    }
}