package jgap;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.StockRandomGenerator;
import org.jgap.impl.SwappingMutationOperator;

public class ExecuteJGAP {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, InvalidConfigurationException {
		AlgoritmoGenetico ag = new AlgoritmoGenetico();
		ag.carregar();
		ag.procurarMelhorSolucao();

		int geracao = 0;

		for (int i = 0; i < ag.melhoresCromossomos.size(); i++) {
			if (ag.melhor == null) {
				ag.melhor = ag.melhoresCromossomos.get(i);
			} else if (ag.melhor.getFitnessValue() < ag.melhoresCromossomos.get(i).getFitnessValue()) {
				ag.melhor = ag.melhoresCromossomos.get(i);
				geracao = i;
			}
		}
		System.out.println("\nMelhor solução ");
		ag.visualizaGeracao(ag.melhor, geracao);

		for (int i = 0; i < ag.listaProdutos.size(); i++) {
			if (ag.melhor.getGene(i).getAllele().toString().equals("1")) {
				System.out.println("Nome: " + ag.listaProdutos.get(i).getNome());
			}
		}
		Grafico g = new Grafico("Algoritmo genético", "Evolução das soluções", ag.melhoresCromossomos);
        g.pack();
        RefineryUtilities.centerFrameOnScreen(g);
        g.setVisible(true);

	}
}

class Produto {

	private String nome;
	private Double valor;
	private Double espaco;

	public Produto(String nome, Double espaco, Double valor) {
		super();
		this.nome = nome;
		this.valor = valor;
		this.espaco = espaco;
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
		return espaco;
	}

	public void setVolume(Double volume) {
		this.espaco = volume;
	}
}

class Grafico extends ApplicationFrame {

	private static final long serialVersionUID = 1L;

	private List<Chromosome> melhoresCromossomos = new ArrayList<Chromosome>();

	public Grafico(String title, String tituloGrafico, List<Chromosome> melhores) {
		super(title);
		this.melhoresCromossomos = melhores;
		JFreeChart graficoDeLinha = ChartFactory.createLineChart(tituloGrafico, "Gerações", "VALOR", carregaDados(),
				PlotOrientation.VERTICAL, true, true, false);
		ChartPanel janela = new ChartPanel(graficoDeLinha);
		janela.setPreferredSize(new Dimension(800, 600));
		setContentPane(janela);
	}

	private DefaultCategoryDataset carregaDados() {
		DefaultCategoryDataset dados = new DefaultCategoryDataset();

		for (int i = 0; i < melhoresCromossomos.size(); i++) {
			dados.addValue(melhoresCromossomos.get(i).getFitnessValue(), "Melhor Solução ", "" + i);
		}

		return dados;
	}

}

class AlgoritmoGenetico {
	Configuration configuration;

	int numeroGeracoes = 100;
	double limite = 3.0;
	int populacao = 10;
	int taxaMutacao = 100;

	List<Chromosome> melhoresCromossomos = new ArrayList<Chromosome>();
	List<Produto> listaProdutos = new ArrayList<Produto>();
	IChromosome melhor;

	public void carregar() {

		listaProdutos.add(new Produto("Geladeira Dako", 0.751, 999.90));
        listaProdutos.add(new Produto("Iphone 6", 0.000089, 2911.12));
        listaProdutos.add(new Produto("TV 55' ", 0.400, 4346.99));
        listaProdutos.add(new Produto("TV 50' ", 0.290, 3999.90));
        listaProdutos.add(new Produto("TV 42' ", 0.200, 2999.00));
        listaProdutos.add(new Produto("Notebook Dell", 0.00350, 2499.90));
        listaProdutos.add(new Produto("Ventilador Panasonic", 0.496, 199.90));
        listaProdutos.add(new Produto("Microondas Electrolux", 0.0424, 308.66));
        listaProdutos.add(new Produto("Microondas LG", 0.0544, 429.90));
        listaProdutos.add(new Produto("Microondas Panasonic", 0.0319, 299.29));
        listaProdutos.add(new Produto("Geladeira Brastemp", 0.635, 849.00));
        listaProdutos.add(new Produto("Geladeira Consul", 0.870, 1199.89));
        listaProdutos.add(new Produto("Notebook Lenovo", 0.498, 1999.90));
        listaProdutos.add(new Produto("Notebook Asus", 0.527, 3999.00));

	}

	public Double somaEspacos(IChromosome cromossomo) {
		Double soma = 0.0;
		for (int i = 0; i < cromossomo.size(); i++) {
			if (cromossomo.getGene(i).getAllele().toString().equals("1")) {
				soma += this.listaProdutos.get(i).getVolume();
			}
		}
		return soma;
	}

	public void visualizaGeracao(IChromosome cromossomo, int geracao) {
		List<String> lista = new ArrayList<>();
		Gene[] genes = cromossomo.getGenes();

		for (int i = 0; i < cromossomo.size(); i++) {
			lista.add(genes[i].getAllele().toString() + " ");
		}
		System.out.println("G -> " + geracao + "\nValor: " + cromossomo.getFitnessValue() + "Espacos: "
				+ somaEspacos(cromossomo) + "Cromossomo: " + lista);
	}

	public IChromosome criarCromossomo() throws InvalidConfigurationException {

		Gene[] genes = new Gene[listaProdutos.size()];

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
		config.addGeneticOperator(new SwappingMutationOperator(config, taxaMutacao));
		config.setKeepPopulationSizeConstant(true);
		config.setEventManager(new EventManager());
		config.setFitnessEvaluator(new DefaultFitnessEvaluator());

		return config;
	}

	public void procurarMelhorSolucao() throws InvalidConfigurationException {
        this.configuration = criarConfiguracao();
        FitnessFunction funcaoFitness = criarFuncaoFitness();
        configuration.setFitnessFunction(funcaoFitness);
        IChromosome modeloCromossomo = criarCromossomo();
        configuration.setSampleChromosome(modeloCromossomo);
        configuration.setPopulationSize(this.populacao);
        IChromosome[] cromossomos = new IChromosome[populacao];
        for (int i = 0; i < this.populacao; i++ ) {
            cromossomos[i] = criarCromossomo();
        }
        Genotype populacao = new Genotype(configuration, new Population(configuration, cromossomos));
        for (int j = 0; j < this.numeroGeracoes; j++) {
            this.visualizaGeracao(populacao.getFittestChromosome(), j);
            this.melhoresCromossomos.add((Chromosome) populacao.getFittestChromosome());
            populacao.evolve();
        }
    }

}

@SuppressWarnings("serial")
class Avaliacao extends FitnessFunction {

	private final AlgoritmoGenetico algoritmoGenetico;

	public Avaliacao(AlgoritmoGenetico ag) {
		this.algoritmoGenetico = ag;
	}

	@Override
	protected double evaluate(IChromosome cromossomo) {
		Double nota = 0.0;
		Double espacos = 0.0;
		for (int i = 0; i < cromossomo.size(); i++) {
			if (cromossomo.getGene(i).getAllele().toString().equals("1")) {
				nota += this.algoritmoGenetico.listaProdutos.get(i).getValor();
				espacos += this.algoritmoGenetico.listaProdutos.get(i).getVolume();
			}
		}
		if (espacos > this.algoritmoGenetico.limite) {
			nota = 1.0;
		}
		return nota;
	}
}
