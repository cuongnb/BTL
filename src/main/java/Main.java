import object.Node;
import object.Paintable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by cuongnb on 11/18/16.
 */
public class Main extends JPanel implements ActionListener {
    private ArrayList<Node> nodes = new ArrayList<>();
    private Font baseFont = new Font("Sans Serif", Font.BOLD, 12);
    private static final int W = 640;
    private static final int H = 480;

    private Paintable selectedShape;
    JCheckBox isAddParent = new JCheckBox("Add Parent");
    JButton addOutcome = new JButton("Next");
    JLabel numberOutcome = new JLabel("outcome : ");
    JTextField tfNumberOutcome = new JTextField(4);
    JPanel numberOutcomePanel = new JPanel();
    JButton run = new JButton("Run");
    JButton edit = new JButton("Edit");
    JButton setProbabilities = new JButton("Probabilities");

    public Main() {
        String fruit1 = "a";
        String fruit2 = "b";
        String fruit3 = "c";
        add(isAddParent);
        numberOutcomePanel.add(addOutcome);
        numberOutcomePanel.add(numberOutcome);
        numberOutcomePanel.add(tfNumberOutcome);
        numberOutcomePanel.add(addOutcome);
        add(numberOutcomePanel);
        add(setProbabilities);
        add(edit);
        add(run);
        isAddParent.addActionListener(this);
        addOutcome.addActionListener(this);
        edit.addActionListener(this);
        setProbabilities.addActionListener(this);

        Node person = new Node(fruit1, baseFont, 150, 50);
        addFruit(person);
        Node bubble = new Node(fruit2, baseFont, 150, 100);
        addFruit(bubble);
        bubble = new Node(fruit3, baseFont, 150, 150);
        addFruit(bubble);

        this.setFont(baseFont);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        for (Node f : nodes) {
            f.paint(this, g2);
        }
        g2.dispose();
    }

    protected void addFruit(Node fruit) {
        nodes.add(fruit);
        repaint();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame();

                f.add(new Main());
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });
    }
}
