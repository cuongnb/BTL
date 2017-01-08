import object.Node;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by cuongnb on 08/01/2017.
 */
public class SelectRank extends JFrame
        implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;
    private Point point;
    private ArrayList<Node> nodes;

    public SelectRank(Point point) {
        this.point = point;
        this.nodes = nodes;
        listModel = new DefaultListModel();

        listModel.addElement("Select rank");
        listModel.addElement("3");
        listModel.addElement("5");
        listModel.addElement("7");

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
        if (index == 1) {
            ProjectManagement.currentNode.sOutcome = new ArrayList<>();
            ProjectManagement.currentNode.sOutcome.add("Low");
            ProjectManagement.currentNode.sOutcome.add("Medium");
            ProjectManagement.currentNode.sOutcome.add("High");
        } else if (index == 2) {
            ProjectManagement.currentNode.sOutcome = new ArrayList<>();
            ProjectManagement.currentNode.sOutcome.add("very Low");
            ProjectManagement.currentNode.sOutcome.add("Low");
            ProjectManagement.currentNode.sOutcome.add("Medium");
            ProjectManagement.currentNode.sOutcome.add("High");
            ProjectManagement.currentNode.sOutcome.add("very High");

        } else if (index == 3) {
            ProjectManagement.currentNode.sOutcome = new ArrayList<>();
            ProjectManagement.currentNode.sOutcome.add("extremely low");
            ProjectManagement.currentNode.sOutcome.add("very Low");
            ProjectManagement.currentNode.sOutcome.add("Low");
            ProjectManagement.currentNode.sOutcome.add("Medium");
            ProjectManagement.currentNode.sOutcome.add("High");
            ProjectManagement.currentNode.sOutcome.add("very High");
            ProjectManagement.currentNode.sOutcome.add("extremely High");
        }
        this.dispose();

        InputRank inputRank = new InputRank(point);
        inputRank.setLocation(point);
        inputRank.setSize(200, 200);
        inputRank.setResizable(true);
        inputRank.pack();
        inputRank.setVisible(true);
    }
}
