package object;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by cuongnb on 11/18/16.
 */
public class Relationship implements Paintable {
    @Override
    public void paint(JComponent parent, Graphics2D g2d) {

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
}
