package br.com.levimendesestudos.starwars.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.levimendesestudos.starwars.R;
import br.com.levimendesestudos.starwars.model.Filme;
import br.com.levimendesestudos.starwars.model.Personagem;
import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.String.valueOf;

public class DetalhesActivity extends AppCompatActivity {

    @BindView(R.id.etId)
    EditText etId;
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
    @BindView(R.id.lvFilms)
    ListView lvFilms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        ButterKnife.bind(this);

        Personagem p = (Personagem) getIntent().getSerializableExtra("personagem");
        etName.setText(p.name);
        etId.setText(valueOf(p.id));

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, urls(p.films));
        lvFilms.setAdapter(adapter);

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

    private List<String> urls(List<Filme> objs) {
        List<String> retorno = new ArrayList<>();

        for (Filme f : objs) {
            retorno.add(f.url);
        }

        return retorno;
    }
}