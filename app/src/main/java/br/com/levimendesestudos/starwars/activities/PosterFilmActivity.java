package br.com.levimendesestudos.starwars.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import br.com.levimendesestudos.starwars.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PosterFilmActivity extends AppCompatActivity {

    @BindView(R.id.ivRevistaCapa)
    ImageView ivRevistaCapa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_film);

        ButterKnife.bind(this);

        String url = getIntent().getStringExtra("url");

        Glide.with(this)
                .load(url)
                .centerCrop()
                //.placeholder(R.drawable.loading)
                .crossFade()
                .into(ivRevistaCapa);
    }

    @OnClick(R.id.ivFechar)
    public void ivFechar() {
        onBackPressed();
    }
}
