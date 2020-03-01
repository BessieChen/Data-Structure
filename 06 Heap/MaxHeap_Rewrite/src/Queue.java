public interface Queue<Element>
{
    public boolean isEmpty();
    public int getSize();

    public void enqueue(Element e);
    public Element dequeue();//取出并删除优先级最高的节点
    public Element getFront();//取出优先级最高的节点
}
