public class LinkedListQueue2<Element> implements Queue<Element>{

    private class Node//内部类，只有LinkedList里面才能访问这个类
    {
        public Element e;
        public Node next;

        public Node(Element e, Node next)
        {
            this.e = e;
            this.next = next;
        }

        public Node(Element e)
        {
            this(e, null);
        }

        public Node()
        {
            this(null, null);
        }

        @Override
        public String toString()
        {
            return e.toString();
        }
    }

    private Node dummyhead;
    private Node tail;
    private int size;

    public LinkedListQueue2()
    {
        dummyhead = new Node();//不能写成 dummyhead = null;
        tail = null;//不能写成 tail = new Node();
        size = 0;
    }

    @Override
    public void enqueue(Element e)
    {
        if(tail == null)//说明此时queue为空，tail.e == head.e == null
        {
            tail = new Node(e, null);
            dummyhead.next = tail;//这时head和tail都指向了第一个元素
        }
        else
        {
            tail.next = new Node(e, null);//先把tail的next勾住new Node
            tail = tail.next;//然后这个new Node再成为tail
        }
        size++;
    }

    @Override
    public Element dequeue()
    {
        if(tail==null)
        {
            throw new IllegalArgumentException("Dequeue failed. Queue is empty.");
        }
        Node ret = dummyhead.next;
        dummyhead.next = ret.next;
        ret.next = null;
        size--;

        return ret.e;
    }

    @Override
    public Element getFront()
    {
        if(tail==null)
        {
            throw new IllegalArgumentException("Get front failed. Queue is empty.");
        }
        return dummyhead.next.e;
    }

    @Override
    public int getSize()
    {
        return size;
    }

    @Override
    public boolean isEmpty()
    {
        return tail==null;
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append("Queue: front ");
        for(Node cur = dummyhead.next; cur != null; cur = cur.next)
        {
            res.append(cur+" -> ");
        }
        res.append("NULL tail");
        return res.toString();
    }

    public static void main(String[] args)
    {
        LinkedListQueue2<Integer> queue = new LinkedListQueue2<>();
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


}
