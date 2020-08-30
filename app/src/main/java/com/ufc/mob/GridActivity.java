package com.ufc.mob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

public class GridActivity extends AppCompatActivity {
    private GridView grid;
    private LinearLayout gridBk;
    String gridItems[] = {"Azul","Vermelho","Verde","Amarelo"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        grid = findViewById(R.id.grid);
        final LinearLayout gridBk = findViewById(R.id.gridbkg);
        ArrayAdapter<String> gridAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,gridItems);
        grid.setAdapter(gridAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        gridBk.setBackgroundColor(getResources().getColor(R.color.update));
                        break;
                    case 1:
                        gridBk.setBackgroundColor(getResources().getColor(R.color.delete));
                        break;
                    case 2:
                        gridBk.setBackgroundColor(getResources().getColor(R.color.append));
                        break;
                    case 3:
                        gridBk.setBackgroundColor(getResources().getColor(R.color.warning));
                        break;
                }
            }
        });
    }
}