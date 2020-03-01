//基于size的优化
public class UnionFind3 implements UF
{
    private int[] parent;
    private int[] set_size;//当前某棵树的拥有元素的多少

    public UnionFind3(int size)
    {
        parent = new int[size];
        set_size = new int[size];
        for(int i = 0; i < parent.length; i++)
        {
            parent[i] = i;
            set_size[i] = 1;
        }
    }

    @Override
    public int getSize()
    {
        return parent.length;
    }

    // O(h)复杂度, h为树的高度
    private int find_parent(int i)
    {
        if(i < 0 || i > parent.length)
        {
            throw new IllegalArgumentException("Index Illegal.");
        }
        while(parent[i] != i)
        {
            i = parent[i];
        }
        return i;
    }

    // O(h)复杂度, h为树的高度
    @Override
    public boolean isConnected(int i, int j)
    {
        return find_parent(i)==find_parent(j);
    }

    // O(h)复杂度, h为树的高度
    @Override
    public void unionElements(int p, int q)
    {
        //这里不需要判断p和q的合法性，因为之前的isConnected中的find_parent已经判断了。
        int p_parent = find_parent(p);
        int q_parent = find_parent(q);

        if(p_parent == q_parent)
        {
            return;
        }

        if(set_size[p_parent] <= set_size[q_parent])
        {
            parent[p_parent] = q_parent;
            set_size[q_parent] += set_size[p_parent];//大树的size要加上小树的size
            //我之前疑惑说：
            //那小树的其他元素的size难道不要变成大树更新后的size吗？
            //事实是不需要的，因为我们之后不管怎么比，比的都是小树的新parent，也就是大树的本身的parent。
            //所以小树的旧parent的set_size其实是根本用不到的。
            //还有，我们在函数最开始声明的int p_parent = find_parent(p);
            //是要提前记录旧parent，因为我们要把旧parent的set_size加到小树新parent也就是大树本来的parent的set_size上。
        }
        else
        {
            parent[q_parent] = p_parent;
            set_size[p_parent] += set_size[q_parent];//大树的size要加上小树的size
        }

    }

    public void unionElements_teacher(int p, int q){

        int pRoot = find_parent(p);
        int qRoot = find_parent(q);

        if(pRoot == qRoot)
            return;

        // 根据两个元素所在树的元素个数不同判断合并方向
        // 将元素个数少的集合合并到元素个数多的集合上
        if(set_size[pRoot] < set_size[qRoot]){
            parent[pRoot] = qRoot;
            set_size[qRoot] += set_size[pRoot];
        }
        else{ // set_size[qRoot] <= set_size[pRoot]
            parent[qRoot] = pRoot;
            set_size[pRoot] += set_size[qRoot];
        }
    }


}
