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

    public void save(ArrayList<Node> nodes, String name) {
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

    public void readFile(ArrayList<Node> nodes) {
        JFileChooser c = new JFileChooser();
        // Demonstrate "Open" dialog:
        int rVal = c.showOpenDialog(Model.this);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            String filename = c.getCurrentDirectory().toString() + "/" + c.getSelectedFile().getName();
            System.out.println(filename);
            ReadFile readFile = new ReadFile();
            readFile.run(filename, nodes);
        }
    }

//    public static void main(String[] args) {
//        Model model = new Model();
//        model.readFile();
//    }

}
