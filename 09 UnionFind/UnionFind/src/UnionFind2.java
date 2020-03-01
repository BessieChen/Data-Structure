//记录parent
public class UnionFind2 implements UF
{
    private int[] parent;

    public UnionFind2(int size)
    {
        parent = new int[size];
        for(int i = 0; i < parent.length; i++)
        {
            parent[i] = i;
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
        if(isConnected(p,q))
        {
            return;
        }
        parent[find_parent(p)] = find_parent(q);
    }


}
