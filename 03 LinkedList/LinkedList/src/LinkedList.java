public class LinkedList<Element> {
    private class Node
    {
        public Element e;
        public Node next;

        public Node(Element e, Node next)//如果用户两个都传过来了
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
            return e.toString();//就像是之前学习的Student和Array的例子，我们最后打印出来的东西就是Student和Array它们两个的toString()的东西，这个就是我们未来用来cur != null时候的cur它就是cur.e != null的判断
        }
    }
    private Node head;
    private int size;

    public LinkedList()
    {
        head = new Node();
        size = 0;
    }

    //public LinkedList()//另一种传入一串数组的构造函数我还是不太会。。反正有递归在
//    public LinkedList(Element[] eArray)
//    {
//       head = new Node(eArray[0], new Node(eArray[1], new Node(.....)));
//       for(int i = eArray.length-1; i >=0 ; i--)
//       {
//
//       }
//
//    }


    public void add(int index, Element e)//Byb 注意不管怎么add，最开始都有一个里面装着null，指向null的节点。
    {
        if(index<0 || index>size)
        {
            throw new IllegalArgumentException("Add Failed. Illegal Index");
        }

        if( index == 0)
        {
            addFirst(e);
        }
        else
        {

            Node cur = head;
            for(int i = 0; i < index-1; i++)
            {
                cur = cur.next;
            }
            cur.next = new Node(e, cur.next);
            size++;
        }
    }

    public void addFirst(Element e)//Byb 注意不管怎么add，最开始都有一个里面装着null，指向null的节点。
    {
        head = new Node(e, head);
        size++;
    }

    public void addLast(Element e)
    {
        add(size, e);
    }



    public Element remove(int index)
    {
        if(size == 0)
        {
            throw new IllegalArgumentException("Linked List is empty");
        }
        if(index < 0 || index >= size)
        {
            throw new IllegalArgumentException("Remove failed. Illegal index");
        }

        Element ret;
        if( index == 0)
        {
            ret = removeFirst();
        }
        else
        {
            Node cur = head;
            for(int i = 0; i < index - 1; i++)
            {
                cur = cur.next;
            }
            Node Next = cur.next;
            cur.next = Next.next;
            Next.next = null;
            size--;

            ret = Next.e;
        }
        return ret;

    }

    public Element removeFirst()
    {
        if(size == 0)
        {
            throw new IllegalArgumentException("Linked List is empty");
        }
        Element ret = head.e;
        head = head.next;
        size--;
        return ret;
    }

    public Element removeLast()
    {
        return remove(size-1);
    }



    public void set(int index, Element e)
    {
        if(index < 0 || index >=size)
        {
            throw new IllegalArgumentException("Remove failed. Illegal index");
        }
        Node cur = head;
        for(int i = 0; i < index; i++)
        {
            cur = cur.next;
        }
        cur.e = e;
    }


    public boolean contains(Element e)
    {
        boolean ret = false;
        //method 1:
//        Node cur = head;
//        for(int i = 0; i < size; i++)
//        {
//            if(cur.e.equals(e))
//            {
//                ret = true;
//                break;
//            }
//            cur = cur.next;
//        }

//        //method 2:
//        Node cur = head;
//        while( cur != null)
//        {
//            if(cur.e.equals(e))
//            {
//                ret = true;
//                break;
//            }
//            cur = cur.next;
//        }

        //method 3:
        for(Node cur = head; cur.e != null; cur = cur.next)
        {
            if(cur.e.equals(e))
            {
                ret = true;
                break;
            }
        }
        return ret;
    }

    public Element get(int index)
    {
        if(index<0 || index>=size)
        {
            throw new IllegalArgumentException("Get failed. Illegal index.");
        }
        Node cur = head;
        for(int i = 0; i < index; i++)
        {
            cur = cur.next;
        }
        return cur.e;
    }

    public Element getFirst()
    {
        return head.e;
    }

    public Element getLast()
    {
        return get(size-1);
    }

    public int getSize()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return head.e == null;
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Linked List: size: %d head |",getSize()));

        //method 1:
//        Node cur = head;
//        for(int i = 0; i < size; i++){//这里要注意了，你看上去是走到size，其实是print到size-1，因为我们是在执行cur = cur.next之前print的cur.e
//            res.append(cur.e + " -> ");
//            cur = cur.next;
//        }
//        res.append("NULL");


        //method 2:
        for(Node cur = head; cur.e != null; cur = cur.next)//这是一直定格到了，我是null, 但是我是null的时候已经执行完了也不会再append(cur.e)了。//其实cur != null也可以，只要在class Node里面有一个toString( return e.toString();) 这样我们说cur的时候就是说cur.e
        {
            res.append(cur.e+" -> ");
        }
        res.append("NULL");

        return res.toString();
    }

}

