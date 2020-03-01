import java.util.ArrayList;
import java.util.Random;

public class RBTree<K extends Comparable<K>, V>
{
    private static final boolean RED = true;//不能设成在Node class里面，否则之后的node.color == RED就会报错，
    private static final boolean BLACK = false;

    private class Node
    {
        private K key;
        private V value;
        private Node left;
        private Node right;
        private boolean color;//因为红和黑就两种，所以可以用boolean来代表。

        public Node(K key, V value, Node left, Node right, boolean color)
        {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.color = color;
        }

        public Node(K key, V value)
        {
            this(key, value, null, null, RED);
        }

        public Node()
        {
            this(null, null, null, null, RED);
        }


        public String toString()
        {
            return key.toString() + " : " + value.toString();
        }
    }

    private Node root;
    private int size;

    public RBTree()
    {
        root = null;
        size = 0;
    }

    public boolean isRed(Node node)
    {
        if(node == null)//空节点是black
        {
            return BLACK;
        }
        return node.color;
    }

    //   node                     x
    //  /   \     左旋转         /  \
    // T1   x   --------->   node   T3
    //     / \              /   \
    //    T2 T3            T1   T2
    private Node leftRotate(Node node){//x一定是红色的，因为x是新加的，新加的都是红色的。

        Node x = node.right;

        // 左旋转
        node.right = x.left;
        x.left = node;

        x.color = node.color;
        node.color = RED;//因为现在node是孩子，孩子是红色，说明就是和它的父类融合的。

        return x;
    }

    //     node                   x
    //    /   \     右旋转       /  \
    //   x    T2   ------->   y   node
    //  / \                       /  \
    // y  T1                     T1  T2
    private Node rightRotate(Node node){

        Node x = node.left;

        // 右旋转
        node.left = x.right;
        x.right = node;

        x.color = node.color;
        node.color = RED;

        return x;
    }

    private void flipColors(Node node)
    {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    public void add(K key, V value)
    {
        root = add(root, key, value);
        root.color = BLACK;
    }

    private Node add(Node node, K key, V value)
    {
        if(node == null)
        {
            size++;
            return new Node(key, value);
        }
        if(node.key.compareTo(key)==0)//compareTo是key拥有的函数，因为K extends Comparable<K>
        {
            node.value = value;//相当于更新了node之前的value
        }
        else if(key.compareTo(node.key)>0)
        {
            node.right = add(node.right, key, value);
        }
        else
        {
            node.left = add(node.left, key, value);
        }

        if(!isRed(node.left) && isRed(node.right))
        {
            node = leftRotate(node);
        }
        if(isRed(node.left) && isRed(node.left.left))
        {
            node = rightRotate(node);
        }
        if(isRed(node.left) && isRed(node.right))
        {
            flipColors(node);
        }

        return node;
    }

    private Node getNode(Node node, K key)
    {
        if(node == null)
        {
            return null;
        }
        if(key.compareTo(node.key)==0)
        {
            return node;
        }
        else if(key.compareTo(node.key)>0)
        {
            return getNode(node.right, key);
        }
        else
        {
            return getNode(node.left, key);
        }
    }

    public V remove(K key)
    {
        if(!contains(key))
        {
            throw new IllegalArgumentException("Key "+ key + "doesn't exist.");
        }
        V ret = getNode(root, key).value;
        root = remove(root, key);
        return ret;
    }

    private Node remove(Node node, K key)
    {
        if(key.compareTo(node.key) > 0)
        {
            node.right = remove(node.right, key);
        }
        else if(key.compareTo(node.key) < 0)
        {
            node.left = remove(node.left, key);
        }
        else
        {
            if(node.left == null)
            {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }
            else if(node.right == null)
            {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }
            else//相当于(node.left != null && node.right != null)
            {
                Node successor = removeMaximum(node.right);
                node.value = successor.value;
                return node;
            }
        }
        return node;
    }

    //------------------------------------teacher's remove starts---------------------------------------------------------------
    private Node minimum_teacher(Node node){
        if(node.left == null)
            return node;
        return minimum_teacher(node.left);
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin_teacher(Node node){

        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }

        node.left = removeMin_teacher(node.left);
        return node;
    }

    // 从二分搜索树中删除键为key的节点
    public V remove_teacher(K key){

        Node node = getNode(root, key);
        if(node != null){
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    private Node remove_teacher(Node node, K key){

        if( node == null )
            return null;

        if( key.compareTo(node.key) < 0 ){
            node.left = remove_teacher(node.left , key);
            return node;
        }
        else if(key.compareTo(node.key) > 0 ){
            node.right = remove_teacher(node.right, key);
            return node;
        }
        else{   // key.compareTo(node.key) == 0

            // 待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }

            // 待删除节点右子树为空的情况
            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            }

            // 待删除节点左右子树均不为空的情况

            // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
            // 用这个节点顶替待删除节点的位置
            Node successor = minimum_teacher(node.right);
            successor.right = removeMin_teacher(node.right);
            successor.left = node.left;

            node.left = node.right = null;

            return successor;
        }
    }
    //------------------------------------teacher's remove ends---------------------------------------------------------------


    //------------------------------------get minimum/maximum starts---------------------------------------------------------------
    public V minimum()
    {
        if(isEmpty())
        {
            throw new IllegalArgumentException("RBTree is empty");
        }
        return minimum(root).value;
    }

    private Node minimum(Node node)
    {
        if(node.left == null)
        {
            return node;
        }
        else
        {
            return minimum(node.left);//这里就是一股气的return，return值的就刚好是终止条件return的值
        }
    }


    public V maximum()
    {
        if(isEmpty())
        {
            throw new IllegalArgumentException("RBTree is empty");
        }
        return maximum(root).value;
    }

    private Node maximum(Node node)
    {
        if(node.right == null)
        {
            return node;
        }
        else
        {
            return maximum(node.right);
        }
    }

    //------------------------------------get minimum/maximum ends---------------------------------------------------------------

    //------------------------------------remove minimum/maximum starts---------------------------------------------------------------

    public V removeMinimum()
    {
        V ret = minimum();
//        wrong:
//        Node res = removeMinimum(root);
        root = removeMinimum(root);
        return ret;
    }

    private Node removeMinimum(Node node)
    {
        if(node.left == null)
        {
            size--;
            return node.right; //如果node.right==null那就是return null，如果不是null就return不是null的node.right.
        }
        else
        {
            node.left = removeMinimum(node.left);
        }
        return node;
    }

    public V removeMaximum()
    {
        V ret = maximum();
        root = removeMaximum(root);
        return ret;
    }

    private Node removeMaximum(Node node)
    {
        if(node.right == null)
        {
            size--;
            return node.left; //如果node.right==null那就是return null，如果不是null就return不是null的node.right.
        }
        else
        {
            node.right = removeMaximum(node.right);
        }
        return node;
    }
    //------------------------------------remove minimum/maximum ends---------------------------------------------------------------


    public void set(K key, V newValue)
    {
        if(!contains(key))
        {
            throw new IllegalArgumentException("Key "+ key + "doesn't exist.");
        }
        Node res = getNode(root, key);
        res.value = newValue;
    }

    public boolean contains(K key)
    {
        return getNode(root, key) != null;
    }


    public V get(K key)
    {
        Node res = getNode(root, key);
        //method 1:
        if(res != null)
        {
            return res.value;
        }
        else
        {
            return null;
        }
        //method 2:
//        return res == null ? null : res.value;
    }


    public int getSize()
    {
        return size;
    }


    public boolean isEmpty()
    {
        return size==0;
    }

    public static void main(String[] args)
    {

        System.out.println("Pride and Prejuidice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words))
        {
            System.out.println("Total words: "+ words.size());
            RBTree<String, Integer> map = new RBTree<>();
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
