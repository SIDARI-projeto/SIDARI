package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btn1,bt2;
    private TextView txt;
    private int n=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.contador);
        bt2 = findViewById(R.id.ecra2);
        txt = findViewById(R.id.cont_view);
/*
        while(true){
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    n++;
                    txt.setText(Integer.toString(n));
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),MainActivity2.class);
                    i.putExtra("nome", txt.getText().toString());
                    startActivity(i);
                    finish();
                }
            });

        }
        */
    }
}