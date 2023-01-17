package algoritmo.view;

import algoritmo.ia.Individuo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Grafico extends ApplicationFrame {

    /**
     * @author Thalles
     * @serial 1L
     */
    private static final long serialVersionUID = 1L;

    private List<Individuo> melhoresCromossomos = new ArrayList<>();

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
