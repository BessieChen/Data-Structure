import java.util.ArrayList;

public class Main {

    private static double testSet(Set<String> set, String filename)
    {
        long startTime = System.nanoTime();
        System.out.println(filename);
        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile(filename,words))
        {
            System.out.println("Total words: "+words.size());
            for(String word : words)
            {
                set.add(word);
            }
            System.out.println("Totol different words: "+set.getSize());
        }

        long endTime = System.nanoTime();

        return (endTime-startTime)/1e9;
    }
    public static void main(String[] args) {
	// write your code here

//        System.out.println("Pride and Prejudice");
//        ArrayList<String> words1 = new ArrayList<>();
//        if(FileOperation.readFile("pride-and-prejudice.txt",words1));
//        System.out.println("Total words: "+words1.size());
//
//        //BSTSet<String> set1 = new BSTSet<>();
//        LinkedListSet<String> set1 = new LinkedListSet<>();
//        for(String word : words1)
//        {
//            set1.add(word);
//        }
//        System.out.println("Totol different words: "+set1.getSize());
//
//        System.out.println();
//        System.out.println("A tale of two cities");
//        ArrayList<String> words2 = new ArrayList<>();
//        if(FileOperation.readFile("a-tale-of-two-cities.txt",words2));
//        System.out.println("Total words: "+words2.size());
//
//        //BSTSet<String> set2 = new BSTSet<>();
//        LinkedListSet<String> set2 = new LinkedListSet<>();
//        for(String word : words2)
//        {
//            set2.add(word);
//        }
//        System.out.println("Totol different words: "+set2.getSize());

        //------------------------------------------------------------
        //String filename = "ride-and-prejudice.txt";
        String filename = "a-tale-of-two-cities.txt";

        BSTSet<String> aa = new BSTSet<>();
        double time1 = testSet(aa, filename);
        System.out.println("BST Set: "+ time1+" s");//0.131216826 s

        System.out.println();
        LinkedListSet<String> bb = new LinkedListSet<>();
        double time2 = testSet(bb, filename);
        System.out.println("LinkedListSet Set: "+ time2+" s");//3.686633352 s
    }
}

//TODO
//1. 用BST实现set，其中add是直接调用了BST的add，我们能这么做的前提是我们的BST不允许重复元素，如果允许，code就需要多些几句。

//2. 用BST实现的set，你会发现函数几乎完全从BST照搬，因为BST本身就是set

//3. BST和LinkedList都是动态数据结构，都是有灵活的Node。这两者关系比较大，所以我们有时候会说，BST最差就会退化成链表

//4.学到现在，你会发现，如果你想用A数据结构来实现你现在的数据结构，你就要在你的private成员变量设置一个这个A数据类型的，然后构造函数里面new它