import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class BSTSet<Element extends Comparable<Element>> implements Set<Element>
{
    private class BST<Element extends Comparable<Element>>
    {
        private class Node
        {
            public Element e;
            public Node left;
            public Node right;

            public Node(Element e)//一个新Node的left和right一般不在构造函数里面声明。
            {
                this.e = e;
                this.left = null;
                this.right = null;
            }

            //区别于之前的LinkedList的Node的构建函数：它可以在参数表中声明 Node next，见下：
/*        public Node(Element e, Node next)
        {
            this.e = e;
            this.next = next;
        }

        //method 1:
        public Node(Element e)
        {
            this(e, null);
        }

        //method 2:
//        public Node(Element e)
//        {
//            this.e = e;
//            next = null;
//        }*/

            @Override
            public String toString()
            {
                return this.e.toString();
            }
        }

        private Node root;
        private int size;

        public BST()
        {
            root = null;
            size = 0;
        }

        public BST(Element e)
        {
            root = new Node(e);
            size = 1;
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
            res.append(generateDepth(depth)+node.e+"\n");
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

        public void add(Element e)
        {
            root = add(root, e);
        }

        private Node add(Node node, Element e)
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
            return node;
        }

        public boolean contains(Element e)
        {
            return contains(root, e);
        }

        private boolean contains(Node node, Element e)
        {
            if(node == null)
                return false;

            if(node.e.compareTo(e) == 0)
            {
                return true;
            }
            else if(node.e.compareTo(e) > 0)
            {
                return contains(node.left, e);
            }
            else
            {
                return contains(node.right, e);
            }
        }

        public void preOrder_R()
        {
            preOrder_R(root);
        }

        private void preOrder_R(Node node)
        {
            if(node == null)
                return;
            System.out.println(node.e);
            preOrder_R(node.left);
            preOrder_R(node.right);
        }


        public void preOrder_NR()
        {
            Stack<Node> stack = new Stack<>();
            stack.push(root);
            Node del;
            while(!stack.isEmpty())
            {
                del = stack.pop();
                System.out.println(del.e);
                if(del.right != null)
                {
                    stack.push(del.right);
                }
                if(del.left != null)
                {
                    stack.push(del.left);
                }
            }
        }


        public void inOrder_R()
        {
            inOrder_R(root);
        }

        private void inOrder_R(Node node)
        {
            if(node == null)
                return;
            inOrder_R(node.left);
            System.out.println(node.e);
            inOrder_R(node.right);
        }

        public void inOrder_NR()
        {
            Stack<Node> stack = new Stack<>();
            Node cur = root;
            while(!stack.empty() || cur!=null)
            {
                while(cur != null)//注意不能是while(cur.left != null),因为如果是这样的话，我们的最后一个cur就是最小的那个，但是这个cur并没有push进去
                {
                    stack.push(cur);
                    cur = cur.left;
                }
                Node pop = stack.pop();
                System.out.println(pop);
                cur = pop.right;
            }
        }

        public void postOrder_R()
        {
            postOrder_R(root);
        }

        private void postOrder_R(Node node)
        {
            if(node == null)
                return;
            postOrder_R(node.left);
            postOrder_R(node.right);
            System.out.println(node.e);
        }

        public void postOrder_NR()
        {
            Stack<Node> stack = new Stack<>();
            Node cur = root;
            Node prev = null;
            while(!stack.empty() || cur != null)
            {
                while(cur != null)
                {
                    stack.push(cur);
                    cur = cur.left;
                }

                Node topOfStack = stack.peek();
                if(topOfStack.right != null && topOfStack.right != prev)
                {
                    cur = topOfStack.right;
                }

                if(topOfStack.right == null || topOfStack.right == prev)
                {
                    stack.pop();//pop最上方的，也就是pop了topOfStack
                    prev = topOfStack;//记录刚刚pop掉的
                    System.out.println(topOfStack);
                }
            }
        }

        public void levelOrder()
        {
            Queue<Node> queue = new LinkedList<>();
            //规则是：父亲如果被remove了，这个父亲的两个孩子：left和right就依次add进queue
            queue.add(root);
            while( !queue.isEmpty())//如果queue不是空的，就remove，然后remove掉的节点的左右孩子依次add进入queue
            {
                Node del = queue.remove();
                System.out.println(del.e);
                if(del.left != null)
                {
                    queue.add(del.left);
                }
                if(del.right != null)
                {
                    queue.add(del.right);
                }
            }

        }

        public Element maximum()
        {
            if(root == null)
            {
                throw new IllegalArgumentException("get Max failed, BST is empty.");
            }

            return maximum(root).e;
        }

        private Node maximum(Node node)
        {
            if(node.right == null)
            {
                return node;
            }
            return maximum(node.right);//这里写的就是如果right还有值就一直去搜，最后两个return会保证找到max就一口气return回去。
        }

        public Element minimum()
        {
            if(root == null)
            {
                throw new IllegalArgumentException("get Min failed, BST is empty.");
            }

            return minimum(root).e;
        }

        private Node minimum(Node node)
        {
            if(node.left == null)
            {
                return node;
            }
            return minimum(node.left);//这里写的就是如果left还有值就一直去搜，最后两个return会保证找到min就一口气return回去。
        }

        public Element removeMax()
        {
            if(root == null)
            {
                throw new IllegalArgumentException("Remove Max failed, BST is empty.");
            }
            Element ret = maximum();
            root = removeMax(root);
            return ret;
        }

        private Node removeMax(Node node)
        {
            if(node.right == null)
            {
                size--;
                return node.left;//这里不用管node.left是null或者不是null，都可以返回
            }

            node.right = removeMax(node.right);//node的右孩子，是将右孩子修正后的值
            return node;//node再传给node本身的父亲的left或者right

        }

        public Element removeMin()
        {
            if(root == null)
            {
                throw new IllegalArgumentException("Remove Min failed, BST is empty.");
            }
            Element ret = minimum();
            root = removeMin(root);
            return ret;
        }

        private Node removeMin(Node node)
        {
            if(node.left == null)
            {
                size--;
                return node.right;//这里不用管node.right是null或者不是null，都可以返回
            }

            node.left = removeMin(node.left);//node的右孩子，是将右孩子修正后的值
            return node;//node再传给node本身的父亲的left或者right
        }

        public void remove(Element e)
        {
            if(root == null)
            {
                throw new IllegalArgumentException("Remove failed, BST is empty.");
            }
            root = remove(root, e);
        }

        private Node remove(Node node, Element e)
        {
            if(node == null)
            {
                System.out.println("Element not found.");
                return node;
            }
            if(node.e.compareTo(e) == 0)
            {
                if(node.left == null)//包括了left == null && right != null, 以及left == null && right == null
                {
                    return node.right;
                }
                else if(node.right == null)//包括了left != null && right == null, 因为进入else if已经保证了node.left != null
                {
                    return node.left;
                }
                else
                {
                    node.e = removeMin(node.right).e;
                    return node;
                }
            }
            else if(node.e.compareTo(e) > 0)
            {
                node.left = remove(node.left, e);
            }
            else//node.e.compareTo(e) < 0
            {
                node.right = remove(node.right, e);
            }

            return node;
        }

    }
    //todo 不能写成public class BSTSet<Element> implements Set<Element>， 即少了extends Comparable<Element>，否则下面的成员变量private BST<Element> bst;会报错。因为BST要求Element是可以compare的
    private BST<Element> bst;

    public BSTSet()
    {
        bst = new BST<>();
    }

    @Override
    public void add(Element e)
    {
        bst.add(e);
    }

    @Override
    public void remove(Element e)
    {
        bst.remove(e);
    }

    @Override
    public boolean contains(Element e)
    {
        return bst.contains(e);
    }

    @Override
    public boolean isEmpty()
    {
        return bst.isEmpty();
    }

    @Override
    public int getSize()
    {
        return bst.getSize();
    }

}
