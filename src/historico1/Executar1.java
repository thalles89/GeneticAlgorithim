package historico1;

import java.util.ArrayList;
import java.util.List;


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

class Individuo {

	/*
	 * script algGen: 1. Gerar população inicial 2. Avaliar população 3. Criterio de
	 * parada 3.1 selecionar pais 3.2 crossover 3.4 Mutação 3.5 Avaliar População
	 * 3.6 Definir população sobrevivente 4 listaProdutosr melhores individuos
	 */

	private List<Double> espacos = new ArrayList<>();
	private List<Double> valores = new ArrayList<>();
	private List<String> cromossomo = new ArrayList<>(); // aqui estara contida a solucao
	private Double limiteEspaco;
	private Double notaAvaliacao;
	private int geracao;

	public Individuo(List<Double> espacos2, List<Double> valores2, Double limiteEspaco) {
		super();
		this.espacos = espacos2;
		this.valores = valores2;
		this.limiteEspaco = limiteEspaco;
		this.notaAvaliacao = 0.0;
		this.geracao = 0;

		for (int i = 0; i < this.espacos.size(); i++) {
			if (Math.random() < 0.5) {

				this.cromossomo.add("0");

			} else {
				this.cromossomo.add("1");
			}

		}

	}
	
	

	public List<Double> getEspacos() {
		return espacos;
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

}

public class Executar1 {

	public static void main(String[] args) {
		List<Produto> listaProdutos = new ArrayList<>();

		listaProdutos.add(new Produto("Geladeira Dako", 999.90, 0.751));
		listaProdutos.add(new Produto("Iphone 6", 2911.12, 0.000089));
		listaProdutos.add(new Produto("Tv 55'", 4346.99, 0.400));
		listaProdutos.add(new Produto("Tv 50'", 3999.90, 0.290));
		listaProdutos.add(new Produto("Tv 42'", 2999.66, 0.200));
		listaProdutos.add(new Produto("Notebook Dell", 2499.00, 0.00350));
		listaProdutos.add(new Produto("Ventilador Panasonic", 199.90, 0.496));
		listaProdutos.add(new Produto("Microondas Eletrolux", 308.60, 0.424));
		listaProdutos.add(new Produto("Microondas LG", 429.90, 0.0544));
		listaProdutos.add(new Produto("Microondas Panasonic", 299.99, 0.0319));
		listaProdutos.add(new Produto("Geladeira Brastemp", 299.29, 0.635));
		listaProdutos.add(new Produto("Geladeira Consul", 849.00, 0.870));
		listaProdutos.add(new Produto("Notebook Lenovo", 1999.89, 0.498));
		listaProdutos.add(new Produto("Notebook Asus", 3999.00, 0.527));

		List<String> nomes = new ArrayList<>();
		List<Double> valores = new ArrayList<>();
		List<Double> espacos = new ArrayList<>();
		
		for (Produto produto : listaProdutos) {
			nomes.add(produto.getNome());
			valores.add(produto.getValor());
			espacos.add(produto.getVolume());
		}
		
		Double limite = 3.0;
		
		Individuo individuo = new Individuo(espacos, valores, limite);
		System.out.println("Espaço: " + individuo.getEspacos());
		System.out.println("Valor: " + individuo.getValores());
		System.out.println("Cromossomo: " + individuo.getCromossomo());
	
		System.out.println("\nCarga:");
		
		for(int i = 0; i < listaProdutos.size(); i++) {
			if(individuo.getCromossomo().get(i) == "1") {
				System.out.println("Produto: " + listaProdutos.get(i).getNome() + "; Valor: " + listaProdutos.get(i).getValor());
			}
		}
	
	}

}
