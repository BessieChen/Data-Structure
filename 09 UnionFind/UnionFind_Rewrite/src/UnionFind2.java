import java.util.TreeSet;

//记录parent
public class UnionFind2 implements UF //注意，里面的装的，不是Element！
{
    private int[] parent;//索引值 <-> 第几个元素， 数组内容 <-> 该元素的id //注意不是private Element[] parent;

    public UnionFind2(int size)
    {
        parent = new int[size];

        for(int i = 0; i < size; i++)
        {
            parent[i] = i;
        }
    }

    public UnionFind2()
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

    @Override
    public void unionElements(int a, int b)
    {
        if(!isConnected(a,b))
        {
            parent[findID(a)] = findID(b); //a的祖先 的父亲 是 b的祖先。
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
// 我们的第二版Union-Find
public class UnionFind2 implements UF {

    // 我们的第二版Union-Find, 使用一个数组构建一棵指向父节点的树
    // parent[i]表示第一个元素所指向的父节点
    private int[] parent;

    // 构造函数
    public UnionFind2(int size){

        parent = new int[size];

        // 初始化, 每一个parent[i]指向自己, 表示每一个元素自己自成一个集合
        for( int i = 0 ; i < size ; i ++ )
            parent[i] = i;
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
        while(p != parent[p])
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

        if( pRoot == qRoot )
            return;

        parent[pRoot] = qRoot;
    }
}*/
