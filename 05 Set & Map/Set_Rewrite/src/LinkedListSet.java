public class LinkedListSet<Element> implements Set<Element>
{
    private LinkedList_recursive_dummyhead<Element> linkedList;

    public LinkedListSet()
    {
        linkedList = new LinkedList_recursive_dummyhead<>();
    }

    @Override
    public void add(Element e)
    {
        linkedList.addFirst(e);
    }

    @Override
    public void remove(Element e)
    {
        linkedList.removeElement(e);
    }

    @Override
    public boolean contains(Element e)
    {
        return linkedList.contains(e);
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
}
