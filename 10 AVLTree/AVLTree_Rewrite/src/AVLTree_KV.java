import java.util.ArrayList;

public class AVLTree_KV<K extends Comparable<K>, V>
{
    private class Node
    {
        public K key;
        public V value;
        public Node left;
        public Node right;
        public int height;

        public Node(K key, V value)
        {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            height = 0;
        }

        @Override
        public String toString()
        {
            StringBuilder res = new StringBuilder();
            res.append(key + " : "+value.toString());
            return res.toString();
        }
    }

    private Node root;
    private int size;

    public AVLTree_KV()
    {
        root = null;
        size = 0;
    }

    public int getSize()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    @Override
    public String toString()//todo 1.
    {
        return "abc";
    }

    public boolean isBST()
    {
        ArrayList<K> array = new ArrayList<>();
        inOrder(root, array);
        for(int i = 1; i < array.size(); i++)
        {
            if(array.get(i).compareTo(array.get(i-1)) < 0)
                return false;
        }
        return true;
    }

    private void inOrder(Node node, ArrayList<K> array)
    {
        if(node == null)
            return;

        inOrder(node.left, array);
        array.add(node.key);
        inOrder(node.right, array);
    }

    private int getHeight(Node node)
    {
        if(node == null)
            return 0;
        return node.height;
    }

    private int updateHeight(Node node)//todo 2: 什么时候用到updateHeight? leftRotate(), rightRotate()
    {
        if(node == null)
            return 0;
        int leftH = getHeight(node.left);
        int rightH = getHeight(node.right);
        return Math.max(leftH, rightH)+1;
    }

    private int getBalanceFactor(Node node)//todo 2: 什么时候用到getBalanceF ?
    {
        if(node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    public boolean isBalanced()
    {
        return isBalanced(root);
    }

    private boolean isBalanced(Node node)
    {
        if(node == null)
            return true;

        if(Math.abs(getBalanceFactor(node)) >= 2)
            return false;

        return isBalanced(node.left) && isBalanced(node.right);
    }

    private Node rightRotate(Node y)
    {
        Node x = y.left;
        Node T2 = x.right;
        y.left = T2;
        x.right = y;

        y.height = updateHeight(y);//注意！！todo 非常容易出错的bug！那就是y先更新height，因为y是x的孩子了！！否则整个AVLTree都会是不平衡的！
        x.height = updateHeight(x);

        return x;
    }

    private Node leftRotate(Node y)//todo 左旋转：将自己旋转到自己右孩子的左边。
    {
        Node x = y.right;
        Node T2 = x.left;
        y.right = T2;
        x.left = y;

        y.height = updateHeight(y);//注意！！todo 非常容易出错的bug！那就是y先更新height，因为y是x的孩子了！！否则整个AVLTree都会是不平衡的！
        x.height = updateHeight(x);

        return x;
    }

    private Node balancingNode(Node node)
    {
        node.height = updateHeight(node);

        int nBal = getBalanceFactor(node);
        int lBal = getBalanceFactor(node.left);
        int rBal = getBalanceFactor(node.right);

        if(nBal >= 2 && lBal >= 0)//LL
        {
            return rightRotate(node);
        }

        if(nBal >= 2 && lBal < 0)//LR
        {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if(nBal <= -2 && rBal <= 0)
        {
            return leftRotate(node);
        }

        if(nBal <= -2 && rBal > 0)
        {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node getNode(K key)
    {
        return getNode(root, key);
    }

    private Node getNode(Node node, K key)
    {
        if(node == null) return null;

        if(node.key.compareTo(key) < 0)
            return getNode(node.right, key);
        else if(node.key.compareTo(key) > 0)
            return getNode(node.left, key);
        else
            return node;
    }

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

        if(node.key.compareTo(key) < 0)
        {
            node.right = add(node.right, key, value);
        }
        else if(node.key.compareTo(key) > 0)
        {
            node.left = add(node.left, key, value);
        }
        else
        {
            node.value = value;
            return node;
        }

        return balancingNode(node);
    }

    public V removeMin()
    {
        return removeMin(root) == null ? null : removeMin(root).value;
    }

    private Node removeMin(Node node)
    {
        if(node == null)
            return node;

        if(node.left == null)
        {
            size--;
            return node.right;
        }

        node.left = removeMin(node.left);
        return balancingNode(node);
    }

    public V remove(K key)
    {
        if(getNode(key) == null)
            return null;
        else
        {
            V ret = getNode(key).value;
            root = remove(root, key);
            return ret;
        }
    }

    private Node remove(Node node, K key)
    {
        if(node == null)
            return node;

        Node retNode;
        if(node.key.compareTo(key) < 0)
        {
            node.right = remove(node.right, key);
            retNode = node;
        }
        else if(node.key.compareTo(key) > 0)
        {
            node.left = remove(node.left, key);
            retNode = node;
        }
        else//key == key
        {
            if(node.left == null)
            {
                size--;
                retNode = node.right;
            }
            else if(node.right == null)
            {
                size--;
                retNode = node.left;
            }
            else
            {
                retNode = minimum(node.right);
                retNode.left = node.left;
                retNode.right = removeMin(node.right);
                node.left = node.right = null;
                return retNode;
            }
        }
        if(retNode == null)
            return null;

        return balancingNode(retNode);
    }

    public boolean set(K key, V newVal)
    {
        Node node = getNode(key);
        if(node == null)
            return false;

        node.value = newVal;
        return true;
    }

    public boolean contains(K key)
    {
        return getNode(key) != null;
    }

    public V get(K key)
    {
        Node retNode = getNode(key);
        return retNode == null ? null : retNode.value;
    }

    public V minimum()
    {
        return minimum(root) == null ? null : minimum(root).value;
    }

    private Node minimum(Node node)
    {
        if(node == null)
            return node;

        if(node.left == null)
            return node;

        return minimum(node.left);
    }

}

