package algoritmo.ia;

import java.util.ArrayList;
import java.util.List;

public class Individuo implements Comparable<Individuo> {

    /*
     * script algGen:
     * 1. Gerar população inicial
     * 2. Avaliar população
     * 3. Criterio de parada
     * 	3.1 selecionar pais
     * 	3.2 crossover
     * 	3.4 Mutação
     * 	3.5 Avaliar População
     * 	3.6 Definir população sobrevivente
     * 4 listar melhores individuos
     */

    private List<Double> espacos = new ArrayList<>();
    private List<Double> valores = new ArrayList<>();
    private List<String> cromossomo = new ArrayList<>(); // aqui estara contida a solucao
    private final Double limiteEspaco;
    private Double notaAvaliacao;
    private Double espacoUsado;
    private int geracao;

    public Individuo(List<Double> espacos, List<Double> valores, Double limiteEspaco) {
        this.espacos = espacos;
        this.valores = valores;
        this.limiteEspaco = limiteEspaco;
        this.notaAvaliacao = 0.0;
        this.espacoUsado = 0.0;
        this.geracao = 0;

        initValues();
    }

    private void initValues() {
        for (int i = 0; i < this.espacos.size(); i++) {
            if (Math.random() < 0.5) {
                this.cromossomo.add("0");
            } else {
                this.cromossomo.add("1");
            }
        }
    }

    /**
     * Função fitness
     * Função de avaliação do cromossomo
     * <p>
     * nota       é a pontuação da funçao, somatorio dos valores dos produtos
     * somaEspaco é o somatorio dos volumes dos produtos
     *
     * @return nulo pois e uma funcao void, apos um loop define uma nota, se o
     * somatorio dos volumes for maior q o volume maximo, atribui nota 1.0
     **/
    public void avaliacao() {
        Double nota = 0.0;
        Double somaEspaco = 0.0;

        for (int i = 0; i < this.cromossomo.size(); i++) {
            if (this.cromossomo.get(i).equals("1")) {
                nota += this.valores.get(i);
                somaEspaco += this.espacos.get(i);
            }
        }

        if (somaEspaco > this.limiteEspaco) {
            nota = 1.0;
        }

        notaAvaliacao = nota;
        espacoUsado = somaEspaco;
    }

    public List<Individuo> crossOver(Individuo outroIndividuo) {

        int corte = (int) Math.round(Math.random() * this.cromossomo.size());

        List<String> filho1 = new ArrayList<>();
        filho1.addAll(outroIndividuo.getCromossomo().subList(0, corte));
        filho1.addAll(this.cromossomo.subList(corte, this.cromossomo.size()));

        List<String> filho2 = new ArrayList<>();
        filho2.addAll(this.cromossomo.subList(0, corte));
        filho2.addAll(outroIndividuo.getCromossomo().subList(corte, this.cromossomo.size()));

        List<Individuo> filhos = new ArrayList<>();
        filhos.add(new Individuo(espacos, valores, limiteEspaco));
        filhos.add(new Individuo(espacos, valores, limiteEspaco));

        filhos.get(0).setCromossomo(filho1);
        filhos.get(1).setCromossomo(filho2);

        filhos.get(0).setGeracao(this.geracao + 1);
        filhos.get(1).setGeracao(this.geracao + 1);

        return filhos;
    }

    public Individuo mutacao(Double taxaMutacao) {
        for (int i = 0; i < this.getCromossomo().size(); i++) {
            if (Math.random() < taxaMutacao) {
                if (this.cromossomo.get(i).equals("1")) {
                    this.cromossomo.set(i, "0");
                } else {
                    this.cromossomo.set(i, "1");
                }
            }
        }
//		System.out.println("Depois d mutação " + this.cromossomo);
        return this;
    }

    public Double getEspacoUsado() {
        return espacoUsado;
    }


    public List<String> getCromossomo() {
        return cromossomo;
    }

    public void setCromossomo(List<String> cromossomo) {
        this.cromossomo = cromossomo;
    }


    public Double getNotaAvaliacao() {
        return notaAvaliacao;
    }


    public int getGeracao() {
        return geracao;
    }

    public void setGeracao(int geracao) {
        this.geracao = geracao;
    }

    @Override
    public int compareTo(Individuo ind) {
        return ind.getNotaAvaliacao().compareTo(this.notaAvaliacao);
    }

}
