package algoritmo;

import algoritmo.ia.AlgoritmoGenetico;
import algoritmo.model.Produto;
import algoritmo.view.Grafico;
import org.jfree.ui.RefineryUtilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Executar {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        List<Produto> listaProdutos = new ArrayList<>();

        ResultSet result = getDataProductSet();

        while (result.next()) {
            for (int i = 0; i < result.getInt("quantidade"); i++) {
                listaProdutos.add(new Produto(result.getString("nome"),
                        result.getDouble("valor"), result.getDouble("espaco")));
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

    private static ResultSet getDataProductSet() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/genalgor", "root", "root");
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT nome, valor, espaco, quantidade FROM produtos");
    }
}
