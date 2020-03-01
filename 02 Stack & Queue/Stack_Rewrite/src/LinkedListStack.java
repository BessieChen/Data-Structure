public class LinkedListStack<Element> implements Stack<Element>
{
    /*
     * 目录：
     * 1. 5个从Stack重载的函数
     * 2. 重载toString(),但是toString()是祖先类的函数
     * 3. 在设置LinkedListStack的toString()时候，你一般来说，因为之前一直是用addFirst，
     *    所以栈顶的东西，是LinkedList的头结点。如果我们从头结点开始打印，那就是先打印top
     *    这个要区别与ArrayStack的先打印出bottom
     * */

    private LinkedList_dummyhead<Element> linkedList;

    public LinkedListStack()
    {
        linkedList = new LinkedList_dummyhead<>();
    }

    @Override
    public void push(Element e)
    {
        linkedList.addFirst(e);
    }

    @Override
    public Element pop()
    {
        return linkedList.removeFirst();
    }

    @Override
    public Element peek()
    {
        return linkedList.getFirst();
    }

    @Override
    public boolean isEmpty()
    {
        return linkedList.isEmpty();
    }

    @Override
    public int getSize()
    {
        return linkedList.getSize();
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();

        //method 1: 自己重新写toString()
        res.append(String.format("LinkedList Stack size: %d | top [ ", linkedList.getSize()));

        for(int i = 0; i < linkedList.getSize(); i++)
        {
            res.append(linkedList.get(i));
            if(i != linkedList.getSize()-1)
            {
                res.append(", ");
            }
        }
        res.append(" ] bottom");

        //method 2: 利用linkedlist对象自己的toString(), 已经包含了关于size的信息。
//        res.append(" top ");
//        res.append(linkedList);
//        res.append(" bottom ");
        return res.toString();
    }
}
