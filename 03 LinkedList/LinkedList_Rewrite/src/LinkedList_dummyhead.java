public class LinkedList_dummyhead<Element> {

    /*
     * 目录：
     * 1.private类Node
     *   构造一个private类叫Node，这个类里面也有一个成员变量是Node类型。
     *   然后我们可能会print某个Node的Element e，所以我们在Node类里面需要一个toString():return e.toString();
     *   //注意：我发现Node类中的构造函数可以有：有参数Element e的，也可以有：无参数的，例如dummyHead = new Node()。因为
     *   //如果你只是想要一个空Node，那你是在外部的LinkedList里面的Node next = null;构建空Node
     *   //也就是说空Node是在外部类的public LinkedList(){ head = null; size = 0;}中构建
     *
     * 2.构造函数
     *   注意是head = null;
     *   不可能是head = new Node<>(null);
     *   因为：1.this.e = null会报错。 2.这样初始一个LinkedList的时候，就会自动生成一个Node, 即head = new Node<>()，事实是，初始一个LinkedList时候，head还是空的。
     * */

    private class Node//<Element>
    {
        public Element e;
        public Node next;

        //注意：我发现Node类中的构造函数一定是有参数Element e还有Node next的，而不是无参数的。因为
        //如果你要构建一个new Node<>()，那就一定要传入你要构建的值，如果你只是想要一个空Node，那你是在外部的LinkedList里面的Node next = null;构建空Node
        //也就是说空Node是在外部类的public LinkedList(){ head = null; size = 0;}中构建
        public Node(Element e, Node next)
        {
            this.e = e;
            this.next = next;
        }

        //method 1:
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
            return this.e.toString();//假如说e是一个Student类对象，那么这里调用的就是Student类中的toString();
        }
    }

    private Node dummyHead;
    private int size;

    public LinkedList_dummyhead()
    {
        dummyHead = new Node();//一定是new Node(),因为要用到dummyHead.next;
        size = 0;
    }

    public LinkedList_dummyhead(Element e)
    {
        dummyHead = new Node();
        dummyHead.next = new Node(e);//而不是：new Node(e,dummyHead.next);
        size = 1;
    }

    public LinkedList_dummyhead(Element[] arr)
    {
        dummyHead = new Node();
        dummyHead.next = new Node(arr[0]);
        size = 1;
        for(int i = 1; i < arr.length; i++)
        {
            addFirst(arr[i]);
            size++;
        }
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int getSize()
    {
        return size;
    }


    @Override
    public String toString()//和LinkedList的toString()几乎一样的，只不过是dummyHead.next开始。
    {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Linked List: size: %d head |",getSize()));

        //method 1:
        Node cur = dummyHead.next;
        for(int i = 0; i < size; i++){//这里要注意了，你看上去是走到size，其实是print到size-1，因为我们是在执行cur = cur.next之前print的cur.e
            res.append(cur.e.toString() + " -> ");
            cur = cur.next;
        }
        res.append("NULL");


//        //method 2:
//        Node prev = dummyHead;
//        while(prev.next != null)
//        {
//            //method 2.1:
////            prev = prev.next;
////            res.append(prev.e + " -> ");
//            //method 2.2:
//            res.append(prev.next.e + " -> ");
//            prev = prev.next;
//        }
//        res.append("NULL");


//        //method 3:
//        for(Node cur2 = dummyHead.next; cur2 != null; cur2 = cur2.next)//这是一直定格到了，我是null, 但是我是null的时候已经执行完了也不会再append(cur.e)了。//其实cur != null也可以，只要在class Node里面有一个toString( return e.toString();) 这样我们说cur的时候就是说cur.e
//        {
//            res.append(cur2.e+" -> ");
//        }
//        res.append("NULL");

        return res.toString();
    }


    //一般来说LinkedList的add()，用户是不需要给一个index的，这里只是一个例子。
    //这里是0-based，即index == 0是head
    public void add(int index, Element e)
    {
        if (index < 0 || index > size)
        {
            throw new IllegalArgumentException("Add failed, illegal index.");
        }

        Node prev = dummyHead;
        for(int i = 0; i <= index-1; i++)//需要走到(index-1),需要走(index-1)+1步，即[0,index-1]共index步
        {
            prev = prev.next;
        }
        prev.next = new Node(e, prev.next);
        size++;
    }

    public void addFirst(Element e)
    {
        add(0,e);
    }

    public void addLast(Element e)
    {
        add(size,e);
    }

    public Element remove(int index)
    {
        if(index < 0 || index >= size)
        {
            throw new IllegalArgumentException("Remove failed, illegal index.");
        }
        Element ret;

        Node prev = dummyHead;
        for(int i = 0; i <= index-1; i++)
        {
            prev = prev.next;
        }
        Node delNode = prev.next;
        ret = delNode.e;
        prev.next = delNode.next;
        delNode.next = null;
        size--;

        return ret;
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
        removeElement(dummyHead, e);
    }

    private void removeElement(Node node, Element e)
    {
        if(node.next == null)
        {
            System.out.println("removeElement failed, element not found.");
            return;
        }
        if(node.next.e.equals(e))
        {
            Node del = node.next;
            node.next = del.next;
            del.next = null;
            size--;
            return;
        }
        removeElement(node.next, e);
    }

    public void removeElement_NR(Element e)
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

    public boolean contains(Element e)
    {
        Node cur = dummyHead;
        while(cur.next != null)
        {
            if(cur.next.e.equals(e))
            {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    //这里的查还有一个就是用户给index，然后返回元素值。这里也是一个练习而已，因为用户使用LinkedList不会知道里面有index
    public Element get(int index)
    {
        if(index < 0 || index >= size)
        {
            throw new IllegalArgumentException("Remove failed, illegal index.");
        }

        Node prev = dummyHead;
        for(int i = 0; i <= index; i++)
        {
            prev = prev.next;
        }

        return prev.e;
    }

    public Element getFirst()
    {
        return get(0);
    }

    public Element getLast()
    {
        return get(size-1);
    }

    //这里的改一个就是用户给index，然后改该index的元素值。这里也是一个练习而已，因为用户使用LinkedList不会知道里面有index
    public void set(int index, Element e)
    {
        if(index < 0 || index >= size)
        {
            throw new IllegalArgumentException("Remove failed, illegal index.");
        }

        Node prev = dummyHead;
        for(int i = 0; i <= index; i++)
        {
            prev = prev.next;
        }
        prev.e = e;
    }

    public static void main(String[] args)
    {
        Integer[] nums = {1,2,3,4,5,6,7,8,9,10};
        LinkedList_dummyhead<Integer> test = new LinkedList_dummyhead<>(nums);
        System.out.println(test);
        System.out.println("");

        System.out.println("After test.set(1,100);");
        test.set(1,100);
        System.out.println(test);
        System.out.println("");

        System.out.println("After test.set(0,50);");
        test.set(0,50);
        System.out.println(test);
        System.out.println("");

        System.out.println("After test.set(3,60);");
        test.set(3,60);
        System.out.println(test);
        System.out.println("");

        System.out.println("After test.add(5,200);");
        test.add(5,200);
        System.out.println(test);
        System.out.println("");

        System.out.println("After test.addLast(101);");
        test.addLast(101);
        System.out.println(test);
        System.out.println("");

        System.out.println("After test.removeLast();");
        test.removeLast();
        System.out.println(test);
        System.out.println("");

        System.out.println("After test.addFirst(202);");
        test.addFirst(202);
        System.out.println(test);
        System.out.println("");

        System.out.println("After test.removeFirst();");
        test.removeFirst();
        System.out.println(test);
        System.out.println("");

        System.out.println("After test.contains(50);");
        System.out.println(test.contains(50));
        System.out.println("");

        System.out.println("After test.contains(10231);");
        System.out.println(test.contains(10231));
        System.out.println("");

        System.out.println("After test.contains(10);");
        System.out.println(test.contains(10));
        System.out.println("");

        System.out.println("After test.get(1)");
        System.out.println(test.get(1));
        System.out.println("");

        System.out.println("After test.getFirst()");
        System.out.println(test.getFirst());
        System.out.println("");

        System.out.println("After test.getLast()");
        System.out.println(test.getLast());
        System.out.println("");

        System.out.println("After test.removeElement(100);");
        test.removeElement(100);
        System.out.println(test);
        System.out.println("");

        System.out.println("After test.removeElement(123124);");
        test.removeElement(123124);
        System.out.println(test);
        System.out.println("");

        System.out.println("After test.removeElement(100);");
        test.removeElement_NR(100);
        System.out.println(test);
        System.out.println("");

        System.out.println("After test.removeElement(123124);");
        test.removeElement_NR(123124);
        System.out.println(test);
        System.out.println("");



    }
}
