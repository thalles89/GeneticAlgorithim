package jgap;

import jgap.ia.AlgoritmoGenetico;
import jgap.view.Grafico;
import org.jfree.ui.RefineryUtilities;
import org.jgap.InvalidConfigurationException;

import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InvalidConfigurationException {
        AlgoritmoGenetico ag = new AlgoritmoGenetico();
        ag.carregar();
        ag.searchBestSolution();
        ag.setLimit(5d);
        int generation = 0;

        for (int i = 0; i < ag.getMelhoresCromossomos().size(); i++) {
            if (ag.getMelhor() == null) {
                ag.setMelhor(ag.getMelhoresCromossomos().get(i));
            } else if (ag.getMelhor().getFitnessValue() < ag.getMelhoresCromossomos().get(i).getFitnessValue()) {
                ag.setMelhor(ag.getMelhoresCromossomos().get(i));
                generation = i;
            }
        }
        System.out.println("\nMelhor solução ");
        ag.visualizeGeneration(ag.getMelhor(), generation);

        for (int i = 0; i < ag.productList.size(); i++) {
            if (ag.getMelhor().getGene(i).getAllele().toString().equals("1")) {
                System.out.println("Nome: " + ag.productList.get(i).getNome());
            }
        }
        Grafico g = new Grafico("Algoritmo genético", "Evolução das soluções", ag.getMelhoresCromossomos());
        g.pack();
        RefineryUtilities.centerFrameOnScreen(g);
        g.setVisible(true);

    }
}

