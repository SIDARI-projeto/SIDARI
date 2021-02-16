package com.example.firebase_teste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btn, btn2;
    private EditText txt_nome, txt_idade, txt_altura, txt_numero;
    private DatabaseReference reff;
    private Member member;
    private long contador=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn2 = findViewById(R.id.pag2);
        btn = findViewById(R.id.button);
        txt_nome = findViewById(R.id.txt_nome);
        txt_idade = findViewById(R.id.txt_idade);
        txt_altura = findViewById(R.id.txt_altura);
        txt_numero = findViewById(R.id.txt_numero);
        member = new Member(); //criar um novo objeto apartir do construjtor de objetos member
        reff = FirebaseDatabase.getInstance().getReference().child("Dados_pessoais");//referencia a uma child criada no firebase "member"
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idade = Integer.parseInt(txt_idade.getText().toString().trim()); //pegar no texto e tranformar em inteiro
                int altura = Integer.parseInt(txt_altura.getText().toString().trim());
                int numero = Integer.parseInt(txt_numero.getText().toString().trim());
                member.setNome(txt_nome.getText().toString().trim());
                member.setAltura(altura);
                member.setIdade(idade);
                member.setNumero(numero);
                reff.child(member.getNome()).setValue(member);//gera a child member no firebase
                Toast.makeText(MainActivity.this, "data insert com sucesso", Toast.LENGTH_LONG).show();//mostra o aviso
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), screen_2.class);
                i.putExtra("nome", member.getNome());
                startActivity(i);
                finish();

            }
        });
    }


}