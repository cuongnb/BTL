import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by cuongnb on 11/18/16.
 */
public class AddOutcome extends JFrame
        implements ListSelectionListener, ActionListener {
    private JList list;
    private DefaultListModel listModel;

    private static final String addString = "Add";
    private static final String delString = "Del";
    private static final String okString = "Ok";
    private JButton fireButton;
    private JButton okButton;
    private JTextField outcomeName;

    public AddOutcome() {
        listModel = new DefaultListModel();

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton hireButton = new JButton(addString);
        AddListener hireListener = new AddListener(hireButton);
        hireButton.setActionCommand(addString);
        hireButton.addActionListener(hireListener);
        hireButton.setEnabled(false);

        fireButton = new JButton(delString);
        fireButton.setActionCommand(delString);
        fireButton.addActionListener(new DelListener());
        fireButton.setEnabled(false);

        okButton = new JButton(okString);
        okButton.setActionCommand(okString);
        okButton.addActionListener(this);
        okButton.setEnabled(false);


        outcomeName = new JTextField(10);
        outcomeName.addActionListener(hireListener);
        outcomeName.getDocument().addDocumentListener(hireListener);
        if (listModel.size() > 0) {
            String name = listModel.getElementAt(
                    list.getSelectedIndex()).toString();
            System.out.println(name);
        }
        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(fireButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(outcomeName);
        buttonPane.add(hireButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(okButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand() == "Ok") {
            if (listModel.size() > 0) {
                okButton.setEnabled(true);
                int index = list.getSelectedIndex();
                System.out.println("select: " + listModel.get(index));

                ProjectManagement.currentNode.sOutcome = new ArrayList<>();
                for (int i = 0; i < listModel.size(); i++) {
                    System.out.println(listModel.get(i).toString());
                    ProjectManagement.currentNode.sOutcome.add(listModel.get(i).toString());
                }
                this.dispose();
            } else {
                okButton.setEnabled(false);
            }

        }
    }


    class DelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                fireButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    //This listener is shared by the text field and the hire button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = outcomeName.getText();

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                outcomeName.requestFocusInWindow();
                outcomeName.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.insertElementAt(outcomeName.getText(), index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(outcomeName.getText());

            //Reset the text field.
            outcomeName.requestFocusInWindow();
            outcomeName.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                fireButton.setEnabled(false);
                okButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                fireButton.setEnabled(true);
                okButton.setEnabled(true);
            }
        }
    }
}