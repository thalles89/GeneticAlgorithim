package jgap.model;

public class Produto {

    private String nome;
    private Double valor;
    private Double espaco;

    public Produto(String nome, Double espaco, Double valor) {
        super();
        this.nome = nome;
        this.valor = valor;
        this.espaco = espaco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getVolume() {
        return espaco;
    }

    public void setVolume(Double volume) {
        this.espaco = volume;
    }
}