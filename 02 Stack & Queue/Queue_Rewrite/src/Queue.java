public interface Queue<Element>
{
    void enqueue(Element e);
    Element dequeue();
    Element getFront();
    boolean isEmpty();
    int getSize();
}
