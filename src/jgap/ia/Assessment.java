package jgap.ia;

import jgap.Evaluable;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

class Assessment<T extends Evaluable> extends FitnessFunction {

    private final GeneticAlgorithm<T> geneticAlgorithm;

    public Assessment(GeneticAlgorithm<T> ag) {
        this.geneticAlgorithm = ag;
    }

    @Override
    protected double evaluate(IChromosome chromosome) {
        Double score = 0.0;
        Double spaces = 0.0;
        for (int i = 0; i < chromosome.size(); i++) {
            if ("1".equals(chromosome.getGene(i).getAllele().toString())) {
                score += this.geneticAlgorithm.getList().get(i).getScore();
                spaces += this.geneticAlgorithm.getList().get(i).getTarget();
            }
        }
        if (spaces > this.geneticAlgorithm.getLimit()) {
            score = 1.0;
        }
        return score;
    }
}