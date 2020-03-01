import java.util.TreeMap;

public class HashTable<K extends Comparable<K>, V>
{
    private TreeMap<K, V>[] hashTable;
    private int M;
    private int size;

    public HashTable(int capacity)
    {
        M = capacity;
        hashTable = new TreeMap[M];
        for(int i = 0; i < M ; i++ )
        {
            hashTable[i] = new TreeMap<>();
        }
        size = 0;
    }

    public HashTable()
    {
        this(97);
    }

    //--------------- 辅助函数 ----------------
    private int hash(K key)
    {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    //--------------- 基本函数 ----------------
    public int getSize()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    //--------------- 增删改查 ----------------
    public void add(K key, V value)
    {
        TreeMap<K, V> map = hashTable[hash(key)];//todo Notes: 不能少了<K, V> 在TreeMap<K, V>中，否则会报错。
        if(map.containsKey(key))
        {
            map.put(key, value);
        }
        else
        {
            map.put(key, value);
            size++;
        }
    }

    public V remove(K key)
    {
        V ret = null;
        TreeMap<K, V> map = hashTable[hash(key)];//todo Notes: 不能少了<K, V> 在TreeMap<K, V>中，否则会报错。
        if(map.containsKey(key))
        {
            ret = map.remove(key);
            size--;
        }
        return ret;
    }

    public void set(K key, V newVal)
    {
        TreeMap<K, V> map = hashTable[hash(key)];//todo Notes: 不能少了<K, V> 在TreeMap<K, V>中，否则会报错。
        if(map.containsKey(key))
            map.put(key, newVal);
        else
            throw new IllegalArgumentException("Key" + key + " doesn't exist.");
    }

    public V get(K key)
    {
        TreeMap<K, V> map = hashTable[hash(key)];//todo Notes: 不能少了<K, V> 在TreeMap<K, V>中，否则会报错。
        return map.containsKey(key) ? map.get(key) : null;
    }

    public boolean contains(K key)
    {
        return hashTable[hash(key)].containsKey(key);
    }
}
