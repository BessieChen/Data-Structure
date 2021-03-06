import java.util.ArrayList;

public class Main {

    private static double testMap(Map<String, Integer> map, String filename){

        long startTime = System.nanoTime();

        System.out.println(filename);
        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile(filename, words)) {
            System.out.println("Total words: " + words.size());

            for (String word : words){
                if(map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());

            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1e9;
    }

    public static void main(String[] args) {

        String filename = "pride-and-prejudice.txt";

        BSTMap<String, Integer> bstMap = new BSTMap<>();
        double time1 = testMap(bstMap, filename);
        System.out.println("BST Map: " + time1 + " s");//0.158913269 s

        System.out.println();

        LinkedListMap<String, Integer> linkedListMap = new LinkedListMap<>();
        double time2 = testMap(linkedListMap, filename);
        System.out.println("Linked List Map: " + time2 + " s");//9.932915363 s

    }
}

/*
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=59025:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/bessie/Downloads/ByB/Data-Structure/Map_Rewrite/out/production/Map_Rewrite Main
pride-and-prejudice.txt
Total words: 125901
Total different words: 6530
Frequency of PREJUDICE: 119372
BST Map: 0.163561618 s

pride-and-prejudice.txt
Total words: 125901
Total different words: 6530
Frequency of PREJUDICE: 11
Linked List Map: 10.16623079 s

Process finished with exit code 0
*/
//1. map比set要多了两个函数：通过key得到get value，将key上的值修改了。set这个数据结构里面就没有说什么get e，或者将e改了。没有意义。

//2. map小套路：set(key,map.get(key)+1) 以及 add（key, 1)