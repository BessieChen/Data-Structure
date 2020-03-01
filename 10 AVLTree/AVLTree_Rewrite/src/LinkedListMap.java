public class LinkedListMap<K, V> implements Map<K, V>
{
    private class Node
    {
        public K key;
        public V value;
        public Node next;

        public Node(K key, V value, Node next)
        {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Node(K key, V value)
        {
            this(key, value,null);
        }

        public Node()
        {
            this(null, null, null);
        }

        @Override
        public String toString()
        {
            StringBuilder res = new StringBuilder();
            res.append("Key: "+ key+" - "+" Value: "+value);
            return res.toString();
        }
    }

    private Node dummyHead;
    private int size;

    public LinkedListMap(K key, V value, Node next)
    {
        dummyHead = new Node();
        dummyHead.next = new Node(key, value, next);
        size = 1;
    }

    public LinkedListMap(K key, V value)
    {
        dummyHead = new Node();
        dummyHead.next = new Node(key, value);
        size = 1;
    }

    public LinkedListMap()
    {
        dummyHead = new Node();
        size = 0;
    }

    /*
     * 这里有很多函数，都用到了一个功能：Node getNode(K key), 意思是：找到K为key的节点Node
     * 所以我们先构造一个辅助函数：Node getNode(K key)
     * 1.add(K key, V value)中用到getNode:
     *   如果getNode()返回的是null，那就在链表的dummyhead.next添加上new Node(key, value)
     *   如果getNode()返回的是某个Node，那就直接将Node.value = value -- 和set()相同，就是更新value
     *       这里要注意的是，因为链表中的每个节点，都是灵活的，所以，getNode()返回的Node，能够保证对这个节点进行操作，从而影响整个链表，这也是我们想要的结果。
     * 2.set(K key, V newValue)中用到getNode:
     *   如果getNode()返回的是null，那就打印key对应的Node不存在
     *   如果getNode()返回的是某个Node，那就直接将Node.value = newValue
     * 3.get(K key)
     *   直接调用getNode()
     * 4.contains(K key)
     *   直接看getNode()返回的是否是null
     *
     * 5.注意，我们的remove是不需要用到这个辅助函数的，因为我们remove需要的是待删除节点的前一个节点，而getNode()只能返回待删除节点。
     *   所以remove还是和普通链表的remove一样，要记录待删除节点的前一个节点。
     * */

    //辅助函数
    private Node getNode(K key)
    {
        Node cur = dummyHead.next;
        while(cur != null)
        {
            if(cur.key.equals(key))//cur此时就是我们要找的节点！
            {
                return cur;
            }
            cur = cur.next;
        }
        return null;//说明找到最后也没有找到
    }

    @Override
    public void add(K key, V value)// 要实现的功能：如果没有key，就new Node(key, value); 如果key已经存在，那么更新key的值为value
    {
        Node target = getNode(key);
        if(target == null)
        {
            dummyHead.next = new Node(key, value, dummyHead.next);
            size++;
            return;
        }
        target.value = value;
    }

    public void add2(K key, V value)//虽然快，但是不是我们想要的结果，因为这个add2()实际上上，并没有检查是否已经含有key
    {
        Node cur = dummyHead;
        while(cur.next != null)
        {
            cur = cur.next;
        }
        cur.next = new Node(key, value);
        size++;
    }

    @Override
    public void remove(K key)
    {
        Node prev = dummyHead;
        while(prev.next != null)
        {
            if(prev.next.key.equals(key))
            {
                Node del = prev.next;
                prev.next = del.next;
                del.next = null;
                size--;
                return;
            }
            prev = prev.next;
        }
        System.out.println("Remove failed, key doesn't exist.");
    }

    @Override
    public void set(K key, V newValue)// 要实现的功能：如果没有key，就new Node(key, value); 如果key已经存在，那么更新key的值为value
    {
        Node target = getNode(key);
        if(target == null)
        {
            System.out.println("Set failed, key doesn't exist.");
        }
        target.value = newValue;
    }

    @Override
    public boolean contains(K key)
    {
        return getNode(key) != null;
    }

    @Override
    public V get(K key)
    {
        Node target = getNode(key);
        return target != null ? target.value : null;
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

}
