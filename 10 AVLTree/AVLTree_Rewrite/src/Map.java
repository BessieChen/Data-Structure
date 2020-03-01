public interface Map<K, V>
{
    void add(K key, V value);
    void remove(K key);
    void set(K key, V newValue);
    boolean contains(K key);
    V get(K key);
    boolean isEmpty();
    int getSize();
}

