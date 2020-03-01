import java.util.ArrayList;
import java.util.Random;

public class Main {//main函数的测试中已经测试过BST中的全部函数！

    public static void main(String[] args) {

        BST<Integer> bst = new BST<>();
        //int[] nums = {5,2,8,6,4,3,3,4,5,2,4,6,7,93,4,6,3,2,4,6,3,25,3};
        //int[] nums = {5,4,6,3,7,2,8,1,9,0};
        int[] nums = {5,3,7,4,6,1,9,2,8};
        for(int num:nums)
        {
            bst.add(num);
        }

        //System.out.println(bst);

        bst.preOrder_R();
        System.out.println();

        bst.preOrder_NR();
        System.out.println("Above is pre Order.");

        bst.inOrder_R();
        System.out.println();

        bst.inOrder_NR();
        System.out.println("Above is in Order.");

        bst.postOrder_R();
        System.out.println();

        bst.postOrder_NR();
        System.out.println();

        //bst.postOrder_NR2();
        System.out.println("Above is post Order.");

        bst.levelOrder();
        System.out.println();

        System.out.println("bst.minimum(): "+bst.minimum());
        System.out.println();

        System.out.println("bst.maximum(): "+bst.maximum());
        System.out.println();

        System.out.println("bst.minimum_NR(): "+bst.minimum());
        System.out.println();

        System.out.println("bst.maximum_NR(): "+bst.maximum());
        System.out.println();

        System.out.println(bst);
        System.out.println("Before remove 4: bst.contains(4); ");
        System.out.println(bst.contains(4));
        bst.remove(4);
        System.out.println("bst.remove(4); ");
        System.out.println(bst);
        System.out.println("After remove 4: bst.contains(4); ");
        System.out.println(bst.contains(4));

        System.out.println(bst);
        System.out.println("Before remove 6: bst.contains(6); ");
        System.out.println(bst.contains(6));
        bst.remove(6);
        System.out.println("bst.remove(6); ");
        System.out.println(bst);
        System.out.println("After remove 6: bst.contains(6); ");
        System.out.println(bst.contains(6));


        //---------------------------------------------
        //test removeMin.
        BST<Integer> bst2 = new BST<>();
        Random random = new Random();

        int n = 100;
        for(int i = 0; i < n; i++)
        {
            bst2.add(random.nextInt(10000));
        }

        ArrayList<Integer> nums2 = new ArrayList<>();
        while(!bst2.isEmpty())
        {
            nums2.add(bst2.removeMin());

        }

        System.out.println("\n"+nums2);

        for(int i = 1; i < nums2.size(); i++)
        {
            if(nums2.get(i-1) > nums2.get(i))
            {
                throw new IllegalArgumentException("Error");
            }
        }
        System.out.println("removeMin test completed.");

        //---------------------------------------------
        //test removeMax.
        for(int i = 0; i < n; i++)
        {
            bst2.add(random.nextInt(10000));
        }

        nums2 = new ArrayList<>();
        ArrayList nums3 = new ArrayList();
        while(!bst2.isEmpty())
        {
            nums2.add(bst2.removeMax());
        }
        System.out.println("\n"+nums2);

        for(int i = 1; i < nums2.size(); i++)
        {
            if(nums2.get(i-1) < nums2.get(i))
            {
                throw new IllegalArgumentException("Error");
            }
        }
        System.out.println("removeMax test completed.");

        //---------------------------------------------
    }
}
