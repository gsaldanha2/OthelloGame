import java.util.ArrayList;
import java.util.List;

public class Node {

  private Node parent = null;
  private List<Node> children = new ArrayList<Node>();
  private Move move = null;
  
  public Node(Node parent, Move move) {
    this.move = move;
    this.parent = parent;
  }
  
  public Node() {}

    @Override
    public boolean equals(Object obj) {
        Node n2 = (Node) obj;
        if((n2.getMove().row == this.getMove().row) && (n2.getMove().col == this.getMove().col)) {
            return true;
        }
        return false;
    }

    public Move getMove() {
    return move;
  }
  
  public Node getParent() {
    return parent;
  }

  public void addChild(Node child) {
    children.add(child);
  } 
  
  public List<Node> getChildren() {
    return children;
  }
}
