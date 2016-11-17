package object;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by cuongnb on 11/18/16.
 */
public class ArrowHead {

    public ArrowHead() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new ArrowPanel());
        f.setSize(400, 400);
        f.setLocation(200, 200);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        new ArrowHead();
    }
}

class ArrowPanel extends JPanel {
    double phi;
    int barb;
    Point sw = new Point(40, 50);
    Point ne = new Point(130, 70);

    public ArrowPanel() {
        phi = Math.toRadians(40);
        barb = 20;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
//        Point sw = new Point(w / 8, h * 7 / 8);
//        Point ne = new Point(w * 7 / 8, h / 8);
        g2.draw(new Line2D.Double(sw, ne));
//        drawArrowHead(g2, sw, ne, Color.red);
        drawArrowHead(g2, ne, sw, Color.blue);
    }

    private void drawArrowHead(Graphics2D g2, Point tip, Point tail, Color color) {
        g2.setPaint(color);
        double dy = tip.y - tail.y;
        double dx = tip.x - tail.x;
        double theta = Math.atan2(dy, dx);
//        System.out.println("theta = " + Math.toDegrees(theta));
        double x, y, rho = theta + phi;
        for (int j = 0; j < 2; j++) {
            x = tip.x - barb * Math.cos(rho);
            y = tip.y - barb * Math.sin(rho);
            g2.draw(new Line2D.Double(tip.x, tip.y, x, y));
            rho = theta - phi;
        }
    }
}
