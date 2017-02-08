package br.com.levimendesestudos.starwars.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.levimendesestudos.starwars.R;
import br.com.levimendesestudos.starwars.model.Filme;
import br.com.levimendesestudos.starwars.model.Personagem;
import br.com.levimendesestudos.starwars.mvp.contracts.DetalhesMVP;
import br.com.levimendesestudos.starwars.mvp.presenter.DetalhesPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.valueOf;

public class DetalhesActivity extends BaseActivity implements DetalhesMVP.View {

    @BindView(R.id.etId)
    TextInputEditText etId;
    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.etHeight)
    TextInputEditText etHeight;
    @BindView(R.id.etMassa)
    TextInputEditText etMass;
    @BindView(R.id.etHairColor)
    TextInputEditText etHairColor;
    @BindView(R.id.etSkinColor)
    TextInputEditText etSkinColor;
    @BindView(R.id.etEyeColor)
    TextInputEditText etEyeColor;
    @BindView(R.id.etBirthYear)
    TextInputEditText etBirthYear;
    @BindView(R.id.etGender)
    TextInputEditText etGender;
    @BindView(R.id.etCreated)
    TextInputEditText etCreated;
    @BindView(R.id.etEdited)
    TextInputEditText etEdited;
    @BindView(R.id.llFilmes)
    LinearLayout llFilmes;

    private DetalhesMVP.Presenter mPresenter;
    private Personagem mPersonagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        ButterKnife.bind(this);

        mPersonagem = (Personagem) getIntent().getSerializableExtra("personagem");

        mPresenter = new DetalhesPresenter(this);
        mPresenter.init();
    }

    @Override
    public void preencherCampos() {
        etName.setText(mPersonagem.name);
        etId.setText(valueOf(mPersonagem.id));

        adicionarLinkFilmes();

        etHeight.setText(valueOf(mPersonagem.height));
        etMass.setText(valueOf(mPersonagem.mass));
        etHairColor.setText(mPersonagem.hairColor);
        etSkinColor.setText(mPersonagem.skinColor);
        etEyeColor.setText(mPersonagem.eyeColor);
        etBirthYear.setText(mPersonagem.birthYear);
        etGender.setText(mPersonagem.gender);
        etCreated.setText(mPersonagem.created);
        etEdited.setText(mPersonagem.edited);
    }

    @Override
    public void adicionarLinkFilmes() {
        for (String url : mPersonagem.urlFilmes) {
            TextView tvFilme = new TextView(this);

            tvFilme.setTextSize(20f);
            tvFilme.setPadding(15, 15, 15, 0);

            tvFilme.setOnClickListener(view -> mPresenter.poster(url));
            //tvFilme.setBackground(android.R.attr.selectableItemBackground);
            tvFilme.setText(url);
            llFilmes.addView(tvFilme);
        }
    }

    @Override
    public void callPoster(Filme f) {
        Intent intent = new Intent(this, PosterFilmActivity.class);
        intent.putExtra("filme", f);
        startActivity(intent);
    }
}