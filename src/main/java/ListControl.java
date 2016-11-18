import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * Created by cuongnb on 11/18/16.
 */
public class ListControl extends JFrame
        implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    public ListControl() {
//        super(new BorderLayout());
        listModel = new DefaultListModel();
        listModel.addElement("Rename");
        listModel.addElement("Add Outcome");
        listModel.addElement("Add Probabilities");

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
        switch (index) {
            case 0:
                // a jframe here isn't strictly necessary, but it makes the example a little more real
                JFrame frame = new JFrame("InputDialog Example #1");
                // prompt the user to enter their name
                String name = JOptionPane.showInputDialog(frame, "What's your name?");
                // get the user's input. note that if they press Cancel, 'name' will be null
                System.out.printf("The user's name is '%s'.\n", name);
                break;
            case 1:
                ListOutcome.createAndShowGUI();
                break;

            case 2:
                break;

        }

    }


    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
//    public static void createAndShowGUI() {
//        //Create and set up the window.
//        JFrame frame = new JFrame("Add OutCome");
////        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        //Create and set up the content pane.
//        JComponent newContentPane = new ListControl();
//        newContentPane.setOpaque(true); //content panes must be opaque
//        frame.setContentPane(newContentPane);
//
//        frame.setLocationRelativeTo(null);
//        //Display the window.
//        frame.pack();
//        frame.setVisible(true);
//    }
//    public static void main(String[] args) {
//        ListControl listControl = new ListControl();
//        listControl.setSize(100, 100);
//        listControl.setResizable(true);
//        listControl.pack();
//        listControl.setVisible(true);
//    }
}