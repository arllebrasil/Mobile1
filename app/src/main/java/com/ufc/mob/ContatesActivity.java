package com.ufc.mob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ContatesActivity extends AppCompatActivity {
    public static final int RESULT_OK = 1;
    public static final int RESULT_CANCEL = 2;

    private String nameContate;
    EditText nameTextEdit;

    private String email;
    private String emailList[];
    private AutoCompleteTextView emailAutoComplete;

    private String whatsApp;
    private EditText whatsEditText;

    private String genere;
    private RadioGroup radioGroupGenere;

    private String idadeList[];
    private String idade;
    private AutoCompleteTextView idateTextEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contate);

        Intent intent = getIntent();

        nameTextEdit = findViewById(R.id.nameContate);
        emailAutoComplete = findViewById(R.id.emailTextEdit);
        whatsEditText = findViewById(R.id.whatsApp);
        radioGroupGenere = findViewById(R.id.radioGroupGenere);
        idateTextEdit = findViewById(R.id.idadeSpiner);

        radioGroupGenere.clearCheck();
        radioGroupGenere.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioGenere = group.findViewById(checkedId);
                if (radioGenere != null ){
                    genere = radioGenere.getText().toString();
                }
            }
        });

        Intent reqIntent = getIntent();
        if (intent.getIntExtra("index",-1) != -1){
            nameTextEdit.setText(reqIntent.getStringExtra("name"));
            emailAutoComplete.setText(reqIntent.getStringExtra("email"));
            whatsEditText.setText(reqIntent.getStringExtra("whatsApp"));
            radioGroupGenere.check((intent.getStringExtra("genere").equals("Man"))?R.id.radioMan:R.id.radioWoman);
            idateTextEdit.setText(reqIntent.getStringExtra("idade"));
        }

        emailList = getResources().getStringArray(R.array.email_array);
        ArrayAdapter<String> emailAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,emailList);
        emailAutoComplete.setAdapter(emailAdapter);

        idadeList = getResources().getStringArray(R.array.age_array);
        ArrayAdapter<String> idadeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,idadeList);
        idateTextEdit.setAdapter(idadeAdapter);

        setResult(RESULT_CANCEL);
    }
    public void applyOnClick(View view){
        nameContate = nameTextEdit.getText().toString();
        email = emailAutoComplete.getText().toString();
        whatsApp = whatsEditText.getText().toString();
        idade = idateTextEdit.getText().toString();

        Intent resIntent = new Intent();
        resIntent.putExtra("index",getIntent().getIntExtra("index",-1));
        resIntent.putExtra("name",nameContate);
        resIntent.putExtra("email",email);
        resIntent.putExtra("whatsApp",whatsApp);
        resIntent.putExtra("idade",idade);
        resIntent.putExtra("genere",genere);

        setResult(RESULT_OK,resIntent);
        finish();
    }
    public void cancelOnClick(View view){
        finish();
    }
}