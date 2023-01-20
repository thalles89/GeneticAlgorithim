package jgap.ia;

import jgap.Evaluable;
import org.jgap.*;
import org.jgap.event.EventManager;
import org.jgap.impl.*;

import java.util.ArrayList;
import java.util.List;


public class GeneticAlgorithm<T extends Evaluable> {
    Configuration configuration;
    private final List<Chromosome> bestChromosomes;
    private List<T> list;
    private double limit;
    private IChromosome bestChromosome;
    private final int numberOfGenerations;

    public GeneticAlgorithm(int numberOfGenerations) {
        this.numberOfGenerations = numberOfGenerations;
        this.bestChromosomes = new ArrayList<>();
        this.list = new ArrayList<>();
    }

    public List<T> getList() {
        return list;
    }

    /**
     * @param limit refers to the maximum
     *              number that the variable
     *              can receive on your problem
     */
    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Double getLimit() {
        return limit;
    }

    public IChromosome getBestChromosome() {
        return bestChromosome;
    }

    public void setBestChromosome(IChromosome bestChromosome) {
        this.bestChromosome = bestChromosome;
    }

    public List<Chromosome> getBestChromosomes() {
        return bestChromosomes;
    }

    /**
     * Exemplo de carga de dados como teste
     */

    public void loadDataSet(List<T> dataSet) {
        list = dataSet;
    }

    public Double sumSpaces(IChromosome chromosome) {
        Double sum = 0.0;
        for (int i = 0; i < chromosome.size(); i++) {
            if ("1".equals(chromosome.getGene(i).getAllele().toString())) {
                sum += this.list.get(i).getTarget();
            }
        }
        return sum;
    }

    public void visualizeGeneration(IChromosome cromossomo, int geracao) {
        List<String> list = new ArrayList<>();
        Gene[] genes = cromossomo.getGenes();

        for (int i = 0; i < cromossomo.size(); i++) {
            list.add(genes[i].getAllele().toString() + " ");
        }
        System.out.println("Best Solution");
        System.out.println("G -> " + geracao + "\nValor: " + cromossomo.getFitnessValue() + "\nEspacos: "
                + sumSpaces(cromossomo) + "\nCromossomo: " + list);
    }

    public IChromosome createChromosome() throws InvalidConfigurationException {

        Gene[] genes = new Gene[list.size()];

        for (int i = 0; i < genes.length; i++) {
            genes[i] = new IntegerGene(configuration, 0, 1);
            genes[i].setAllele(i);
        }

        return new Chromosome(configuration, genes);
    }

    public FitnessFunction criarFuncaoFitness() {
        return new Assessment<>(this);
    }

    public Configuration criarConfiguracao() throws InvalidConfigurationException {

        Configuration config = new Configuration();
        config.removeNaturalSelectors(true);

        config.addNaturalSelector(new BestChromosomesSelector(config, 0.4), true);
        config.setRandomGenerator(new StockRandomGenerator());
        config.addGeneticOperator(new CrossoverOperator(config));
        int taxaMutacao = 100;
        config.addGeneticOperator(new SwappingMutationOperator(config, taxaMutacao));
        config.setKeepPopulationSizeConstant(true);
        config.setEventManager(new EventManager());
        config.setFitnessEvaluator(new DefaultFitnessEvaluator());

        return config;
    }

    public void searchBestSolution() throws InvalidConfigurationException {
        this.configuration = criarConfiguracao();

        FitnessFunction fitnessFunction = criarFuncaoFitness();
        IChromosome chromosomeModel = createChromosome();
        int populations = 10;

        configuration.setFitnessFunction(fitnessFunction);
        configuration.setSampleChromosome(chromosomeModel);
        configuration.setPopulationSize(populations);

        IChromosome[] chromosomes = new IChromosome[populations];

        for (int i = 0; i < populations; i++) {
            chromosomes[i] = createChromosome();
        }

        Genotype population = new Genotype(configuration, new Population(configuration, chromosomes));

        for (int j = 0; j < numberOfGenerations; j++) {
            this.visualizeGeneration(population.getFittestChromosome(), j);
            this.bestChromosomes.add((Chromosome) population.getFittestChromosome());
            population.evolve();
        }
    }

}