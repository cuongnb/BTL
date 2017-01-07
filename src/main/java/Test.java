
import jayes.BayesNet;
import jayes.BayesNode;
import jayes.inference.IBayesInferer;
import jayes.inference.junctionTree.JunctionTreeAlgorithm;
import jayes.inference.junctionTree.JunctionTreeBuilder;
import jayes.util.triangulation.MinFillIn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuongnb on 11/7/16.
 */
public class Test {
    public static BayesNet net = new BayesNet();
    public static JunctionTreeBuilder builder = JunctionTreeBuilder.forHeuristic(new MinFillIn());
    public static JunctionTreeAlgorithm algo = new JunctionTreeAlgorithm();

    public static void run(String[] args) {
        BayesNode a = net.createNode("a");
        a.addOutcomes("true", "false");
//        String[] list = new String[2];
//        list[0] = "true";
//        list[1] = "false";
//        a.addOutcomes(list);

        a.setProbabilities(0.2, 0.8);
        BayesNode b = net.createNode("b");
        b.addOutcomes("one", "two", "three");
        b.setParents(Arrays.asList(a));

        b.setProbabilities(
                0.1, 0.4, 0.5, /* a == true*/
                0.3, 0.4, 0.3 /* a == false*/
        );

//        double[] bProbabilities = new double[6];
//        bProbabilities[0] = 0.1;
//        bProbabilities[1] = 0.4;
//        bProbabilities[2] = 0.5;
//        bProbabilities[3] = 0.3;
//        bProbabilities[4] = 0.4;
//        bProbabilities[5] = 0.3;
//
//        b.setProbabilities(bProbabilities);

        BayesNode c = net.createNode("c");
        c.addOutcomes("true", "false");
        c.setParents(Arrays.asList(a, b));
        c.setProbabilities(
                /* a == true*/
                0.1, 0.9, /* b == one*/
                0.8, 0.2, /* b == two*/
                0.5, 0.5,/* b == three*/
                /* a == false*/
                0.7, 0.3, /* b == one*/
                0.6, 0.4,/*  b == two*/
                0.3, 0.7 /* b == three*/
        );


        IBayesInferer inferer = new JunctionTreeAlgorithm();
        inferer.setNetwork(net);

        Map<BayesNode, String> evidence = new HashMap<BayesNode, String>();
        evidence.put(a, "false");
        evidence.put(b, "three");
        inferer.setEvidence(evidence);

        double[] beliefsC = inferer.getBeliefs(c);

        System.out.println(beliefsC[0] + " ---- " + beliefsC[1]);
        algo.getFactory().setFloatingPointType(float.class);
        algo.setNetwork(net);

//        JunctionTreeAlgorithm algo = new JunctionTreeAlgorithm();
//        algo.getFactory().setUseLogScale(true);

//        JunctionTreeAlgorithm algo = new JunctionTreeAlgorithm();
        algo.setJunctionTreeBuilder(builder);
    }
}
