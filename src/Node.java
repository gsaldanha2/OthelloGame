public class Node {

  private Node parent = null;
  private List<Node> children = new List<Node>;
  private Move move = null;
  
  public Node(Node parent, Move move) {
    this.move = move;
    this.parent = parent;
  }
  
  public Move getMove() {
    return move;
  }
  
  public Parent getParent() {
    return parent;
  }

  public void addChild(Node child) {
    children.add(child);
  } 
  
  public List<Node> getChildren() {
    return children;
  }
}
