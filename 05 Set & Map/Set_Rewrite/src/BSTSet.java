public class BSTSet<Element extends Comparable<Element>> implements Set<Element>
//todo 不能写成public class BSTSet<Element> implements Set<Element>， 即少了extends Comparable<Element>，否则下面的成员变量private BST<Element> bst;会报错。因为BST要求Element是可以compare的
{
    private BST<Element> bst;

    public BSTSet()
    {
        bst = new BST<>();
    }

    @Override
    public void add(Element e)
    {
        bst.add(e);
    }

    @Override
    public void remove(Element e)
    {
        bst.remove(e);
    }

    @Override
    public boolean contains(Element e)
    {
        return bst.contains(e);
    }

    @Override
    public boolean isEmpty()
    {
        return bst.isEmpty();
    }

    @Override
    public int getSize()
    {
        return bst.getSize();
    }

}
