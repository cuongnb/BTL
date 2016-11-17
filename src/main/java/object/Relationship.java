package object;

/**
 * Created by cuongnb on 11/18/16.
 */
public class Relationship {
    private Paintable parent;
    private Paintable child;

    public Relationship(Paintable parent, Paintable child) {
        this.parent = parent;
        this.child = child;
    }

    public Paintable getParent() {
        return parent;
    }

    public void setParent(Paintable parent) {
        this.parent = parent;
    }

    public Paintable getChild() {
        return child;
    }

    public void setChild(Paintable child) {
        this.child = child;
    }
}
