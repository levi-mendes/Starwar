package br.com.levimendesestudos.starwars.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import br.com.levimendesestudos.starwars.R;
import br.com.levimendesestudos.starwars.model.Filme;
import br.com.levimendesestudos.starwars.model.Personagem;
import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.valueOf;

public class DetalhesActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        ButterKnife.bind(this);

        Personagem p = (Personagem) getIntent().getSerializableExtra("personagem");
        etName.setText(p.name);
        etId.setText(valueOf(p.id));

        adicionarFilmes(p.films);

        etHeight.setText(valueOf(p.height));
        etMass.setText(valueOf(p.mass));
        etHairColor.setText(p.hairColor);
        etSkinColor.setText(p.skinColor);
        etEyeColor.setText(p.eyeColor);
        etBirthYear.setText(p.birthYear);
        etGender.setText(p.gender);
        etCreated.setText(p.created);
        etEdited.setText(p.edited);
    }

    private void adicionarFilmes(List<Filme> objs) {
        for (Filme f : objs) {
            TextView tvFilme = new TextView(this);

            tvFilme.setTextSize(20f);
            tvFilme.setPadding(15, 15, 15, 0);

            tvFilme.setOnClickListener(view -> callPoster(f));
            //tvFilme.setBackground(android.R.attr.selectableItemBackground);
            tvFilme.setText(f.url);
            llFilmes.addView(tvFilme);
        }
    }

    private void callPoster(Filme f) {
        Intent intent = new Intent(this, PosterFilmActivity.class);
        intent.putExtra("filme", f);
        startActivity(intent);
    }
}