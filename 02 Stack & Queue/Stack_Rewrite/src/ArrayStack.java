public class ArrayStack<Element> implements Stack<Element>
{
    /*
    * 目录：
    * 1. 5个从Stack重载的函数
    * 2. 重载toString(),但是toString()是祖先类的函数
    * 3. 在设置ArrayStack的toString()时候，你一般来说，都是从index == 0开始，往后打印array的值，
    *    所以，我们先打印出来的是 Stack的bottom，最后打印出来的才是top
    * */

    private Array<Element> array;
    //private int size; //这一个是不需要的，我直接用array对象自带的getSize()就好。

    public ArrayStack()
    {
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
    public boolean isEmpty()
    {
        return array.isEmpty();
    }

    @Override
    public int getSize()
    {
        return array.getSize();
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();

        //method 1: 自己重新写toString()
        res.append(String.format("Array Stack size: %d | bottom [ ", array.getSize()));

        for(int i = 0; i < array.getSize(); i++)
        {
            res.append(array.get(i));
            if(i != array.getSize()-1)
            {
                res.append(", ");
            }
        }
        res.append(" ] top");

        //method 2: 利用array对象自己的toString(), 已经包含了关于size的信息。
//        res.append("Array Stack: ");
//        res.append(array);
//        res.append(" top");
        return res.toString();
    }

}
