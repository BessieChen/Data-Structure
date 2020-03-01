public class LinkedListQueue<Element> implements Queue<Element>
{
    private class Node
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
            return this.e.toString();
        }
    }

    private Node front;
    private Node tail;
    private int size;

    public LinkedListQueue()
    {
        front = null;
        tail = null;
        size = 0;
    }

    public LinkedListQueue(Element e)
    {
        front = new Node(e); //注意：不能直接使用enqueue(e)函数，因为现在front还没有初始化，甚至都不是null
        tail = front;
        size = 1;
    }

    public LinkedListQueue(Element[] arr)
    {
        //注意：不能直接使用enqueue(e)函数，因为现在front还没有初始化，甚至都不是null
        front = new Node(arr[0]);
        tail = front;
        size = 1;
        for( int i = 1; i < arr.length; i++)
        {
            enqueue(arr[i]);
        }
    }

    @Override
    public void enqueue(Element e)
    {
        //method 1:
        if(front == null)
        {
            front = new Node(e);
            tail = front;
            size++;
            return;
        }

        tail.next = new Node(e);
        tail = tail.next;
        size++;

        //method 2:
//        if(tail == null)//说明此时queue为空，tail.e == front.e == null
//        {
//            tail = new Node(e, null);
//            front = tail;//这时front和tail都指向了第一个元素
//        }
//        else
//        {
//            tail.next = new Node(e, null);//先把tail的next勾住new Node
//            tail = tail.next;//然后这个new Node再成为tail
//        }
//        size++;
    }

    @Override
    public Element dequeue()
    {
        if(front == null)
        {
            throw new IllegalArgumentException("Dequeue failed, queue is empty.");
        }

        //method 1:
        Element ret = front.e;
        front = front.next; //于是java自动把front.next给垃圾回收了。
        size--;
        return ret;

        //method 2:
//        Node retNode = front;
//        front = front.next;
//        retNode.next = null; //我们手动将front.next回收
//        if(front == null)
//            tail = null;
//        size --;
//        return retNode.e;
    }

    @Override
    public Element getFront()
    {
        if(front == null)
        {
            throw new IllegalArgumentException("Dequeue failed, queue is empty.");
        }
        return front.e;
    }

    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    @Override
    public int getSize()
    {
        return size;
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append("LinkedList Queue, size: "+ size +" front [");
        Node cur = front;
        while(cur != null)
        {
            res.append(cur.e);
            if(cur.next != null)//当cur.next == null的时候，说明cur当前是最后一个，所以cur.next != null说明cur不能是最后一个。
            {
                res.append(", ");
            }
            cur = cur.next;
        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args)
    {
        Integer[] nums = {99,98,97};
        LinkedListQueue<Integer> queue = new LinkedListQueue<>(nums);
        int n = 3;
        for(int i = 0; i < 12; i++)
        {
            queue.enqueue(i);
            System.out.println(queue);
            //if(i%3==(3-1))//也就是说每插入3个元素的时候，打印一下。因为i%3==2的i有2,5,8...对应的就是第3,6,9个元素、
            //if(i%3==(3-3))//也就是说每插入3个元素的时候，打印一下。因为i%3==2的i有0,3,6...对应的就是第1,4,7个元素、
            if(i%n==(n-1))//也就是说每插入n个元素的时候，打印一下。因为i%n==(n-1)的i有n-1,2n-1,3n-1...对应的就是第n,2n,3n个元素、
            {
                queue.dequeue();
                System.out.println(queue);
                System.out.println("");
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

/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=51577:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/bessie/Downloads/ByB/Data-Structure/Queue_Rewrite/out/production/Queue_Rewrite LinkedListQueue
LinkedList Queue, size: 4 front [99, 98, 97, 0] tail
LinkedList Queue, size: 5 front [99, 98, 97, 0, 1] tail
LinkedList Queue, size: 6 front [99, 98, 97, 0, 1, 2] tail
LinkedList Queue, size: 5 front [98, 97, 0, 1, 2] tail

LinkedList Queue, size: 6 front [98, 97, 0, 1, 2, 3] tail
LinkedList Queue, size: 7 front [98, 97, 0, 1, 2, 3, 4] tail
LinkedList Queue, size: 8 front [98, 97, 0, 1, 2, 3, 4, 5] tail
LinkedList Queue, size: 7 front [97, 0, 1, 2, 3, 4, 5] tail

LinkedList Queue, size: 8 front [97, 0, 1, 2, 3, 4, 5, 6] tail
LinkedList Queue, size: 9 front [97, 0, 1, 2, 3, 4, 5, 6, 7] tail
LinkedList Queue, size: 10 front [97, 0, 1, 2, 3, 4, 5, 6, 7, 8] tail
LinkedList Queue, size: 9 front [0, 1, 2, 3, 4, 5, 6, 7, 8] tail

LinkedList Queue, size: 10 front [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] tail
LinkedList Queue, size: 11 front [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10] tail
LinkedList Queue, size: 12 front [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11] tail
LinkedList Queue, size: 11 front [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11] tail


Process finished with exit code 0

*/


/*
    @Override
    public void enqueue(E e){
        if(tail == null){
            tail = new Node(e);
            head = tail;
        }
        else{
            tail.next = new Node(e);
            tail = tail.next;
        }
        size ++;
    }

    @Override
    public E dequeue(){
        if(isEmpty())
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");

        Node retNode = head;
        head = head.next;
        retNode.next = null;
        if(head == null)
            tail = null;
        size --;
        return retNode.e;
    }

    @Override
    public E getFront(){
        if(isEmpty())
            throw new IllegalArgumentException("Queue is empty.");
        return head.e;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("Queue: front ");

        Node cur = head;
        while(cur != null) {
            res.append(cur + "->");
            cur = cur.next;
        }
        res.append("NULL tail");
        return res.toString();
    }

    public static void main(String[] args){

        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        for(int i = 0 ; i < 10 ; i ++){
            queue.enqueue(i);
            System.out.println(queue);

            if(i % 3 == 2){
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }

*/