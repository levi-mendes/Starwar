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

        //TODO NAO FINALIZADO

        String url = getIntent().getStringExtra("url");

        //https://image.tmdb.org/t/p/w500/tgr5Pdy7ehZYBqBkN2K7Q02xgOb.jpg
        //https://image.tmdb.org/t/p/w500/6u1fYtxG5eqjhtCPDx04pJphQRW.jpg
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg")
                .centerCrop()
                .placeholder(R.mipmap.the_movie_db)
                .crossFade()
                .into(ivRevistaCapa);
    }

    @OnClick(R.id.ivFechar)
    public void ivFechar() {
        onBackPressed();
    }
}