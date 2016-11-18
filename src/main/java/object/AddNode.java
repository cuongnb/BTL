package object;

import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by cuongnb on 11/18/16.
 */
public class AddNode implements Paintable {
    String name;
    Color background;

    private Font font = new Font("Sans Serif", Font.BOLD, 8);
    private Ellipse2D bounds;
    private int w = 50;
    private int h = 25;
    private int x;
    private int y;

    public AddNode(String name, int x, int y, Color baground) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.background = baground;
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
        g2.setColor(background);
        g2.fill3DRect((int) bounds.getX() - 5, (int) bounds.getY() - 5, (int) bounds.getWidth() + 10, (int) bounds.getHeight() + 10, true);

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
        Rectangle2D rect = new Rectangle2D.Float((int) bounds.getX() - 5, (int) bounds.getY() - 5, (int) bounds.getWidth() + 10, (int) bounds.getHeight() + 10);
        return rect.getBounds2D();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }
}
