package br.com.levimendesestudos.starwars.mvp.contracts;

import java.util.List;

import br.com.levimendesestudos.starwars.model.Personagem;

/**
 * Created by 809778 on 02/02/2017.
 */

public interface MainMvp {

    interface View {
        void configureList();
        void callCameraActivity();
        void carregarLista(List<Personagem> list);
        void adicionarItemNaLista(Personagem p);
        void showLoading();
        void showListaVazia();
        void hideLoading();
        void hideListaVazia();
    }

    interface Presenter {
        void init();
        void itemSelected(int itemId);
        void buscarESalvar(String url);
    }
}
