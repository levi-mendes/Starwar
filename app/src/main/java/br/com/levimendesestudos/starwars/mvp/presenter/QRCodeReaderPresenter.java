package br.com.levimendesestudos.starwars.mvp.presenter;

import br.com.levimendesestudos.starwars.mvp.contracts.QRCodeReaderMvp;

/**
 * Created by 809778 on 02/02/2017.
 */

public class QRCodeReaderPresenter implements QRCodeReaderMvp.Presenter {

    private QRCodeReaderMvp.View mView;

    public QRCodeReaderPresenter(QRCodeReaderMvp.View view) {
        mView = view;
    }

    @Override
    public void init() {
        mView.configurarCamera();
    }
}
