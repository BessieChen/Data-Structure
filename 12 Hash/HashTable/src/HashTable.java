import java.util.TreeMap;

public class HashTable<K, V>
{
    private TreeMap<K, V>[] hashtable;//HashTable是一个TreeMap数组
    private int size;
    private int M;

    public HashTable(int M)
    {
        size = 0;
        this.M = M;
        hashtable = new TreeMap[M];//注意，不能写成new TreeMap<K, V>[M];
        for(int i = 0 ; i < M; i++)
        {
            hashtable[i] = new TreeMap();//new TreeMap<K, V>();
        }
    }

    public HashTable()
    {
        this(97);
    }

    private int hash(K key)
    {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public int getSize()
    {
        return size;
    }

    public void add(K key, V value)
    {
        TreeMap<K, V> map = hashtable[hash(key)];
        if(map.containsKey(key))
        {
            map.put(key,value);
        }
        else
        {
            map.put(key,value);
            size++;
        }
    }

    public V remove(K key)
    {
        TreeMap<K, V> map = hashtable[hash(key)];
        V ret = null;
        if(map.containsKey(key))
        {
            ret = map.remove(key);
            size--;
        }
        return ret;
    }

    public void set(K key, V value)
    {
        TreeMap<K, V> map = hashtable[hash(key)];
        if(!map.containsKey(key))
            throw new IllegalArgumentException(key + " doesn't exist!");
        map.put(key, value);
    }

    public boolean contains(K key)
    {
        return hashtable[hash(key)].containsKey(key);//一个是假设如果有key，这个key会在哪个index，然后真的去找是否存在这个key在这个index中
    }

    public V get(K key)
    {
        V ret = null;
        TreeMap<K, V> map = hashtable[hash(key)];
        if(map.containsKey(key))
        {
            ret = map.get(key);
        }
        return ret;
    }
}
