import jayes.BayesNet;
import jayes.BayesNode;
import jayes.inference.IBayesInferer;
import jayes.inference.junctionTree.JunctionTreeAlgorithm;
import jayes.inference.junctionTree.JunctionTreeBuilder;
import jayes.util.triangulation.MinFillIn;
import object.*;
import util.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuongnb on 11/18/16.
 */
public class Main extends JPanel implements ActionListener {
    public boolean DEBUG = true;
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

    public BayesNet net;
    IBayesInferer inferer;

    boolean isOpen = false;

    public JunctionTreeBuilder builder = JunctionTreeBuilder.forHeuristic(new MinFillIn());
    public JunctionTreeAlgorithm algo = new JunctionTreeAlgorithm();

    AddNode addNode = new AddNode("add node", 10, 10, Color.WHITE);
    AddArrow addArrow = new AddArrow(80, 10, Color.WHITE);
    AddNode setBayes = new AddNode("Set Bayes", 150, 10, Color.BLUE);
    AddNode run = new AddNode("Run", 220, 10, Color.BLUE);
    AddNode evidence = new AddNode("Evidence", 290, 10, Color.BLUE);
    AddNode saveModel = new AddNode("Save Model", 360, 10, Color.BLUE);
    AddNode openModel = new AddNode("Open Model", 430, 10, Color.BLUE);
    AddNode snyc = new AddNode("SNYC", 500, 10, Color.BLUE);

    public boolean isRun = false;

    Point pointStart = null;
    Point pointEnd = null;
    Arrow arrow = new Arrow();

    private String input = "";

    public Main() {


        controls.add(addNode);
        controls.add(addArrow);
        controls.add(setBayes);
        controls.add(run);
        controls.add(evidence);
        controls.add(saveModel);
        controls.add(openModel);
        controls.add(snyc);

        this.setFont(baseFont);
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground();
                if (e.getButton() == MouseEvent.BUTTON1) {

                    if (addNode.contains(e.getPoint())) {
                        // a jframe here isn't strictly necessary, but it makes the example a little more real
                        JFrame frame = new JFrame("InputDialog Example #1");
                        // prompt the user to enter their name
                        String name = JOptionPane.showInputDialog(frame, "node name?");
                        // get the user's input. note that if they press Cancel, 'name' will be null
//                        System.out.printf("The user's name is '%s'.\n", name);

                        addNode.setBackground(Color.RED);
                        Node newNode = new Node(name, baseFont, 10, 60);
                        addFruit(newNode);
                    } else if (addArrow.contains(e.getPoint())) {
                        if (isClickAddArrow) {
                            isClickAddArrow = false;
                            addArrow.setBackground(Color.WHITE);
                        } else {
                            isClickAddArrow = true;
                            addArrow.setBackground(Color.RED);
                        }
                    } else if (setBayes.contains(e.getPoint())) {
                        net = new BayesNet();

                        for (Node node : nodes) {
                            node.setNode(net);
                        }
                        inferer = new JunctionTreeAlgorithm();
                        inferer.setNetwork(net);

                        if (ProjectManagement.nodeStringMap.size() > 0) {
                            Map<BayesNode, String> evidence = new HashMap<BayesNode, String>();
                            for (Map.Entry entry : ProjectManagement.nodeStringMap.entrySet()) {
                                Node node = (Node) entry.getKey();
                                String outcome = (String) entry.getValue();
                                evidence.put(node.node, outcome);
                                System.out.println(node.node.getName() + " -- " + outcome);
                            }
                            inferer.setEvidence(evidence);
                        }

                    } else if (run.contains(e.getPoint())) {
                        if (isRun) {
                            run.setBackground(Color.BLUE);
                            isRun = false;
                        } else {
                            run.setBackground(Color.RED);
                            isRun = true;
                        }
                    } else if (evidence.contains(e.getPoint())) {
                        Evidence evidence = new Evidence(e.getLocationOnScreen(), nodes);
                        evidence.setLocation(e.getLocationOnScreen());
                        evidence.setSize(200, 200);
                        evidence.setResizable(true);
                        evidence.pack();
                        evidence.setVisible(true);

                    } else if (saveModel.contains(e.getPoint())) {
                        JFrame frame = new JFrame("InputDialog Example #1");
                        // prompt the user to enter their name
                        String name = JOptionPane.showInputDialog(frame, "Model name?");
                        Model model = new Model();
                        model.save(nodes, relationships, name);
                    } else if (snyc.contains(e.getPoint())) {
                        syn();

                    } else if (openModel.contains(e.getPoint())) {
                        String[] choices = {"Create new", "Open File"};
                        input = (String) JOptionPane.showInputDialog(null, "Choose now...",
                                "The Choice of ...", JOptionPane.QUESTION_MESSAGE, null, // Use
                                // default
                                // icon
                                choices, // Array of choices
                                choices[1]); // Initial choice
                        System.out.println(input);
                        if (input.equals("Create new")) {

                        } else {
                            isOpen = true;
                            Model model = new Model();
                            model.readFile(ProjectManagement.openNodes, ProjectManagement.openRelationships);
                        }
                    }

                } else if (e.getButton() == MouseEvent.BUTTON3) {

                    if (evidence.contains(e.getPoint())) {
                        ShowEvidence showEvidence = new ShowEvidence();
                        showEvidence.setLocation(e.getPoint());
                        showEvidence.setSize(100, 100);
                        showEvidence.setResizable(true);
                        showEvidence.pack();
                        showEvidence.setVisible(true);

                    }

                    for (int i = 0; i < nodes.size(); i++) {
                        if (nodes.get(i).contains(e.getPoint())) {
                            // System.out.println("Detect Mouse Right Click");

                            ProjectManagement.currentNode = nodes.get(i);
                            if (DEBUG) {
                                System.out.println(ProjectManagement.currentNode.toString());
                            }

                            if (isRun) {
                                ProjectManagement.currentNode.background = Color.RED;
                                System.out.println(ProjectManagement.currentNode.name);
                                double[] beliefsC = inferer.getBeliefs(ProjectManagement.currentNode.node);
                                ProjectManagement.currentNode.setValueOutcomes(beliefsC);
                                for (int n = 0; n < ProjectManagement.currentNode.sOutcome.size(); n++) {
                                    System.out.println(ProjectManagement.currentNode.sOutcome.get(n) + " : " + beliefsC[n]);
                                }
                            }

                            ListControl listControl = new ListControl(e.getLocationOnScreen(), isRun);
                            listControl.setLocation(e.getLocationOnScreen());
                            listControl.setSize(200, 200);
                            listControl.setResizable(true);
                            listControl.pack();
                            listControl.setVisible(true);
                            break;
                        }
                    }
                }

                if (e.getButton() == MouseEvent.BUTTON1) {
                    boolean isHaveNode = false;
                    for (int i = 0; i < nodes.size(); i++) {
                        Paintable p = nodes.get(i);
                        if (p.contains(e.getPoint())) {
                            // select
                            selectedShape = p;
                            if (isRun) {
                                isHaveNode = true;
                                ProjectManagement.currentNode = nodes.get(i);
                            }

                            if (DEBUG) {
                                System.out.println(nodes.get(i).toString());
                            }

                            offset = new Point2D.Double(e.getX() - p.getBounds().getX(), e.getY() - p.getBounds().getY());
                        }
                        nodes.get(i).background = Color.WHITE;
                    }
                    pointStart = e.getPoint();
                    if (isHaveNode) {
                        ProjectManagement.currentNode.background = Color.RED;
                    }
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                System.out.println("Mouse Cursor Coordinates => X:" + e.getX() + " |Y:" + e.getY());
                if (!isOpen) {
                    Node nodeStart = null;
                    Node nodeEnd = null;
                    if (pointStart != null && pointEnd != null) {
                        for (Node p : nodes) {
                            if (p.contains(pointStart)) {
//                            System.out.println("point start");
                                nodeStart = p;
                            }
                            if (p.contains(pointEnd)) {
//                            System.out.println("pont end");
                                nodeEnd = p;
                            }
                        }
                        if (nodeEnd != null && nodeStart != null && nodeEnd != nodeStart) {
                            relationships.add(new Relationship(nodeStart, nodeEnd));
                            nodeStart.nodeChild.add(nodeEnd);
                            nodeEnd.nodeParent.add(nodeStart);

                            if (DEBUG) {
                                for (Node node : nodeEnd.nodeParent) {
                                    System.out.println(node.name);
                                }
                            }

                        }
                    }
                    addNode.setBackground(Color.WHITE);
                    selectedShape = null;
                    pointStart = null;
                    pointEnd = null;
                }
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


    public void setBackground() {
        for (Node node : nodes) {
            node.background = Color.WHITE;
        }
    }

    private void getPoint() {
        // TODO: 11/18/16 change position for arrow
    }

    private void syn() {
        nodes = new ArrayList<>();
        relationships = new ArrayList<>();
        repaint();
        for (Node node : ProjectManagement.openNodes) {
            nodes.add(node);
            repaint();
        }
        for (Relationship relationship : ProjectManagement.openRelationships) {
            relationships.add(relationship);
            repaint();
        }
    }

}
