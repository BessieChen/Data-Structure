import java.util.ArrayList;
import java.util.Random;

public class Main3 {

    public static void main(String[] args) {

        int n = 20000000;

        ArrayList<Integer> testData = new ArrayList<>(n);
        for(int i = 0 ; i < n ; i ++)
            testData.add(i);

        // Test AVL
        long startTime = System.nanoTime();

        AVLTree<Integer, Integer> avl = new AVLTree<>();
        for (Integer x: testData)
            avl.add(x, null);

        long endTime = System.nanoTime();

        double time = (endTime - startTime) / 1000000000.0;
        System.out.println("AVL: " + time + " s");//AVL: 4.29451502 s


        // Test RBTree
        startTime = System.nanoTime();

        RBTree<Integer, Integer> rbt = new RBTree<>();
        for (Integer x: testData)
            rbt.add(x, null);

        endTime = System.nanoTime();

        time = (endTime - startTime) / 1000000000.0;
        System.out.println("RBTree: " + time + " s");//RBTree: 4.230451755 s
    }
}

//当我们是按顺序add时，BST会退化成链表，BST适合随机数的add
//AVl的高度一定是lgN，所以AVL适合查找，add的话因为旋转操作会牺牲一点时间。
//RBTree高度是两倍lgN，所以查找会慢，更适合add
//总之，综合四个操作:增删改查平均来看，RBTree的统计性能更优。