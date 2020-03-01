public interface Stack<Element>
{
    //注意，两个基本函数 isEmpty(), getSize()
    //附加：增删查
    //因为只能入栈和出栈，所以没有改这个函数

    public void push(Element e);
    public Element pop();
    public Element peek();
    public boolean isEmpty();
    public int getSize();
}
