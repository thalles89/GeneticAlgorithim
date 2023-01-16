package jgap.ia;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

class Avaliacao extends FitnessFunction {

    private final AlgoritmoGenetico algoritmoGenetico;

    public Avaliacao(AlgoritmoGenetico ag) {
        this.algoritmoGenetico = ag;
    }

    @Override
    protected double evaluate(IChromosome cromossomo) {
        Double score = 0.0;
        Double spaces = 0.0;
        for (int i = 0; i < cromossomo.size(); i++) {
            if ("1".equals(cromossomo.getGene(i).getAllele().toString())) {
                score += this.algoritmoGenetico.productList.get(i).getValor();
                spaces += this.algoritmoGenetico.productList.get(i).getVolume();
            }
        }
        if (spaces > this.algoritmoGenetico.getLimit()) {
            score = 1.0;
        }
        return score;
    }
}