public interface Map<K, V> {

    void add(K key, V value);
    V remove(K key);
    void set(K key, V newValue);
    boolean contains(K key);
    V get(K key);
    int getSize();
    boolean isEmpty();
}
