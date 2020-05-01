package ru.ipimenov.myviolets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ru.ipimenov.myviolets.data.MainViewModel;
import ru.ipimenov.myviolets.data.Violet;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewVioletImage;
    private TextView textViewVioletName;
    private TextView textViewVioletBreeder;
    private TextView textViewVioletYear;
    private TextView textViewVioletOverview;

    private int id;
    private MainViewModel viewModel;
    private Violet violet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageViewVioletImage = findViewById(R.id.imageViewVioletImage);
        textViewVioletName = findViewById(R.id.textViewVioletName);
        textViewVioletBreeder = findViewById(R.id.textViewVioletBreeder);
        textViewVioletYear = findViewById(R.id.textViewVioletYear);
        textViewVioletOverview = findViewById(R.id.textViewVioletOverview);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        } else {
            finish();
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        violet = viewModel.getVioletById(id);

        Picasso.get().load(violet.getVioletImagePath()).into(imageViewVioletImage);
        textViewVioletName.setText(violet.getVioletName());
        textViewVioletBreeder.setText(violet.getVioletBreeder());
        textViewVioletYear.setText(violet.getVioletYear());
        textViewVioletOverview.setText(violet.getVioletOverview());
    }

    public void onClickChangeFavourite(View view) {
    }
}
