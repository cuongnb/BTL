package table;

import object.Node;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

/**
 * Created by cuongnb on 11/18/16.
 */
public class ViewNode extends JFrame {


    public ViewNode(Node node) {
        super("Node: " + node.name);
        Object[][] objects1 = new Object[node.sOutcome.size()][2];

        for (int i = 0; i < node.sOutcome.size(); i++) {
            objects1[i][0] = node.sOutcome.get(i);
            objects1[i][1] = node.outcomeValues[i];
        }
        DefaultTableModel dm = new DefaultTableModel();
        dm.setDataVector(objects1,
                new Object[]{"SNo.", "Probabilities"});

        JTable table = new JTable(dm) {
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
        };

        JScrollPane scroll = new JScrollPane(table);
        getContentPane().add(scroll);
        setSize(400, 120);
    }

}
