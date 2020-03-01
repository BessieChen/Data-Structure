public class AVLTreeSet<E extends Comparable<E>> implements Set<E>
{
    //todo Note：错误 private AVLTree_KV<K, V>
    private AVLTree_KV<E, Object> avl;

    public AVLTreeSet()
    {
        avl = new AVLTree_KV<>();
    }

    @Override
    public void add(E e)
    {
        avl.add(e, null);
    }

    @Override
    public void remove(E e)
    {
        avl.remove(e);
    }

    @Override
    public boolean contains(E e)
    {
        return avl.contains(e);
    }

    @Override
    public int getSize()
    {
        return avl.getSize();
    }

    @Override
    public boolean isEmpty()
    {
        return avl.isEmpty();
    }

}
