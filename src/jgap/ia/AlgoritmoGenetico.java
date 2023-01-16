package jgap.ia;

import jgap.model.Produto;
import org.jgap.*;
import org.jgap.event.EventManager;
import org.jgap.impl.*;

import java.util.ArrayList;
import java.util.List;

public class AlgoritmoGenetico {
    Configuration configuration;
    private List<Chromosome> melhoresCromossomos;
    public List<Produto> productList = new ArrayList<>();
    private Double limit = 5.0;
    private IChromosome melhor;

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public AlgoritmoGenetico(){
        melhoresCromossomos = new ArrayList<>();
    }
    public IChromosome getMelhor() {
        return melhor;
    }

    public void setMelhor(IChromosome melhor) {
        this.melhor = melhor;
    }

    public List<Chromosome> getMelhoresCromossomos() {
        return melhoresCromossomos;
    }

    /**
     * Exemplo de carga de dados como teste
     * */
    public void carregar() {

        productList.add(new Produto("Geladeira Dako", 0.751, 999.90));
        productList.add(new Produto("Iphone 6", 0.000089, 2911.12));
        productList.add(new Produto("TV 55' ", 0.400, 4346.99));
        productList.add(new Produto("TV 50' ", 0.290, 3999.90));
        productList.add(new Produto("TV 42' ", 0.200, 2999.00));
        productList.add(new Produto("Notebook Dell", 0.00350, 2499.90));
        productList.add(new Produto("Ventilador Panasonic", 0.496, 199.90));
        productList.add(new Produto("Microondas Electrolux", 0.0424, 308.66));
        productList.add(new Produto("Microondas LG", 0.0544, 429.90));
        productList.add(new Produto("Microondas Panasonic", 0.0319, 299.29));
        productList.add(new Produto("Geladeira Brastemp", 0.635, 849.00));
        productList.add(new Produto("Geladeira Consul", 0.870, 1199.89));
        productList.add(new Produto("Notebook Lenovo", 0.498, 1999.90));
        productList.add(new Produto("Notebook Asus", 0.527, 3999.00));

    }


    public Double sumSpaces(IChromosome chromosome) {
        Double sum = 0.0;
        for (int i = 0; i < chromosome.size(); i++) {
            if (chromosome.getGene(i).getAllele().toString().equals("1")) {
                sum += this.productList.get(i).getVolume();
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
        System.out.println("G -> " + geracao + "\nValor: " + cromossomo.getFitnessValue() + "Espacos: "
                + sumSpaces(cromossomo) + "Cromossomo: " + list);
    }

    public IChromosome createChromosome() throws InvalidConfigurationException {

        Gene[] genes = new Gene[productList.size()];

        for (int i = 0; i < genes.length; i++) {
            genes[i] = new IntegerGene(configuration, 0, 1);
            genes[i].setAllele(i);
        }

        return new Chromosome(configuration, genes);
    }

    public FitnessFunction criarFuncaoFitness() {
        return new Avaliacao(this);
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

        for (int i = 0; i < populations; i++ ) {
            chromosomes[i] = createChromosome();
        }

        Genotype population = new Genotype(configuration, new Population(configuration, chromosomes));

        int numberOfGenerations = 1000;

        for (int j = 0; j < numberOfGenerations; j++) {
            this.visualizeGeneration(population.getFittestChromosome(), j);
            this.melhoresCromossomos.add((Chromosome) population.getFittestChromosome());
            population.evolve();
        }
    }

    public Double getLimit() {
        return limit;
    }
}