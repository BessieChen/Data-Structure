public class LoopQueue<Element> implements Queue<Element>
{
    private Element[] queue;
    private int front;
    private int tail;

    public LoopQueue(int capacity)
    {
        queue = (Element[]) new Object[capacity+1];
        front = 0;
        tail = 0;
    }

    public LoopQueue()
    {
        this(10);
    }

    @Override
    public int getSize()
    {
        int size = 0;
        if(!isEmpty()){

            int a = front - tail;
            if(a < 0)
            {
                size = -a;
            }
            else
            {
                size = queue.length - a;
            }
        }
        return size;
    }

    public int getCapacity()
    {
        return queue.length - 1;
    }

    @Override
    public void enqueue(Element e)
    {
        if(getSize() == getCapacity())
        {
            resize(getCapacity()*2);//Byb 这里应该是getCapacity*2 而不是length*2，因为用户只关心capacity是不是*2*2，而不关系你是怎么实现的。
        }
        queue[tail] = e;
        tail = (tail+1)%queue.length;
    }

    @Override
    public Element dequeue()
    {
        if(isEmpty())
        {
            throw new IllegalArgumentException("Queue is empty");
        }
        Element ret = queue[front];
        queue[front] = null;
        front = (front+1)%queue.length;
        if(getSize() <= getCapacity()/4 && getCapacity()/2 != 0)
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
            throw new IllegalArgumentException("Queue is empty");
        }
        return queue[front];
    }

    @Override
    public boolean isEmpty()
    {
        return front==tail;
    }


    public void resize(int newCapcacity)
    {
        Element[] newqueue = (Element[]) new Object[++newCapcacity];//为了实现用户的newCapacity，我们实现时要+1，注意！Byb 不能写成newCapacity++, 因为这个就是没有加！
        for(int i = front, j = 0; i != tail; i = (i+1)%queue.length)
        {
            newqueue[j++] = queue[i];
        }

        /*
        Byb Byb
        我的天啊，被这句坑了！你知道哪里错了吗？是这样的：最开始的queue有三个位置【0，1，2】，其中【1，2】是有元素的，也就是size=2，即front=1，tail=0
        然后我要扩容了，也就是我想更新的front和tail是：
        queue = newqueue;
        front = 0；
        tail = getSize();
        看上去没什么问题对吧，可是!!!!!  当front=0执行完之后，getSize()看见front==tail了，此时就是认为size=0了！所以你后面都错了
        还有坑啊！！！
        那就是tail = getSize()里面我写了一句：
        int size = 0;
        if(!isEmpty()){

            int a = front - tail;
            if(a < 0)
            {
                size = -a;
            }
            else
            {
                size = queue.length - a;//这一句有坑啊！！！！！！那就是，如果我先写了queue = newqueue; 再写tail = getSize(),那么size = queue.length - a;那就是用了新的length-a了，肯定错啊！
            }
        }

        Byb 所以我们写顺序的时候，一定要看这句话的函数，需要的东西，是不是刚好在上一句话更新了值！
         */
        tail = getSize();
        front = 0;
        queue = newqueue;
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Queue: Size: %d, Capacity: %d |",getSize(),getCapacity()));
        res.append("front [");
        for(int i = 0; i < getSize(); i++)
        {
            res.append(queue[(front+i)%queue.length]);
            if(i != getSize()-1)
            {
                res.append(", ");
            }
        }
        //method 2!
//        for(int i = front; i != tail; i = (i+1)%queue.length)
//        {
//            res.append(queue[i]);
//            if((i+1)%queue.length != tail)
//            {
//                res.append(", ");
//            }
//        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args)
    {
        LoopQueue<Integer> queue = new LoopQueue<>(2);
        for(int i = 0; i < 10; i++)
        {
            queue.enqueue(i);
            System.out.println(queue);
            if(i%3==(3-2))//也就是说每插入3个元素的时候，打印一下。因为i%3==2的i有2,5,8...对应的就是第3,6,9个元素、
            {
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }


}
