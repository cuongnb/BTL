package util;

import object.Node;
import object.Relationship;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by cuongnb on 14/12/2016.
 */
public class ReadFile {


    public void run(String FILENAME, ArrayList<Node> nodes, ArrayList<Relationship> openRelationships) {

        BufferedReader br = null;
        FileReader fr = null;

        try {

            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;

            br = new BufferedReader(new FileReader(FILENAME));

            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                if (sCurrentLine.length() > 0) {
//                    nodes.add(new Node(sCurrentLine));
                    if (sCurrentLine.contains("relationship: ")) {
                        String[] nameNode = sCurrentLine.split("relationship: ")[1].split("---");

                        Node parent = null, child = null;
                        boolean flag = false;
                        for (Node node : nodes) {
                            if (nameNode[0].equals(node.name)) {
                                parent = node;
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            for (Node node : nodes) {
                                if (nameNode[1].equals(node.name)) {
                                    child = node;
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag == false) {
                                parent.nodeChild.add(child);
                                child.nodeParent.add(parent);
                                openRelationships.add(new Relationship(parent, child));
                                System.out.println("add relationship success" + " parent: " + parent.name + " child: " + child.name);
                            }
                        }

                    } else {
                        String[] fields = sCurrentLine.split("\t");
                        String name = fields[0].split("name=")[1];
                        String datas = fields[4].split("data= ")[1];
                        String[] rows = datas.split("------");
                        int column = rows[0].split("---").length;
                        Object[][] newData = new Object[rows.length][column];
                        for (int m = 0; m < rows.length; m++) {
                            String[] element = rows[m].split("---");
                            for (int n = 0; n < column; n++) {
                                newData[m][n] = element[n];
                            }
                        }
//                     Node name=a	sOutcome= true---false	nodeParent=
//                     nodeChild= 	data= 0.3---0.2---0.3---0.4---0.5---0.9------0.7---0.8---0.7---0.6---0.5---0.1
//                     	sColumns= No.---Probabilities
                        String[] sColumns = fields[5].split("sColumns= ")[1].split("---");
                        String[] outcome = fields[1].split("sOutcome= ")[1].split("---");
                        ArrayList<String> sOutcome = new ArrayList<>();
                        for (String s : outcome) {
                            sOutcome.add(s);
                        }
                        Node node = new Node(name, 10, 60);
                        node.sColumns = sColumns;
                        node.sOutcome = sOutcome;
                        node.data = newData;
                        nodes.add(node);
                        System.out.println("Add node success " + "name: " + node.name + "numberOutcome: " + node.sOutcome.size() + " column: " + node.sColumns.length);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

}