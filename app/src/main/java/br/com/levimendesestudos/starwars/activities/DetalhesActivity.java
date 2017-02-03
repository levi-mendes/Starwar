package br.com.levimendesestudos.starwars.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import br.com.levimendesestudos.starwars.R;
import br.com.levimendesestudos.starwars.model.Personagem;
import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.valueOf;

public class DetalhesActivity extends AppCompatActivity {

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.etHeight)
    EditText etHeight;

    @BindView(R.id.etMassa)
    EditText etMass;

    @BindView(R.id.etHairColor)
    EditText etHairColor;

    @BindView(R.id.etSkinColor)
    EditText etSkinColor;

    @BindView(R.id.etEyeColor)
    EditText etEyeColor;

    @BindView(R.id.etBirthYear)
    EditText etBirthYear;

    @BindView(R.id.etGender)
    EditText etGender;

    @BindView(R.id.etCreated)
    EditText etCreated;

    @BindView(R.id.etEdited)
    EditText etEdited;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        ButterKnife.bind(this);

        Personagem p = (Personagem) getIntent().getSerializableExtra("personagem");
        etName.setText(p.name);

        //public List<Filme> films;

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
}
