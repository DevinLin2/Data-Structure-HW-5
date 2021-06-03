public class Node {

  private String name;
  private int number;
  private int height;
  private Node left;
  private Node right;
  private Node parent;

  public Node(String n, int num) {
    name = n;
    number = num;
    left = null;
    right = null;
    parent = null;
    height = 0;
  }

  /*
  Simple O(1) getter method
  */
  public String getName() {
    return name;
  }

  /*
  Simple O(1) getter method
  */
  public int getNum() {
    return number;
  }

  /*
  Simple O(1) getter method
  */
  public Node getParent() {
    return parent;
  }

  /*
  Simple O(1) getter method
  */
  public Node getLeft() {
    return left;
  }

  /*
  Simple O(1) getter method
  */
  public Node getRight() {
    return right;
  }

  /*
  Simple O(1) getter method
  */
  public int getHeight() {
    return height;
  }

  /*
  Simple O(1) setter method
  */
  public void setParent(Node newNode) {
    parent = newNode;
  }

  /*
  Simple O(1) setter method
  */
  public void setLeft(Node newNode) {
    left = newNode;
  }

  /*
  Simple O(1) setter method
  */
  public void setRight(Node newNode) {
    right = newNode;
  }

  /*
  Simple O(1) setter method
  */
  public void setHeight(int newHeight) {
    height = newHeight;
  }

  /*
  Simple O(1) setter method
  */
  public void setName(String name) {
    this.name = name;
  }

  /*
  Simple O(1) setter method
  */
  public void setNum(int num) {
    number = num;
  }

}
