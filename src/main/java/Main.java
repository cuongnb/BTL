import object.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by cuongnb on 11/18/16.
 */
public class Main extends JPanel implements ActionListener {

    ArrayList<Paintable> controls = new ArrayList<>();
    private boolean isClickAddArrow = false;

    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Relationship> relationships = new ArrayList<>();
    private ArrayList<Arrow> arrows = new ArrayList<>();

    private Font baseFont = new Font("Sans Serif", Font.BOLD, 12);
    private static final int W = 640;
    private static final int H = 480;
    private Paintable selectedShape;
    private Point2D offset;

    AddNode addNode = new AddNode("add node", 10, 10, Color.WHITE);
    AddArrow addArrow = new AddArrow(80, 10, Color.WHITE);

    Point pointStart = null;
    Point pointEnd = null;
    Arrow arrow = new Arrow();

    public Main() {

        controls.add(addNode);
        controls.add(addArrow);

        String fruit1 = "a";
        String fruit2 = "b";
        String fruit3 = "c";

        Node person = new Node(fruit1, baseFont, 150, 50);
        addFruit(person);
        Node bubble = new Node(fruit2, baseFont, 150, 100);
        addFruit(bubble);
        bubble = new Node(fruit3, baseFont, 150, 150);
        addFruit(bubble);

        this.setFont(baseFont);
        this.addMouseListener(new MouseAdapter() {

            Date pressedTime;
            long timeClicked;

            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON1) {

                    if (addNode.contains(e.getPoint())) {

                        // a jframe here isn't strictly necessary, but it makes the example a little more real
                        JFrame frame = new JFrame("InputDialog Example #1");
                        // prompt the user to enter their name
                        String name = JOptionPane.showInputDialog(frame, "What's your name?");
                        // get the user's input. note that if they press Cancel, 'name' will be null
                        System.out.printf("The user's name is '%s'.\n", name);


                        addNode.setBackground(Color.RED);
                        Node newNode = new Node(name, baseFont, 10, 60);
                        addFruit(newNode);
                    }
                    if (addArrow.contains(e.getPoint())) {
                        if (isClickAddArrow) {
                            isClickAddArrow = false;
                            addArrow.setBackground(Color.WHITE);
                        } else {
                            isClickAddArrow = true;
                            addArrow.setBackground(Color.RED);
                        }
                    }
                    System.out.println("Detect Mouse Left Click");

                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.println("Detect Mouse Right Click");
                }

                for (int i = 0; i < nodes.size(); i++) {
                    Paintable p = nodes.get(i);
                    if (p.contains(e.getPoint())) {
                        // select
                        selectedShape = p;
                        offset = new Point2D.Double(e.getX() - p.getBounds().getX(), e.getY() - p.getBounds().getY());

                    }
                }
                pointStart = e.getPoint();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Mouse Cursor Coordinates => X:" + e.getX() + " |Y:" + e.getY());
                Paintable nodeStart = null;
                Paintable nodeEnd = null;
                if (pointStart != null && pointEnd != null) {
                    for (Paintable p : nodes) {
                        if (p.contains(pointStart)) {
                            System.out.println("point start");
                            nodeStart = p;
                        }
                        if (p.contains(pointEnd)) {
                            System.out.println("pont end");
                            nodeEnd = p;
                        }
                    }
                    if (nodeEnd != null && nodeStart != null) {
                        relationships.add(new Relationship(nodeStart, nodeEnd));
                    }
                }
                addNode.setBackground(Color.WHITE);
                selectedShape = null;
                pointStart = null;
                pointEnd = null;
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                pointEnd = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                pointEnd = e.getPoint();
                if (selectedShape != null && !isClickAddArrow) {
                    Point2D d = new Point2D.Double(e.getX() - offset.getY(), e.getY() - offset.getY());
                    selectedShape.moveTo(d);
                }
                repaint();
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        drawArrow();
        for (Arrow arrow : arrows) {
            arrow.paint(this, g2);
        }

        for (Node f : nodes) {
            f.paint(this, g2);
        }
        for (Paintable p : controls) {
            p.paint(this, g2);
        }

        if (isClickAddArrow) {
            if (pointStart != null) {
                arrow.setTip(pointStart);
                arrow.setTail(pointEnd);
                arrow.paint(this, g2);
            }
        }

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(W, H);
    }

    protected void addFruit(Node fruit) {
        nodes.add(fruit);
        repaint();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame();

                f.add(new Main());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });
    }

    public void drawArrow() {
        arrows.clear();
//        System.out.println(relationships.size());
        for (Relationship relationship : relationships) {
            Paintable parent = relationship.getParent();
            Paintable child = relationship.getChild();
            Arrow arrow = new Arrow(new Point((int) parent.getBounds().getCenterX(), (int) (parent.getBounds().getBounds().getCenterY() + parent.getBounds().getHeight() / 2)),
                    new Point((int) child.getBounds().getCenterX(), (int) child.getBounds().getBounds().getY()));
            arrows.add(arrow);
        }
    }


    private void getPoint() {
        // TODO: 11/18/16 change position for arrow
    }
}
