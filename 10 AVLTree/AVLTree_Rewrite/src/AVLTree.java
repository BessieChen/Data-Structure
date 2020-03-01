import java.util.ArrayList;

public class AVLTree<E extends Comparable<E>>
{
    private class Node
    {
        public E e;
        public Node left;
        public Node right;
        public int height;
        //todo 1: 为什么没有public int balanceFactor

        public Node(E e)
        {
            this.e = e;
            left = null;
            right = null;
            height = 1;
        }

        //注意！不存在使用default构造函数的情况：没有参数e的public Node()
        //即当初始一个AVLTree()时候，构造的root也依旧是 root = null; 如果root = new Node(); 的话，add(root, e) 中的if(node == null) return new Node(e);就不能判断root是不是空了！
        @Override
        public String toString()
        {
            return e.toString();
        }
    }

    private Node root;
    private int size;

    public AVLTree()
    {
        root = null;//注意这里需要设置成null！
        size = 0;
    }

    //基本函数 -------------------------------------------------------------------------------------
    public int getSize()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    @Override
    public String toString()//todo 2.
    {
        return "abc";
    }


    //辅助函数：-------------------------------------------------------------------------------------
    // -- BST相关: inOrder()
    // -- Balance相关: 计算节点高度, 通过孩子高度计算节点平衡因子

    public boolean isBST()
    {
        ArrayList<E> array = new ArrayList<>();
        if(root == null)
            return true;
        inOrder(root, array);
        for(int i = 1; i < array.size(); i++)
        {
            if(array.get(i).compareTo(array.get(i-1)) < 0)
                return false;
        }
        return true;
    }

    private void inOrder(Node node, ArrayList<E> array)
    {
        if(node == null)
            return;
        inOrder(node.left, array);
        array.add(node.e);
        inOrder(node.right, array);
    }

    private int getHeight(Node node)
    {
        return node != null ? node.height : 0;
    }
    private int updateHeight(Node node)//我喜欢显示的更新height
    {
        //method 1:
//        int leftH = 0;
//        int rightH = 0;
//        if(node.left != null) leftH = node.left.height;
//        if(node.right != null) rightH = node.right.height;

        //method 2: 将重复的思想封装：
        if(node == null) return 0;
        int leftH = getHeight(node.left);
        int rightH = getHeight(node.right);
        return Math.max(leftH, rightH) + 1;
    }

    private int getBalance(Node node)
    {
        if(node == null) return 0;
        int leftH = getHeight(node.left);
        int rightH = getHeight(node.right);
        return leftH - rightH;
    }

    public boolean isBalanceTree()
    {
        return isBalanceTree(root);
    }

    //错误的isBalanceTree()
/*
    private boolean isBalanceTree(Node node)//我想要的：到达最底端，再判断node是不是平衡，再返回上一层，用&&返回
    {
        if(node == null)
        {
            return true;
        }

        isBalanceTree(node.left);
        isBalanceTree(node.right);

        int leftBal = getBalance(node.left);
        int rightBal = getBalance(node.right);

        return (Math.abs(leftBal) <= 1) && (Math.abs(rightBal) <= 1);
    }

*/

    private boolean isBalanceTree(Node node)//既然上面的函数行不通，那就换一种方式：自顶向下判断。
    {
        if(node == null)
        {
            return true;
        }

        if(Math.abs(getBalance(node)) >= 2)
            return false;//如果是false就不需要继续判断了；如果是true则继续向下判断。

        return isBalanceTree(node.left) && isBalanceTree(node.right);
    }

    private Node getNode(Node node, E e)//用于contains(), set()
    {
        if(node == null)
            return node;

        if(node.e.compareTo(e) < 0)
        {
            return getNode(node.right, e);
        }
        else if(node.e.compareTo(e) > 0)
        {
            return getNode(node.left, e);
        }
        else//node.e.compareTo(e) == 0
        {
            return node;
        }
    }

    // 对节点y进行向右旋转操作，返回旋转后新的根节点x
    //        y                              x
    //       / \                           /   \
    //      x   T1     向右旋转 (y)        z     y
    //     / \       - - - - - - - ->    / \   / \
    //    z   T2                       T3  T4 T2 T1
    //   / \
    // T3   T4
    private Node rightRotate(Node y)
    {
        Node x = y.left;
        Node T2 = x.right;
        y.left = T2;
        x.right = y;

        y.height = updateHeight(y);
        x.height = updateHeight(x);

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
    private Node leftRotate(Node y)
    {
        Node x = y.right;
        Node T2 = x.left;
        y.right = T2;
        x.left = y;

        y.height = updateHeight(y);
        x.height = updateHeight(x);

        return x;
    }

    private Node balancingNode(Node node)
    {
        node.height = updateHeight(node);

        int nodeBal = getBalance(node);
        int leftBal = getBalance(node.left);//todo 5:既然node.left都是平等的了，还需要判断leftBal吗？ 可能继续往上返回的时候，就不平衡了。
        int rightBal = getBalance(node.right);

        if(nodeBal >= 2 && leftBal >= 0)//LL: 左孩子没有向右倾：leftBal >= 0
        {
            return rightRotate(node);
        }

        if(nodeBal >= 2 && leftBal <= -1)//LR
        {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if(nodeBal <= -2 && rightBal <= 0)//RR: 右孩子没有向左倾：rightBal <= 0
        {
            return leftRotate(node);
        }

        if(nodeBal <= -2 && rightBal > 0)//RL
        {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;//其余都是正常情况，直接return node;
    }

    //增删改查 -------------------------------------------------------------------------------------
    public void add(E e)
    {
        root = add(root, e);
    }

    private Node add(Node node, E e)
    {
        if(node == null)
        {
            size++;
            return new Node(e);
        }

        if(node.e.compareTo(e) < 0)
        {
            node.right = add(node.right, e);
        }
        else if(node.e.compareTo(e) > 0)
        {
            node.left = add(node.left, e);
        }
        else
        {
            return node;
        }

        //todo 3: 解释new Node()在底部，肯定平衡，可能不平衡的只能是爸爸
        /*int nodeBal = getBalance(node);
        int leftBal = getBalance(node.left);
        int rightBal = getBalance(node.right);

        if(nodeBal >= 2 && leftBal >= 0)//LL: 左孩子没有向右倾：leftBal >= 0
        {
            return rightRotate(node);
        }

        if(nodeBal >= 2 && leftBal <= -1)//LR
        {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if(nodeBal <= -2 && rightBal <= 0)//RR: 右孩子没有向左倾：rightBal <= 0
        {
            return leftRotate(node);
        }

        if(nodeBal <= -2 && rightBal > 0)//RL
        {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;//其余都是正常情况，直接return node;*/

        return balancingNode(node);
    }

    //-------------------------------------------------------------------------------------
    public void remove(E e)
    {
        remove(root, e);
    }

    private Node remove(Node node, E e)
    {
        if(node == null)
            return node;

        Node retNode;
        if(node.e.compareTo(e) < 0)
        {
            node.right = remove(node.right, e);
            retNode = node;
        }
        else if(node.e.compareTo(e) > 0)
        {
            node.left = remove(node.left, e);
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
                Node successor = removeMin(node.right);
                if(successor != null)
                {
                    successor.right = node.right;
                    successor.left = node.left;
                    node.left = node.right = null;
                }
                retNode = successor;
            }
        }
        if(retNode == null)
            return null;

        return balancingNode(retNode);
    }

    public E removeMin()
    {
        Node ret = removeMin(root);
        return ret == null ? null : ret.e;
    }

    private Node removeMin(Node node)
    {
        if(node == null)
            return node;

        if(node.left == null) {
            size--;
            return node.right;
        }

        node.left = removeMin(node.left);
        return balancingNode(node);

        //todo 4: 解释返回的 node.right 肯定平衡，因为它之前就是平衡的，删除的只是它父亲，可能不平衡的只能是node
        /*int nodeBal = getBalance(node);
        int leftBal = getBalance(node.left);//todo 5:既然node.left都是平等的了，还需要判断leftBal吗？ 可能继续往上返回的时候，就不平衡了。
        int rightBal = getBalance(node.right);

        if(nodeBal >= 2 && leftBal >= 0)//LL: 左孩子没有向右倾：leftBal >= 0
        {
            return rightRotate(node);
        }

        if(nodeBal >= 2 && leftBal <= -1)//LR
        {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if(nodeBal <= -2 && rightBal <= 0)//RR: 右孩子没有向左倾：rightBal <= 0
        {
            return leftRotate(node);
        }

        if(nodeBal <= -2 && rightBal > 0)//RL
        {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;//其余都是正常情况，直接return node;*/
    }

    //-------------------------------------------------------------------------------------
    public void set(E e, E newE)
    {
        Node node = getNode(root, e);
        if(node != null)
            node.e = newE;
    }

    //-------------------------------------------------------------------------------------
    public boolean contains(E e)
    {
        return getNode(root, e) != null;
    }

    public E minimum()
    {
        return minimum(root);
    }

    private E minimum(Node node)
    {
        if(node.left == null)
            return node.e;
        return minimum(node.left);
    }

    public E maximum()
    {
        return maximum(root);
    }

    private E maximum(Node node)
    {
        if(node.right == null)
            return node.e;
        return maximum(node.right);
    }
    //-------------------------------------------------------------------------------------

}