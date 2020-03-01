import java.util.TreeSet;

//基于size的优化
public class UnionFind3 implements UF //注意，里面的装的，不是Element！
{
    private int[] parent;//索引值 <-> 第几个元素， 数组内容 <-> 该元素的id //注意不是private Element[] parent;
    private int[] unionSize;//记录某个元素所属集合的祖先的大小。

    public UnionFind3(int size)
    {
        parent = new int[size];
        unionSize = new int[size];

        for(int i = 0; i < size; i++)
        {
            parent[i] = i;
            unionSize[i] = 1;
        }
    }

    public UnionFind3()
    {
        this(10);
    }

    private int findID(int i) //这个就是主要区别：i的parent 和 i的ID 是两码事。parent是父亲，ID是祖先。
    {
        if(i < 0 || i >= parent.length)
            throw new IllegalArgumentException("p is out of bound.");

        while(i != parent[i])
        {
            i = parent[i];
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

    //注意：尽可能的将调用相同函数的部分提取出来，否则消耗性能，例如下面的code一直在调用findID(a), findID(b)
    //错误示范：
    /*@Override
    public void unionElements(int a, int b)
    {
        if(!isConnected(a,b))
        {
            if(unionSize[findID(a)] <= unionSize[findID(b)]) //没有等于号也可以。这里是a所属集合更小，所以a臣服于b
            {
                parent[findID(a)] = findID(b);
                unionSize[findID(b)] += unionSize[findID(a)];
            }
            else
            {
                parent[findID(b)] = findID(a);
                unionSize[findID(a)] += unionSize[findID(b)];
            }
        }
    }*/

    public void unionElements(int a, int b)
    {
        if(!isConnected(a,b))
        {
            int aID = findID(a);
            int bID = findID(b);
            if(unionSize[aID] <= unionSize[bID]) //没有等于号也可以。这里是a所属集合更小，所以a臣服于b
            {
                parent[aID] = bID;
                unionSize[bID] += unionSize[aID];
            }
            else
            {
                parent[bID] = aID;
                unionSize[aID] += unionSize[bID];
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


//以下来自老师：
/*
// 我们的第三版Union-Find
public class UnionFind3 implements UF{

    private int[] parent; // parent[i]表示第一个元素所指向的父节点
    private int[] sz;     // sz[i]表示以i为根的集合中元素个数

    // 构造函数
    public UnionFind3(int size){

        parent = new int[size];
        sz = new int[size];

        // 初始化, 每一个parent[i]指向自己, 表示每一个元素自己自成一个集合
        for(int i = 0 ; i < size ; i ++){
            parent[i] = i;
            sz[i] = 1;
        }
    }

    @Override
    public int getSize(){
        return parent.length;
    }

    // 查找过程, 查找元素p所对应的集合编号
    // O(h)复杂度, h为树的高度
    private int find(int p){
        if(p < 0 || p >= parent.length)
            throw new IllegalArgumentException("p is out of bound.");

        // 不断去查询自己的父亲节点, 直到到达根节点
        // 根节点的特点: parent[p] == p
        while( p != parent[p] )
            p = parent[p];
        return p;
    }

    // 查看元素p和元素q是否所属一个集合
    // O(h)复杂度, h为树的高度
    @Override
    public boolean isConnected( int p , int q ){
        return find(p) == find(q);
    }

    // 合并元素p和元素q所属的集合
    // O(h)复杂度, h为树的高度
    @Override
    public void unionElements(int p, int q){

        int pRoot = find(p);
        int qRoot = find(q);

        if(pRoot == qRoot)
            return;

        // 根据两个元素所在树的元素个数不同判断合并方向
        // 将元素个数少的集合合并到元素个数多的集合上
        if(sz[pRoot] < sz[qRoot]){
            parent[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        }
        else{ // sz[qRoot] <= sz[pRoot]
            parent[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        }
    }
}*/
