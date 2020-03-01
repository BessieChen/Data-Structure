public class ArrayQueue<Element> implements Queue<Element>
{
    Array<Element> array;

    public ArrayQueue(int capacity)
    {
        array = new Array<>(capacity);
    }

    public ArrayQueue()
    {
        //this(10);这么写应该也可以
        array = new Array<>();
    }

    @Override
    public void enqueue(Element e)
    {
        array.addLast(e);
    }

    @Override
    public Element dequeue()
    {
        return array.removeFirst();
    }

    @Override
    public Element getFront()
    {
        return array.getFirst();
    }

    @Override
    public int getSize()
    {
        return array.getSize();
    }

    //Byb 注意Interface Stack中没有这个getCapacity。原因是，只有在我们用动态数组实现stack的时候，可能会存在我们想知道capacity是多大。毕竟capacity也可能不等于size
    //所以我们需要一个函数知道capacity是多少，但是我们的抽象的接口，不需要知道我们是array实现的，从而也不需要我们的capacity
    public int getCapacity()
    {
        return array.getCapacity();
    }

    @Override
    public boolean isEmpty()
    {
        return array.isEmpty();
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append("Queue: front [");
        for(int i = 0; i < array.getSize(); i++)
        {
            res.append(array.get(i));
            if( i != array.getSize()-1)
            {
                res.append(", ");
            }
        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args)
    {
        ArrayQueue<Integer> queue = new ArrayQueue<>();
        for(int i = 0; i < 10; i++)
        {
            queue.enqueue(i);
            System.out.println(queue);
            if(i%3==(3-1))//也就是说每插入3个元素的时候，打印一下。因为i%3==2的i有2,5,8...对应的就是第3,6,9个元素、
            {
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }

    /*
    Queue: front [0] tail
    Queue: front [0,1] tail
    Queue: front [0,1,2] tail
    Queue: front [1,2] tail
    Queue: front [1,2,3] tail
    Queue: front [1,2,3,4] tail
    Queue: front [1,2,3,4,5] tail
    Queue: front [2,3,4,5] tail
    Queue: front [2,3,4,5,6] tail
    Queue: front [2,3,4,5,6,7] tail
    Queue: front [2,3,4,5,6,7,8] tail
    Queue: front [3,4,5,6,7,8] tail
    Queue: front [3,4,5,6,7,8,9] tail
     */

}
