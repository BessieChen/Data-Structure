public class AVLTreeMap<K extends Comparable<K>, V> implements Map<K, V>
{
    private AVLTree_KV<K, V> avl;//todo Note:这个<K, V>不能少。

    public AVLTreeMap()
    {
        avl = new AVLTree_KV();
    }

    @Override
    public void add(K key, V val)
    {
        avl.add(key, val);
    }

    @Override
    public void remove(K key)
    {
        avl.remove(key);
    }

    @Override
    public void set(K key, V newVal)
    {
        avl.set(key, newVal);
    }

    @Override
    public V get(K key)
    {
        return avl.get(key);
    }

    @Override
    public boolean contains(K key)
    {
        return avl.contains(key);
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
