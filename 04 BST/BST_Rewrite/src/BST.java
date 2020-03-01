import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BST<Element extends Comparable<Element>>//既然是树，里面的元素Element是可比较的, 所以是extends Comparable
{
    /*
     * 目录：
     * 0.private类Node
     *   构造一个private类叫Node，这个类里面也有一个成员变量是Node类型。
     *   然后我们可能会print某个Node的Element e，所以我们在Node类里面需要一个toString():return e.toString();
     *   //注意：我发现Node类中的构造函数一定是有参数Element e的，而不是无参数的。因为
     *   //如果你要构建一个new Node<>()，那就一定要传入你要构建的值，如果你只是想要一个空Node，那你是在外部的LinkedList里面的Node next = null;
     *   //构建空 Node
     *   //也就是说空Node是在外部类的public LinkedList(){ head = null; size = 0;}中构建
     *
     * 1. 构造函数
     *   注意是root = null;
     *   不可能是root = new Node<>(null);
     *   因为：1.this.e = null会报错。 2.这样初始一个BST的时候，就会自动生成一个Node, 即root = new Node<>()，事实是，初始一个BST时候，root还是空的。
     *    关于权限：
     *    成员变量：private的，例如root，否则会通过root.e = xxx等危险的行为改动我的内部数据。
     *    构造函数：一般都是public
     *    成员函数：大多数都是public的
     *    如果含有内部类，例如LinkedList和BST里面的Node，类是私有的：private class Node{}
     *    但是内部类的：成员函数，构造函数和成员函数需要是public的，例如public Element e; public Node left; public Node next;
     *
     * 2.函数：
     *   基本的：isEmpty(), getSize(), toString()
     *   增，删，查（没有改，因为如果有改的话，那就是用户指定要改的元素a，还要指定将a改成b，感觉很奇怪）
     *   特殊的：前序遍历，中序遍历，后序遍历. 因为这里的案例是打印，所以遍历函数的返回类型是void
     *   老师为了理解递归使用的: generateDepth(), generateString()
     *
     * 3.构造函数
     *   BST的构造函数，是指构造一个Tree，两种方法：
     *   无参数：BST的根root就是一个null
     *   有参数Element e：BST的root就是这个new Node(e)
     *
     * 4.remove函数
     *   以前的remove一般都是用户给一个index，然后返回类型是Element，但是tree比较特殊，不存在index，所以一般都是用户指定删除哪一个Element，返回值是void
     *   另外，可能存在不存在那一个Element的情况，所以我们需要先contains判断有没有，可是contains也需要消耗性能，
     *   所以还可以在递归到底的时候，若找不到则返回找不到。
     *
     *   当然，我们可以removeMin和removeMax，此时的返回类型就是Element
     * */



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

        /*
        * todo:
        *  1. pre,in,post NR
        *  3. toStirng()
        * */

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

//    @Override
//    public String toString()
//    {
//        return "abc";
//    }//todo

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append("\n");
        generateString(root, 0, res);
        return res.toString();
    }


    private int generateDepth()
    {
        return 0;
    } //todo

    private String generateString()
    {
        return "abc";
    } //todo


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

    //错误示范：add 不能用void递归实现！
    /*
         错误示范：
         public void add(Element e)
        {
            if(root == null)
                {
                    root = new Node(e);
                    return;
                }
            add(root, e);
        }

        private void add(Node node, Element e)
        {
            if(node == null)
            {
                node = new Node(e); todo 这一句也是错的，因为传进来的是null，不是一个地址，你对null是做不了修改的。
                //node.right = new Node(e); todo 显然是错的，node是null就没有right
            }

            if(node.e.compareTo(e)>0)
            {
                add(node.left, e);
            }
            else if(node.e.compareTo(e)<0)
            {
                add(node.right, e);
            }
            //剩下==e的话就不处理。
        }
    */

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

    private void test(int n)//todo 这里就是为了说明int，null当做参数传入，是无法被修改的。但是如果int[], object当做参数传入，是可以被修改的。
    {
        if(n == 0)
        {
            n = 1;
            return;
        }
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

//    public void preOrder_NR()
//    {
//
//    }

    public void preOrder_NR()
    {
    //        Stack<Node> stack = new Stack<>();
    //        Node del;
    //        while(!isEmpty())
    //        {
    //            stack.push(root);
    //            del = stack.pop();
    //            System.out.println(del.e);
    //            if(del.right != null)
    //            {
    //                stack.push(del.right);
    //            }
    //            if(del.left != null)
    //            {
    //                stack.push(del.left);
    //            }
    //        }
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

//    public void inOrder_NR()
//    {
//
//    }

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

//    public void postOrder_NR()
//    {
//
//    }

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
        Queue<Node> queue = new LinkedList<>();//todo 1.适合用queue的先入先出来实现levelOrder，而不是Stack的后入先出，注意Queue是接口，然后里面是Node泛型，最后使用java的LinkedList实现Queue
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

    //BST中最复杂的就是关于怎么remove了，我会从以下方式展开：
    /*
    1. 返回 max 和 min： Element maximum(...), Element minimum(...)
    2. 删除 max 和 min并返回该删除值： Element removeMax(), Element removeMin()
    3. 删除某个特定值： void remove(Element e)
    */

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
        if(node.right == null) //todo 这里是我之前纠结的，我之前觉得终止是if(node == null)，不是，是这个if(node.right == null)，我一定要找到一个没有right节点的Node，于是说明这个Node就是最右边的。
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
        if(node.left == null) //todo 这里是我之前纠结的，我之前觉得终止是if(node == null)，不是，是这个if(node.left == null)，我一定要找到一个没有right节点的Node，于是说明这个Node就是最右边的。
        {
            return node;
        }
        return minimum(node.left);//这里写的就是如果left还有值就一直去搜，最后两个return会保证找到min就一口气return回去。
    }

//    public Element maximum_NR()//todo
//    {
//
//    }
//
//    public Element minimum_NR()//todo
//    {
//
//    }

    /*
    这个让我想到了之前 用递归方法实现LinkedList的时候：
    有一个函数叫做：removeElement(Element e),这个函数是给定e，然后我们把它删除
    和BST中的removeMax()类似，这个removeElement()也是没有涉及index
    回忆removeElement()中是需要我们找到e的前一个节点，然后删除。其实删除操作都是要找到该删除节点的前一个节点。
    所以，由于node.right == null的话，node就是待删除节点，但是！
    我发现这里有点不同：
    例如这个待删除节点node，我们不知道它的前一个，也就是它的父亲，假设叫dad，我们不知道node是dad的left还是dad的right，所以不可能直接用void递归+
    之前的linkedList中的removeElement，是直接到e这个节点上，从e的前一个节点删除就好了，private的递归函数可以是void类型
    虽然BST的remove也是找到e元素的节点，并且删除，不需要返回什么，但是！
    LinkedList 是线性的，我们容易获知删除节点的前一个节点。
    所以可以有：node.next = node.next.next;
    BST 是非线性的，所以删除节点可能是父亲节点的左孩子，或者是父亲节点的右孩子。
    假设父亲节点是node，删除节点之后被加工过的节点（例如如果删除节点有左右孩子，那么加工过的节点就是：右孩子的最小元素，或者叫：successor/rightMin
    所以可能有：
    node.right = successor; --当删除节点是父亲节点的右孩子
    node.left = successor; --当删除节点是父亲节点的左孩子
    所以，要实现这种 赋值，我们就需要node.right = remove(node.right, e)
    所以remove需要是返回Node类，而不是void！
    */

    public Element removeMax()
    {
        if(root == null)
        {
            throw new IllegalArgumentException("Remove Max failed, BST is empty.");
        }
        Element ret = maximum();
        root = removeMax(root); //todo 这里就是我说的：private的removeMax不能是void类型，需要是Node类型，来修改BST内部的节点关系。而返回目标值Element的任务，交给了maximum()
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
        root = removeMin(root);//todo 这里就是我说的：private的removeMin不能是void类型，需要是Node类型，来修改BST内部的节点关系。而返回目标值Element的任务，minimin()
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



    //removeMin(), removeMax()错误示范：
    /*
    public Element removeMax()
    {
        if(root == null)
        {
            throw new IllegalArgumentException("Remove Max failed, BST is empty.");
        }
        return removeMax(root).e;
    }

    private Node removeMax(Node node)
    {
        if(node.right.right == null)//满足这个if条件的node，就是待删除节点node.right的父亲。
        {
            Node del = node.right;
            node.right = node.right.left;
            size--;
            return del;
        }
        return removeMax(node.right);//todo 错在这里，你返回的是Node, 可是这个Node都没有作为右值给任何节点。
    }

    public Element removeMin()
    {
        if(root == null)
        {
            throw new IllegalArgumentException("Remove Min failed, BST is empty.");
        }
        return removeMin(root).e;
    }

    private Node removeMin(Node node)
    {
        if(node.left.left == null)
        {
            if(node.left.right == null)
            {
                Node del = node.left;
                node.left = node.left.right;
                size--;
                return del;
            }
            else
            {
                return removeMax(node.left.right);
            }
        }
        return removeMax(node.left);//todo 错在这里，你返回的是Node, 可是这个Node都没有作为右值给任何节点。
    }

    */

    //todo 能不能将void改成对的？
    /*
    public Element removeMax()
    {
        if(root == null)
        {
            throw new IllegalArgumentException("Remove Max failed, BST is empty.");
        }
        Element ret = maximum();
        if(root.right == null)
        {
            root = root.left;
        }
        else
        {
            removeMax(root);
        }
        return ret;
    }

    private void removeMax(Node node)
    {
        if(node.right.right == null)//满足这个if条件的node，就是待删除节点node.right的父亲。
        {
            Node del = node.right;
            node.right = del.left;
            size--;
        }
        removeMax(node.right);//todo 错在这里，你返回的是Node, 可是这个Node都没有作为右值给任何节点。
    }

    public Element removeMin()
    {
        if(root == null)
        {
            throw new IllegalArgumentException("Remove Min failed, BST is empty.");
        }
        Element ret = minimum();
        if(root.left == null)
        {
            root = root.right;
        }
        else
        {
            removeMax(root);
        }
        return ret;
    }

    private void removeMin(Node node)
    {
        if(node.left.left == null)
        {
            Node del = node.left;
            node.left = del.right;
            size--;
        }
        removeMin(node.left);//todo 错在这里，你返回的是Node, 可是这个Node都没有作为右值给任何节点。
    }
    */


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
            return node;//这里return的是null，然后这个null再返回给它本身的父亲节点的left或者right，所以没有影响整个BST的数据
        }
        if(node.e.compareTo(e) == 0)//todo 符合我的原则：先考虑特殊的情况，或者需要较为复杂处理的情况
        {
            if(node.left == null)//包括了left == null && right != null, 以及left == null && right == null
            {
                return node.right;
            }
            else if(node.right == null)//包括了left != null && right == null, 因为进入else if已经保证了node.left != null
            {
                return node.left;
            }
            else//node.right != null && node.left != null
            //remove操作中最复杂的可能就是，当待删除节点有左右孩子。我们选择由
            //1. node的右孩子为根节点的树里面的最 小 值，作为node的替代，代替node，返回给node的父亲
            //2. node的左孩子为根节点的树里面的最 大 值，作为node的替代，代替node，返回给node的父亲
            //method 1:使用右孩子树中的 最 小 值, 我称为rightMin
            {
                //method 1:
                node.e = removeMin(node.right).e;
                return node;

                //method 1.2:
//                node.e = removeMax(node.left).e;
//                return node;

                //method 2:使用右孩子树中的 最 小 值
//                Node successor = removeMin(node.right);//注意removeMin这里已经size--过了，所以之后就不必size--了。
//                successor.right = node.right;//既然successor替代了node，那么node也要把node自己拥有的东西给rightMin, 此时的node.right已经是删除过最小节点的了
//                successor.left = node.left;
//                node.right = node.left = null;
//                return successor;
//                这里不需要额外的size--;

                //method 3:使用左孩子树中的 最 大 值
//                Node predecessor = removeMaximum(node.left);
//                predecessor.right = node.right;
//                predecessor.left = node.left;
//                node.right = node.left = null;
//                return predecessor;
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

        return node;//最后这个node再传回它本身的父亲的left或者right
    }

}
