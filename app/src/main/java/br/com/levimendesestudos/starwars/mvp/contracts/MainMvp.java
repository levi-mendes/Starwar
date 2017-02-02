package br.com.levimendesestudos.starwars.mvp.contracts;

/**
 * Created by 809778 on 02/02/2017.
 */

public interface MainMvp {

    interface View {
        void configureList();
        void callCameraActivity();
    }

    interface Presenter {
        void init();
        void itemSelected(int itemId);
    }
}
