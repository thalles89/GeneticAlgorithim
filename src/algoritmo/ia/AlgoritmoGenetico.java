package algoritmo.ia;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlgoritmoGenetico {

    private int tamanhoPopulacao;
    private List<Individuo> populacao = new ArrayList<>();
    private int geracao;
    private Individuo melhorSolucao;
    private List<Individuo> melhoresCromossomos = new ArrayList<Individuo>();

    public AlgoritmoGenetico(int tamanhoPopulacao) {
        this.tamanhoPopulacao = tamanhoPopulacao;
    }

    public List<Individuo> getMelhoresCromossomos() {
        return melhoresCromossomos;
    }

    public void setMelhoresCromossomos(List<Individuo> melhoresCromossomos) {
        this.melhoresCromossomos = melhoresCromossomos;
    }

    public void inicializarPopulacao(List<Double> valor, List<Double> espaco, Double limiteEspaco) {
        for (int i = 0; i < tamanhoPopulacao; i++) {
            this.populacao.add(new Individuo(espaco, valor, limiteEspaco));
        }
        this.melhorSolucao = this.populacao.get(0);
    }

    public void melhorSolucao(Individuo individuo) {
        this.melhorSolucao = individuo;
    }

    public void ordenar() {
        Collections.sort(this.populacao);
    }

    public Double somaNotas() {
        Double soma = 0.0;

        for (Individuo individuo : this.populacao) {
            soma += individuo.getNotaAvaliacao();
        }
        return soma;
    }

    public int selecionaPai(Double somaAvaliacao) {

        int pai = -1;
        Double valorSorteado = Math.random() * somaAvaliacao;
        Double soma = 0.0;
        int i = 0;

        while (i < this.populacao.size() && soma < valorSorteado) {

            soma += this.populacao.get(i).getNotaAvaliacao();
            pai++;
            i++;
        }
        return pai;
    }

    public void visualizaPopulacao() {
        Individuo melhor = this.populacao.get(0);

        this.melhoresCromossomos.add(melhor);

        System.out.println("G: " + this.populacao.get(0).getGeracao() + "\nValor: " + melhor.getNotaAvaliacao()
                + "\nEspaços: " + melhor.getEspacoUsado() + "\nCromossomo: " + melhor.getCromossomo());
    }

    public List<String> resolver(Double taxaMutacao, int numeroGeracoes, List<Double> espacos, List<Double> valores,
                                 Double limiteEspacos) {

        this.inicializarPopulacao(valores, espacos, limiteEspacos);

        for (Individuo individuo : this.populacao) {
            individuo.avaliacao();
        }
        this.ordenar();
        this.visualizaPopulacao();

        for (int geracao = 0; geracao < numeroGeracoes; geracao++) {
            Double somaAvaliacao = this.somaNotas();
            List<Individuo> novaPopulacao = new ArrayList<Individuo>();

            for (int i = 0; i < this.populacao.size() / 2; i++) {
                int pai1 = this.selecionaPai(somaAvaliacao);
                int pai2 = this.selecionaPai(somaAvaliacao);

                List<Individuo> filhos = this.getPopulacao().get(pai1).crossOver(getPopulacao().get(pai2));

                novaPopulacao.add(filhos.get(0).mutacao(taxaMutacao));
                novaPopulacao.add(filhos.get(1).mutacao(taxaMutacao));
            }

            this.setPopulacao(novaPopulacao);

            for (Individuo individuo : novaPopulacao) {
                individuo.avaliacao();
            }
            this.ordenar();
            this.visualizaPopulacao();

            Individuo melhor = this.populacao.get(0);
            this.melhorIndividuo(melhor);
        }
        System.out.println("Melhor Solução: G -> " + this.melhorSolucao.getGeracao() + "\nValor: "
                + this.melhorSolucao.getNotaAvaliacao() + "\nEspaço Usado: " + this.melhorSolucao.getEspacoUsado()
                + "\nCromossomo: " + this.melhorSolucao.getCromossomo());
        return this.melhorSolucao.getCromossomo();

    }

    public void melhorIndividuo(Individuo individuo) {
        if (individuo.getNotaAvaliacao() > this.getMelhorSolucao().getNotaAvaliacao()) {
            this.melhorSolucao = individuo;
        }
    }

    public int getTamanhoPopulacao() {
        return tamanhoPopulacao;
    }

    public void setTamanhoPopulacao(int tamanhoPopulacao) {
        this.tamanhoPopulacao = tamanhoPopulacao;
    }

    public List<Individuo> getPopulacao() {
        return populacao;
    }

    public void setPopulacao(List<Individuo> populacao) {
        this.populacao = populacao;
    }

    public int getGeracao() {
        return geracao;
    }

    public void setGeracao(int geracao) {
        this.geracao = geracao;
    }

    public Individuo getMelhorSolucao() {
        return melhorSolucao;
    }

    public void setMelhorSolucao(Individuo melhorSolucao) {
        this.melhorSolucao = melhorSolucao;
    }

}