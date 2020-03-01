import java.util.EventListener;

public class LoopQueue<Element> implements Queue<Element>
{
    private Element[] loopQueue;
    private int front;
    private int tail;

    public LoopQueue(int capacity)
    {
        loopQueue = (Element[]) new Object[capacity+1];
        front = 0;
        tail = 0;
    }

    public LoopQueue()
    {
        this(4+1);//新开辟的时候，可以占4个元素，一个富余。resize的时候也是相当于开辟新的array
    }

    public LoopQueue(Element[] arr)
    {
        loopQueue = (Element[]) new Object[arr.length + 1]; //初始的时候就需要手动长度+1
        for(int i = 0; i < arr.length; i++)
        {
            enqueue(arr[i]);
        }
    }

    @Override
    public void enqueue(Element e) //入队是队尾，所以tail是队尾
    {
        if( (tail + 1) % loopQueue.length == front)//说明此时loopQueue是满的，另一种方式：if(getSize() == getCapacity())，这个方式也是直接可以在full案例中使用的。
        {
            resize(2 * getCapacity());//这里就需要协调了，到底是enqueue()负责声明多capacity+1，还是resize()内部负责capacity+1. 我这里prefer在resize内部+1
        }
        loopQueue[tail] = e;
        tail = (tail+1)%loopQueue.length;
    }

    private void resize(int newLen)
    {
        Element[] newQ = (Element[]) new Object[newLen+1]; //初始的时候就需要手动长度+1

        //错误方式： 不能使用i <= tail - 1，因为如果front在tail左边的话，下面的for loop一次都进入不了。
//        错误 int j = 0;
//        for(int i = front; i <= tail-1; i = (i+1)%loopQueue.length, j++)
//        {
//            newQ[j] = loopQueue[i];
//        }
//        front = 0;
//        tail = j;
//
//
//        //method 1:
//        //这段代码写的不优雅：
//        不优雅 int i = 0;
//        int j = front;
//        while(i <= getSize()-1)
//        {
//            newQ[i] = loopQueue[j];
//            i++;
//            j = (j+1) % loopQueue.length;
//        }

        //method 2: 在富余一位的时候，可以用method 2，但是数组全部使用的时候就不可以了，因为front可以等于tail
        // newQ的i，对应着loopQ的位移+取余：(front + i)%loopQ.length; 所以即loopQ上的index != tail的时候继续：
//        for(int i = 0; (front + i) % loopQueue.length != tail; i ++ )
//        {
//            newQ[i] = loopQueue[ (front + i) % loopQueue.length];
//        }

//        //method 2.2: 在富余一位的时候，可以用method 2，但是数组全部使用的时候就不可以了，因为front可以等于tail
//        // newQ的i，对应着loopQ的位移+取余：(front + i)%loopQ.length; 所以即loopQ上的index != tail的时候继续：
//        int i2 = 0;
//        while((front + i2) % loopQueue.length != tail)
//        {
//            newQ[i2] = loopQueue[ (front + i2) % loopQueue.length];
//            i2++;
//        }
//
//        //method 3: 富余一位和全部使用，两种情况都可以用method 3
//        // 用getSize()来得知for loop应该循环几次：然后newQ的i，对应着loopQ的位移+取余：(front + i)%loopQ.length;
        //method 3是我最喜欢的:
        for(int i = 0; i < getSize(); i++)
        {
            newQ[i] = loopQueue[ (front + i) % loopQueue.length];
        }
//
//        //method 3.2:
//        int i2 = 0;
//        while(i2 < getSize())
//        {
//            newQ[i2] = loopQueue[ (front + i2) % loopQueue.length];
//            i2++;
//        }

        tail = getSize();
        front = 0;
        loopQueue = newQ;
    }

    @Override
    public Element dequeue()//出队是队首，所以front是队首 //todo
    {
        if(isEmpty())
        {
            throw new IllegalArgumentException("Dequeue failed, queue is empty.");
        }
        Element ret = loopQueue[front];
        front = (front+1)%loopQueue.length;
        if(this.getSize() <= getCapacity() / 2  && (getCapacity() / 2) >= 1+1)
        {
            resize(getCapacity()/2);
        }
        return ret;
    }

    @Override
    public Element getFront()
    {
        if(isEmpty())
        {
            throw new IllegalArgumentException("Get front failed, queue is empty.");
        }
        return loopQueue[front];
    }

    @Override
    public boolean isEmpty()
    {
        return front == tail;//或者getSize() == getCapacity();
    }

    @Override
    public int getSize()
    {
        //method 1: lengthy
//        if(tail - front >= 0)
//        {
//            return tail-front;
//        }
//        else
//        {
//            return loopQueue.length + (tail-front);
//        }

        //method 2:
        //return tail >= front ? tail - front : tail - front + loopQueue.length;

        //method 3:
        return (tail - front + loopQueue.length) % loopQueue.length;
    }

    public int getCapacity()
    {
        return loopQueue.length - 1;
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append("Loop Queue, size: " + this.getSize() + ", capacity: "+ this.getCapacity() + ", front: " + front + ", tail: " + tail + " front [");

        //method 1:
//        for(int i = front; i != tail; i = (i+1) % loopQueue.length)
//        {
//            res.append(loopQueue[i]);
//            if((i+1)%loopQueue.length != tail)
//            {
//                res.append(", ");
//            }
//        }

        //method 2: 我最喜欢的写法，可复制性很强，都可以直接用到full的例子中。
        for(int i = 0 ; i < getSize(); i++)
        {
            res.append(loopQueue[(front+i) % loopQueue.length]);
            if( i != getSize()-1)
            {
                res.append(", ");
            }
        }

        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args)
    {
        Integer[] nums = {99,98,97};
        LoopQueue<Integer> queue_nums = new LoopQueue<>(nums);
        int n = 3;
        for(int i = 0; i < 10; i++)
        {
            queue_nums.enqueue(i);
            System.out.println(queue_nums);
            //if(i%3==(3-1))//也就是说每插入3个元素的时候，打印一下。因为i%3==2的i有2,5,8...对应的就是第3,6,9个元素、
            //if(i%3==(3-3))//也就是说每插入3个元素的时候，打印一下。因为i%3==2的i有0,3,6...对应的就是第1,4,7个元素、
            if(i%n==(n-1))//也就是说每插入n个元素的时候，打印一下。因为i%n==(n-1)的i有n-1,2n-1,3n-1...对应的就是第n,2n,3n个元素、
            {
                System.out.println(" ");
                queue_nums.dequeue();
                System.out.println(queue_nums);
            }
        }

        System.out.println("\n---------------------\n");

        LoopQueue<Integer> queue = new LoopQueue<>(2);
        for(int i = 0; i < 10; i++)
        {
            queue.enqueue(i);
            System.out.println(queue);
            //if(i%3==(3-1))//也就是说每插入3个元素的时候，打印一下。因为i%3==2的i有2,5,8...对应的就是第3,6,9个元素、
            //if(i%3==(3-3))//也就是说每插入3个元素的时候，打印一下。因为i%3==2的i有0,3,6...对应的就是第1,4,7个元素、
            if(i%n==(n-1))//也就是说每插入n个元素的时候，打印一下。因为i%n==(n-1)的i有n-1,2n-1,3n-1...对应的就是第n,2n,3n个元素、
            {
                System.out.println(" ");
                queue.dequeue();
                System.out.println(queue);
            }
        }

        System.out.println("\ndequeue: ");
        int size = queue.getSize();
        for(int i = 0; i < size; i++)
        {
            queue.dequeue();
            System.out.println(queue);
        }
    }


}



/*

/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=54838:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/bessie/Downloads/ByB/Data-Structure/Queue_Rewrite/out/production/Queue_Rewrite LoopQueue
Loop Queue, size: 4, capacity: 6, front: 0, tail: 4 front [99, 98, 97, 0] tail
Loop Queue, size: 5, capacity: 6, front: 0, tail: 5 front [99, 98, 97, 0, 1] tail
Loop Queue, size: 6, capacity: 6, front: 0, tail: 6 front [99, 98, 97, 0, 1, 2] tail

Loop Queue, size: 5, capacity: 6, front: 1, tail: 6 front [98, 97, 0, 1, 2] tail
Loop Queue, size: 6, capacity: 6, front: 1, tail: 0 front [98, 97, 0, 1, 2, 3] tail
Loop Queue, size: 7, capacity: 12, front: 0, tail: 7 front [98, 97, 0, 1, 2, 3, 4] tail
Loop Queue, size: 8, capacity: 12, front: 0, tail: 8 front [98, 97, 0, 1, 2, 3, 4, 5] tail

Loop Queue, size: 7, capacity: 12, front: 1, tail: 8 front [97, 0, 1, 2, 3, 4, 5] tail
Loop Queue, size: 8, capacity: 12, front: 1, tail: 9 front [97, 0, 1, 2, 3, 4, 5, 6] tail
Loop Queue, size: 9, capacity: 12, front: 1, tail: 10 front [97, 0, 1, 2, 3, 4, 5, 6, 7] tail
Loop Queue, size: 10, capacity: 12, front: 1, tail: 11 front [97, 0, 1, 2, 3, 4, 5, 6, 7, 8] tail

Loop Queue, size: 9, capacity: 12, front: 2, tail: 11 front [0, 1, 2, 3, 4, 5, 6, 7, 8] tail
Loop Queue, size: 10, capacity: 12, front: 2, tail: 12 front [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] tail

---------------------

Loop Queue, size: 1, capacity: 2, front: 0, tail: 1 front [0] tail
Loop Queue, size: 2, capacity: 2, front: 0, tail: 2 front [0, 1] tail
Loop Queue, size: 3, capacity: 4, front: 0, tail: 3 front [0, 1, 2] tail

Loop Queue, size: 2, capacity: 2, front: 0, tail: 2 front [1, 2] tail
Loop Queue, size: 3, capacity: 4, front: 0, tail: 3 front [1, 2, 3] tail
Loop Queue, size: 4, capacity: 4, front: 0, tail: 4 front [1, 2, 3, 4] tail
Loop Queue, size: 5, capacity: 8, front: 0, tail: 5 front [1, 2, 3, 4, 5] tail

Loop Queue, size: 4, capacity: 4, front: 0, tail: 4 front [2, 3, 4, 5] tail
Loop Queue, size: 5, capacity: 8, front: 0, tail: 5 front [2, 3, 4, 5, 6] tail
Loop Queue, size: 6, capacity: 8, front: 0, tail: 6 front [2, 3, 4, 5, 6, 7] tail
Loop Queue, size: 7, capacity: 8, front: 0, tail: 7 front [2, 3, 4, 5, 6, 7, 8] tail

Loop Queue, size: 6, capacity: 8, front: 1, tail: 7 front [3, 4, 5, 6, 7, 8] tail
Loop Queue, size: 7, capacity: 8, front: 1, tail: 8 front [3, 4, 5, 6, 7, 8, 9] tail

dequeue:
Loop Queue, size: 6, capacity: 8, front: 2, tail: 8 front [4, 5, 6, 7, 8, 9] tail
Loop Queue, size: 5, capacity: 8, front: 3, tail: 8 front [5, 6, 7, 8, 9] tail
Loop Queue, size: 4, capacity: 4, front: 0, tail: 4 front [6, 7, 8, 9] tail
Loop Queue, size: 3, capacity: 4, front: 1, tail: 4 front [7, 8, 9] tail
Loop Queue, size: 2, capacity: 2, front: 0, tail: 2 front [8, 9] tail
Loop Queue, size: 1, capacity: 2, front: 1, tail: 2 front [9] tail
Loop Queue, size: 0, capacity: 2, front: 2, tail: 2 front [] tail

Process finished with exit code 0

*/
