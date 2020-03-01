// 在这一版LoopQueue的实现中，我们将不浪费那1个空间，但是需要引进一个新的变量：size
public class LoopQueue_full<Element> implements Queue<Element>
{
    private Element[] loopQueue;
    private int front;
    private int tail;
    private int size;

    public LoopQueue_full(int capacity)
    {
        loopQueue = (Element[]) new Object[capacity];
        front = 0;
        tail = 0;
        size = 0;
    }

    public LoopQueue_full()
    {
        this(2);
    }

    public LoopQueue_full(Element[] arr)
    {
        loopQueue = (Element[]) new Object[arr.length];
        for(int i = 0; i < arr.length; i++)
        {
            enqueue(arr[i]);
        }
    }

    @Override
    public void enqueue(Element e)
    {
        if(getSize() == getCapacity()) //同样也是：front == tail
        {
            resize(2 * getCapacity());
        }
        loopQueue[tail] = e;
        tail = (tail+1) % loopQueue.length;
        size++;
    }

    private void resize(int newLen)
    {
        Element[] newQueue = (Element[]) new Object[newLen];
        for(int i = 0; i < getSize(); i++)
        {
            newQueue[i] = loopQueue[ (i+front) % loopQueue.length];
        }

        //method 1:注意如果使用tail = getSize()，tail的赋值需要在front = 0前面，否则，getSize()的使用的就是front = 0了
        //我喜欢method 1，因为可复制性强，富余一个的案例中，也是可以这么写的。
        tail = getSize();
        front = 0;
        loopQueue = newQueue;

        //method 2:
//        front = 0;
//        tail = size;
//        loopQueue = newQueue;
    }

    @Override
    public Element dequeue()
    {
        if(isEmpty())
        {
            throw new IllegalArgumentException("dequeue failed, queue is empty.");
        }
        Element ret = loopQueue[front];
        front = ( front + 1 ) % loopQueue.length;
        size--;
        if(getSize()  <= getCapacity()/4 && getCapacity()/2 >= 2) //front == tail ?
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
            throw new IllegalArgumentException("getFront failed, queue is empty.");
        }
        Element ret = loopQueue[front];
        return ret;
    }

    @Override
    public int getSize()
    {
        return size;
    }

    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    public int getCapacity()
    {
        return loopQueue.length;
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append("Loop Queue, size: "+ this.getSize() + ", capacity: "+ this.getCapacity() + ", front: " + front + ", tail: " + tail + " front [");

        //错误写法：这个在富余一个中可以用，但是这里不可以，因为full例子中front可以==tail
//        错误 for(int i = front; i != tail; i = (i+1) % loopQueue.length)
//        {
//            res.append(loopQueue[i]);
//            if((i+1)%loopQueue.length != tail)
//            {
//                res.append(", ");
//            }
//        }

        //我最喜欢的写法，可复制性很强，都可以直接用到富余一个的例子中。
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
        LoopQueue_full<Integer> queue_nums = new LoopQueue_full<>(nums);
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

        LoopQueue_full<Integer> queue = new LoopQueue_full<>(2);
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
//        for(int i = 0; i < queue.getSize(); i++)//如果queue.getSize()也会变化的话，那就是i和一个会变化的值比较~
//        {
//            queue.dequeue();
//            System.out.println(queue);
//        }
        int size = queue.getSize();
        for(int i = 0; i < size; i++)
        {
            queue.dequeue();
            System.out.println(queue);
        }
    }
}


/*

/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=55152:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/bessie/Downloads/ByB/Data-Structure/Queue_Rewrite/out/production/Queue_Rewrite LoopQueue_full
Loop Queue, size: 4, capacity: 6, front: 0, tail: 4 front [99, 98, 97, 0] tail
Loop Queue, size: 5, capacity: 6, front: 0, tail: 5 front [99, 98, 97, 0, 1] tail
Loop Queue, size: 6, capacity: 6, front: 0, tail: 0 front [99, 98, 97, 0, 1, 2] tail

Loop Queue, size: 5, capacity: 6, front: 1, tail: 0 front [98, 97, 0, 1, 2] tail
Loop Queue, size: 6, capacity: 6, front: 1, tail: 1 front [98, 97, 0, 1, 2, 3] tail
Loop Queue, size: 7, capacity: 12, front: 0, tail: 7 front [98, 97, 0, 1, 2, 3, 4] tail
Loop Queue, size: 8, capacity: 12, front: 0, tail: 8 front [98, 97, 0, 1, 2, 3, 4, 5] tail

Loop Queue, size: 7, capacity: 12, front: 1, tail: 8 front [97, 0, 1, 2, 3, 4, 5] tail
Loop Queue, size: 8, capacity: 12, front: 1, tail: 9 front [97, 0, 1, 2, 3, 4, 5, 6] tail
Loop Queue, size: 9, capacity: 12, front: 1, tail: 10 front [97, 0, 1, 2, 3, 4, 5, 6, 7] tail
Loop Queue, size: 10, capacity: 12, front: 1, tail: 11 front [97, 0, 1, 2, 3, 4, 5, 6, 7, 8] tail

Loop Queue, size: 9, capacity: 12, front: 2, tail: 11 front [0, 1, 2, 3, 4, 5, 6, 7, 8] tail
Loop Queue, size: 10, capacity: 12, front: 2, tail: 0 front [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] tail

---------------------

Loop Queue, size: 1, capacity: 2, front: 0, tail: 1 front [0] tail
Loop Queue, size: 2, capacity: 2, front: 0, tail: 0 front [0, 1] tail
Loop Queue, size: 3, capacity: 4, front: 0, tail: 3 front [0, 1, 2] tail

Loop Queue, size: 2, capacity: 4, front: 1, tail: 3 front [1, 2] tail
Loop Queue, size: 3, capacity: 4, front: 1, tail: 0 front [1, 2, 3] tail
Loop Queue, size: 4, capacity: 4, front: 1, tail: 1 front [1, 2, 3, 4] tail
Loop Queue, size: 5, capacity: 8, front: 0, tail: 5 front [1, 2, 3, 4, 5] tail

Loop Queue, size: 4, capacity: 8, front: 1, tail: 5 front [2, 3, 4, 5] tail
Loop Queue, size: 5, capacity: 8, front: 1, tail: 6 front [2, 3, 4, 5, 6] tail
Loop Queue, size: 6, capacity: 8, front: 1, tail: 7 front [2, 3, 4, 5, 6, 7] tail
Loop Queue, size: 7, capacity: 8, front: 1, tail: 0 front [2, 3, 4, 5, 6, 7, 8] tail

Loop Queue, size: 6, capacity: 8, front: 2, tail: 0 front [3, 4, 5, 6, 7, 8] tail
Loop Queue, size: 7, capacity: 8, front: 2, tail: 1 front [3, 4, 5, 6, 7, 8, 9] tail

dequeue:
Loop Queue, size: 6, capacity: 8, front: 3, tail: 1 front [4, 5, 6, 7, 8, 9] tail
Loop Queue, size: 5, capacity: 8, front: 4, tail: 1 front [5, 6, 7, 8, 9] tail
Loop Queue, size: 4, capacity: 8, front: 5, tail: 1 front [6, 7, 8, 9] tail
Loop Queue, size: 3, capacity: 8, front: 6, tail: 1 front [7, 8, 9] tail
Loop Queue, size: 2, capacity: 8, front: 7, tail: 1 front [8, 9] tail
Loop Queue, size: 1, capacity: 4, front: 1, tail: 2 front [9] tail
Loop Queue, size: 0, capacity: 2, front: 1, tail: 1 front [] tail

Process finished with exit code 0

*/