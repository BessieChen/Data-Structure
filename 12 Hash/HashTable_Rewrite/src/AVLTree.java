import java.util.ArrayList;


public class AVLTree<K extends Comparable<K>, V>
{
    private class Node
    {
        private K key;
        private V value;
        private Node left;
        private Node right;
        private int height;

        public Node(K key, V value, Node left, Node right)
        {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            height = 1;

        }

        public Node(K key, V value)
        {
            this(key, value, null, null);
        }

        public Node()
        {
            this(null, null, null, null);
        }


        public String toString()
        {
            return key.toString() + " : " + value.toString();
        }
    }

    private Node root;
    private int size;

    public AVLTree()
    {
        root = null;
        size = 0;
    }


    //---------------------isBST from teacher starts---------------------------------------

    //引入了AVL机制之后，仍然可能平衡后的树不再是二分搜索树BST，所以下面的函数是检查是否依旧为BSTTree
    public boolean isBST()
    {
        ArrayList<K> keys = new ArrayList<>();
        inOrder(root, keys);
        for(int i = 0; i < keys.size()-1; i++)
        {
            if(keys.get(i).compareTo(keys.get(i+1)) > 0)
            {
                return false;
            }
        }
        return true;
    }

    //---------------------isBST from teacher ends---------------------------------------

    public boolean isBalanced()
    {
        return isBalanced(root);
    }

    //我的写法没有递归到底！
    private boolean isBalanced(Node node)
    {
        if(node == null)
        {
            return true;
        }
        if(node.left != null)
        {
            if(!isBalanced(node.left))
                return false;
        }
        if(node.right != null)
        {
            if(!isBalanced(node.right))
                return false;
        }

        return Math.abs(getBalanceFactor(node)) < 1;
    }

    //---------------------isBalanced from teacher starts---------------------------------------

    // 判断该二叉树是否是一棵平衡二叉树
    public boolean isBalanced_teacher(){
        return isBalanced_teacher(root);
    }

    // 判断以Node为根的二叉树是否是一棵平衡二叉树，递归算法
    private boolean isBalanced_teacher(Node node){

        if(node == null)
            return true;

        int balanceFactor = getBalanceFactor(node);
        if(Math.abs(balanceFactor) > 1)
            return false;
        return isBalanced_teacher(node.left) && isBalanced_teacher(node.right);
    }

    //---------------------isBalanced from teacher ends---------------------------------------

    private void inOrder(Node node, ArrayList<K> keys)
    {
        if(node == null)
        {
            return;
        }
        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }
    //---------------------isBST from teacher ends---------------------------------------

    private int getBalanceFactor(Node node)
    {
        if(node == null)
        {
            return 0;
        }
        //method 1:
        //return node.left.height - node.right.height;
        return getHeight(node.left) - getHeight(node.right);
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
        if(node.key.compareTo(key)==0)//compareTo是key拥有的函数，因为K extends Comparable<K>
        {
            node.value = value;//相当于更新了node之前的value
            return node;
        }
        else if(key.compareTo(node.key)>0)
        {
            node.right = add(node.right, key, value);
        }
        else
        {
            node.left = add(node.left, key, value);
        }

        //高度的更新：
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balanceFactor = getBalanceFactor(node);


        // 平衡维护
        //注意，因为每次都是添加一个元素后，就立马来检测balance，然后去平衡，所以，每次我们出现的不平衡都是==2，不可能大于2.
        //整体向左倾斜
        if (balanceFactor >= 2 && getBalanceFactor(node.left) >= 0)
            return rightRotate(node);
        //整体向右倾斜
        if (balanceFactor <= -2 && getBalanceFactor(node.right) <= 0)
            return leftRotate(node);

        if (balanceFactor >= 2 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balanceFactor <= -2 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        if(Math.abs(balanceFactor) > 1)
        {
            System.out.println("unbalanced: "+balanceFactor);
        }

        return node;
    }



    //--------------------------LR/RR from teacher starts-----------------------------
    // 对节点y进行向右旋转操作，返回旋转后新的根节点x
    //        y                              x
    //       / \                           /   \
    //      x   T4     向右旋转 (y)        z     y
    //     / \       - - - - - - - ->    / \   / \
    //    z   T3                       T1  T2 T3 T4
    //   / \
    // T1   T2
    // 注意，因为不能直接的x.right = y,这样T3就被覆盖了。
    private Node rightRotate(Node y) {
        Node x = y.left;//因为参数没有给x，所以我们为了找到T3，要先找到x
        Node T3 = x.right;

        // 向右旋转过程
        y.left = T3;
        x.right = y;

        // 更新height,注意，因为x的高度取决于y的高度，所以要先更新y的高度，再用y的新高度来更改x的高度。
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    // 对节点y进行向左旋转操作，返回旋转后新的根节点x
    //    y                             x
    //  /  \                          /   \
    // T1   x      向左旋转 (y)       y     z
    //     / \   - - - - - - - ->   / \   / \
    //   T2  z                     T1 T2 T3 T4
    //      / \
    //     T3 T4
    private Node leftRotate(Node y) {
        Node x = y.right;
        Node T2 = x.left;

        // 向左旋转过程
        y.right = T2;
        x.left = y;

        // 更新height
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }
    //--------------------------LR/RR from teacher starts-----------------------------

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

    private int getHeight(Node node)
    {
        if(node == null)
        {
            return 0;
        }
        return node.height;
    }

    //Wrong!
//    public V remove(K key)
//    {
//        if(!contains(key))
//        {
//            throw new IllegalArgumentException("Key "+ key + "doesn't exist.");
//        }
//        V ret = getNode(root, key).value;
//        root = remove(root, key);
//        return ret;
//    }

    //Right! from teacher
    public V remove(K key){

        Node node = getNode(root, key);
        if(node != null){
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    private Node remove(Node node, K key)
    {
        if(node == null)
        {
            return null;
        }

        Node retNode;
        if(key.compareTo(node.key) > 0)
        {
            node.right = remove(node.right, key);
            retNode = node;
        }
        else if(key.compareTo(node.key) < 0)
        {
            node.left = remove(node.left, key);
            retNode = node;
        }
        else
        {
            if(node.left == null)
            {
                Node rightNode = node.right;
                node.right = null;
                size--;
                //return rightNode;
                retNode = rightNode;
            }
            else if(node.right == null)
            {
                Node leftNode = node.left;
                node.left = null;
                size--;
                //return leftNode;
                retNode = leftNode;
            }
            else//相当于(node.left != null && node.right != null)
            {
                //之前的普通BST的remove才这么写
//                Node successor = removeMaximum(node.right);
//                node.value = successor.value;
//                return node;

                //因为这里也需要递归调用来保证平衡，所以要在删除successor之前，记录下successor的key，再递归删除
                Node successor = minimum(node.right);
                successor.right = remove(node.right, successor.key);//如此之后，保证了node.right的左右子树高度相差不超过1.
                successor.left = node.left;
                node.left = node.right = null;
                retNode = successor;
            }
        }

        if(retNode == null)//因为retNode是空指针的话，就不可能有retNode.left和right了
        {
            return null;
        }

        //以下是对retNode进行平衡，平衡之后retNode的左右子树高度相差不超过1.
        //高度的更新：
        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));

        int balanceFactor = getBalanceFactor(retNode);


        // 平衡维护
        //注意，因为每次都是添加一个元素后，就立马来检测balance，然后去平衡，所以，每次我们出现的不平衡都是==2，不可能大于2.
        //整体向左倾斜
        if (balanceFactor >= 2 && getBalanceFactor(retNode.left) >= 0)
            return rightRotate(retNode);
        //整体向右倾斜
        if (balanceFactor <= -2 && getBalanceFactor(retNode.right) <= 0)
            return leftRotate(retNode);

        if (balanceFactor >= 2 && getBalanceFactor(retNode.left) < 0) {
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }

        if (balanceFactor <= -2 && getBalanceFactor(node.right) > 0) {
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }

        if(Math.abs(balanceFactor) > 1)
        {
            System.out.println("unbalanced: "+balanceFactor);
        }

        return retNode;
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
            throw new IllegalArgumentException("BST is empty");
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
            throw new IllegalArgumentException("BST is empty");
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
        Node res = getNode(root, key);
        if(!contains(key))
        {
            throw new IllegalArgumentException("Key "+ key + " doesn't exist.");
        }
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
            AVLTree<String, Integer> map = new AVLTree<>();
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

            System.out.println("is BST: "+map.isBST());
            System.out.println("is Balanced: " + map.isBalanced_teacher());

            for(String word: words) {
                map.remove(word);
                if (!map.isBST() || !map.isBalanced_teacher())
                    throw new RuntimeException("Error");
            }
        }

    }
}
