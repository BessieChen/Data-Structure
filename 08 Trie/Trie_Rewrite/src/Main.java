import java.util.ArrayList;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)){

            long startTime = System.nanoTime();

            Trie trie = new Trie();
            for(String word: words)
                //trie.add_R(word);
                trie.add(word);

            TreeSet<Boolean> print = new TreeSet<Boolean>();
            for(String word: words)
                //print.add(trie.contains_R(word));
                print.add(trie.contains(word));

            long endTime = System.nanoTime();

            double time = (endTime - startTime) / 1e9;

            System.out.println("Total different words: " + trie.getSize());
            System.out.println("Trie: " + time + " s");
            System.out.println(print);

            // ---

            startTime = System.nanoTime();



            BSTSet<String> set = new BSTSet<>();
            for(String word: words)
                set.add(word);

            TreeSet<Boolean> print2 = new TreeSet<Boolean>();
            for(String word: words)
                print2.add(set.contains(word));

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1e9;

            System.out.println("Total different words: " + set.getSize());
            System.out.println("BSTSet: " + time + " s");
            System.out.println(print2);


        }
    }
}


/*
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=61879:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/bessie/Downloads/ByB/Data-Structure/Trie_Rewrite/out/production/Trie_Rewrite Main
Pride and Prejudice
Total different words: 6530
Trie: 0.085380024 s
[true]
Total different words: 6530
BSTSet: 0.079329475 s
[true]

Process finished with exit code 0
*/