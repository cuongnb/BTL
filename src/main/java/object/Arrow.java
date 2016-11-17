package object;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by cuongnb on 11/18/16.
 */
public class Arrow implements Paintable {
    double phi;
    int barb;
    Point tail;
    Point tip;

    public Arrow(Point tip, Point tail) {
        phi = Math.toRadians(40);
        barb = 20;
        this.tail = tail;
        this.tip = tip;
    }

    @Override
    public void paint(JComponent parent, Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.draw(new Line2D.Double(tip, tail));
        drawArrowHead(g2, tip, tail, Color.blue);
    }

    @Override
    public boolean contains(Point p) {
        return false;
    }

    @Override
    public void moveTo(Point2D p) {

    }

    @Override
    public Rectangle2D getBounds() {
        return null;
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

}
