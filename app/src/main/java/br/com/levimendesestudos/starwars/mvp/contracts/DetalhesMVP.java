package br.com.levimendesestudos.starwars.mvp.contracts;

import br.com.levimendesestudos.starwars.model.Filme;

/**
 * Created by 809778 on 07/02/2017.
 */

public interface DetalhesMVP {

    interface View {
        void preencherCampos();
        void adicionarLinkFilmes();
        void callPoster(Filme f);
    }

    interface Presenter {
        void init();
        void poster(String url);
    }
}
