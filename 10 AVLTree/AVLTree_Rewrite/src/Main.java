import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            //这就是按顺序放单词，于是BST就会退化成链表，但是AVL却不会受影响。
            Collections.sort(words);

            // Test BST
            long startTime = System.nanoTime();

//            BSTMap<String, Integer> bst = new BSTMap<>();
//            for (String word : words) {
//                if (bst.contains(word))
//                    bst.set(word, bst.get(word) + 1);
//                else
//                    bst.add(word, 1);
//            }
//
//            for(String word: words)
//                bst.contains(word);

            long endTime = System.nanoTime();

            double time = (endTime - startTime) / 1e9;
            System.out.println("BST: " + time + " s");//BST: 18.823374113 s
            System.out.println("------------------------------");

            // Test AVLTree
            startTime = System.nanoTime();

            AVLTree<String> avl = new AVLTree<>();
            for (String word : words) {
                if (!avl.contains(word))
                    avl.add(word);
            }

            for(String word: words)
                avl.contains(word);

            //System.out.println(avl.getSize());
            if(avl.isBalanceTree() && avl.isBST()) System.out.println("avl.isBalanceTree() && avl.isBST(): true");
            System.out.println("removeMin: "+ avl.removeMin());
            //System.out.println(avl.getSize());

            if(avl.isBalanceTree() && avl.isBST()) System.out.println("avl.isBalanceTree() && avl.isBST() 2: true");

            for(String word: words)
            {
                avl.remove(word);
                System.out.println(avl.getSize());
            }

            if(avl.isEmpty()) System.out.println("isEmpty: true.");

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1e9;
            System.out.println("AVL: " + time + " s");//AVL: 0.06702473 s
            System.out.println("------------------------------");

            // Test AVL_KV
            startTime = System.nanoTime();

            AVLTree_KV<String, Integer> avl_KV = new AVLTree_KV<>();
            for (String word : words) {
                if (avl_KV.contains(word))
                    avl_KV.set(word, avl_KV.get(word) + 1);
                else
                    avl_KV.add(word, 1);
            }

            for(String word: words)
                avl_KV.contains(word);

            //System.out.println(avl_KV.getSize());
            if(avl_KV.isBalanced() && avl_KV.isBST()) System.out.println("avl_KV.isBalanced() && avl_KV.isBST(): true");
            System.out.println("removeMin: "+ avl_KV.removeMin());

            if(avl_KV.isBalanced() && avl_KV.isBST()) System.out.println("avl_KV.isBalanced() && avl_KV.isBST() 2: true");

            for(String word: words)
            {
                avl_KV.remove(word);
            }
            if(avl_KV.isEmpty()) System.out.println("isEmpty: true.");

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1e9;
            System.out.println("AVL KV: " + time + " s");//AVL: 0.06702473 s
        }

        System.out.println();
    }
}