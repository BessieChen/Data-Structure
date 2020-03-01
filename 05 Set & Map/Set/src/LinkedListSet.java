public class LinkedListSet<Element extends Comparable<Element>> implements Set<Element>{

    private LinkedList_dummyhead<Element> list;

    public LinkedListSet()
    {
        list = new LinkedList_dummyhead<>();
    }

    @Override
    public int getSize()
    {
        return list.getSize();
    }

    @Override
    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    @Override
    public void add(Element e)
    {
        if(!list.contains(e))
        {
            list.addFirst(e);
        }
    }

    @Override
    public void remove(Element e)
    {
        list.removeElement(e);
    }

    @Override
    public boolean contains(Element e)
    {
        return list.contains(e);
    }
}
