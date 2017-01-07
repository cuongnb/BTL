import object.Node;
import object.Relationship;
import util.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuongnb on 11/18/16.
 */
public class ProjectManagement {
    public static Node currentNode;

    // for evidence
    public static Map<Node, String> nodeStringMap = new HashMap<Node, String>();

    public static ArrayList<Node> openNodes = new ArrayList<>();
    public static ArrayList<Relationship> openRelationships = new ArrayList<>();

}
