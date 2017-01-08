import object.Node;
import table.Table;
import util.UtilMath;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * Created by cuongnb on 08/01/2017.
 */
public class InputRank extends JFrame
        implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;
    private Point point;

    public InputRank(Point point) {
        this.point = point;
        listModel = new DefaultListModel();

        listModel.addElement("Select rank");
        listModel.addElement("hand");
        listModel.addElement("recommend");

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);


        if (listModel.size() > 0) {
            String name = listModel.getElementAt(
                    list.getSelectedIndex()).toString();
            System.out.println(name);
        }
        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        System.out.println(listModel.get(listSelectionEvent.getLastIndex()));
        int index = listSelectionEvent.getLastIndex();
        System.out.println("this is index " + index);
        this.dispose();
        if (index == 1) {
            if (ProjectManagement.currentNode.sOutcome.size() > 0) {
                if (setOutcome()) {
                    Table table = new Table(ProjectManagement.currentNode);
                    table.setLocation(point);
                    table.setVisible(true);
                }
            } else {
                JFrame frame1 = new JFrame("InputDialog Example #1");
                JOptionPane.showMessageDialog(frame1, "vui long nhap outcome truoc!");
            }
        } else if (index == 2) {
            JTextField xField = new JTextField(5);
            JTextField yField = new JTextField(5);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("mean:"));
            myPanel.add(xField);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel(" standard deviation:"));
            myPanel.add(yField);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Please Enter mean and  standard_deviation Values", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                System.out.println("mean value: " + xField.getText());
                System.out.println(" standard_deviation value: " + yField.getText());
            }

            double mu = Double.parseDouble(xField.getText());
            double sigma = Double.parseDouble(yField.getText());
            System.out.println("mean: " + mu + "  standard deviation: " + sigma);
            setOutcome();
            if (ProjectManagement.currentNode.sOutcome.size() == 3) {
                double[] normals = new double[3];
                double sum = 0.0;
                for (int i = 0; i < 3; i++) {
                    normals[i] = Math.pow(UtilMath.NormalDistribution(0.5 * (i + 1), mu, sigma * sigma), ProjectManagement.currentNode.nodeParent.size());
//                    normals[i] = UtilMath.NormalDistribution(0.5 * (i + 1), mu, sigma * sigma);
                    sum += normals[i];
                }
                for (int i = 0; i < 3; i++) {
                    normals[i] = normals[i] / sum;
                }

                for (int i = 0; i < 3; i++) {
                    for (int j = 1; j < ProjectManagement.currentNode.sColumns.length; j++) {
                        ProjectManagement.currentNode.data[i][j] = normals[i] + "";
                    }
                }
            } else if (ProjectManagement.currentNode.sOutcome.size() == 5) {
                double[] normals = new double[5];
                double sum = 0.0;
                for (int i = 0; i < 5; i++) {
//                    normals[i] = UtilMath.NormalDistribution(0.5 * (i + 1), mu, sigma * sigma);
                    normals[i] = Math.pow(UtilMath.NormalDistribution(0.5 * (i + 1), mu, sigma * sigma), ProjectManagement.currentNode.nodeParent.size());
                    sum += normals[i];
                }
                for (int i = 0; i < 5; i++) {
                    normals[i] = normals[i] / sum;
                }

                for (int i = 0; i < 5; i++) {
                    for (int j = 1; j < ProjectManagement.currentNode.sColumns.length; j++) {
                        ProjectManagement.currentNode.data[i][j] = normals[i] + "";
                    }
                }

            } else if (ProjectManagement.currentNode.sOutcome.size() == 7) {
                double[] normals = new double[7];
                double sum = 0.0;
                for (int i = 0; i < 7; i++) {
                    normals[i] = Math.pow(UtilMath.NormalDistribution(0.5 * (i + 1), mu, sigma * sigma), ProjectManagement.currentNode.nodeParent.size());
//                    normals[i] = UtilMath.NormalDistribution(0.5 * (i + 1), mu, sigma * sigma);
                    sum += normals[i];
                }

                for (int i = 0; i < 7; i++) {
                    normals[i] = normals[i] / sum;
                }

                for (int i = 0; i < 7; i++) {
                    for (int j = 1; j < ProjectManagement.currentNode.sColumns.length; j++) {
                        ProjectManagement.currentNode.data[i][j] = normals[i] + "";
                    }
                }
            }
        }
    }

    public boolean setOutcome() {
        int numColumn = 1;
        for (Node node : ProjectManagement.currentNode.nodeParent) {
            if (node.sOutcome.size() > 0) {
                numColumn *= node.sOutcome.size();
            } else {
                JFrame frame1 = new JFrame("InputDialog Example #1");
                JOptionPane.showMessageDialog(frame1, "vui long nhap outcome truoc!");
                return false;
            }
        }
        if (ProjectManagement.currentNode.nodeParent.size() > 0) {
            Node lastNode = ProjectManagement.currentNode.nodeParent.get(ProjectManagement.currentNode.nodeParent.size() - 1);
            int n = lastNode.sOutcome.size();
            // tang column them 1 de o thu nhat de column thu nhat la chua thong tin cua no.
            String[] column = new String[numColumn + 1];
            column[0] = "No.";
            for (int i = 1; i < column.length; i++) {
                column[i] = lastNode.sOutcome.get((i - 1) % n);
                System.out.println("-------" + lastNode.sOutcome.get((i - 1) % n));
            }
            ProjectManagement.currentNode.setsColumns(column);
            ProjectManagement.currentNode.setData();
            System.out.println("column: " + column.length);
        } else {
            String[] column = new String[2];
            column[0] = "No.";
            column[1] = "Probabilities";
            ProjectManagement.currentNode.setsColumns(column);
            ProjectManagement.currentNode.setData();
            System.out.println("column: " + column.length);
        }
        return true;
    }
}
