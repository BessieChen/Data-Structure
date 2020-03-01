import java.util.ArrayList;
import java.util.Random;

public class Main3 {

    public static void main(String[] args) {

        int n = 20000000;

        ArrayList<Integer> testData = new ArrayList<>(n);
        for(int i = 0 ; i < n ; i ++)
            testData.add(i);

        // Test BST: Exception in thread "main" java.lang.StackOverflowError
//        会报错溢出，因为一直向右子添加元素，太大了。
//        long startTime = System.nanoTime();
//
//        BST<Integer, Integer> bst = new BST<>();
//        for (Integer x: testData)
//            bst.add(x, null);
//
//        long endTime = System.nanoTime();
//
//        double time = (endTime - startTime) / 1000000000.0;
//        System.out.println("BST: " + time + " s");


        // Test AVL

        long startTime = System.nanoTime();

        AVLTree<Integer, Integer> avl = new AVLTree<>();
        for (Integer x: testData)
            avl.add(x, null);

        long endTime = System.nanoTime();

        double time = (endTime - startTime) / 1000000000.0;
        System.out.println("AVL: " + time + " s");


        // Test RBTree
        startTime = System.nanoTime();

        RBTree<Integer, Integer> rbt = new RBTree<>();
        for (Integer x: testData)
            rbt.add(x, null);

        endTime = System.nanoTime();

        time = (endTime - startTime) / 1000000000.0;
        System.out.println("RBTree: " + time + " s");
    }
}

/*
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=62840:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/bessie/Downloads/ByB/Data-Structure/RedBlackTree_Rewrite/out/production/RedBlackTree_Rewrite Main3
AVL: 4.4446561 s
RBTree: 4.185253694 s

Process finished with exit code 0
*/