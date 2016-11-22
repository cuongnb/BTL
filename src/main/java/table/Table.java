package table;

import object.Node;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by cuongnb on 11/21/16.
 */
public class Table extends JFrame {


    public Table(Node currentNode) {
        super("Groupable Header Example");

        DefaultTableModel dm = new DefaultTableModel();
        dm.setDataVector(currentNode.data, currentNode.sColumns);
        System.out.println("data: " + currentNode.data.length * currentNode.data[0].length + " column: " + currentNode.sColumns.length);
        JTable table = new JTable(dm) {
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
        };

        TableColumnModel cm = table.getColumnModel();
        GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();

        if (currentNode.nodeParent.size() > 1) {

            // khoi tao arraylist
            ArrayList<ColumnGroup[]> columnGroups = new ArrayList<>();
            int column = 1;
            for (int i = 0; i < currentNode.nodeParent.size() - 1; i++) {
                column *= currentNode.nodeParent.get(i).sOutcome.size();
                columnGroups.add(new ColumnGroup[column]);
                System.out.println("line " + i + " is: " + column);
            }

            // name for header
            for (int i = 0; i < currentNode.nodeParent.size() - 1; i++) {
                for (int j = 0; j < columnGroups.get(i).length; j++) {
                    Node node = currentNode.nodeParent.get(i);
                    int numOutcomeNode = node.sOutcome.size();
                    columnGroups.get(i)[j] = new ColumnGroup(node.sOutcome.get(j % numOutcomeNode));
                    System.out.print(node.sOutcome.get(j % numOutcomeNode) + "\t");
                }
                System.out.println("");
            }

            // add head have relationship
            for (int i = 0; i < currentNode.nodeParent.size() - 1; i++) {
                if (i < currentNode.nodeParent.size() - 2) {
                    int numNextOutcome = currentNode.nodeParent.get(i + 1).sOutcome.size();
                    int n = 0;
                    int index = 0;
                    for (int j = 0; j < columnGroups.get(i).length; j++) {
                        for (; n < columnGroups.get(i + 1).length; n++) {
                            columnGroups.get(i)[j].add(columnGroups.get(i + 1)[n]);
                            index++;
                            if (index == numNextOutcome) {
                                index = 0;
                                n++;
                                break;
                            }
                        }
                    }
                } else {
                    int index = 0;
                    int count = 0;
                    int numLastOutcome = currentNode.nodeParent.get(currentNode.nodeParent.size() - 1).sOutcome.size();
                    for (int j = 1; j < currentNode.sColumns.length; j++) {
                        columnGroups.get(i)[index].add(cm.getColumn(j));
                        count++;
                        if (count == numLastOutcome) {
                            index++;
                            count = 0;
                        }
                    }
                }
            }

            for (ColumnGroup group : columnGroups.get(0)) {
                header.addColumnGroup(group);
            }
        }
        JScrollPane scroll = new JScrollPane(table);
        getContentPane().add(scroll);
        setSize(400, 220);


        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                System.out.println("cuongnb");
                System.out.println(table.getEditingColumn());

                TableModel tb = table.getModel();


                for (int m = 0; m < currentNode.sOutcome.size(); m++) {
                    for (int n = 0; n < currentNode.sColumns.length; n++) {
                        if (n == 0) {
                            continue;
                        } else {
                            Object o = tb.getValueAt(m, n);
                            String s = (String) o;
                            currentNode.data[m][n] = s;
                            System.out.print(s + "\t");
                        }
                    }
                    System.out.println("");
                }
            }
        });

    }
}
