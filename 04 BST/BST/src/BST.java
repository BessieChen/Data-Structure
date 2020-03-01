import java.util.*;

public class BST<Element extends Comparable<Element>> {
    private class Node
    {
        public Element e;
        public Node left;
        public Node right;

        public Node(Element e)
        {
            this.e = e;
            left = null;
            right = null;
        }

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
        //注意这里不能写成root = new Node(e:null), 因为这样之后判断if (root == null),即便是null也会认为不是null，因为的确是跟了一个节点的。
        root = null;
        size = 0;
    }
    public BST(Element e)
    {
        root = new Node(e);
        size = 0;
    }

    public int getSize()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size==0;
    }

    //------------------------------------Add starts---------------------------------------------------------------
    public void add_R(Element e)
    {
        root = add_R(root, e);
    }

    private Node add_R(Node node, Element e)
    {
        if(node == null)
        {
            size++;
            return new Node(e);
        }
        if(e.compareTo(node.e) == 0)
        {
            return node;
        }
        else if(e.compareTo(node.e) > 0)
        {
            node.right = add_R(node.right, e);
        }
        else
        {
            node.left = add_R(node.left,e);
        }
        return node;
    }


    //add错误示范：
    /*
    public void add_R2(Element e)
    {
        if(root == null)
        {
            root = new Node(e);
        }
        else
        {
            add_R2(root, e);
        }
//        add_R2(root, e);
    }
    private void add_R2(Node node, Element e)
    {
        if(node != null)
            System.out.println(node.e);

        if(node == null)
        {
            node.right = new Node(e);// todo node是null，所以node就不可能有个right
            node = new Node(e); //todo 这一句也是错的，因为传进来的是null，不是一个地址，你对null是做不了修改的。
            size++;
            return;
        }
        if(e.compareTo(node.e) == 0)
        {
            //return;
        }
        else if(e.compareTo(node.e) > 0)
        {
            add_R2(node.right, e);
        }
        else
        {
            add_R2(node.left, e);
        }
    }*/

    private void test(int n)
    {
        if(n == 0)
        {
            n = 1;
            return;
        }
    }


    public void add_R3(Element e)//写的不够好，没有递归到底
    {
        if(root == null)
        {
            root = new Node(e);
        }
        else
        {
            add_R3(root, e);
        }
    }

    private void add_R3(Node node, Element e)//写的不够好，没有递归到底
    {
        if(e.compareTo(node.e) == 0)
        {
        }
        else if(e.compareTo(node.e)>0 && node.right == null)
        {
            node.right = new Node(e);
        }
        else if(e.compareTo(node.e)<0 && node.left == null)
        {
            node.left = new Node(e);
        }
        else if(e.compareTo(node.e)>0 && node.right != null)
        {
            add_R3(node.right, e);
        }
        else
        {
            add_R3(node.left, e);
        }
    }


    //------------------------------------Add ends---------------------------------------------------------------

    //------------------------------------contains starts---------------------------------------------------------------
    public boolean contains(Element e)
    {
        return contains(root,e);
    }

    private boolean contains(Node node, Element e)
    {
        if(node == null)
        {
            return false;
        }
        if(e.compareTo(node.e) == 0)
        {
            return true;
        }
        else if(e.compareTo(node.e) > 0)
        {
            return contains(node.right, e);
        }
        else
        {
            return contains(node.left, e);
        }
    }
    //------------------------------------contains ends---------------------------------------------------------------

    //------------------------------------traverse starts---------------------------------------------------------------
    public void preOrder_R()
    {
        preOrder_R(root);
    }

    private void preOrder_R(Node node)
    {
        if(node == null)
        {
            return;
        }
        System.out.println(node.e);
        preOrder_R(node.left);
        preOrder_R(node.right);
    }

//    //method 2:
//    private void preOrder_R(Node node)
//    {
//        if(node != null);
//        {
//            System.out.println(node.e);
//            preOrder(node.left);
//            preOrder(node.right);
//        }
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

    public void inOrder()
    {
        inOrder(root);
    }

    private void inOrder(Node node)
    {
        if(node != null)
        {
            inOrder(node.left);
            System.out.println(node.e);
            inOrder(node.right);
        }
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

    public void postOrder()
    {
        postOrder(root);
    }

    private void postOrder(Node node)
    {
        if(node != null)
        {
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.e);
        }
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


    public void postOrder_NR2() {
        Stack<Node> stack = new Stack<Node>();
        Node cur = root;
        Node prev = null;
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            Node topOfStack = stack.peek();
            if (topOfStack.right == null || prev == topOfStack.right) {
                stack.pop();
                prev = topOfStack;
                System.out.println(topOfStack);
            } else {
                cur = topOfStack.right;
            }
        }
    }

    public void levelOrder()
    {
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        Node del;
        while (!q.isEmpty()) {
            del = q.remove();
            System.out.println(del.e);
            if (del.left != null) {
                q.add(del.left);
            }
            if (del.right != null) {
                q.add(del.right);
            }
        }

    }

    //------------------------------------traverse ends---------------------------------------------------------------

    //------------------------------------toString starts---------------------------------------------------------------
//    @Override
//    public String toString()
//    {
//        StringBuilder res = new StringBuilder();
//        res.append(generateString(root, 0));
//        return res.toString();
//    }
//
//    private String generateString(Node node, int depth)
//    {
//        StringBuilder res = new StringBuilder();
//        if(node == null)
//        {
//            res.append(generateDepth(depth)+"null\n");
//        }
//        res.append(generateDepth(depth)+node.e+"\n");
//        generateString(node.left,depth+1);
//        generateString(node.right,depth+1);
//
//        return res.toString();
//    }
//
//    private String generateDepth(int depth)
//    {
//        StringBuilder res = new StringBuilder();
//        for(int i = 0; i < depth; i++)
//        {
//            res.append("--|");
//        }
//        return res.toString();
//    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
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
    //------------------------------------toString ends---------------------------------------------------------------

    //------------------------------------get minimum/maximum starts---------------------------------------------------------------
    public Element minimum()
    {
        if(isEmpty())
        {
            throw new IllegalArgumentException("BST is empty");
        }
        return minimum(root).e;
    }

    //wrong:
//    private Node minimum(Node node)
//    {
//        if (node.left == null)
//        {
//            if (node.right == null)
//            {
//                return node;
//            }
//            else
//            {
//                return node.right;
//            }
//        }
//        else
//        {
//            node.left = minimum(node.left);
//        }
//        Node ret = minimum(node.left);
//        return ret;
//    }

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

    public Node minimum_NR()
    {
        if (size == 0) {
            throw new IllegalArgumentException("BST is empty");
        }
        Node node = root;
        while(node.left != null)
        {
            node = node.left;
        }
        return node;
    }

    public Element maximum()
    {
        if(isEmpty())
        {
            throw new IllegalArgumentException("BST is empty");
        }
        return maximum(root).e;
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

    public Node maxmimum_NR()
    {
        if (size == 0) {
            throw new IllegalArgumentException("BST is empty");
        }
        Node node = root;
        while(node.right != null)
        {
            node = node.right;
        }
        return node;
    }
    //------------------------------------get minimum/maximum ends---------------------------------------------------------------

    //------------------------------------remove minimum/maximum starts---------------------------------------------------------------

    public Element removeMinimum()
    {
        Element ret = minimum();
//        wrong:
//        Node res = removeMinimum(root);
        root = removeMinimum(root);
        return ret;
    }

    //method 1:
//    private Node removeMinimum(Node node)
//    {
//        if(node.left == null)
//        {
//            if(node.right == null)
//            {
//                size--;
//                return null;
//            }
//            else
//            {
//                size--;
//                return node.right;
//            }
//        }
//        else
//        {
//            node.left = removeMinimum(node.left);
//        }
//        return node;
//    }


//    //method 2:
//
//    private Node removeMinimum(Node node)
//    {
//        if(node.left == null)
//        {
//            Node del = node.right;
//            node.right = null;
//            size--;
//            return del;
//        }
//        else
//        {
//            node.left = removeMinimum(node.left);
//        }
//        return node;
//    }

    //method 3:
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

    public Element removeMaximum()
    {
        Element ret = maximum();
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

    //------------------------------------remove arbitrary starts---------------------------------------------------------------
    public void remove(Element e)
    {
        root = remove(root, e);
    }

    private Node remove(Node node, Element e)
    {
        if(node == null)
        {
            throw new IllegalArgumentException("Remove failed. Do not find the element");
        }

        if(e.compareTo(node.e) == 0)
        {
            if(node.left == null)
            {
                Node ret = node.right;
                node.right = null;
                size--;
                return ret;
            }
            else if(node.right == null)
            {
                Node ret = node.left;
                node.left = null;
                size--;
                return ret;
            }
            else//相当于node.left != null && node.right != null
            {
                //method 1:
                node.e = removeMinimum(node.right).e;//这里不需要额外的size--;
                return node;

//                method 2:
//                Node successor = removeMinimum(node.right);
//                successor.right = node.right;
//                successor.left = node.left;
//                node.right = node.left = null;
//                return successor;
//                这里不需要额外的size--;

//                method 3:
//                Node predecessor = removeMaximum(node.left);
//                predecessor.right = node.right;
//                predecessor.left = node.left;
//                node.right = node.left = null;
//                return predecessor;


            }


        }
        else if (e.compareTo(node.e) < 0)
        {
            node.left = remove(node.left, e);
        }
        else
        {
            node.right = remove(node.right, e);
        }
        return node;
    }

    private Node remove_old(Node node, Element e)
    {
        if(node == null)
        {
            throw new IllegalArgumentException("Remove failed. Do not find the element");
        }

        if(e.compareTo(node.e) == 0)
        {
            if(node.left == null && node.right != null)
            {
                Node ret = node.right;
                node.right = null;
                return ret;
            }
            else if(node.left != null && node.right == null)
            {
                Node ret = node.left;
                node.left = null;
                return ret;
            }
            else if(node.left != null)//相当于node.left != null && node.right != null
            {
                //method 1:
                node.e = removeMinimum(node.right).e;//这里不需要额外的size--;
                return node;

//                method 2:
//                Node successor = removeMinimum(node.right);
//                successor.right = node.right;
//                successor.left = node.left;
//                node.right = node.left = null;
//                return successor;
//                这里不需要额外的size--;

//                method 3:
//                Node predecessor = removeMaximum(node.left);
//                predecessor.right = node.right;
//                predecessor.left = node.left;
//                node.right = node.left = null;
//                return predecessor;

            }
            else
            {
                return null;
            }

        }
        else if (e.compareTo(node.e) < 0)
        {
            node.left = remove(node.left, e);
        }
        else
        {
            node.right = remove(node.right, e);
        }
        return node;
    }

    // 从二分搜索树中删除元素为e的节点
    public void remove_teacher(Element e){
        root = remove_teacher(root, e);
    }

    // 删除掉以node为根的二分搜索树中值为e的节点, 递归算法
    // 返回删除节点后新的二分搜索树的根
    private Node remove_teacher(Node node, Element e){

        if( node == null )
            return null;

        if( e.compareTo(node.e) < 0 ){
            node.left = remove_teacher(node.left , e);
            return node;
        }
        else if(e.compareTo(node.e) > 0 ){
            node.right = remove_teacher(node.right, e);
            return node;
        }
        else{   // e.compareTo(node.e) == 0

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
            successor.right = removeMinimum(node.right);
            successor.left = node.left;

            node.left = node.right = null;

            return successor;
        }
    }

    //------------------------------------remove arbitrary ends---------------------------------------------------------------

}
