package object;

/**
 * Created by cuongnb on 11/18/16.
 */
public class Relationship {
    private Node parent;
    private Node child;

    public Relationship(Node parent, Node child) {
        this.parent = parent;
        this.child = child;
    }

    public Paintable getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Paintable getChild() {
        return child;
    }

    public void setChild(Node child) {
        this.child = child;
    }
}
