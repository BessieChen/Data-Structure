public class LinkedList_recursive<Element>
    /*
    * 目录：
    * 1.对于add()的递归。只能用private void add(...)，而不能用private Node add(...)
    *   原因是，add需要做的只是去到那个index节点，然后添加上去，而不用返回什么东西，如果真的返回一个Node，你知道会发生什么吗？
    *   你肯定在 public add(Element e)函数中写了head = add(...) 结果就糟了，也就是将递归到底的那个index的那个Node赋值给了head！！这显然是错的
    *
    * 2.对于remove()的递归。
    * */
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

    private Node head;
    private int size;

    public LinkedList_recursive(Element e, Node next)
    {
        head = new Node(e, next);
        size = 1;
    }

    public LinkedList_recursive(Element e)
    {
        head = new Node(e);
        size = 1;
    }

    public LinkedList_recursive()
    {
        head = null;
        size = 0;
    }

    public LinkedList_recursive(Element[] arr)
    {
        head = new Node(arr[0]);
        Node cur = head;
        size++;
        for(int i = 1; i < arr.length; i++)
        {
            cur.next = new Node(arr[i]);
            size++;
            cur = cur.next;
        }
    }

    public void add(int index, Element e)
    {
        if(index < 0 || index > size)
        {
            throw new IllegalArgumentException("Add failed, illegal index.");
        }
        if(index == 0)
        {
            head = new Node(e, head);
            size++;
            return;
        }

        //只能用void返回值的private void add(...)
          add(head, index-1, e);//如果idex - 1 == 0， 此时head是需要修饰节点的前一个，即index-1处。

        //WRONG! 错误方法：用返回值为 Node 的add_Node(), 注意，这里不能依旧使用private add()作为函数名，即便返回值不一样。
        //错误：head = add_Node(head, index-1, e);

    }

    private void add(Node node, int index, Element e)
    {
        if(index == 0)
        {
            node.next = new Node(e, node.next);
            size++;
            return;
        }
        add(node.next, index-1, e);//如果index - 1 == 0， 此时node.next是需要修饰节点的前一个，即index-1处。
    }

    //Wrong！错误函数！！不能使用返回值为Node 的private add(...)
/*    Wrong!!!  private Node add_Node(Node node, int index, Element e)
    {
        if(index == 0)
        {
            node.next = new Node(e, node.next);
            size++;
            return node;
        }
        return add_Node(node.next, index-1, e);
    }*/

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

        Element ret;
        if(index == 0)
        {
            ret = head.e;
            head = head.next;
            size--;
            return ret;
        }

        return remove(head, index-1).e;//如果index - 1 == 0， 此时head是需要修饰节点的前一个，即index-1处。

    }

    private Node remove(Node node, int index)
    {
        Node ret;
        if(index == 0)
        {
            Node delNode = node.next;
            ret = delNode;
            node.next = delNode.next;
            delNode.next = null; //这里不会影响ret 不让ret == null啊！
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
        if(head == null)
            return;

        if(head.e.equals(e))
        {
            head = head.next;
            size--;
            return;
        }

        removeElement(head, e);
    }

    private void removeElement(Node node, Element e)
    {
        if(node.next == null)//  这里需要判断的是  node.next == null, 而不是判断node == null, 如果真的写成判断node == null.
            return;         // 如果node.next == null时候，下面的if(node.next.e.equals(e))就会报错

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
        set(head, index, e);
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
        if(head == null)
        {
            System.out.println("Element " + e+ " doesn't exist.");
            return;
        }
        setElement(head, e, newE);
    }

    private void setElement(Node node, Element e, Element newE)
    {
        if(node == null)
        {
            System.out.println("Element " + e+ " doesn't exist.");
            return;
        }
        if(node.e.equals(e))
        {
            node.e = newE;
            return;
        }
        setElement(node.next, e, newE);
    }

    public boolean contains(Element e)
    {
        return contains(head, e);
    }

    private boolean contains(Node node, Element e)
    {
        if(node == null)
        {
            return false;
        }
        if(node.e.equals(e))
        {
            return true;
        }
        return contains(node.next,e);
    }

    public Element get(int index)
    {
        if(index < 0 || index >= size)
        {
            throw new IllegalArgumentException("Set failed, illegal index.");
        }
        return get(head, index);
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
        Node cur = head;
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
        LinkedList_recursive<Integer> test = new LinkedList_recursive<>(nums);
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

/*
* 全部函数都已经检查过，检查案例的结果都正确：

/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=64626:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/bessie/Downloads/ByB/Data-Structure/LinkedList_Rewrite/out/production/LinkedList_Rewrite LinkedList_recursive
Linked List, size: 10, head |1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10 -> NULL

After test.set(1,100);
Linked List, size: 10, head |1 -> 100 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10 -> NULL

After test.set(0,50);
Linked List, size: 10, head |50 -> 100 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10 -> NULL

After test.set(3,60);
Linked List, size: 10, head |50 -> 100 -> 3 -> 60 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10 -> NULL

After test.add(5,200);
Linked List, size: 11, head |50 -> 100 -> 3 -> 60 -> 5 -> 200 -> 6 -> 7 -> 8 -> 9 -> 10 -> NULL

After test.addLast(101);
Linked List, size: 12, head |50 -> 100 -> 3 -> 60 -> 5 -> 200 -> 6 -> 7 -> 8 -> 9 -> 10 -> 101 -> NULL

After test.removeLast();
Linked List, size: 11, head |50 -> 100 -> 3 -> 60 -> 5 -> 200 -> 6 -> 7 -> 8 -> 9 -> 10 -> NULL

After test.addFirst(202);
Linked List, size: 12, head |202 -> 50 -> 100 -> 3 -> 60 -> 5 -> 200 -> 6 -> 7 -> 8 -> 9 -> 10 -> NULL

After test.removeFirst();
Linked List, size: 11, head |50 -> 100 -> 3 -> 60 -> 5 -> 200 -> 6 -> 7 -> 8 -> 9 -> 10 -> NULL

After test.contains(50);
true

After test.contains(10231);
false

After test.contains(10);
true

After test.get(1)
100

After test.getFirst()
50

After test.getLast()
10

After test.removeElement(100);
Linked List, size: 10, head |50 -> 3 -> 60 -> 5 -> 200 -> 6 -> 7 -> 8 -> 9 -> 10 -> NULL

After test.removeElement(123124);
Linked List, size: 10, head |50 -> 3 -> 60 -> 5 -> 200 -> 6 -> 7 -> 8 -> 9 -> 10 -> NULL

After test.setElement(50,11);
Linked List, size: 10, head |11 -> 3 -> 60 -> 5 -> 200 -> 6 -> 7 -> 8 -> 9 -> 10 -> NULL

After test.setElement(1231421,11);
Element 1231421 doesn't exist.
Linked List, size: 10, head |11 -> 3 -> 60 -> 5 -> 200 -> 6 -> 7 -> 8 -> 9 -> 10 -> NULL


Process finished with exit code 0
* */
