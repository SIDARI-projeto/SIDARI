package com.example.teste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btn; //atribuir o tipo de variaveis
    private TextView view;
    private EditText pt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button); //atribuir a variavel ao id da cena que queremos
        view = findViewById(R.id.blabla);
        pt = findViewById(R.id.edit_text);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setText(pt.getText());
                Intent i = new Intent(v.getContext()/*funciona com this*/, screen_2.class);//como o this iria fazer referencia á class "On click", por essa razão temos  que estar no contexto do v
                i.putExtra("Nome", pt.getText().toString());
                startActivity(i);
                finish();
            }
        });
    }
}