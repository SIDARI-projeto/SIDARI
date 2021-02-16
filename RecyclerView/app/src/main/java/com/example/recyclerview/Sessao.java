package com.example.recyclerview;

public class Sessao {
    private long data, duracao, valor, perigo, fugas;

    public Sessao(long data,long duracao, long valor, long perigo, long fugas) {

        this.data = data;
        this.duracao = duracao;
        this.valor = valor;
        this.perigo = perigo;
        this.fugas = fugas;
    }

    public Sessao() {
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public long getDuracao() {
        return duracao;
    }

    public void setDuracao(long duracao) {
        this.duracao = duracao;
    }

    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
    }

    public long getPerigo() {
        return perigo;
    }

    public void setPerigo(long perigo) {
        this.perigo = perigo;
    }

    public long getFugas() {
        return fugas;
    }

    public void setFugas(long fugas) {
        this.fugas = fugas;
    }
}
