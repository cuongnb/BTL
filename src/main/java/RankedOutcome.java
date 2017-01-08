import util.Constant;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cuongnb on 07/12/2016.
 */
public class RankedOutcome extends JFrame
        implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;
    private Point point;

    public RankedOutcome(Point point, ArrayList<String> array) {
        this.point = point;
        listModel = new DefaultListModel();

        listModel.addElement("Select ...");
        for (String s : array) {
            listModel.addElement(s);
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
        int index = listSelectionEvent.getLastIndex();
        System.out.println(listModel.get(index));
        System.out.println(index);
        this.dispose();

        ArrayList<String> arrayList = new ArrayList<>();
        if (index == 1) {
            for (Constant.threeOutcome outcome : Arrays.asList(Constant.threeOutcome.values())) {
                arrayList.add(outcome.toString());
            }
            RankedOutcome evidence = new RankedOutcome(point, arrayList);
            evidence.setLocation(point);
            evidence.setSize(200, 200);
            evidence.setResizable(true);
            evidence.pack();
            evidence.setVisible(true);

        } else if (index == 2) {

            for (Constant.fiveOutcome outcome : Arrays.asList(Constant.fiveOutcome.values())) {
                arrayList.add(outcome.toString());
            }
            RankedOutcome evidence = new RankedOutcome(point, arrayList);
            evidence.setLocation(point);
            evidence.setSize(200, 200);
            evidence.setResizable(true);
            evidence.pack();
            evidence.setVisible(true);
        } else if (index == 3) {
            for (Constant.sevenOutcome outcome : Arrays.asList(Constant.sevenOutcome.values())) {
                arrayList.add(outcome.toString());
            }

            RankedOutcome evidence = new RankedOutcome(point, arrayList);
            evidence.setLocation(point);
            evidence.setSize(200, 200);
            evidence.setResizable(true);
            evidence.pack();
            evidence.setVisible(true);
        }
    }
    public static void main(String[] args) {
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("x:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("y:"));
        myPanel.add(yField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println("x value: " + xField.getText());
            System.out.println("y value: " + yField.getText());
        }
    }
}