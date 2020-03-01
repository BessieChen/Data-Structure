public class LinkedList_Recursive<Element> {

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
    private Node head;
    private int size;

    public LinkedList_Recursive()
    {
        head = new Node(null, null);
        size = 0;
    }

    public LinkedList_Recursive (Element[] arr)
    {
        if(arr.length == 0)
        {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        head = new Node(arr[0]);
        size++;
        Node aa = head;
        for(int i = 1; i < arr.length; i++)
        {
            aa.next = new Node(arr[i]);
            aa = aa.next;
            size++;
        }
    }

    public void add(int index, Element e)
    {
        if(index<0 || index >size)
        {
            throw new IllegalArgumentException("Add failed. Illegal index");
        }

        //head = add_recursive_old(head, index, e, 0);
        head = add_recursive(head, index, e);
    }

    private Node add_recursive_old(Node head, int index, Element e, int depth)
    {
        System.out.print(plotDepth(depth));
        System.out.print("Now we want to add to "+index+" of this linkedList ");
        System.out.println(head.toString());

        if(index == 0)
        {
//            System.out.print(plotDepth(depth));
//            Node aa = new Node(e, head.next);//Byb, 这里head.next错了，下面写的才是对的！
//            System.out.println("New node: "+ aa.e+", Next Node: "+ head.next);
//            return aa;
            System.out.print(plotDepth(depth));
            Node aa = new Node(e, head);
            System.out.println("New node: "+ aa.e+", Next Node: "+ head);
            return aa;

        }
        else
        {
            head.next =  add_recursive_old(head.next, index-1, e, depth+1);
            System.out.print(plotDepth(depth));
            System.out.println("head.next.e is : "+ head.next.e+", Return: "+ head);
            return head;
        }
    }

    private String plotDepth(int depth)
    {
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < depth; i++)
        {
            res.append("--|");
        }
        return res.toString();
    }

    private Node add_recursive(Node head, int index, Element e)
    {
        if(index == 0)
        {
            return new Node(e, head);
        }
        else
        {
            head.next =  add_recursive(head.next, index-1, e);
            return head;
        }
    }



    public void addFirst(Element e)
    {
        add(0,e);
    }

    public void addLast(Element e)
    {
        add(size-1,e);
    }



//    public Element remove(int index)
//    {
//        if(size == 0)
//        {
//            throw new IllegalArgumentException("Linked List is empty");
//        }
//        if(index < 0 || index >=size)
//        {
//            throw new IllegalArgumentException("Remove failed. Illegal index");
//        }
//
//        KeyPair<Node, Element> del = remove_recursive(head, index);
//        return
//
//    }
//
//    private Pair<Node, Element> remove_recursive(Node head, int index)
//    {
//        if(index == 0)
//        {
//            return head.next;
//        }
//        else
//        {
//            head.next = remove_recursive(head.next, index-1);
//            return head;
//        }
//    }
//
//
//    public Element removeFirst()
//    {
//        return remove(0);
//    }
//
//    public Element removeLast()
//    {
//        return remove(size-1);
//    }

    public void removeElement(Element e)
    {
        Node cur = head;
        while(cur.next != null)
        {
            if(cur.next.e.equals(e))
            {
                break;
            }
            cur = cur.next;
        }

        if(cur.next != null)
        {
            Node delNode = cur.next;
            cur.next = delNode.next;
            delNode.next = null;
            size--;
        }
    }

    public void set(int index, Element e)
    {
        if(index < 0 || index >=size)
        {
            throw new IllegalArgumentException("Remove failed. Illegal index");
        }
        head = set(head, index, e);
    }

    private Node set(Node head, int index ,Element e)
    {
        if(index == 0)
        {
            return new Node(e, head.next);
        }
        else
        {
            head.next = set(head.next, index-1, e);
            return head;
        }
    }

    public void set_teacher(int index, Element e)
    {
        if(index < 0 || index >=size)
        {
            throw new IllegalArgumentException("Remove failed. Illegal index");
        }
        set_teacher(head, index, e);
    }

    private void set_teacher(Node head, int index, Element e)
    {
        if(index == 0)
        {
            head.e = e;
        }
        else
        {
            set_teacher(head.next, index-1, e);
        }
    }

    public boolean contains(Element e)
    {
        return contains(head, e);

    }

    private boolean contains(Node head, Element e)
    {
        if(head == null)
        {
            return false;
        }
        if(head.e.equals(e))
        {
            return true;
        }
        else
        {
            return contains(head.next, e);
        }
    }

    public Element get(int index)
    {
        if(index<0 || index>=size)
        {
            throw new IllegalArgumentException("Get failed. Illegal index.");
        }
        return get(head, index);
    }

    private Element get(Node head, int index)
    {
        if(index == 0)
        {
            return head.e;
        }
        else
        {
            return get(head.next, index-1);
        }
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
        return head.next.e == null;//注意是里面装的东西
    }


    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Linked List: size: %d head |",getSize()));
        Node cur = head;
        for(int i = 0; i < size; i++){//这里要注意了，你看上去是走到size，其实是print到size-1，因为我们是在执行cur = cur.next之前print的cur.e
            res.append(cur.e+" -> ");
            cur = cur.next;
        }
        res.append("NULL");
        return res.toString();
    }

    public static void main(String[] args)
    {
        Integer[] nums = {1,2,3,4,5,6,7,8,9,10};
        LinkedList_Recursive<Integer> test = new LinkedList_Recursive<>(nums);
        System.out.println(test);

        test.set(1,100);
        test.set(0,50);
        test.set(3,60);
        System.out.println(test+"  My set");


        test.set_teacher(1,2);
        test.set_teacher(0,1);
        test.set_teacher(3,4);
        System.out.println(test+"  Teacher's set");

        test.add(5,200);
        System.out.println(test);

    }
}
