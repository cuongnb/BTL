import object.Node;
import table.*;
import table.ViewNode;
import util.Constant;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cuongnb on 11/18/16.
 */
public class ListControl extends JFrame
        implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;
    private Point point;
    private boolean isRun = false;
    private String input;

    public ListControl(Point point, boolean isRun) {
//        super(new BorderLayout());
        this.isRun = isRun;
        this.point = point;
        listModel = new DefaultListModel();

        if (isRun) {
            listModel.addElement("....");
            listModel.addElement("View probabilities");
        } else {
            listModel.addElement("Select Option");
            listModel.addElement("Rename");
            listModel.addElement("Add Outcome");
            listModel.addElement("Add Probabilities");
        }

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
        System.out.println(index);
        this.dispose();
        if (isRun) {
            switch (index) {
                case 0:
                    break;
                case 1:
                    ViewNode frame = new ViewNode(ProjectManagement.currentNode);
                    frame.setVisible(true);
                    break;
            }
        } else {

            switch (index) {
                case 0:
                    break;
                case 1:
                    // a jframe here isn't strictly necessary, but it makes the example a little more real
                    JFrame frame = new JFrame("InputDialog Example #1");
                    // prompt the user to enter their name
                    String name = JOptionPane.showInputDialog(frame, "Node name?");
                    // get the user's input. note that if they press Cancel, 'name' will be null
                    System.out.printf("The user's name is '%s'.\n", name);
                    ProjectManagement.currentNode.name = name;
                    break;
                case 2:
//                    AddOutcome.createAndShowGUI(point);

                    String[] choices = {Constant.caseTest.rankedNode.toString(), Constant.caseTest.normally.toString()};
                    input = (String) JOptionPane.showInputDialog(null, "Choose now...",
                            "The Choice of ...", JOptionPane.QUESTION_MESSAGE, null, // Use
                            // default
                            // icon
                            choices, // Array of choices
                            choices[1]); // Initial choice
                    System.out.println(input);
                    if (input.equals(Constant.caseTest.normally)) {
                        AddOutcome addOutcome = new AddOutcome();
                        addOutcome.setLocation(point);
                        addOutcome.setSize(100, 100);
                        addOutcome.setResizable(true);
                        addOutcome.pack();
                        addOutcome.setVisible(true);
                    } else {
                        ArrayList<String> arrayList = new ArrayList<>();
                        for (Constant.outcome outcome : Arrays.asList(Constant.outcome.values())) {
                            arrayList.add(outcome.toString());
                        }
                        RankedOutcome evidence = new RankedOutcome(point, arrayList);
                        evidence.setLocation(point);
                        evidence.setSize(200, 200);
                        evidence.setResizable(true);
                        evidence.pack();
                        evidence.setVisible(true);
                    }
                    break;

                case 3:
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
                    break;
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