package com.example.firebase_teste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class screen_2 extends AppCompatActivity {
private TextView utilizador;
private EditText rad_txt, data_txt, duracao_txt, perigo_txt, fugas_txt;
private Button btn;
private DatabaseReference reff1;
private String ut;
private long cont = 0;
private Sessao sessao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_2);
        utilizador = findViewById(R.id.Utlizador);
        rad_txt = findViewById(R.id.radia_txt);
        data_txt = findViewById(R.id.data_txt);
        duracao_txt = findViewById(R.id.duracao_txt);
        perigo_txt = findViewById(R.id.perigo);
        fugas_txt = findViewById(R.id.fugas);
        btn = findViewById(R.id.guardar);
        sessao = new Sessao();
        reff1 = FirebaseDatabase.getInstance().getReference().child("Dados");
        Intent i = getIntent();
        ut = i.getStringExtra("nome");
        utilizador.setText("Dr " + ut);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int data = Integer.parseInt(data_txt.getText().toString().trim());
                int rad = Integer.parseInt(rad_txt.getText().toString().trim());
                int duracao = Integer.parseInt(duracao_txt.getText().toString().trim());
                int perigo = Integer.parseInt(perigo_txt.getText().toString().trim());
                int fugas = Integer.parseInt(fugas_txt.getText().toString().trim());
                sessao.setData(data);
                sessao.setDuracao(duracao);
                sessao.setRad(rad);
                sessao.setFugas(fugas);
                sessao.setPerigo(perigo);
                reff1.child(ut).push().setValue(sessao);
          //      reff1.child(ut).child(Long.toString(conta(ut))).setValue(sessao);
                Toast.makeText(screen_2.this, "data insert com sucesso", Toast.LENGTH_LONG).show();
            }
        });

    }
    public long conta(String ut){
        DatabaseReference reff2;
        reff2 = FirebaseDatabase.getInstance().getReference().child("Dados").child(ut);
        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) cont = (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return cont;
    }
}

