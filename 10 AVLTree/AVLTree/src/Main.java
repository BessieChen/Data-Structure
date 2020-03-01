import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;


public class Main {


    public static void main(String[] args) {

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            //这就是按顺序放单词，于是BST就会退化成链表，但是AVL却不会受影响。
            Collections.sort(words);

            //---------------------------------------------------------------//---------------------------------------------------------------
            // Test BST
            long startTime = System.nanoTime();

            TreeSet<Boolean> set = new TreeSet<>();

            BST<String, Integer> bst = new BST<>();
            for (String word : words) {
                if (bst.contains(word))
                    bst.set(word, bst.get(word) + 1);
                else
                    bst.add(word, 1);
            }

            for(String word: words)
                set.add(bst.contains(word));
            System.out.println(set);

            for (String word : words) {
                if (bst.contains(word))
                {
                    bst.remove(word);
                }
            }

            set = new TreeSet<>();
            for(String word: words)
                set.add(bst.contains(word));
            System.out.println(set);

            long endTime = System.nanoTime();

            double time = (endTime - startTime) / 1e9;
            System.out.println("BST: " + time + " s");//BST: 18.823374113 s


            //---------------------------------------------------------------//---------------------------------------------------------------
            // Test AVL Tree
            startTime = System.nanoTime();

            AVLTree<String, Integer> avl = new AVLTree<>();
            for (String word : words) {
                if (avl.contains(word))
                    avl.set(word, avl.get(word) + 1);
                else
                    avl.add(word, 1);
            }

            set = new TreeSet<>();
            for(String word: words)
                set.add(avl.contains(word));
            System.out.println(set);

            for (String word : words) {
                if (avl.contains(word))
                {
                    avl.remove(word);
                }
            }

            set = new TreeSet<>();
            for(String word: words)
                set.add(avl.contains(word));
            System.out.println(set);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1e9;
            System.out.println("AVL: " + time + " s");//AVL: 0.06702473 s
        }

        System.out.println();
    }
}