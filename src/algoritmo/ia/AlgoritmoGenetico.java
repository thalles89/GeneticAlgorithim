package algoritmo.ia;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AlgoritmoGenetico {

    private final int tamanhoPopulacao;
    private List<Individuo> populacao = new ArrayList<>();
    private Individuo melhorSolucao;
    private List<Individuo> melhoresCromossomos = new ArrayList<>();

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


    public Double somaNotas() {
        AtomicReference<Double> soma = new AtomicReference<>(0.0);
        populacao.forEach(it-> soma.updateAndGet(v -> v + it.getNotaAvaliacao()));
        return soma.get();
    }

    public int selecionaPai(Double somaAvaliacao) {

        double soma = 0.0;
        int pai = -1;
        int i = 0;

        while (i < this.populacao.size() && soma < (Math.random() * somaAvaliacao)) {

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

        this.populacao.forEach(Individuo::avaliacao);

        Collections.sort(this.populacao);
        this.visualizaPopulacao();

        for (int geracao = 0; geracao < numeroGeracoes; geracao++) {
            Double somaAvaliacao = this.somaNotas();
            List<Individuo> novaPopulacao = new ArrayList<>();

            for (int i = 0; i < this.populacao.size() / 2; i++) {
                int pai1 = this.selecionaPai(somaAvaliacao);
                int pai2 = this.selecionaPai(somaAvaliacao);

                List<Individuo> filhos = this.getPopulacao().get(pai1).crossOver(getPopulacao().get(pai2));

                novaPopulacao.add(filhos.get(0).mutacao(taxaMutacao));
                novaPopulacao.add(filhos.get(1).mutacao(taxaMutacao));
            }

            this.setMelhoresCromossomos(novaPopulacao);
            this.setPopulacao(novaPopulacao);

            for (Individuo individuo : novaPopulacao) {
                individuo.avaliacao();
            }
            Collections.sort(this.populacao);
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

    public List<Individuo> getPopulacao() {
        return populacao;
    }

    public void setPopulacao(List<Individuo> populacao) {
        this.populacao = populacao;
    }


    public Individuo getMelhorSolucao() {
        return melhorSolucao;
    }

}