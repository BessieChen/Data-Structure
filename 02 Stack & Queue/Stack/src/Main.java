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

    public static void main(String[] args) {

        ArrayStack<Integer> stack = new ArrayStack<>();
        for(int i = 0; i < 5; i++)
        {
            stack.push(i);
            System.out.println(stack);
        }

        stack.pop();
        System.out.println(stack);


        LinkedListStack<Integer> stack2 = new LinkedListStack<>();
        for(int i = 0; i < 5; i++)
        {
            stack2.push(i);
            System.out.println(stack2);
        }

        stack2.pop();
        System.out.println(stack2);

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

