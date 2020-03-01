public class LinkedList_dummyhead<Element> {
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
            return e.toString();
        }
    }
    private Node dummyHead;
    private int size;

    public LinkedList_dummyhead()
    {
        dummyHead = new Node(null, null);
        size = 0;
    }

    public void add(int index, Element e)
    {
        if(index<0 || index >size)
        {
            throw new IllegalArgumentException("Add failed. Illegal index");
        }
        //不能像下面这么写：因为这样子的话想在index=0的地方加就不可以了。这里index==0是dummyHead的下一位，dummyHead不算成index
//        Node cur = dummyHead.next;
//        for(int i = 0; i < index-1; i++)
//        {
//            cur = cur.next;
//        }
//        cur.next = new Node(e, cur.next);
        Node cur = dummyHead;
        for(int i = 0; i < index; i++)
        {
            cur = cur.next;
        }
        cur.next = new Node(e, cur.next);
        size++;
    }

    public void addFirst(Element e)
    {
        add(0,e);
    }

    public void addLast(Element e)
    {
        add(size-1,e);
    }



    public Element remove(int index)
    {
        if(size == 0)
        {
            throw new IllegalArgumentException("Linked List is empty");
        }
        if(index < 0 || index >=size)
        {
            throw new IllegalArgumentException("Remove failed. Illegal index");
        }
        Node cur = dummyHead;
        for(int i = 0; i < index; i++)
        {
            cur = cur.next;
        }
        Node next = cur.next;
        cur.next = next.next;
        next.next = null;
        size--;
        return next.e;
    }

    public Element removeFirst()
    {
        return remove(0);
    }

    public Element removeLast()
    {
        return remove(size-1);
    }

    public void removeElement(Element e)
    {
        Node cur = dummyHead;
        while(cur.next != null)
        {
            if(cur.next.e.equals(e))
            {
                Node delNode = cur.next;
                cur.next = delNode.next;
                delNode.next = null;
                size--;
                break;
            }
            cur = cur.next;
        }
    }

    public void set(int index, Element e)
    {
        if(index < 0 || index >=size)
        {
            throw new IllegalArgumentException("Remove failed. Illegal index");
        }
        Node cur = dummyHead.next;
        for(int i = 0; i < index; i++)
        {
            cur = cur.next;
        }
        cur.e = e;
    }

    public boolean contains(Element e)
    {
        boolean res = false;

        //method 1:
        Node cur = dummyHead.next;
        for(int i = 0; i < size; i++)
        {
            if(cur.e.equals(e))
            {
                res = true;
                break;
            }
            cur = cur.next;
        }

        //method 2:
//        for(Node cur = dummyHead.next; cur != null; cur = cur.next)
//        {
//            if(cur.e.equals(e))
//            {
//                res = true;
//                break;
//            }
//        }

        //method 3:
//        Node cur = dummyHead.next;
//        while(cur != null)
//        {
//            if(cur.e.equals(e))
//            {
//                res = true;
//                break;
//            }
//            cur = cur.next;
//        }
        return res;
    }

    public Element get(int index)
    {
        if(index<0 || index>=size)
        {
            throw new IllegalArgumentException("Get failed. Illegal index.");
        }
        Node cur = dummyHead.next;
        for(int i = 0; i < index; i++)
        {
            cur = cur.next;
        }
        return cur.e;
    }

    public Element getFirst()
    {
        return get(0);
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
        return dummyHead.next.e == null;//注意是里面装的东西
    }


    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Linked List: size: %d head |",getSize()));

        Node cur = dummyHead.next;
        for(int i = 0; i < size; i++){//这里要注意了，你看上去是走到size，其实是print到size-1，因为我们是在执行cur = cur.next之前print的cur.e
            res.append(cur.e+" -> ");
            cur = cur.next;
        }
        res.append("NULL");
        return res.toString();

    }
}
