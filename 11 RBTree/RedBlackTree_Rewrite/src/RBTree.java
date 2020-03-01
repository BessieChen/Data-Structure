import java.util.ArrayList;

/*
* 目录
* 1. 红色节点的孩子一定是黑色节点，
*    黑色节点的右孩子一定是黑色节点，左侧孩子可能是黑色也可能是红色节点
*
* 2. 如果节点个数是 N， RB Tree的高度最高是 2*logN
*    如果经常进行添加，删除操作，红黑树的性能更好
*    如果树固定，不再添加删除，只是查询，AVL性能更好
*
* 3. 每次新添加的节点是红色，意味着要和黑色节点融合。
* */
public class RBTree<K extends Comparable<K>, V> {

    private class Node
    {
        public K key;
        public V value;
        public Node left;
        public Node right;
        public boolean color;

        public Node(K key, V value)
        {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            color = RED;
        }
    }

    private static final boolean RED = true;//设置RED == true，这样isRED()就可以直接return color了。
    private static final boolean BLACK = false;

    private Node root;
    private int size;

    public RBTree()
    {
        root = null;
        size = 0;
    }

    //--------------------- 辅助函数 ---------------------
    private void flipcolor(Node node)
    {
        node.color = RED;
        node.left.color = node.right.color = BLACK;
    }

    //     y                    x
    //    /  \     右旋转      /  \
    //   x   T1   ------->   T3    y
    //  / \                       /  \
    // T3  T2                     T2  T1
    private Node rightRotate(Node y)
    {
        Node x = y.left;
        Node T2 = x.right;
        y.left = T2;
        x.right = y;

        x.color = y.color;
        y.color = RED;
        return x;
    }

    //    y                      x
    //  /   \     左旋转         /  \
    // T1   x   --------->     y   T3
    //     / \               /   \
    //    T2 T3             T1   T2
    private Node leftRotate(Node y)
    {
        Node x = y.right;
        Node T2 = x.left;
        y.right = T2;
        x.left = y;

        x.color = y.color;
        y.color = RED;
        return x;
    }

    private boolean isRed(Node node)
    {
        if(node == null)
            return BLACK;
        return node.color;
    }

    //--------------------- 基本函数 ---------------------
    public int getSize()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    @Override
    public String toString()
    {
        return "abc";
    }
    //--------------------- 增删改查 ---------------------

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
        }

        //左子黑 && 右子红： leftRotate()
        if(!isRed(node.left) && isRed(node.right))
        {
            node = leftRotate(node);
        }
        //左子红 && 左子的左子红： rightRotate()
        if(isRed(node.left) && isRed(node.left.left))
        {
            node = rightRotate(node);
        }

        //左子红 && 右子红： flipColor()
        if(isRed(node.left) && isRed(node.right))
        {
            flipcolor(node);
        }

        //以上三个if都不用return，等到全部if都检测过之后再return
        return node;
    }

    // 返回以node为根节点的二分搜索树中，key所在的节点
    private Node getNode(Node node, K key){

        if(node == null)
            return null;

        if(key.equals(node.key))
            return node;
        else if(key.compareTo(node.key) < 0)
            return getNode(node.left, key);
        else // if(key.compareTo(node.key) > 0)
            return getNode(node.right, key);
    }

    public boolean contains(K key){
        return getNode(root, key) != null;
    }

    public V get(K key){

        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue){
        Node node = getNode(root, key);
        if(node == null)
            throw new IllegalArgumentException(key + " doesn't exist!");

        node.value = newValue;
    }

    // 返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node){
        if(node.left == null)
            return node;
        return minimum(node.left);
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node){

        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    // 从二分搜索树中删除键为key的节点
    public V remove(K key){

        Node node = getNode(root, key);
        if(node != null){
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    private Node remove(Node node, K key){

        if( node == null )
            return null;

        if( key.compareTo(node.key) < 0 ){
            node.left = remove(node.left , key);
            return node;
        }
        else if(key.compareTo(node.key) > 0 ){
            node.right = remove(node.right, key);
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
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;

            return successor;
        }
    }

    public static void main(String[] args){

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            RBTree<String, Integer> map = new RBTree<>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }

        System.out.println();
    }
}
