package com.ufc.mob;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ufc.mob.model.Contate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_ALT = 2;

    private LinearLayout themeContainer;
    private Switch themeSwitch;

    private ArrayList<Contate> contateList;
    private ArrayAdapter<String> cotateAdapter;
    private ListView cotateListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set Default Background Image
        themeContainer = findViewById(R.id.themeContainer);
        themeContainer.setBackground(getDrawable(R.drawable.background2));
        // Toogle Buttom Imputs and Image BackGround Update
        themeSwitch = this.findViewById(R.id.themeSwich);
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (themeContainer != null){
                    if (!isChecked){
                        themeContainer.setBackground(getDrawable(R.drawable.background2));
                        buttonView.setText(getString(R.string.toggle_on));
                    }else{
                        themeContainer.setBackground(getDrawable(R.drawable.background1));
                        buttonView.setText(getString(R.string.toggle_off));
                    }
                }
            }
        });

//        ListView Controllers

        contateList = new ArrayList<Contate>();
        cotateAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, contateList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View item = getLayoutInflater().inflate(R.layout.item,null);
                TextView itemTitle = item.findViewById(R.id.itemTitle);
                TextView itemDecribre = item.findViewById(R.id.itemDescription);

                Contate itemContato = contateList.get(position);

                itemTitle.setText(itemContato.name);
                itemDecribre.setText(
                        "Email: "+itemContato.email+"\n"+
                        "WhatsApp: "+itemContato.whatsApp+"\n"+
                        "Idade: "+itemContato.idade+"\t\t"+"Genero: "+itemContato.genere
                );
                return item;
            }
        };
        cotateListView = findViewById(R.id.usersListView);
        cotateListView.setAdapter(cotateAdapter);

        cotateListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this,view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.updatePopup:
                                Intent intent = new Intent(MainActivity.this, ContatesActivity.class);
                                if (intent.resolveActivity(getPackageManager())!= null){
                                    Contate c1 = contateList.get(position);

                                    intent.putExtra("index",position);
                                    intent.putExtra("name",c1.name);
                                    intent.putExtra("email",c1.email);
                                    intent.putExtra("whatsApp",c1.whatsApp);
                                    intent.putExtra("idade",c1.idade);
                                    intent.putExtra("genere",c1.genere);
                                    startActivityForResult(intent,REQUEST_ALT);
                                }
                                break;
                            case R.id.deletePopup:
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        contateList.remove(position);
                                        cotateAdapter.notifyDataSetChanged();
                                        Toast.makeText(MainActivity.this,"Item Removido com sucesso!",Toast.LENGTH_LONG).show();
                                    }
                                });
                                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this,"Operação cancecelada!",Toast.LENGTH_LONG).show();;
                                    }
                                });
                                builder.setMessage(contateList.get(position).name);
                                builder.setTitle("Delete Item?");
                                builder.show();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ContatesActivity.RESULT_CANCEL){
            Toast.makeText(this,"Operação cancelada!",Toast.LENGTH_SHORT).show();
            return;
        }
        String name = data.getStringExtra("name");
        String email = data.getStringExtra("email");
        String whatsApp = data.getStringExtra("whatsApp");
        String idade = data.getStringExtra("idade");
        String genere = data.getStringExtra("genere");
        Contate c1 =  new Contate(name,email,whatsApp,genere,idade);

        if (requestCode == REQUEST_ADD){
            if (contateList.size() >= 4){
                contateList.remove(0);
            }
            contateList.add(c1);
        }
        if (requestCode == REQUEST_ALT){
            int index = data.getIntExtra("index",-1);
            contateList.remove(index);
            contateList.add(c1);
        }
        cotateAdapter.notifyDataSetChanged();
        return;
    }

//  Option Menu Code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.optionRed:
                Toast.makeText(this,"Red Menu Clicked",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.optionGreen:
                Toast.makeText(this,"Green Menu Clicked",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.optionBlue:
                Toast.makeText(this,"Blue Menu Clicked",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//  Buttons Events Clicks
    public void goToContateClick(View view){
        Intent intent = new Intent(this, ContatesActivity.class);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,REQUEST_ADD);
        }
    }
    public void goToSoundPlayerOnClick(View view){
        Intent intent = new Intent(this,SoundActivity.class);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
    public void goToSoundTabsOnClick(View view){
        Intent intent = new Intent(this,TabActivity.class);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
    public void goToGridOnClick(View view){
        Intent intent = new Intent(this,GridActivity.class);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
}