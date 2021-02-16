package com.example.receber_firebase;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private DatabaseReference reff;
private long cont = 0;
private ArrayList<String> lista;
private TextView txt1, txt2, txt3, txt4;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        reff = FirebaseDatabase.getInstance().getReference().child("Dados").child("huguinho");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int n = 0;
                txt1.setText(snapshot.child("Sessao " + n).child("data").getValue().toString());
                txt2.setText(snapshot.child("Sessao " + n).child("duracao").getValue().toString());
                txt3.setText(snapshot.child("Sessao " + n).child("rad").getValue().toString());
             //   txt4.setText(snapshot.child("Sessao " + (n+3)).getValue().toString());


/*
                cont = snapshot.getChildrenCount();
                int n = (int)cont;
                for (int i = 0; i<n; i++){
                    lista.add(snapshot.child("Sessao " + i).getValue().toString());
                }

 */
         //       lista.add(snapshot.child("Sessao 0").getValue().toString());
           //     lista.add(snapshot.child("Sessao 1").getValue().toString());
             //   lista.add(snapshot.child("Sessao 2").getValue().toString());
               // lista.add(snapshot.child("Sessao 3").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
 //       txt1.setText(lista.get(0));
   //     txt2.setText(lista.get(1));
     //   txt3.setText(lista.get(2));
       // txt4.setText(lista.get(3));
    }
}