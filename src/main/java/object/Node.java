package object;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by cuongnb on 11/18/16.
 */
public class Node implements Paintable {

    String name;
    ArrayList<String> sOutcome;
    Map<String, Double> mapOutcomes;
    ArrayList<Node> nodeParent;

    private Font font = new Font("Sans Serif", Font.BOLD, 12);
    private Ellipse2D bounds;
    private int w = 90;
    private int h = 40;

    public Node(String name, Font font, int x, int y) {
        this.name = name;
        this.font = font;
        //Ellipse2D.Double(double x, double y, double w, double h)
        bounds = new Ellipse2D.Double(x, y, w, h);
    }

    public Node(String name, int x, int y) {
        this.name = name;
        bounds = new Ellipse2D.Double(x, y, w, h);
    }

    public Node(int x, int y) {
        this.name = name;
        bounds = new Ellipse2D.Double(x, y, w, h);
    }

    @Override
    public void paint(JComponent parent, Graphics2D g2d) {
        Graphics2D g2 = (Graphics2D) g2d.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        int height = fm.getHeight();
        int width = fm.stringWidth(name);
        g2.setColor(Color.WHITE);
        g2.fill(bounds);
        g2.setColor(Color.BLACK);
        g2.draw(bounds);

        double centreX = bounds.getX() + bounds.getWidth() / 2d;
        double centreY = bounds.getY() + bounds.getHeight() / 2d;
        g2.drawString(name, (int) (centreX - width / 2d), (int) (centreY + height / 4d));

        g2.dispose();
    }

    @Override
    public boolean contains(Point p) {
        return bounds.contains(p);
    }

    @Override
    public void moveTo(Point2D p) {
        bounds = new Ellipse2D.Double(p.getX(), p.getY(), w, h);
    }

    @Override
    public Rectangle2D getBounds() {
        return bounds.getBounds2D();
    }

}
