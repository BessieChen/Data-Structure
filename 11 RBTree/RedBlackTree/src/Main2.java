import java.util.ArrayList;
import java.util.Random;

public class Main2 {

    public static void main(String[] args) {

        // int n = 20000000;
        int n = 20000000;

        Random random = new Random(n);
        ArrayList<Integer> testData = new ArrayList<>(n);
        for(int i = 0 ; i < n ; i ++)
            testData.add(random.nextInt(Integer.MAX_VALUE));

        // Test BST
        long startTime = System.nanoTime();

        BST<Integer, Integer> bst = new BST<>();
        for (Integer x: testData)
            bst.add(x, null);

        long endTime = System.nanoTime();

        double time = (endTime - startTime) / 1e9;
        System.out.println("BST: " + time + " s");//BST: 32.612055636 s


        // Test AVL
        startTime = System.nanoTime();

        AVLTree<Integer, Integer> avl = new AVLTree<>();
        for (Integer x: testData)
            avl.add(x, null);

        endTime = System.nanoTime();

        time = (endTime - startTime) / 1e9;
        System.out.println("AVL: " + time + " s");//AVL: 38.951133643 s


        // Test RBTree
        startTime = System.nanoTime();

        RBTree<Integer, Integer> rbt = new RBTree<>();
        for (Integer x: testData)
            rbt.add(x, null);

        endTime = System.nanoTime();

        time = (endTime - startTime) / 1e9;
        System.out.println("RBTree: " + time + " s");//RBTree: 38.708350799 s
    }
}

//BST的时间比较快：首先我们的输入数据是随机的，导致了BST不太可能退化成链表。同时BST没有什么复杂的旋转操作。