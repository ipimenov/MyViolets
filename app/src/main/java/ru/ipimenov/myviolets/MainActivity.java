package ru.ipimenov.myviolets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.ipimenov.myviolets.data.MainViewModel;
import ru.ipimenov.myviolets.data.Violet;
import ru.ipimenov.myviolets.utils.ContentUtils;
import ru.ipimenov.myviolets.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private Switch switchCatalog;
    private TextView textViewRuUaSelection;
    private TextView textViewForeignSelection;
    private RecyclerView recyclerViewThumbnails;
    private VioletAdapter violetAdapter;

    private MainViewModel viewModel;

//    private int id;
    private String violetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        switchCatalog = findViewById(R.id.switchCatalog);
        textViewRuUaSelection = findViewById(R.id.textViewRuUaSelection);
        textViewForeignSelection = findViewById(R.id.textViewForeignSelection);
        recyclerViewThumbnails = findViewById(R.id.recyclerViewThumbnails);
        recyclerViewThumbnails.setLayoutManager(new GridLayoutManager(this, 2));
        violetAdapter = new VioletAdapter();
        recyclerViewThumbnails.setAdapter(violetAdapter);
        switchCatalog.setChecked(true);
        switchCatalog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setCatalogOfViolet(isChecked);

                // TODO
                for (Violet violet : violetAdapter.getViolets()) {
                    Log.i("My", violet.getVioletCounterId() + "");
                }

            }
        });
        switchCatalog.setChecked(false);
        violetAdapter.setOnVioletThumbnailClickListener(new VioletAdapter.OnVioletThumbnailClickListener() {
            @Override
            public void onVioletThumbnailClick(int position) {
                Violet violet = violetAdapter.getViolets().get(position);
//                id = violet.getVioletCounterId();
                violetName = violet.getVioletName();


                // TODO
                Log.i("My", violet.getVioletImagePath() + "");

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("violetName", violetName);
                startActivity(intent);
//                Toast.makeText(MainActivity.this, "Позиция" + position, Toast.LENGTH_SHORT).show();
            }
        });
        violetAdapter.setOnReachEndListener(new VioletAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
//                Toast.makeText(MainActivity.this, "Конец списка", Toast.LENGTH_SHORT).show();
            }
        });
        LiveData<List<Violet>> violetsFromLiveData = viewModel.getViolets();
        violetsFromLiveData.observe(this, new Observer<List<Violet>>() {
            @Override
            public void onChanged(List<Violet> violets) {
                violetAdapter.setViolets(violets);
            }
        });
    }

    public void onClickSetRuUaSelection(View view) {
        setCatalogOfViolet(false);
        switchCatalog.setChecked(false);
    }

    public void onClickSetForeignSelection(View view) {
        setCatalogOfViolet(true);
        switchCatalog.setChecked(true);
    }

    private void setCatalogOfViolet(boolean isForeignSelection) {
        int catalog;
        if (isForeignSelection) {
            textViewForeignSelection.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewRuUaSelection.setTextColor(getResources().getColor(R.color.colorWhite));
            catalog = NetworkUtils.FOREIGN_SELECTION;
        } else {
            textViewForeignSelection.setTextColor(getResources().getColor(R.color.colorWhite));
            textViewRuUaSelection.setTextColor(getResources().getColor(R.color.colorAccent));
            catalog = NetworkUtils.RU_UA_SELECTION;
        }
        downloadData(catalog, 2);
    }

    private void downloadData(int catalog, int page) {
        String content = NetworkUtils.getContentFromNetwork(catalog, page);
        ArrayList<Violet> violets = ContentUtils.getVioletsFromContent(content);
        if (violets != null && !violets.isEmpty()) {
            viewModel.deleteAllViolets();
            for (Violet violet : violets) {
                viewModel.insertViolet(violet);
            }
        }
    }

//    private void setCatalogOfViolet(boolean isForeignSelection) {
//        int catalog;
//        if (isForeignSelection) {
//            textViewForeignSelection.setTextColor(getResources().getColor(R.color.colorAccent));
//            textViewRuUaSelection.setTextColor(getResources().getColor(R.color.colorWhite));
//            catalog = NetworkUtils.FOREIGN_SELECTION;
//        } else {
//            textViewForeignSelection.setTextColor(getResources().getColor(R.color.colorWhite));
//            textViewRuUaSelection.setTextColor(getResources().getColor(R.color.colorAccent));
//            catalog = NetworkUtils.RU_UA_SELECTION;
//        }
//        downloadData(catalog, 2);
//    }
}
