package jgap;

import jgap.ia.GeneticAlgorithm;
import jgap.model.Product;
import jgap.view.Grafico;
import org.jfree.ui.RefineryUtilities;
import org.jgap.InvalidConfigurationException;

import java.util.List;

public class Application {

    public static void main(String[] args) throws InvalidConfigurationException {
        GeneticAlgorithm<Product> ag = new GeneticAlgorithm<>(300);
        ag.loadDataSet(getList());
        ag.setLimit(5.0);

        ag.searchBestSolution();

        int generation = 0;

        for (int i = 0; i < ag.getBestChromosomes().size(); i++) {
            if (ag.getBestChromosome() == null) {
                ag.setBestChromosome(ag.getBestChromosomes().get(i));
            } else if (ag.getBestChromosome().getFitnessValue() < ag.getBestChromosomes().get(i).getFitnessValue()) {
                ag.setBestChromosome(ag.getBestChromosomes().get(i));
                generation = i;
            }
        }

        ag.visualizeGeneration(ag.getBestChromosome(), generation);

        for (int i = 0; i < ag.getList().size(); i++) {
            if (ag.getBestChromosome().getGene(i).getAllele().toString().equals("1")) {
                System.out.println("Product: " + ag.getList().get(i).getName());
            }
        }
        /*Graphic generation*/

        Grafico g = new Grafico("Algoritmo genético", "Evolução das soluções", ag.getBestChromosomes());
        g.pack();
        RefineryUtilities.centerFrameOnScreen(g);
        g.setVisible(true);

    }

    private static List<Product> getList() {
        return List.of(new Product("Geladeira Dako", 0.751, 999.90),
                new Product("Iphone 6", 0.0089, 2911.12),
                new Product("TV 55' ", 0.400, 4346.99),
                new Product("TV 50' ", 0.290, 3999.90),
                new Product("TV 42' ", 0.200, 2999d),
                new Product("Notebook Dell", 0.350, 2499.90),
                new Product("Ventilador Panasonic", 0.496, 199.90),
                new Product("Microondas Electrolux", 0.0424, 308.66),
                new Product("Microondas LG", 0.0544, 429.90),
                new Product("Microondas Panasonic", 0.0319, 299.29),
                new Product("Geladeira Brastemp", 0.635, 849d),
                new Product("Geladeira Consul", 0.870, 1199.89),
                new Product("Notebook Lenovo", 0.498, 1999.90),
                new Product("Notebook Asus", 0.527, 3999d),
                new Product("Notebook Dell", 0.527, 7999d),
                new Product("Notebook Apple", 0.527, 14000d),
                new Product("Notebook paraguay", 0.927, 999d));
    }
}

