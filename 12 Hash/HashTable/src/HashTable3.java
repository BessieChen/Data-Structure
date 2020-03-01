import java.util.OptionalLong;
import java.util.TreeMap;

public class HashTable3<K, V>
{
    private final int[] capacity
            = {53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593,
            49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469,
            12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};

    private static final int upperTol = 10;
    private static final int lowerTol = 2;
    private int capIndex = 0;
    private TreeMap<K, V>[] hashtable;//HashTable是一个TreeMap数组
    private int size;
    private int M;

    public HashTable3()//不再由用户定义M
    {
        size = 0;
        this.M = capacity[capIndex];
        hashtable = new TreeMap[M];//注意，不能写成new TreeMap<K, V>[M];
        for(int i = 0 ; i < M; i++)
        {
            hashtable[i] = new TreeMap();//new TreeMap<K, V>();
        }
    }

    //下面这种写法会报错：
//    public HashTable3()
//    {
//        this(capacity[capIndex]);
//    }

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
        if(size >= upperTol * M)//相当于size/M >= upperTol, 为了防止int相除会约去小数点
        {
            resize(capacity[++capIndex]);
        }
    }

    private void resize(int newCap)
    {
        TreeMap<K, V>[] newHashTable = new TreeMap[newCap];
        for(int i = 0 ; i < newCap ; i ++)
            newHashTable[i] = new TreeMap<>();

        int OldM = M;
        this.M = newCap;
        for(int n = 0; n < OldM; n++)//index of old HashTable
        {
            for(K key : hashtable[n].keySet())
            {
                V val = hashtable[n].get(key);
                TreeMap<K, V> map = newHashTable[hash(key)];
                map.put(key, val);
            }
        }
        this.hashtable = newHashTable;
    }

    private void resize_teacher(int newM){
        TreeMap<K, V>[] newHashTable = new TreeMap[newM];
        for(int i = 0 ; i < newM ; i ++)
            newHashTable[i] = new TreeMap<>();

        int oldM = M;
        this.M = newM;

        for(int i = 0 ; i < oldM ; i ++){
            TreeMap<K, V> map = hashtable[i];
            for(K key: map.keySet())
                newHashTable[hash(key)].put(key, map.get(key));
        }

        this.hashtable = newHashTable;
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
        if(size <= lowerTol * M && M/2 > capacity[0])
        {
            resize(capacity[--capIndex]);
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
