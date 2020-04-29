package ru.ipimenov.myviolets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.ipimenov.myviolets.data.Violet;
import ru.ipimenov.myviolets.utils.ContentUtils;
import ru.ipimenov.myviolets.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private Switch switchCatalog;
    private TextView textViewRuUaSelection;
    private TextView textViewForeignSelection;
    private RecyclerView recyclerViewThumbnails;
    private VioletAdapter violetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                for (Violet violet : violetAdapter.getViolets()) {
                    Log.i("My", violet.getVioletCounterId() + "");
                }
            }
        });
        switchCatalog.setChecked(false);
        violetAdapter.setOnVioletThumbnailClickListener(new VioletAdapter.OnVioletThumbnailClickListener() {
            @Override
            public void onVioletThumbnailClick(int position) {
                Toast.makeText(MainActivity.this, "Позиция" + position, Toast.LENGTH_SHORT).show();
            }
        });
        violetAdapter.setOnReachEndListener(new VioletAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                Toast.makeText(MainActivity.this, "Конец списка", Toast.LENGTH_SHORT).show();
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
        String content = NetworkUtils.getContentFromNetwork(catalog, 2);
        ArrayList<Violet> violets = ContentUtils.getVioletsFromContent(content);
        violetAdapter.setViolets(violets);
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
