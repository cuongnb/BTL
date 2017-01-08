import object.Node;
import table.Table;

import javax.swing.*;
import java.awt.*;

/**
 * Created by cuongnb on 08/01/2017.
 */
public class ShowProbabities {

    public void showProbab(Point point) {
        Table table = new Table(ProjectManagement.currentNode);
        table.setLocation(point);
        table.setVisible(true);
    }
}
