import java.util.ArrayList;

public class TestSetMain {

    private static double testSet(Set<String> set, String filename){

        long startTime = System.nanoTime();

        System.out.println(filename);
        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile(filename, words)) {
            System.out.println("Total words: " + words.size());

            for (String word : words)
                set.add(word);
            System.out.println("Total different words: " + set.getSize());
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args) {

        String filename = "pride-and-prejudice.txt";

        BSTSet<String> bstSet = new BSTSet<>();
        double time1 = testSet(bstSet, filename);
        System.out.println("BST Set: " + time1 + " s");

        System.out.println();

        LinkedListSet<String> linkedListSet = new LinkedListSet<>();
        double time2 = testSet(linkedListSet, filename);
        System.out.println("Linked List Set: " + time2 + " s");

        System.out.println();

        AVLTreeSet<String> avlSet = new AVLTreeSet<>();
        double time3 = testSet(avlSet, filename);
        System.out.println("AVL Set: " + time3 + " s");
    }
}

/*
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=61473:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/bessie/Downloads/ByB/Data-Structure/AVLTree_Rewrite/out/production/AVLTree_Rewrite TestSetMain
pride-and-prejudice.txt
Total words: 125901
Total different words: 6530
BST Set: 0.119843699 s

pride-and-prejudice.txt
Total words: 125901
Total different words: 125901
Linked List Set: 0.067190401 s

pride-and-prejudice.txt
Total words: 125901
Total different words: 6530
AVL Set: 0.05900127 s

Process finished with exit code 0
*/