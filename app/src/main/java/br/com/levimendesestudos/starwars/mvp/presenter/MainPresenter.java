package br.com.levimendesestudos.starwars.mvp.presenter;

import br.com.levimendesestudos.starwars.mvp.contracts.MainMvp;

/**
 * Created by 809778 on 02/02/2017.
 */

public class MainPresenter implements MainMvp.Presenter {

    private MainMvp.View mView;

    public MainPresenter(MainMvp.View view) {
        mView = view;
    }

    @Override
    public void init() {

    }
}
