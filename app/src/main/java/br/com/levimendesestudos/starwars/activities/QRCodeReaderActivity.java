package br.com.levimendesestudos.starwars.activities;

import android.graphics.PointF;
import android.os.Bundle;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import org.greenrobot.eventbus.EventBus;
import br.com.levimendesestudos.starwars.R;
import br.com.levimendesestudos.starwars.mvp.contracts.QRCodeReaderMvp;
import br.com.levimendesestudos.starwars.mvp.presenter.QRCodeReaderPresenter;
import br.com.levimendesestudos.starwars.utils.EventBusUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QRCodeReaderActivity extends BaseActivity implements QRCodeReaderMvp.View, QRCodeReaderView.OnQRCodeReadListener {

    @BindView(R.id.qrdeCoderView)
    QRCodeReaderView qrdeCoderView;

    QRCodeReaderPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_reader);

        ButterKnife.bind(this);

        mPresenter = new QRCodeReaderPresenter(this);
        mPresenter.init();
    }

    @Override
    public void configurarCamera() {
        qrdeCoderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        qrdeCoderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrdeCoderView.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        qrdeCoderView.setTorchEnabled(true);

        // Use this function to set front camera preview
        qrdeCoderView.setFrontCamera();

        // Use this function to set back camera preview
        qrdeCoderView.setBackCamera();
    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed in View
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        qrdeCoderView.stopCamera();
        EventBusUtil.getInstance().post(text);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrdeCoderView.startCamera();
    }
}