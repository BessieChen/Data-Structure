import java.util.ArrayList;

public class LinkedListMap<K, V> implements Map<K, V> {

    private class Node
    {
        private K key;
        private V value;
        private Node next;

        public Node(K key, V value, Node next)
        {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Node(K key, V value)
        {
            this(key, value, null);
        }

        public Node()
        {
            this(null, null, null);
        }

        @Override
        public String toString()
        {
            //method 1:
//            StringBuilder res = new StringBuilder();
//            res.append(key.toString() + " : " + value.toString());
//            return res.toString();
            //method 2:
            return key.toString() + " : " + value.toString();
        }
    }

    private Node dummyHead;
    private int size;

    public LinkedListMap(K key, V value, Node next)
    {
        //Not GOOD:
//        dummyHead.key = key;
//        dummyHead.value = value;
//        dummyHead.next = next;
        dummyHead = new Node(key, value, next);
        size = 1;
    }

    public LinkedListMap(K key, V value)
    {
        dummyHead = new Node(key, value);
        size = 1;
    }

    public LinkedListMap()
    {
        dummyHead = new Node();
        size = 0;
    }

    //辅助函数
    private Node getNode(K key)
    {
        //Wrong:
        //Node cur = dummyHead;
        Node cur = dummyHead.next;
        while(cur != null)
        {
            if(cur.key.equals(key))
            {
                return cur;
            }
            cur = cur.next;
        }
        return null;//如果没有找到，我们并不是报错，而是返回null
    }

    public void add_my(K key, V value)//虽然快，但是不是我们想要的结果，因为这个add2()实际上上，并没有检查是否已经含有key
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
    public void add(K key, V value)
    {
        Node node = getNode(key);
        if(node == null)
        {
            dummyHead.next = new Node(key, value, dummyHead.next);
            size++;
        }
        else
        {
            node.value = value;
        }
    }

    @Override
    public V remove(K key)
    {
        Node cur = dummyHead;
        while(!cur.next.key.equals(key))
        {
            cur = cur.next;
        }
        //method 1:
//        cur.next = cur.next.next;
        //method 2:
        Node del = cur.next;
        cur.next = del.next;
        del.next = null;
        size--;
        return cur.value;
    }

    public V remove_teacher(K key)
    {
        Node cur = dummyHead;
        while(cur.next != null)
        {
            if(cur.next.key.equals(key))
            {
                break;
            }
            cur = cur.next;
        }

        if(cur.next != null)
        {
            Node del = cur.next;
            cur.next = del.next;
            del.next = null;
            size--;
            return del.value;
        }
        return null;
    }

    @Override
    public void set(K key, V newValue)
    {
        Node ret = getNode(key);
        if(ret == null)
        {
            throw new IllegalArgumentException("Key"+ key + " doesn't exist.");
        }
        ret.value = newValue;
    }

    @Override
    public boolean contains(K key)
    {
        //method 1:
//        if(getNode(key) != null)
//        {
//            return true;
//        }
//        return false;
        //method 2:
        return getNode(key) != null;
    }

    @Override
    public V get(K key)
    {
        Node ret = getNode(key);
        return ret != null ? ret.value : null;//注意不能写成：return ret != null ? ret.value : ret;即便ret == null是ret也是null，但是编译会出错，因为计算机认为ret是Node但是我们要返回V
    }

    @Override
    public int getSize()
    {
        return size;
    }

    @Override
    public boolean isEmpty()
    {
        return size==0;
    }

//    private static double testSet(Set<String> set, String filename)
//    {
//        long startTime = System.nanoTime();
//        System.out.println(filename);
//        ArrayList<String> words = new ArrayList<>();
//        if(FileOperation.readFile(filename,words))
//        {
//            System.out.println("Total words: "+words.size());
//            for(String word : words)
//            {
//                set.add(word);
//            }
//            System.out.println("Totol different words: "+set.getSize());
//        }
//
//        long endTime = System.nanoTime();
//
//        return (endTime-startTime)/1e9;
//    }


}
