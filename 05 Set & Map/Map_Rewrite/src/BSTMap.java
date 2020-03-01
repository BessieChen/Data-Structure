import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BSTMap<K extends Comparable<K>, V> implements Map<K, V>
{
    private class Node
    {
        public K key;
        public V value;
        public Node left;
        public Node right;

        public Node(K key, V value)//一个新Node的left和right一般不在构造函数里面声明。
        {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }

        @Override
        public String toString()
        {
            StringBuilder res = new StringBuilder();
            res.append("Key: "+ key+" - "+" Value: "+value);
            return res.toString();
        }
    }

    private Node root;
    private int size;

    public BSTMap()
    {
        root = null;
        size = 0;
    }

    public BSTMap(K key, V value)
    {
        root = new Node(key, value);
        size = 1;
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
        res.append("\n");
        generateString(root, 0, res);
        return res.toString();
    }

    private void generateString(Node node, int depth, StringBuilder res)
    {
        if(node == null)
        {
            res.append(generateDepth(depth)+"null\n");
            return;

        }
        res.append(generateDepth(depth)+node+"\n");
        generateString(node.left,depth+1,res);
        generateString(node.right,depth+1,res);

    }

    private String generateDepth(int depth)
    {
        StringBuilder res = new StringBuilder();
        if(depth == 0)
        {
            res.append('|');
        }
        for(int i = 0; i < depth; i++)
        {
            res.append("--|");
        }
        return res.toString();
    }

    @Override
    public void add(K key, V value)
    {
        root = add(root, key, value);
    }

    private Node add(Node node, K key, V value)
    {
        if(node == null)
        {
            size++;
            return new Node(key, value);
        }

        if(node.key.compareTo(key) > 0)
        {
            node.left = add(node.left, key, value);
        }
        else if(node.key.compareTo(key) < 0)
        {
            node.right = add(node.right, key, value);
        }
        else//node.key.compareTo(key) == 0
        {
            node.value = value;
        }
        return node;
    }

    @Override
    public void remove(K key)
    {
        root = remove(root, key);
    }

    private Node remove(Node node, K key)
    {
        if(node == null)
        {
            System.out.println("Remove failed, key doesn't exist.");
            return null;
        }

        if(node.key.compareTo(key) == 0)
        {
            if(node.right == null)
            {
                size--;
                return node.left;
            }
            else if(node.left == null)
            {
                size--;
                return node.right;
            }
            else//node.left != null && node.right != null
            {
                node.value = removeMin(node.right).value; //我这里选择让右孩子为根节点的树的 最 小 值 代替node，并且这里不需要再size--；
                return node;
            }
        }
        else if(node.key.compareTo(key) < 0)
        {
            node.right = remove(node.right, key);
        }
        else//node.key.compareTo(key) > 0
        {
            node.left = remove(node.left, key);
        }
        return node;
    }

    private Node removeMin(Node node)
    {
        if(node == null)
        {
            return null;
        }

        if(node.left == null)
        {
            return node.right;
        }

        node.left = removeMin(node.left);
        return node;
    }

    @Override
    public void set(K key, V newValue)
    {
        Node ret = get(root, key);
        if(ret == null)
        {
            System.out.println("Set failed, key doesn't exist.");
        }
        ret.value = newValue;
    }

    @Override
    public boolean contains(K key)
    {
        return contains(root, key);
    }

    private boolean contains(Node node, K key)
    {
        if(node == null)
        {
            return false;
        }

        if(node.key.compareTo(key) < 0)
        {
            return contains(node.right, key);
        }
        else if(node.key.compareTo(key) > 0)
        {
            return contains(node.left, key);
        }
        else//node.key.compareTo(key) == 0
        {
            return true;
        }
    }

    @Override
    public V get(K key)
    {
        Node ret = get(root, key);
        if(ret == null)
        {
            throw new IllegalArgumentException("Get failed, key doesn't exist.");
        }
        return ret.value;
    }

    private Node get(Node node, K key)
    {
        if(node == null)
        {
            return null;
        }

        if(node.key.compareTo(key) < 0)
        {
            node.right = get(node.right, key);
        }
        else if(node.key.compareTo(key) > 0)
        {
            node.left = get(node.left, key);
        }
        return node; //node.key.compareTo(key) == 0 以及返回 从上面if出来的node
    }

    public static void main(String[] args)
    {

        System.out.println("Pride and Prejuidice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words))
        {
            System.out.println("Total words: "+ words.size());
            BSTMap<String, Integer> map = new BSTMap<>();
            for(String word: words)
            {
                if(map.contains(word))
                {
                    map.set(word, map.get(word)+1);
                }
                else
                {
                    map.add(word, 1);
                }
            }

            System.out.println("Total different words: "+ map.getSize());
            System.out.println("Frequency of PRIDE: "+ map.get("pride"));
            System.out.println("Frequency of PREJUDICE: "+ map.get("prejudice"));
        }

    }

}
