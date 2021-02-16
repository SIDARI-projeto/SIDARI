package com.example.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycle_adaptar extends RecyclerView.Adapter<recycle_adaptar.MyViewHolder> {
    private Sessao[] sessao;
    private Context context;
    public recycle_adaptar(Sessao[] sessao,Context context){
        this.sessao = sessao;
        this.context = context;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView data_txt, valor_txt, duracao_txt;
        public MyViewHolder(final View View){
            super(View);
            data_txt = View.findViewById(R.id.data_txt);
            valor_txt = View.findViewById(R.id.valor_txt);
            duracao_txt = View.findViewById(R.id.duracao_txt);
        }
    }
    @NonNull
    @Override
    public recycle_adaptar.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemsview = LayoutInflater.from(context).inflate(R.layout.list1_items, parent, false);
        return new MyViewHolder(itemsview);
    }

    @Override
    public void onBindViewHolder(@NonNull recycle_adaptar.MyViewHolder holder, int position) {
        holder.data_txt.setText(new StringBuilder().append("data: ").append(sessao[position].getData()).toString());
        holder.duracao_txt.setText(new StringBuilder().append("duracao: ").append(sessao[position].getDuracao()).toString());
        holder.valor_txt.setText(new StringBuilder().append("valor: ").append(sessao[position].getValor()).toString());
    }

    @Override
    public int getItemCount() {
        return sessao.length;
    }
}
