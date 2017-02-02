package br.com.levimendesestudos.starwars.mvp.contracts;

/**
 * Created by 809778 on 02/02/2017.
 */

public interface MainMvp {

    interface View {
        void configureList();
    }

    interface Presenter {
        void init();
    }
}
