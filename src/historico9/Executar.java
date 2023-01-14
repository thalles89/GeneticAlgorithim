package historico9;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * Classe usada como MODEL (MVC)
 * 
 * @author Thalles
 */
class Produto {

	private String nome;
	private Double valor;
	private Double volume;

	/**
	 * @param nome   (nome do produto)
	 * @param valor  (Preço da unidade)
	 * @param volume (medido em metros cubicos)
	 */
	public Produto(String nome, Double valor, Double volume) {
		super();
		this.nome = nome;
		this.valor = valor;
		this.volume = volume;
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
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

}

class Individuo implements Comparable<Individuo> {

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
	private Double limiteEspaco;
	private Double notaAvaliacao;
	private Double espacoUsado;
	private int geracao;

	public Individuo(List<Double> espacos2, List<Double> valores2, Double limiteEspaco) {
		this.espacos = espacos2;
		this.valores = valores2;
		this.limiteEspaco = limiteEspaco;
		this.notaAvaliacao = 0.0;
		this.espacoUsado = 0.0;
		this.geracao = 0;

		for (int i = 0; i < this.espacos.size(); i++) {
			if (Math.random() < 0.5) {
				this.cromossomo.add("0");
			} else {
				this.cromossomo.add("1");
			}
		}
	}

	/**
	 * Função fitness Função de avaliação do cromossomo
	 * 
	 * @param nota       é a pontuação da funçao, somatorio dos valores dos produtos
	 * @param somaEspaco é o somatorio dos volumes dos produtos
	 * @return nulo pois e uma funcao void, apos um loop define uma nota, se o
	 *         somatorio dos volumes for maior q o volume maximo, atribui nota 1.0
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

	@SuppressWarnings("unchecked")
	public List<Individuo> crossOver(Individuo outroIndividuo) {
		int corte = (int) Math.round(Math.random() * this.cromossomo.size());
		@SuppressWarnings("rawtypes")
		List filho1 = new ArrayList<>();
		filho1.addAll(outroIndividuo.getCromossomo().subList(0, corte));
		filho1.addAll(this.cromossomo.subList(corte, this.cromossomo.size()));

		@SuppressWarnings("rawtypes")
		List filho2 = new ArrayList<>();
		filho2.addAll(this.cromossomo.subList(0, corte));
		filho2.addAll(outroIndividuo.getCromossomo().subList(corte, this.cromossomo.size()));

		List<Individuo> filhos = new ArrayList<Individuo>();
		filhos.add(new Individuo(espacos, valores, limiteEspaco));
		filhos.add(new Individuo(espacos, valores, limiteEspaco));

		filhos.get(0).setCromossomo(filho1);
		filhos.get(1).setCromossomo(filho2);

		filhos.get(0).setGeracao(this.geracao + 1);
		filhos.get(1).setGeracao(this.geracao + 1);
		return filhos;
	}

	public Individuo mutacao(Double taxaMutacao) {
//		System.out.println("Antes da mutação " + this.getCromossomo());

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

	public List<Double> getEspacos() {
		return espacos;
	}

	public Double getEspacoUsado() {
		return espacoUsado;
	}

	public void setEspacoUsado(Double espacoUsado) {
		this.espacoUsado = espacoUsado;
	}

	public void setEspacos(List<Double> espacos) {
		this.espacos = espacos;
	}

	public List<Double> getValores() {
		return valores;
	}

	public void setValores(List<Double> valores) {
		this.valores = valores;
	}

	public List<String> getCromossomo() {
		return cromossomo;
	}

	public void setCromossomo(List<String> cromossomo) {
		this.cromossomo = cromossomo;
	}

	public Double getLimiteEspaco() {
		return limiteEspaco;
	}

	public void setLimiteEspaco(Double limiteEspaco) {
		this.limiteEspaco = limiteEspaco;
	}

	public Double getNotaAvaliacao() {
		return notaAvaliacao;
	}

	public void setNotaAvaliacao(Double notaAvaliacao) {
		this.notaAvaliacao = notaAvaliacao;
	}

	public int getGeracao() {
		return geracao;
	}

	public void setGeracao(int geracao) {
		this.geracao = geracao;
	}

	@Override
	public int compareTo(Individuo ind) {

		if (this.notaAvaliacao > ind.getNotaAvaliacao()) {
			return -1;
		}
		if (this.notaAvaliacao < ind.getNotaAvaliacao()) {
			return 1;
		}
		return 0;
	}

}

class AlgoritmoGenetico {

	private int tamanhoPopulacao;
	private List<Individuo> populacao = new ArrayList<>();
	private int geracao;
	private Individuo melhorSolucao;
	private List<Individuo> melhoresCromossomos = new ArrayList<Individuo>();

	public List<Individuo> getMelhoresCromossomos() {
		return melhoresCromossomos;
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

	public void setMelhoresCromossomos(List<Individuo> melhoresCromossomos) {
		this.melhoresCromossomos = melhoresCromossomos;
	}

	public AlgoritmoGenetico(int tamanhoPopulacao) {
		this.tamanhoPopulacao = tamanhoPopulacao;
	}

}

class Grafico extends ApplicationFrame {

	/**
	 * @author Thalles
	 * @serial 1L
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Individuo> melhoresCromossomos = new ArrayList<Individuo>();

	public Grafico(String title, String tituloGrafico, List<Individuo> melhores) {
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
			dados.addValue(melhoresCromossomos.get(i).getNotaAvaliacao(), "Melhor Solução ", "" + i);
		}

		return dados;
	}

}

public class Executar {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		List<Produto> listaProdutos = new ArrayList<>();

		Class.forName("com.mysql.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/genalgor", "root", "root");
		Statement stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery("SELECT nome, valor, espaco, quantidade FROM produtos");
		
		while(result.next()) {
			for(int i=0; i < result.getInt("quantidade"); i++) {
				listaProdutos.add(new Produto(result.getString("nome"), result.getDouble("valor"), result.getDouble("espaco")));
			}
		}
		
		List<String> nomes = new ArrayList<>();
		List<Double> valores = new ArrayList<>();
		List<Double> espacos = new ArrayList<>();

		for (Produto produto : listaProdutos) {
			nomes.add(produto.getNome());
			valores.add(produto.getValor());
			espacos.add(produto.getVolume());
		}

		Double limite = 10.0;
		int tamanhoPopulacao = 60;
		Double taxaMutacao = 0.08;
		int numGeracoes = 250;

		AlgoritmoGenetico ag = new AlgoritmoGenetico(tamanhoPopulacao);
		List<String> resultado = ag.resolver(taxaMutacao, numGeracoes, espacos, valores, limite);

		for (int i = 0; i < listaProdutos.size(); i++) {
			if (resultado.get(i).equals("1")) {
				System.out.println("Nome: " + listaProdutos.get(i).getNome());
			}
		}

		Grafico g = new Grafico("Algoritmo Genético", "Evolução das Gerações", ag.getMelhoresCromossomos());
		g.pack();
		RefineryUtilities.centerFrameOnScreen(g);
		g.setVisible(true);

	}
}
