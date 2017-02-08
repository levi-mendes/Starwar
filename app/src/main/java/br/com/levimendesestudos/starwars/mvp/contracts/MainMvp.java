package br.com.levimendesestudos.starwars.mvp.contracts;

import java.util.List;

import br.com.levimendesestudos.starwars.model.Personagem;

/**
 * Created by 809778 on 02/02/2017.
 */

public interface MainMvp {

    interface View extends BasicView {
        int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 16;
        int REQUEST_ACCES_FINE_LOCATION_PERMISSION    = 17;
        int REQUEST_CAMERA_PERMISSION                 = 18;

        void configureList();
        void callCameraActivity();
        void carregarLista(List<Personagem> list);
        void adicionarItemNaLista(Personagem p);
        void showLoading();
        void showTvStatus(int id);
        void hideLoading();
        void hideTvStatus();
        boolean accessFineLocation();
        boolean camera();
        boolean writeExternalStorage();
        void requesAccessFineLocation();
        void requesWriteExternalStorage();
        void requesCamera();
        void finalizar();
    }

    interface Presenter {
        void init();
        void itemSelected(int itemId);
        void buscarESalvarPersonagem(String url);
        void buscarESalvarFilmes(List<String> urls);
        boolean checkPermissions();
        void requestPermissionsResult(int requestCode, int[] grantResults);
    }
}
