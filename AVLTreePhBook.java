import java.util.*;

public class AVLTreePhBook {

  private nameTreeNode nameTreeRoot;
  public numTreeNode numTreeRoot;

  private class nameTreeNode extends Node{

    public nameTreeNode(String n, int num) {
      super(n, num);
    }

  }

  public class numTreeNode extends Node{

    public numTreeNode(String n, int num) {
      super(n, num);
    }

  }

  public AVLTreePhBook() {
    nameTreeRoot = null;
    numTreeRoot = null;
  }

  /**
  Constant time O(1) runtime function to get the height of the node in question by using its getter method.
  **/
  private int height(Node node) {
    if (node == null) {
      return -1;
    }
    return node.getHeight();
  }

  /**
  Constant time O(1) runtime function to set the height of the node in question by using its setter method.
  It takes the max height of either the left or right child and adds one to it to account for itself.
  **/
  private void updateHeight(Node node) {
    node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
  }

  /**
  Constant time O(1) runtime function to get the balance of the node in question. The formula for balance is the height of the right node
  minus the height of the left node
  **/
  private int getBalance(Node node) {
    if (node == null) {
      return 0;
    }
    return height(node.getRight()) - height(node.getLeft());
  }

  /**
  Constant time O(1) function to rotate the tree right about the given node.
  **/
  private Node rotateRight(Node node) {
    Node left = node.getLeft();
    Node leftRight = left.getRight();
    left.setRight(node);
    node.setLeft(leftRight);
    updateHeight(node);
    updateHeight(left);
    return left;
  }

  /**
  Constant time O(1) function to rotate the tree left about the given node.
  **/
  private Node rotateLeft(Node node) {
    Node right = node.getRight();
    Node rightLeft = right.getLeft();
    right.setLeft(node);
    node.setRight(rightLeft);
    updateHeight(node);
    updateHeight(right);
    return right;
  }

  /**
  Constant time O(1) function to rebalance the tree about the given node.
  If the balance is greater than 1, meaning the right subtree of given node is greater than the height of the left subtree by a factor greater than one.
  In the case that the right child of the node's right child is greater than the left child of the node's right child, a simple left rotation of given node
  is enough to balance the tree. Otherwise a right rotation of the right child of node followed by a left rotation of given node will be performed to balance the tree.
  If the balance is less than -1, meaning the left subtree of given node is greater than the height of the right subtree by a factor greater than one.
  In the case that the left child of the node's left child is greater than the right child of the node's left child, a simple right rotation of given node
  is enough to balance the tree. Otherwise a left rotation of the left child of node followed by a right rotation of given node will be performed to balance the tree.
  **/
  private Node rebalance(Node node) {
    updateHeight(node);
    int balance = getBalance(node);
    if (balance > 1) {
      if (height(node.getRight().getRight()) > height(node.getRight().getLeft())) {
        node = rotateLeft(node);
      } else {
        node.setRight(rotateRight(node.getRight()));
        node = rotateLeft(node);
      }
    } else if (balance < -1) {
      if (height(node.getLeft().getLeft()) > height(node.getLeft().getRight())) {
        node = rotateRight(node);
      } else {
        node.setLeft(rotateLeft(node.getLeft()));
        node = rotateRight(node);
      }
    }
    return node;
  }

  /**
  If both root nodes are empty meaning that the entry is the first entry into the phonebook, this function makes two nodes and sets then as the root of the phonebook.
  Otherwise it calls the two insert helper functions to insert the new entry into the phonebook. My insert function throws a runtime exception when there is a collision
  and thus I catch the exception and have the system print out that there is a collision of elements. The worst case runtime of this function is O(2log(n)) which is
  just O(log(n)) since our tree is balanced and the longest traversal will only be the height of the tree.
  **/
  public boolean PhBInsert(String name, int num) {
    if (nameTreeRoot == null && numTreeRoot == null) {
      nameTreeRoot = new nameTreeNode(name, num);
      numTreeRoot = new numTreeNode(name, num);
      return true;
    }
    try {
      nameTreeRoot = (nameTreeNode)insert(nameTreeRoot, name, num);
      numTreeRoot = (numTreeNode)insert(numTreeRoot, num, name);
      return true;
    } catch (Exception e) {
      System.out.println("There already exists an entry in the phonebook with an identical name or number.");
      return false;
    }
  }

  /**
  This is the insert helper function for the tree that stores the entries based on names.
  It is given the root node of the name tree, the String key which is the element we are comparing and the number.
  It recursively traverses through the tree by comparing the key to the key of the node it currently is at, which is updated in the recursive call,
  until it finds a vacant spot (when the node it currently is at == null). Then the function returns a new node which allows previous recursive call to execute
  setting the parent node of the new node's left or right child to the new node. Then, the other remaining recursive calls will execute rebalance(node)
  on all the nodes that were traversed through which will maintain the balance of the AVL tree. It is worth noting that if an entry is found to match the name
  of the new entry, a runtime error will be thrown. This function has a worst case runtime of O(log(n)) as the maximun traversal length is the height of the tree.
  **/
  private Node insert(Node node, String key, int num) {
    if (node == null) {
      return new nameTreeNode(key, num);
    } else if (node.getName().compareTo(key) > 0) {
      node.setLeft(insert(node.getLeft(), key, num));
    } else if (node.getName().compareTo(key) < 0) {
      node.setRight(insert(node.getRight(), key, num));
    } else {
      throw new RuntimeException("Element already exists");
    }
    return rebalance(node);
  }

  /**
  This is the insert helper function for the tree that stores the entries based on numbers.
  It is given the root node of the number tree, the int key which is the element we are comparing and the name.
  It recursively traverses through the tree by comparing the key to the key of the node it currently is at, which is updated in the recursive call,
  until it finds a vacant spot (when the node it currently is at == null). Then the function returns a new node which allows previous recursive call to execute
  setting the parent node of the new node's left or right child to the new node. Then, the other remaining recursive calls will execute rebalance(node)
  on all the nodes that were traversed through which will maintain the balance of the AVL tree. It is worth noting that if an entry is found to match the number
  of the new entry, a runtime error will be thrown. This function has a worst case runtime of O(log(n)) as the maximun traversal length is the height of the tree.
  **/
  private Node insert(Node node, int key, String name) {
    if (node == null) {
      return new numTreeNode(name, key);
    } else if (node.getNum() > key) {
      node.setLeft(insert(node.getLeft(), key, name));
    } else if (node.getNum() < key) {
      node.setRight(insert(node.getRight(), key, name));
    } else {
      throw new RuntimeException("Element already exists");
    }
    return rebalance(node);
  }

  /**
  This function attempts to call the two delete helper methods. If the delete helper methods do not return null, that means the entry
  in question was successfully deleted. Otherwise it was not. The worst case runtime of this function is O(2log(n)) which is just O(log(n))
  as each delete function will have a worst case runtime of O(log(n)) and we are colling it twice.
  **/
  public boolean PhBDelete(String name, int num) {
    try {
      nameTreeRoot = (nameTreeNode)delete(nameTreeRoot, name, num);
      numTreeRoot = (numTreeNode)delete(numTreeRoot, num, name);
      return true;
    } catch (Exception e) {
      System.out.println("Such an entry does not exist in the phonebook");
      return false;
    }
  }

  /**
  This is the delete method for the tree that stored the entries based on names.
  It is given the root node of the name tree, the String key which is the element we are comparing, and the number.
  The function recursively traverses through the tree by comparing the key to the key of the node it currently is at, which is updated in the recursive call,
  until it either finds the entry we are attemping to delete (target node) or the entry is not found. If the entry is not found the function throws a runtime exception.
  If the entry is found and both the name and number matches, the function will then perform one of two possible operations. If the target node has 1 or no
  children, we will return its child or null respectively which will allow the previous recursive call to execute setting the child of the parent of the target node
  to the child of the target node or null. This will effectively delete the target node from the tree as there will be no references to it. Then the previous recursive calls execute
  rebalance which maintains the balance of the tree. If the target node has two children, we look for the smallest (leftmost) child of target node's right branch. We then copy that nodes
  elements into target node, effectively deleting it, and call another delete method to remove the leftmost child from the tree. The worst cose runtime of delete is O(log(n)) as the
  maximum number of possible traversals/recursive calls is the height of the tree.
  **/
  private Node delete(Node node, String key, int num) {
    //System.out.println(node.getRight().getName()); // the tree is not connected properly
    if (node == null) {
      throw new RuntimeException("Element does not exist");
      // return node;
    } else if (node.getName().compareTo(key) > 0) {
      node.setLeft(delete(node.getLeft(), key, num));
    } else if (node.getName().compareTo(key) < 0) {
      node.setRight(delete(node.getRight(), key, num));
    } else if (node.getNum() == num) {
      if (node.getLeft() == null || node.getRight() == null) {
        if (node.getLeft() == null) {
          node = node.getRight();
        } else {
          node = node.getLeft();
        }
      } else {
        Node leftmostChild = node.getRight();
        while (leftmostChild.getLeft() != null) {
          leftmostChild = leftmostChild.getLeft();
        }
        node.setName(leftmostChild.getName());
        node.setNum(leftmostChild.getNum());
        node.setRight(delete(node.getRight(), node.getName(), node.getNum()));
      }
    } else {
      throw new RuntimeException("Element does not exist");
    }
    if (node != null) {
      node = rebalance(node);
    }
    return node;
  }

  /**
  This is the delete method for the tree that stored the entries based on numbers.
  It is given the root node of the number tree, the int key which is the element we are comparing, and the name.
  The function recursively traverses through the tree by comparing the key to the key of the node it currently is at, which is updated in the recursive call,
  until it either finds the entry we are attemping to delete (target node) or the entry is not found. If the entry is not found the function throws a runtime exception.
  If the entry is found and both the name and number matches, the function will then perform one of two possible operations. If the target node has 1 or no
  children, we will return its child or null respectively which will allow the previous recursive call to execute setting the child of the parent of the target node
  to the child of the target node or null. This will effectively delete the target node from the tree as there will be no references to it. Then the previous recursive calls execute
  rebalance which maintains the balance of the tree. If the target node has two children, we look for the smallest (leftmost) child of target node's right branch. We then copy that nodes
  elements into target node, effectively deleting it, and call another delete method to remove the leftmost child from the tree. The worst cose runtime of delete is O(log(n)) as the
  maximum number of possible traversals/recursive calls is the height of the tree.
  **/
  private Node delete(Node node, int key, String name) {
    if (node == null) {
      throw new RuntimeException("Element does not exist");
      // return node;
    } else if (node.getNum() > key) {
      node.setLeft(delete(node.getLeft(), key, name));
    } else if (node.getNum() < key) {
      node.setRight(delete(node.getRight(), key, name));
    } else if (node.getName().equals(name)) {
      if (node.getLeft() == null || node.getRight() == null) {
        if (node.getLeft() == null) {
          node = node.getRight();
        } else {
          node = node.getLeft();
        }
      } else {
        Node leftmostChild = node.getRight();
        while (leftmostChild.getLeft() != null) {
          leftmostChild = leftmostChild.getLeft();
        }
        node.setName(leftmostChild.getName());
        node.setNum(leftmostChild.getNum());
        node.setRight(delete(node.getRight(), node.getNum(), node.getName()));
      }
    } else {
      throw new RuntimeException("Element does not exist");
    }
    if (node != null) {
      node = rebalance(node);
    }
    return node;
  }

  /**
  This function takes a name and searches the name tree for the target element. It does this through a while loop
  with the conditions that it either finds the target entry or that it doesnt when it hits null. The target or null is then
  returned. The Worst case runtime of this function is O(log(n)) as our tree is balanced and the worst case length of traversal
  will only be the height of the tree.
  **/
  public Node PhBSearch(String name) {
    Node current = nameTreeRoot;
    while (current != null && !current.getName().equals(name)) {
      if (current.getName().compareTo(name) > 0) {
        current = current.getLeft();
      } else {
        current = current.getRight();
      }
    }
    return current;
  }

  /**
  This function takes a number and searches the number tree for the target element. It does this through a while loop
  with the conditions that it either finds the target entry or that it doesnt when it hits null. The target or null is then
  returned. The Worst case runtime of this function is O(log(n)) as our tree is balanced and the worst case length of traversal
  will only be the height of the tree.
  **/
  public Node PhBSearch(int num) {
    Node current = numTreeRoot;
    while (current != null && current.getNum() != num) {
      if (current.getNum() > num) {
        current = current.getLeft();
      } else {
        current = current.getRight();
      }
    }
    return current;
  }

  /**
  O(n^2) quadratic runtime functions to print the phonebooks out in level order
  **/
  public void print() {
    int nameTreeHeight = height(nameTreeRoot);
    int numTreeHeight = height(numTreeRoot);
    System.out.println("----------------------------------");
    System.out.println("Printing name tree: ");
    for (int i = 1; i <= nameTreeHeight + 1; i++) {
      printLevel(nameTreeRoot, i);
      System.out.println();
    }
    System.out.println("----------------------------------");
    System.out.println("Printing number tree: ");
    for (int i = 1; i <= numTreeHeight + 1; i++) {
      printLevel(numTreeRoot, i);
      System.out.println();
    }
    System.out.println("----------------------------------");
  }

  public void printLevel(Node node, int level) {
    if (node == null) {
      return;
    }
    if (level == 1) {
      System.out.print(node.getName() + ": " + node.getNum() + " ");
    } else if (level > 1) {
      printLevel(node.getLeft(), level - 1);
      printLevel(node.getRight(), level - 1);
    }
  }
}
