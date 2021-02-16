package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<User> Userlist;
    FirebaseRecyclerAdapter<Sessao, MyViewHolder> TestAdapter;
    private Sessao[]  sessao;
    private DatabaseReference reff;
    private long cont = 0;
    private RecyclerView historico;
    private Spinner spinner;
    private boolean firstspinner = false;
    private String[] ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//Spinner
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.ordenacao, android.R.layout.simple_spinner_item); //Array do strings.xml
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        reff = FirebaseDatabase.getInstance().getReference().child("Dados").child("huguinho"); //Referencia das sessoes no firebase->dados->huguinho


        //recycler adapter para firebase
        Query query = reff;
        FirebaseRecyclerOptions<Sessao> mOptions = new FirebaseRecyclerOptions.Builder<Sessao>()
                .setQuery(query, Sessao.class)
                .build();

        if (TestAdapter == null) {

            TestAdapter = new FirebaseRecyclerAdapter<Sessao, MyViewHolder>(mOptions) {
                @NonNull
                @Override
                public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View itemsview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list1_items, viewGroup, false);
                    return new MyViewHolder(itemsview);
                }

                @Override
                protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Sessao model) {
                    holder.data_txt.setText(new StringBuilder().append("data: ").append(sessao[position].getData()).toString());
                    holder.duracao_txt.setText(new StringBuilder().append("duracao: ").append(sessao[position].getDuracao()).toString());
                    holder.valor_txt.setText(new StringBuilder().append("valor: ").append(sessao[position].getValor()).toString());
                    holder.perigo.setVisibility(View.GONE);
                    holder.perigo_txt.setVisibility(View.GONE);
                    holder.fugas.setVisibility(View.GONE);
                    holder.fugas_txt.setVisibility(View.GONE);
                    if(sessao[position].getFugas() != 0){
                        holder.fugas_txt.setVisibility(View.VISIBLE);
                        holder.fugas.setVisibility(View.VISIBLE);
                        holder.fugas_txt.setText(new StringBuilder().append("Aviso: Poderá ter estado exposto a ").append(sessao[position].getFugas()).append(" fugas de radiação").toString());
                    }
                    if(sessao[position].getPerigo() != 0){
                        holder.perigo_txt.setVisibility(View.VISIBLE);
                        holder.perigo.setVisibility(View.VISIBLE);
                        holder.perigo_txt.setText(new StringBuilder().append("Perigo: Ultrapassou o limite de radiação!"));
                    }
                    holder.apagar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String id_sessao = ID[position];//id do item escolhido
                            DatabaseReference eliminar = FirebaseDatabase.getInstance().getReference().child("Dados").child("huguinho").child(id_sessao);
                            eliminar.removeValue();
                            //TestAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };
        }

        //buscra os valores a base de dados

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cont = snapshot.getChildrenCount();
                sessao = new Sessao[(int) cont];
                ID = new String[(int) cont];
                /*
                for(int i = 0; i<cont ; i++){
                    Long data = (long) snapshot.child(String.valueOf(i)).child("data").getValue();
                    Long duracao = (long) snapshot.child(String.valueOf(i)).child("duracao").getValue();
                    Long rad = (long) snapshot.child(String.valueOf(i)).child("rad").getValue();
                    Long perigo = (long) snapshot.child(String.valueOf(i)).child("perigo").getValue();
                    Long fugas = (long) snapshot.child(String.valueOf(i)).child("fugas").getValue();
                    sessao[i] = new Sessao(data, duracao, rad, perigo, fugas, (long) i);
               }
                 */

                int i = 0;
                for (DataSnapshot dados : snapshot.getChildren()){
                    long data = (long) dados.child("data").getValue();
                    System.out.println("dados: "+ data);
                    long duracao = (long) dados.child("duracao").getValue();
                    long rad = (long) dados.child("rad").getValue();
                    long perigo = (long) dados.child("perigo").getValue();
                    long fugas = (long) dados.child("fugas").getValue();
                    sessao[i] = new Sessao(data, duracao, rad, perigo, fugas);
                    System.out.println("dados(2): "+ sessao[i].getData());
                    String IDent = dados.getKey();
                    ID[i] = IDent;
                    i++;
                }


                //so incia o spinner quando os valores tiverem sido recebidos
                if(firstspinner == false){
                    firstspinner = true;
                }
                sortspinner(spinner.getSelectedItem().toString()); //vai ordenar os valores pela string default
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//quando um valor for escolhido vai chamar a ordenação pela opçáo escolhida
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selecao = parent.getItemAtPosition(position).toString();
                sortspinner(selecao);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String selecao = parent.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                sortspinner(selecao);
            }
        });


        //historico.
        historico = findViewById(R.id.recycleview);
        historico.setAdapter(TestAdapter);
        historico.setLayoutManager(new LinearLayoutManager(this));

    }
//ordenaçáo dos valores pela opção escolhida
    private void sortspinner (String selecao){
        if(sessao == null) return;
        switch (selecao){
            case "Data(mais antigo)":
                for(int pass = 1; pass < sessao.length; pass++){
                    for(int i = 0; i < (sessao.length-1); i++) {
                        if ((sessao[i].getData()) >sessao[i+1].getData()){
                            long[] hold = {sessao[i].getData(), sessao[i].getDuracao(), sessao[i].getValor(), sessao[i].getPerigo(), sessao[i].getFugas()};
                            String hold2 = ID[i];
                            sessao[i].setData(sessao[i+1].getData());
                            sessao[i].setDuracao(sessao[i+1].getDuracao());
                            sessao[i].setValor(sessao[i+1].getValor());
                            sessao[i].setPerigo(sessao[i+1].getPerigo());
                            sessao[i].setFugas(sessao[i+1].getFugas());
                            ID[i] = ID[i+1];
                            //sessao[i].setID(sessao[i+1].getID());
                            sessao[i+1].setData(hold[0]);
                            sessao[i+1].setDuracao(hold[1]);
                            sessao[i+1].setValor(hold[2]);
                            sessao[i+1].setPerigo(hold[3]);
                            sessao[i+1].setFugas(hold[4]);
                            //sessao[i+1].setID(hold[5]);
                            ID[i+1] = hold2;
                        }
                    }
                }
                break;
            case "Data(mais recente)":
                for(int pass = 1; pass < sessao.length; pass++){
                    for(int i = 0; i < (sessao.length-1); i++) {
                        if (sessao[i].getData()<sessao[i+1].getData()){
                            long[] hold = {sessao[i].getData(), sessao[i].getDuracao(), sessao[i].getValor(), sessao[i].getPerigo(), sessao[i].getFugas()};
                            String hold2 = ID[i];
                            sessao[i].setData(sessao[i+1].getData());
                            sessao[i].setDuracao(sessao[i+1].getDuracao());
                            sessao[i].setValor(sessao[i+1].getValor());
                            sessao[i].setPerigo(sessao[i+1].getPerigo());
                            sessao[i].setFugas(sessao[i+1].getFugas());
                            ID[i] = ID[i+1];
                            //sessao[i].setID(sessao[i+1].getID());
                            sessao[i+1].setData(hold[0]);
                            sessao[i+1].setDuracao(hold[1]);
                            sessao[i+1].setValor(hold[2]);
                            sessao[i+1].setPerigo(hold[3]);
                            sessao[i+1].setFugas(hold[4]);
                            //sessao[i+1].setID(hold[5]);
                            ID[i+1] = hold2;
                        }
                    }
                }
                break;
            case "Valor(maior)":
                for(int pass = 1; pass < sessao.length; pass++){
                    for(int i = 0; i < (sessao.length-1); i++) {
                        if (sessao[i].getValor()<sessao[i+1].getValor()){
                            long[] hold = {sessao[i].getData(), sessao[i].getDuracao(), sessao[i].getValor(), sessao[i].getPerigo(), sessao[i].getFugas()};
                            String hold2 = ID[i];
                            sessao[i].setData(sessao[i+1].getData());
                            sessao[i].setDuracao(sessao[i+1].getDuracao());
                            sessao[i].setValor(sessao[i+1].getValor());
                            sessao[i].setPerigo(sessao[i+1].getPerigo());
                            sessao[i].setFugas(sessao[i+1].getFugas());
                            ID[i] = ID[i+1];
                            //sessao[i].setID(sessao[i+1].getID());
                            sessao[i+1].setData(hold[0]);
                            sessao[i+1].setDuracao(hold[1]);
                            sessao[i+1].setValor(hold[2]);
                            sessao[i+1].setPerigo(hold[3]);
                            sessao[i+1].setFugas(hold[4]);
                            //sessao[i+1].setID(hold[5]);
                            ID[i+1] = hold2;
                        }
                    }
                }
                break;
            case "Valor(menor)":
                for(int pass = 1; pass < sessao.length; pass++){
                    for(int i = 0; i < (sessao.length-1); i++) {
                        if (sessao[i].getValor()>sessao[i+1].getValor()){
                            long[] hold = {sessao[i].getData(), sessao[i].getDuracao(), sessao[i].getValor(), sessao[i].getPerigo(), sessao[i].getFugas()};
                            String hold2 = ID[i];
                            sessao[i].setData(sessao[i+1].getData());
                            sessao[i].setDuracao(sessao[i+1].getDuracao());
                            sessao[i].setValor(sessao[i+1].getValor());
                            sessao[i].setPerigo(sessao[i+1].getPerigo());
                            sessao[i].setFugas(sessao[i+1].getFugas());
                            ID[i] = ID[i+1];
                            //sessao[i].setID(sessao[i+1].getID());
                            sessao[i+1].setData(hold[0]);
                            sessao[i+1].setDuracao(hold[1]);
                            sessao[i+1].setValor(hold[2]);
                            sessao[i+1].setPerigo(hold[3]);
                            sessao[i+1].setFugas(hold[4]);
                            //sessao[i+1].setID(hold[5]);
                            ID[i+1] = hold2;
                        }
                    }
                }
                break;
        }
        TestAdapter.notifyDataSetChanged(); //notifica que os dados mudaram
    }

    @Override
    protected void onStart() {
        super.onStart();
        TestAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        TestAdapter.stopListening();
    }

//diz em que posição um item vai ficar no recycler view
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView data_txt, valor_txt, duracao_txt, perigo_txt, fugas_txt;
        private ImageButton apagar;
        private ImageView fugas, perigo;
        public MyViewHolder(final View View){
            super(View);
            data_txt = View.findViewById(R.id.data_txt); //view -> referente ao contexto
            valor_txt = View.findViewById(R.id.valor_txt);
            duracao_txt = View.findViewById(R.id.duracao_txt);
            perigo_txt = View.findViewById(R.id.perigo_txt);
            fugas_txt = View.findViewById(R.id.fugas_txt);
            apagar = View.findViewById(R.id.apagar); //imagem apagar
            fugas = View.findViewById(R.id.fugas);
            perigo = View.findViewById(R.id.perigo);
        }
    }
}