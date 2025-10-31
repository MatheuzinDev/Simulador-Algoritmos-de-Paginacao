import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SimuladorSubstituicao extends JFrame {

    private JTextField txtPaginas;
    private JTextField txtQuadros;
    private JTextArea txtResultado;
    private GraficoPanel graficoPanel;

    public SimuladorSubstituicao() {
        setTitle("Simulador de Substituição de Páginas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Entrada de Dados"));

        inputPanel.add(new JLabel("Sequência de páginas:"));
        txtPaginas = new JTextField("1 2 3 4 1 2 5");
        inputPanel.add(txtPaginas);

        inputPanel.add(new JLabel("Número de quadros:"));
        txtQuadros = new JTextField("3");
        inputPanel.add(txtQuadros);

        JButton btnSimular = new JButton("Simular");
        inputPanel.add(btnSimular);

        add(inputPanel, BorderLayout.NORTH);

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(txtResultado), BorderLayout.CENTER);

        graficoPanel = new GraficoPanel();
        graficoPanel.setPreferredSize(new Dimension(800, 200));
        graficoPanel.setBorder(BorderFactory.createTitledBorder("Comparativo de Faltas de Página"));
        add(graficoPanel, BorderLayout.SOUTH);

        btnSimular.addActionListener(e -> simular());
    }

    private void simular() {
        try {
            int[] pages = Arrays.stream(txtPaginas.getText().trim().split("\\s+"))
                    .mapToInt(Integer::parseInt).toArray();
            int frames = Integer.parseInt(txtQuadros.getText().trim());

            Map<String, Integer> resultados = new LinkedHashMap<>();
            resultados.put("FIFO", FIFO.simular(pages, frames));
            resultados.put("LRU", LRU.simular(pages, frames));

            StringBuilder sb = new StringBuilder("--- Resultados ---\n");
            for (var entry : resultados.entrySet())
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" faltas de página\n");

            txtResultado.setText(sb.toString());
            graficoPanel.setResultados(resultados);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro na entrada de dados: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimuladorSubstituicao().setVisible(true));
    }

    // Painel gráfico interno
    static class GraficoPanel extends JPanel {
        private Map<String, Integer> resultados;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (resultados == null || resultados.isEmpty()) return;

            int width = getWidth();
            int height = getHeight();
            int barWidth = width / (resultados.size() * 2);
            int maxFaltas = Collections.max(resultados.values());
            int x = barWidth;

            for (var entry : resultados.entrySet()) {
                int barHeight = (int) ((entry.getValue() / (double) maxFaltas) * (height - 60));
                g.setColor(new Color(70, 130, 180));
                g.fillRect(x, height - barHeight - 30, barWidth, barHeight);
                g.setColor(Color.BLACK);
                g.drawRect(x, height - barHeight - 30, barWidth, barHeight);
                g.drawString(entry.getKey(), x + 5, height - 10);
                g.drawString(String.valueOf(entry.getValue()), x + barWidth / 3, height - barHeight - 35);
                x += barWidth * 2;
            }
        }

        public void setResultados(Map<String, Integer> resultados) {
            this.resultados = resultados;
            repaint();
        }
    }
}
