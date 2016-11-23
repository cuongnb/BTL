import object.Node;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by cuongnb on 23/11/2016.
 */
public class Evidence extends JFrame
        implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;
    private Point point;
    private ArrayList<Node> nodes;

    public Evidence(Point point, ArrayList<Node> nodes) {
        this.point = point;
        this.nodes = nodes;
        listModel = new DefaultListModel();

        listModel.addElement("Select Node");
        for (Node node : nodes) {
            listModel.addElement(node.name);
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
        ListOutcome evidence = new ListOutcome(nodes.get(index - 1));
        evidence.setLocation(point);
        evidence.setSize(100, 100);
        evidence.setResizable(true);
        evidence.pack();
        evidence.setVisible(true);

    }
}