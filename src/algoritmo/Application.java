package algoritmo;

import algoritmo.ia.AlgoritmoGenetico;
import algoritmo.model.Product;
import algoritmo.view.Grafico;
import org.jfree.ui.RefineryUtilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        List<Product> listaProdutos = getList();
//        ResultSet result = getDataProductSet();

//        while (result.next()) {
//            for (int i = 0; i < result.getInt("quantidade"); i++) {
//                listaProdutos.add(new Produto(result.getString("nome"),
//                        result.getDouble("valor"), result.getDouble("espaco")));
//            }
//        }

        List<String> nomes = new ArrayList<>();
        List<Double> valores = new ArrayList<>();
        List<Double> espacos = new ArrayList<>();

        for (Product produto : listaProdutos) {
            nomes.add(produto.getName());
            valores.add(produto.getScore());
            espacos.add(produto.getTarget());
        }

        Double limite = 10.0;
        int tamanhoPopulacao = 60;
        Double taxaMutacao = 0.08;
        int numGeracoes = 250;

        AlgoritmoGenetico ag = new AlgoritmoGenetico(tamanhoPopulacao);
        List<String> resultado = ag.resolver(taxaMutacao, numGeracoes, espacos, valores, limite);

        for (int i = 0; i < listaProdutos.size(); i++) {
            if (resultado.get(i).equals("1")) {
                System.out.println("Nome: " + listaProdutos.get(i).getName());
            }
        }

        Grafico g = new Grafico("Algoritmo Genético", "Evolução das Gerações", ag.getMelhoresCromossomos());
        g.pack();
        RefineryUtilities.centerFrameOnScreen(g);
        g.setVisible(true);

    }

    private static ResultSet getDataProductSet() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/genalgor", "root", "root");
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT nome, valor, espaco, quantidade FROM produtos");
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
