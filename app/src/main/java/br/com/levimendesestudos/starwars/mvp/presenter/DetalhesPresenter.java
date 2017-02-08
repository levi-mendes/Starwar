package br.com.levimendesestudos.starwars.mvp.presenter;

import br.com.levimendesestudos.starwars.mvp.contracts.DetalhesMVP;

/**
 * Created by 809778 on 07/02/2017.
 */

public class DetalhesPresenter implements DetalhesMVP.Presenter {

    private DetalhesMVP.View mView;

    public DetalhesPresenter(DetalhesMVP.View view) {
        mView = view;
    }

    @Override
    public void init() {
        mView.preencherCampos();
        mView.adicionarLinkFilmes();
    }

    @Override
    public void poster(String url) {

    }
}