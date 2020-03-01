//基于rank的优化
public class UnionFind4 implements UF
{
    private int[] parent;
    private int[] rank;//当前某棵树的拥有元素的多少

    public UnionFind4(int size)
    {
        parent = new int[size];
        rank = new int[size];
        for(int i = 0; i < parent.length; i++)
        {
            parent[i] = i;
            rank[i] = 1;
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

        if(rank[p_parent] <= rank[q]-1)//深度低的，屈服于深度高的。
        {
            parent[p] = q_parent;
            //我之前疑惑说：
            //那小树的其他元素的rank难道不要变成大树更新后的rank吗？
            //事实是不需要的，因为我们之后不管怎么比，比的都是小树的新parent，也就是大树的本身的parent。
            //所以小树的旧parent的rank其实是根本用不到的。
            //还有，我们在函数最开始声明的int p_parent = find_parent(p);
            //是要提前记录旧parent，因为我们要把旧parent的rank加到小树新parent也就是大树本来的parent的rank上。
        }
        else if(rank[p_parent] >= rank[q_parent]+1)
        {
            parent[q] = p_parent;
        }
        else
        {
            parent[q] = p_parent;
            rank[p_parent] += 1;//大树的rank+1,小树rank不用变
        }

    }

    public void unionElements_teacher(int p, int q){

        int pRoot = find_parent(p);
        int qRoot = find_parent(q);

        if( pRoot == qRoot )
            return;

        // 根据两个元素所在树的rank不同判断合并方向
        // 将rank低的集合合并到rank高的集合上
        if(rank[pRoot] < rank[qRoot])
            parent[pRoot] = qRoot;
        else if(rank[qRoot] < rank[pRoot])
            parent[qRoot] = pRoot;
        else{ // rank[pRoot] == rank[qRoot]
            parent[pRoot] = qRoot;
            rank[qRoot] += 1;   // 此时, 我维护rank的值
        }
    }


}
