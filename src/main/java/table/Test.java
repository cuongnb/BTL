package table;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

/**
 * Created by cuongnb on 11/18/16.
 */
public class Test extends JFrame {


    Test() {
        super("Groupable Header Example");
        Object[][] objects1 = new Object[2][6];
        for (int m = 0; m < objects1.length; m++) {
            for (int n = 0; n < objects1[0].length; n++) {
                objects1[m][n] = n + " " + m;
            }
        }
        DefaultTableModel dm = new DefaultTableModel();
        dm.setDataVector(objects1,
                new Object[]{"SNo.", "1", "2", "Native", "2", "3"});

        JTable table = new JTable(dm) {
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
        };

        TableColumnModel cm = table.getColumnModel();
        ColumnGroup g_name = new ColumnGroup("Name");
        g_name.add(cm.getColumn(1));
        g_name.add(cm.getColumn(2));
        ColumnGroup g_lang = new ColumnGroup("Language");
        g_lang.add(cm.getColumn(3));
        ColumnGroup g_other = new ColumnGroup("Others");
        g_other.add(cm.getColumn(4));
        g_other.add(cm.getColumn(5));
        g_lang.add(g_other);

        GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();
        header.addColumnGroup(g_name);
        header.addColumnGroup(g_lang);
        JScrollPane scroll = new JScrollPane(table);
        getContentPane().add(scroll);
        setSize(400, 120);


        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                System.out.println("cuongnb");
                System.out.println(table.getEditingColumn());


//                table.getColumnModel();
//                table.getEditingColumn();
//                System.out.println(table.getColumnModel().getColumn(0).get);
                TableModel tb = table.getModel();
                Object o = tb.getValueAt(1, 0);
                String s = (String) o;
                System.out.println(s);
            }
        });

    }


    public static void main(String[] args) {
        Test frame = new Test();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }

}