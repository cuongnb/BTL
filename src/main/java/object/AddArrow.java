package object;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by cuongnb on 11/18/16.
 */
public class AddArrow implements Paintable {
    double phi;
    int barb;
    Point tip;
    Point tail;
    private Rectangle2D bounds;
    private int w = 50;
    private int h = 25;
    Color background;

    public AddArrow(int x, int y, Color background) {
        phi = Math.toRadians(40);
        barb = 20;
        bounds = new Rectangle2D.Double(x, y, w, h);
        Point tip = new Point(x + 5, y + h / 2);
        Point tail = new Point((x + w) - 5, y + h / 2);
        this.tail = tail;
        this.tip = tip;
        this.background = background;
    }

    @Override
    public void paint(JComponent parent, Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(background);
        g2.fill3DRect((int) bounds.getX() - 5, (int) bounds.getY() - 5, (int) bounds.getWidth() + 10, (int) bounds.getHeight() + 10, true);

        g2.setColor(Color.BLACK);
        g2.draw(new Line2D.Double(tail, tip));
        drawArrowHead(g2, tail, tip, Color.BLACK);
    }

    @Override
    public boolean contains(Point p) {
        return bounds.contains(p);
    }

    @Override
    public void moveTo(Point2D p) {
        bounds = new Rectangle2D.Double(p.getX(), p.getY(), w, h);
    }

    @Override
    public Rectangle2D getBounds() {
        return bounds.getBounds2D();
    }

    private void drawArrowHead(Graphics2D g2, Point tip, Point tail, Color color) {
        g2.setPaint(color);
        double dy = tip.y - tail.y;
        double dx = tip.x - tail.x;
        double theta = Math.atan2(dy, dx);
        //System.out.println("theta = " + Math.toDegrees(theta));
        double x, y, rho = theta + phi;
        for (int j = 0; j < 2; j++) {
            x = tip.x - barb * Math.cos(rho);
            y = tip.y - barb * Math.sin(rho);
            g2.draw(new Line2D.Double(tip.x, tip.y, x, y));
            rho = theta - phi;
        }
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }
}
