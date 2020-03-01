import java.util.Random;

public class Main {

    //测试使用q运行opCount个enqueue和dequeue操作所需要的时间，单位：秒
    private static double testStack(Stack<Integer> stack, int opCount)
    {
        long startTime = System.nanoTime();
        Random random = new Random();
        for(int i = 0; i < opCount; i++)
        {
            stack.push(random.nextInt(Integer.MAX_VALUE));
        }
        for(int i = 0; i < opCount; i++)
        {
            stack.pop();
        }

        long endTime = System.nanoTime();

        return (endTime - startTime)/1e9;
    }

    private static double testStack_my(Stack<Integer> stack, int opCount)
    {
        long startTime = System.nanoTime(); //易错：是long类型，调用System的nanoTime();
        Random myRandom = new Random(); //记得new一个Random()变量。
        for(int i = 0; i < opCount; i++)
        {
            stack.push(myRandom.nextInt(Integer.MAX_VALUE));
        }
        for(int i = 0; i < opCount; i++)
        {
            stack.pop();
        }
        long endTime = System.nanoTime();
        return (endTime - startTime)/1e9;
    }

    public static void main(String[] args) {

        ArrayStack<Integer> stack = new ArrayStack<>();
        for(int i = 0; i < 5; i++)
        {
            stack.push(i);
            System.out.println(stack);
        }

        stack.pop();
        System.out.println(stack);


        System.out.println("-----------------");

        LinkedListStack<Integer> stack2 = new LinkedListStack<>();
        for(int i = 0; i < 5; i++)
        {
            stack2.push(i);
            System.out.println(stack2);
        }

        stack2.pop();
        System.out.println(stack2);

        System.out.println("-----------------");
        //测试ArrayStack:
        int opCount = 100000;
        ArrayStack<Integer> arraystack = new ArrayStack<>();
        double time1 = testStack(arraystack, opCount);
        System.out.println("ArrayStack time: "+time1+" s");//ArrayStack time: 0.015774153 s



        LinkedListStack<Integer> linkedListStack = new LinkedListStack<>();
        double time2 = testStack(linkedListStack, opCount);
        System.out.println("LinkedListStack time: "+time2+" s");//LinkedListStack time: 0.014181166 s.
        // 其实这个时间比较会很复杂，因为LinkedListStack包含更多的new操作：如果opCount增大，LinkedListStack会比ArrayStack慢！
    }

}

/*
Stack是一种后进先出LIFO(last in first out)
Stack再计算机世界，Stack有不可思议的作用

Stack的应用：
1. Undo最后的那次操作，就是利用了Stack，将栈顶的操作撤销
2. 程序调用的系统栈（递归函数）
*/


/*

/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=63099:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/bessie/Downloads/ByB/Data-Structure/Stack_Rewrite/out/production/Stack_Rewrite Main
Array Stack size: 1 | bottom [ 0 ] top
Array Stack size: 2 | bottom [ 0, 1 ] top
Array Stack size: 3 | bottom [ 0, 1, 2 ] top
Array Stack size: 4 | bottom [ 0, 1, 2, 3 ] top
Array Stack size: 5 | bottom [ 0, 1, 2, 3, 4 ] top
Array Stack size: 4 | bottom [ 0, 1, 2, 3 ] top
-----------------
LinkedList Stack size: 1 | top [ 0 ] bottom
LinkedList Stack size: 2 | top [ 1, 0 ] bottom
LinkedList Stack size: 3 | top [ 2, 1, 0 ] bottom
LinkedList Stack size: 4 | top [ 3, 2, 1, 0 ] bottom
LinkedList Stack size: 5 | top [ 4, 3, 2, 1, 0 ] bottom
LinkedList Stack size: 4 | top [ 3, 2, 1, 0 ] bottom
-----------------
ArrayStack time: 0.012122509 s
LinkedListStack time: 0.008368844 s

Process finished with exit code 0

*/