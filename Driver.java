import java.util.*;

public class Driver {
  public static void main(String[] args) {
    AVLTreePhBook phbook = new AVLTreePhBook();
    phbook.PhBInsert("billy", 145);
    phbook.PhBInsert("jean", 223);
    phbook.PhBInsert("jbob", 2456);
    // This wil result in a rebalance with a left rotation in the num tree
    // This will also result in a right-left rotation combination in the name tree
    // phbook.print();
    // rotations in the right tree work!
    phbook.PhBInsert("zebra", 723);
    phbook.PhBInsert("yebra", 623);
    phbook.PhBInsert("xebra", 653);
    // This wil result in a rebalance with a right rotation in the name tree
    // This will also result in a left-right rotation combination in the num tree
    phbook.print();
    // rotations in the left tree work!
    // Therefore it is safe to conclude that my avl phonebook will rebalance after every insert.
    // Deleting root node of name tree and child of root node in num tree
    // phbook.PhBDelete("jean", 223);
    phbook.print();
    // The node was successfully deleted and the tree rebalanced itself!
    // Deleting node not in either tree
    phbook.PhBDelete("jean", 223);
    phbook.PhBDelete("george", 223);
    // This works as the system prints out that such an element does not exist
    // Testing both search methods
    System.out.println(phbook.PhBSearch("xebra").getNum());
    System.out.println(phbook.PhBSearch(623).getName());
    System.out.println(phbook.PhBSearch("jean"));
    System.out.println(phbook.PhBSearch(223));
    // Both search methods work as intended
  }
}
