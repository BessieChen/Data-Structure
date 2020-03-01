public class ArrayStack<Element> implements Stack<Element> {
    private Array<Element> array; //因为是由Array这个class来实现的Stack，所以构造一个Array类的对象
    //private int size;

    public ArrayStack(int capacity)
    {
        array = new Array<>(capacity);
        //size = 0; Byb 大错误！这里不可以声明ArrayStack的size！我们的size是通过array里面的array.getSize()来实现的！我们ArrayStack类是不会加工size的！
    }

    public ArrayStack()
    {
        //this(10);这么写也可以
        array = new Array<>();
    }

    @Override
    public void push(Element e)
    {
        array.addLast(e);
    }

    @Override
    public Element pop()
    {
        return array.removeLast();
    }

    @Override
    public Element peek()
    {
        return array.getLast();
    }

    @Override
    public int getSize()
    {
        return array.getSize();
    }

    @Override
    public boolean isEmpty()
    {
        return array.isEmpty();
    }

    //Byb 注意Interface Stack中没有这个getCapacity。原因是，只有在我们用动态数组实现stack的时候，可能会存在我们想知道capacity是多大。毕竟capacity也可能不等于size
    //所以我们需要一个函数知道capacity是多少，但是我们的抽象的接口，不需要知道我们是array实现的，从而也不需要我们的capacity
    public int getCapacity()
    {
        return array.getCapacity();
    }

    @Override//这里override的是父类Object里面的方法：toString()
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        res.append("Stack: [");
        for(int i = 0; i < array.getSize(); i++)
        {
            res.append(array.get(i));
            if(i!=array.getSize()-1)
            {
                res.append(',');
            }
        }
        res.append("] top");
        return res.toString();
    }

}
