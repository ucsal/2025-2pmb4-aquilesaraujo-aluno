package br.com.mariojp.figureeditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

class DrawingPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    enum TipoFigura { RETANGULO, CIRCULO }

    private final List<Shape> shapes = new ArrayList<>();
    private Point startDrag = null;
    private Point endDrag = null;
    private TipoFigura tipoAtual = TipoFigura.RETANGULO; 

    DrawingPanel() {
        setBackground(Color.WHITE);
        setOpaque(true);
        setDoubleBuffered(true);

        var mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startDrag = e.getPoint();
                endDrag = startDrag;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                endDrag = e.getPoint();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (startDrag != null && endDrag != null) {
                    Shape s = criarForma(startDrag, endDrag);
                    shapes.add(s);
                }
                startDrag = null;
                endDrag = null;
                repaint();
            }
        };

        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    void setTipoFigura(TipoFigura tipo) {
        this.tipoAtual = tipo;
    }

    private Shape criarForma(Point p1, Point p2) {
        int x = Math.min(p1.x, p2.x);
        int y = Math.min(p1.y, p2.y);
        int w = Math.abs(p1.x - p2.x);
        int h = Math.abs(p1.y - p2.y);

        if (tipoAtual == TipoFigura.CIRCULO) {
            return new Ellipse2D.Double(x, y, w, h);
        } else {
            return new Rectangle2D.Double(x, y, w, h);
        }
    }

    void clear() {
        shapes.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Shape s : shapes) {
            g2.setColor(new Color(30, 144, 255));
            g2.fill(s);
            g2.setColor(new Color(0, 0, 0, 70));
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(s);
        }

        if (startDrag != null && endDrag != null) {
            Shape preview = criarForma(startDrag, endDrag);
            g2.setColor(Color.GRAY);
            float[] dash = {5, 5};
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10, dash, 0));
            g2.draw(preview);
        }

        g2.dispose();
    }
}
