import java.util.TreeSet;

//这是第五版，旨在让findID()能快一点。方法：将路径压缩，只需加一行代码。
public class UnionFind5 implements UF //注意，里面的装的，不是Element！
{
    private int[] parent;//索引值 <-> 第几个元素， 数组内容 <-> 该元素的id //注意不是private Element[] parent;
    private int[] rank;//记录某个元素所属集合的祖先的rank。

    public UnionFind5(int size)
    {
        parent = new int[size];
        rank = new int[size];

        for(int i = 0; i < size; i++)
        {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    public UnionFind5()
    {
        this(10);
    }

    private int findID(int i) //这个就是主要区别：i的parent 和 i的ID 是两码事。parent是父亲，ID是祖先。
    {
        if(i < 0 || i >= parent.length)
            throw new IllegalArgumentException("p is out of bound.");

        while(i != parent[i])
        {
            parent[i] = parent[parent[i]];//压缩了一点，就是将i指向父亲，变为i指向爷爷
            i = parent[i];//然后i再跳向爷爷。经过while循环，进一步将爷爷指向爷爷的父亲，变为爷爷指向爷爷的爷爷。
        }
        return i;
    }

    @Override
    public int getSize()
    {
        return parent.length;
    }

    @Override
    public boolean isConnected(int a, int b)
    {
        return findID(a) == findID(b);
    }

    @Override
    public void unionElements(int a, int b)
    {
        if(!isConnected(a,b))
        {
            int aID = findID(a);
            int bID = findID(b);
            if(rank[aID] < rank[bID]) //没有等于号也可以。这里是a所属集合更短，所以a臣服于b
            {
                parent[aID] = bID;//注意因为rank[findID(a)]更短，所以就算加到了b的祖先下，b所属集合的高度也不会变。所以不处理rank
            }
            else if(rank[aID] > rank[findID(b)])
            {
                parent[bID] = aID;
            }
            else//rank[findID(a)] == rank[findID(b)]
            {
                parent[bID] = aID;
                rank[aID] += 1; //当两个集合高度相等，则需要将被加的rank + 1
            }
        }
    }

    public int countUnion()//计算有多少个集合。
    {
        TreeSet<Integer> set = new TreeSet<>();
        for(int i = 0; i < parent.length; i++)
        {
            set.add(findID(i));
        }
        return set.size();
    }
}

//todo 类似：最大堆：完全二叉， 线段树：平衡树， 并查集