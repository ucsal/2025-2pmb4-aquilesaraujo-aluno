package br.com.mariojp.figureeditor;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            JFrame frame = new JFrame("Figure Editor — Clique e arraste para desenhar");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            DrawingPanel panel = new DrawingPanel();

            JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

            JButton btnRect = new JButton("Retângulo");
            btnRect.addActionListener(e -> panel.setTipoFigura(DrawingPanel.TipoFigura.RETANGULO));

            JButton btnCirc = new JButton("Círculo");
            btnCirc.addActionListener(e -> panel.setTipoFigura(DrawingPanel.TipoFigura.CIRCULO));

            JButton btnClear = new JButton("Limpar");
            btnClear.addActionListener(e -> panel.clear());

            botoes.add(btnRect);
            botoes.add(btnCirc);
            botoes.add(btnClear);

            // layout da janela
            frame.setLayout(new BorderLayout());
            frame.add(panel, BorderLayout.CENTER);
            frame.add(botoes, BorderLayout.SOUTH);

            frame.setSize(900, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
