package object;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by cuongnb on 11/8/16.
 */
public interface Paintable {
    public void paint(JComponent parent, Graphics2D g2d);

    public boolean contains(Point p);

    public void moveTo(Point2D p);

    public Rectangle2D getBounds();
}
