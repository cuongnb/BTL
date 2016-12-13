package util;

import object.Node;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by cuongnb on 14/12/2016.
 */
public class Model extends JFrame {
//    private JTextField filename = new JTextField();

    public static void save(ArrayList<Node> nodes, String name) {
        try {
            PrintWriter writer = new PrintWriter(name);
            for (Node node : nodes) {
                writer.println(node.toString());
            }
            writer.close();
        } catch (IOException e) {
            // do something
        }
    }

    public void readFile() {
        JFileChooser c = new JFileChooser();
        // Demonstrate "Open" dialog:
        int rVal = c.showOpenDialog(Model.this);
        if (rVal == JFileChooser.APPROVE_OPTION) {
//            filename.setText(c.getSelectedFile().getName());
            System.out.println(c.getCurrentDirectory().toString() + "/" + c.getSelectedFile().getName());
        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
//            filename.setText("You pressed cancel");
        }
    }

    public static void main(String[] args) {
        Model model = new Model();
        model.readFile();
    }

}
