package object;

import jayes.BayesNet;
import jayes.BayesNode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

/**
 * Created by cuongnb on 11/18/16.
 */
public class Node implements Paintable {
    public BayesNode node;
    public String name;
    public ArrayList<String> sOutcome = new ArrayList<>();
    public double[] outcomeValues;
    public ArrayList<Node> nodeParent = new ArrayList<>();
    public ArrayList<Node> nodeChild = new ArrayList<>();
    public Object[][] data;
    public String[] sColumns;
    public Color background = Color.WHITE;


    private Font font = new Font("Sans Serif", Font.BOLD, 12);
    private Ellipse2D bounds;
    private int w = 90;
    private int h = 40;

    public Node(String name, Font font, int x, int y) {
        this.name = name;
        this.font = font;
        //Ellipse2D.Double(double x, double y, double w, double h)
        bounds = new Ellipse2D.Double(x, y, w, h);
        sOutcome = new ArrayList<String>();
        nodeParent = new ArrayList<Node>();
        nodeChild = new ArrayList<>();
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
        g2.setColor(background);
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

    public void setNode(BayesNet net) {
        node = net.createNode(name);
        System.out.println(name + "\n");
        String[] outcomes = new String[sOutcome.size()];
        for (int i = 0; i < outcomes.length; i++) {
            outcomes[i] = sOutcome.get(i);
        }
        node.addOutcomes(outcomes);
        if (nodeParent.size() > 0) {
            List<BayesNode> bayesNodes = new ArrayList<>();
            for (Node node : nodeParent) {
                bayesNodes.add(node.node);
            }
            node.setParents(bayesNodes);
        }

        // -1 tru cho column chua sOutcome
        double[] values = new double[(sOutcome.size()) * (sColumns.length - 1)];
        int i = 0;
        int row = 0;
        int column = 0;
        for (i = 0; i < values.length; i++) {
            row = i % data.length;
            column = i / data.length;
//            System.out.println(row + " -- " + column);
            values[i] = Double.parseDouble((String) data[row][column + 1]);
            System.out.print(values[i] + "\t");
        }
        node.setProbabilities(values);
    }

    public void setData() {
        data = new Object[sOutcome.size()][sColumns.length];
        for (int m = 0; m < sOutcome.size(); m++) {
            for (int n = 0; n < sColumns.length; n++) {
                if (n == 0) {
                    data[m][n] = sOutcome.get(m);
                } else {
                    data[m][n] = "";
                }
            }
        }
        System.out.println("number data: " + data.length * data[0].length);
    }

    public void setValueOutcomes(double[] result) {
        outcomeValues = result;
    }

    public void setsColumns(String[] sColumns) {
        this.sColumns = sColumns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Node name=" + name + "\t" +
                "sOutcome=" + printOutcome() + "\t" +
                "nodeParent=" + printParent() + "\t" +
                "nodeChild=" + printChild() + "\t" +
                "data=" + " " + printData() + "\t" +
                "sColumns=" + printColumns() + "\n";
    }

    public Node(String sNode) {
        String[] field = sNode.split("\t");
        String name = field[0].toString().split("name=").toString().trim();
        String sOutcome = field[1].split("sOutcome=").toString().trim();
        String nodeParent = field[2].split("sOutcome=").toString().trim();
        String nodeChild = field[3].split("sOutcome=").toString().trim();
        String data = field[4].split("sOutcome=").toString().trim();
        String sColumns = field[5].split("sOutcome=").toString().trim();
        this.name = name;

        ArrayList<String> listOutcome = new ArrayList<>();
        if (sOutcome.length() > 0) {
            String[] outcomes = sOutcome.split("---");
            for (String s : outcomes) {
                listOutcome.add(s);
            }
        }
        this.sOutcome = listOutcome;

        ArrayList<Node> parent = new ArrayList<Node>();
        if (sOutcome.length() > 0) {
            String[] outcomes = sOutcome.split("---");
            for (String s : outcomes) {
                listOutcome.add(s);
            }
        }
        this.nodeParent = parent;

        ArrayList<Node> child = new ArrayList<Node>();
        if (sOutcome.length() > 0) {
            String[] outcomes = sOutcome.split("---");
            for (String s : outcomes) {
                listOutcome.add(s);
            }
        }
        this.nodeChild = child;

        String[] fieldColumns = sColumns.split("---");
        String[] columns = new String[fieldColumns.length];
        for (int i = 0; i < fieldColumns.length; i++) {
            columns[i] = fieldColumns[i].toString().trim();
        }
        this.sColumns = columns;

        Object[][] datas = new Object[listOutcome.size()][columns.length];
        String[] fieldColumn = sColumns.split("------");
        for (int m = 0; m < datas.length; m++) {
            String[] fieldRow = sColumns.split("---");
            for (int n = 0; n < datas[0].length; n++) {
                datas[m][n] = fieldRow[n].toString().trim();
            }
        }
        this.data = datas;

    }


    public String printOutcome() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" ");
        if (sOutcome.size() > 0) {
            for (String s : sOutcome) {
                buffer.append(s + "---");
            }
            buffer.replace(buffer.length() - 3, buffer.length(), "");
        }
        return buffer.toString();
    }

    public String printColumns() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" ");
        try {
            if (sColumns.length > 0) {
                for (String s : sColumns) {
                    buffer.append(s + "---");
                }
                buffer.replace(buffer.length() - 3, buffer.length(), "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public String printParent() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" ");
        if (nodeParent.size() > 0) {
            for (Node node : nodeParent) {
                buffer.append(node.name + "---");
            }
            buffer.replace(buffer.length() - 3, buffer.length(), "");
        }
        return buffer.toString();
    }

    public String printChild() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" ");
        if (nodeChild.size() > 0) {
            for (Node node : nodeChild) {
                buffer.append(node.name + "---");
            }
            buffer.replace(buffer.length() - 3, buffer.length(), "");
        }
        return buffer.toString();
    }

    public String printData() {
        StringBuffer buffer = new StringBuffer();
        if (data != null) {
            for (int m = 0; m < data.length; m++) {
                for (int n = 1; n < data[0].length; n++) {
                    buffer.append(data[m][n] + "---");
                }
                buffer.append("---");
            }
            buffer.replace(buffer.length() - 6, buffer.length(), "");
        } else {
            return "";
        }
        return buffer.toString();
    }
}
