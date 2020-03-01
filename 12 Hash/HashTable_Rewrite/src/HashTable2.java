import java.util.TreeMap;
//现在HashTable如果达到了lowerTol, upperTol，就会动态缩容/扩容。

public class HashTable2<K extends Comparable<K>, V>
{
    private static final int upperTol = 10;
    private static final int lowerTol = 2;
    private static final int initCap = 97;

    private TreeMap<K, V>[] hashTable;
    private int M;
    private int size;

    public HashTable2(int capacity)
    {
        M = capacity;
        hashTable = new TreeMap[M];
        for(int i = 0; i < M ; i++ )
        {
            hashTable[i] = new TreeMap<>();
        }
        size = 0;
    }

    public HashTable2()
    {
        this(initCap);
    }

    //--------------- 辅助函数 ----------------
    private int hash(K key)
    {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    //todo Notes: 关于resize()的使用时机：
    /*
    1.add() 只可能会让size--, 所以考虑需不需要扩容。 扩容可以在 添加新元素之后，也可以在添加新元素之前。这里我们习惯添加之后。
    2.remove() 只可能让size--，所以只需要考虑要不要缩容。缩容可以 在删除元素之后，也可以在之前。这里我们习惯删除之后。

    复习：对于array的缩容扩容：
    add() --先扩容，后添加新元素，否则空间不够
    remove() --先删除元素，后缩容，否则先缩容会导致原先的array在删除之前不够装。

    */
    private void resize(int newM)
    {
        TreeMap<K, V>[] newHashTable = new TreeMap[newM];
        for(int i = 0; i < newM ; i++)
        {
            newHashTable[i] = new TreeMap<>();
        }
        //接下来就是将hashTable中每一个TreeMap里面的所有key value都遍历一遍：

        int oldM = M;
        M = newM;//为了hash()函数里面计算 % M 是使用的最新的M
        for(int i = 0; i < oldM; i++)
        {
            TreeMap<K, V> map = hashTable[i];
            for(K key : map.keySet())
            {
                newHashTable[hash(key)].put(key, map.get(key));
            }
        }
        hashTable = newHashTable;//可以这样直接将hashtable赋值，并且size不需要改变。
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
        if(size >= M * upperTol)//相当于size/M >= upperTol, 为了防止int相除会约去小数点
        {
            resize(2 * M);
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
        if( size <= M * lowerTol && M/2 >= initCap)//相当于size/M >= upperTol, 为了防止int相除会约去小数点
        {
            resize(M / 2);
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
