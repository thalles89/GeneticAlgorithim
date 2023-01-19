package algoritmo.model;

public class Produto {
    private String nome;
    private Double valor;
    private Double volume;

    /**
     * @param nome   (nome do produto)
     * @param valor  (Preço da unidade)
     * @param volume (medido em metros cubicos)
     */
    public Produto(String nome, Double valor, Double volume) {
        super();
        this.nome = nome;
        this.valor = valor;
        this.volume = volume;
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
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

}

