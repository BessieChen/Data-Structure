public class LinkedList_recursive_dummyhead<Element>
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
    }

    private Node dummyHead;
    private int size;

    public LinkedList_recursive_dummyhead(Element e, Node next)
    {
        dummyHead = new Node(e, next);
        size = 1;
    }

    public LinkedList_recursive_dummyhead(Element e)
    {
        dummyHead = new Node(e);
        size = 1;
    }

    public LinkedList_recursive_dummyhead()
    {
        dummyHead = new Node();
        size = 0;
    }

    public LinkedList_recursive_dummyhead(Element[] arr)
    {
        dummyHead = new Node();
        Node cur = dummyHead;
        for(int i = 0; i < arr.length; i++)
        {
            cur.next = new Node(arr[i]);
            cur = cur.next;
            size++;
        }
    }

    public void add(int index, Element e)
    {
        if(index < 0 || index > size)
        {
            throw new IllegalArgumentException("Add failed, illegal index.");
        }
        //错误写法！！dummyhead是不可以有Element值的！！
//        错了！！if(index == 0)
//        {
//            dummyHead = new Node(e, dummyHead);
//            size++;
//            return;
//        }
        add(dummyHead, index, e);//如果index - 1 == 0， 此时head是需要修饰节点的前一个，即index-1处。
    }

    private void add(Node node, int index, Element e)
    {
        if(index == 0)
        {
            node = new Node(e, node.next);
            size++;
            return;
        }
        add(node.next, index-1, e);//如果index - 1 == 0， 此时node.next是需要修饰节点的前一个，即index-1处。
    }

    public void addFirst(Element e)
    {
        add(0, e);
    }

    public void addLast(Element e)
    {
        add(size, e);
    }

    public Element remove(int index)
    {
        if(index < 0 || index >= size)
        {
            throw new IllegalArgumentException("Remove failed, illegal index.");
        }
        return remove(dummyHead, index).e;

    }

    private Node remove(Node node, int index)
    {
        Node ret;
        if(index == 0)
        {
            Node delNode = node.next;
            ret = delNode;
            node.next = delNode.next;
            delNode.next = null;
            size--;
            return ret;
        }
        return remove(node.next, index-1);//如果index - 1 == 0， 此时node.next是需要修饰节点的前一个，即index-1处。
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
            System.out.println("Element " + e+ " doesn't exist.");
            return;
        }
        if(node.next.e.equals(e))
        {
            Node delNode = node.next;
            node.next = delNode.next;
            delNode.next = null;
            size--;
            return;
        }
        removeElement(node.next, e);
    }

    public void set(int index, Element e)
    {
        if(index < 0 || index >= size)
        {
            throw new IllegalArgumentException("Set failed, illegal index.");
        }
        set(dummyHead.next, index, e);
    }

    private void set(Node node, int index, Element e)
    {
        if(index == 0)
        {
            node.e = e;
            return;
        }
        set(node.next, index-1, e);//如果index - 1 == 0， 此时node.next是需要修饰节点的前一个，即index-1处。
    }

    public void setElement(Element e, Element newE)
    {
        setElement(dummyHead, e, newE);
    }

    private void setElement(Node node, Element e, Element newE)
    {
        if(node.next == null)
        {
            System.out.println("Element " + e+ " doesn't exist.");
            return;
        }

        if(node.next.e.equals(e))
        {
            node.next.e = newE;
            return;
        }
        setElement(node.next, e, newE);
    }

    public boolean contains(Element e)
    {
        return contains(dummyHead.next, e);
    }

    private boolean contains(Node node, Element e)
    {
        if(node == null)
        {
            return false;
        }
        if(node.e.equals(e)) //dummyHead没有node.e!所以上面public boolean contains()应该是 contains(dummyHead.next, e);
        {
            return true;
        }
        return contains(node.next,e);
    }

    public Element get(int index)
    {
        if(index < 0 || index >= size)
        {
            throw new IllegalArgumentException("Get failed, illegal index.");
        }
        return get(dummyHead.next, index);
    }

    private Element get(Node node, int index)
    {
        if(index == 0)
        {
            return node.e;
        }
        return get(node.next, index-1);//如果index - 1 == 0， 此时node.next是需要修饰节点的前一个，即index-1处。
    }

    public Element getFirst()
    {
        return get(0);
    }

    public Element getLast()
    {
        return get(size-1);
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
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Linked List, size: %d, head |", size));
        Node cur = dummyHead.next;
        while(cur != null)
        {
            res.append(cur.e + " -> ");
            cur = cur.next;
        }
        res.append("NULL");
        return res.toString();
    }

    public static void main(String[] args)
    {
        Integer[] nums = {1,2,3,4,5,6,7,8,9,10};
        LinkedList_recursive_dummyhead<Integer> test = new LinkedList_recursive_dummyhead<>(nums);
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

        System.out.println("After test.setElement(50,11);");
        test.setElement(50,11);
        System.out.println(test);
        System.out.println("");

        System.out.println("After test.setElement(1231421,11);");
        test.setElement(1231421,11);
        System.out.println(test);
        System.out.println("");

    }


}
